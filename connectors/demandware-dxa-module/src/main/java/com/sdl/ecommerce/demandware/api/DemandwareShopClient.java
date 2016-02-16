package com.sdl.ecommerce.demandware.api;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.demandware.api.model.*;
import com.sun.jersey.api.client.*;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.*;
import javax.ws.rs.core.MediaType;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

/**
 * Demandware Shop Client v.16.1
 *
 * @author nic
 */
@Component
public class DemandwareShopClient {

    static private Log log = LogFactory.getLog(DemandwareShopClient.class);

    static final String BASE_URL_PATH = "/dw/shop/v16_1";

    @Value("${demandware.shopUrl}")
    private String shopUrl;

    @Value("${demandware.clientId}")
    private String clientId;

    @Value("${demandware.overriddenOrigin}")
    private String overriddenOrigin = null;

    @Value("${demandware.trustAllSSLCerts}")
    private boolean trustAllSSLCerts = false;

    private String shopBaseUrl;
    private Client client;
    private WebResource authorizationResource;
    private WebResource basketResource;
    private WebResource productResource;
    private WebResource productSearchResource;
    private WebResource categoryResource;

    class Builder {

        WebResource.Builder requestBuilder;

        Builder(WebResource.Builder requestBuilder) {
            this.requestBuilder = requestBuilder;
        }

        WebResource.Builder getRequestBuilder() {
            return this.requestBuilder;
        }

        Builder jsonHeaders() {
            this.requestBuilder = requestBuilder.
                    accept(MediaType.APPLICATION_JSON).
                    type(MediaType.APPLICATION_JSON);
            return this;
        }

        Builder originHeader() {
            if ( overriddenOrigin != null && overriddenOrigin.length() > 0 ) {
                this.requestBuilder = requestBuilder.header("Origin", overriddenOrigin);
            }
            return this;
        }

        Builder basketHeaders(Basket basket) {
            this.requestBuilder = requestBuilder.
                    header("Authorization", basket.getAuthorizationToken()).
                    header("If-Match", basket.getEtag());
            return this;
        }
    }


    public DemandwareShopClient() {}

    // For standalone use
    //
    public DemandwareShopClient(String shopUrl, String clientId) {
        this.shopUrl = shopUrl;
        this.clientId = clientId;
        setup();
    }

    @PostConstruct
    public void setup() {
        this.shopBaseUrl = shopUrl + BASE_URL_PATH;

        ApacheHttpClientConfig clientConfig = new DefaultApacheHttpClientConfig();
        clientConfig.getProperties().put(ApacheHttpClientConfig.PROPERTY_HANDLE_COOKIES, true);

        this.client = ApacheHttpClient.create(clientConfig);
        this.authorizationResource = this.client.resource(this.shopBaseUrl.replace("http", "https") + "/customers/auth").queryParam("client_id", this.clientId);
        this.basketResource = this.client.resource(this.shopBaseUrl.replace("http", "https") + "/baskets").queryParam("client_id", this.clientId);
        this.productResource = this.client.resource(this.shopBaseUrl + "/products").queryParam("client_id", this.clientId);
        this.productSearchResource = this.client.resource(this.shopBaseUrl + "/product_search").queryParam("client_id", this.clientId);
        this.categoryResource = this.client.resource(this.shopBaseUrl + "/categories").queryParam("client_id", this.clientId);

        if ( this.trustAllSSLCerts ) {
            // SSL configuration
            clientConfig.getProperties().put(com.sun.jersey.client.urlconnection.HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new com.sun.jersey.client.urlconnection.HTTPSProperties(getHostnameVerifier(), getNonValidatingSecurityContext()));
        }
    }

    // do NOT use in Production
    private static SSLContext getNonValidatingSecurityContext() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                // We don't check anything :P
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                // We don't check anything :P
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            return sc;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    // do NOT use in Production
    private static HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            public boolean verify(String string, SSLSession ssls) {
                return true;
            }
        };
    }

    private String authorizeAsGuest() {
        ClientResponse response =
                new Builder(this.authorizationResource.getRequestBuilder()).
                jsonHeaders().
                originHeader().getRequestBuilder().
                post(ClientResponse.class, new AuthenticationRequest(AuthenticationRequest.GUEST));
        return response.getHeaders().getFirst("Authorization");
    }

    private void throwException(Fault fault) throws ECommerceException  {
        throw new ECommerceException(fault.getType() + ": " + fault.getMessage());
    }

    private Basket getBasket(ClientResponse clientResponse, String authToken) throws ECommerceException {

        Basket basket = clientResponse.getEntity(Basket.class);
        if ( basket.getFault() != null ) {
            this.throwException(basket.getFault());
        }
        basket.setAuthorizationToken(authToken);
        basket.setEtag(clientResponse.getHeaders().getFirst("ETag"));
        return basket;
    }


    public Basket createBasket() throws ECommerceException {
        // TODO: Support other authorization patterns here as well
        //
        String authToken = this.authorizeAsGuest();
        ClientResponse response =
                new Builder(this.basketResource.getRequestBuilder()).
                jsonHeaders().
                originHeader().getRequestBuilder().
                header("Authorization", authToken).
                post(ClientResponse.class, new Basket());
        return getBasket(response, authToken);
    }

    public Basket addProductToBasket(Basket basket, String productId, int quantity) throws ECommerceException {

        // Check if the product is already added to the cart -> if so modify the cart item
        //
        if ( basket.getProduct_items() != null ) {
            for (ProductItem productItem : basket.getProduct_items()) {
                if ( productItem.getProduct_id().equals(productId) ) {
                    productItem.setQuantity(productItem.getQuantity()+quantity);
                    return this.modifyProductItemInBasket(basket, productItem);
                }
            }
        }

        // Create a new cart item
        //
        ProductItem productItem = new ProductItem();
        productItem.setProduct_id(productId);
        productItem.setQuantity(quantity);

        return this.addProductItemToBasket(basket, productItem);
    }

    public Basket addProductItemToBasket(Basket basket, ProductItem productItem) throws ECommerceException {
        ClientResponse response =
                new Builder(this.basketResource.path(basket.getBasket_id() + "/items").getRequestBuilder()).
                jsonHeaders().
                originHeader().
                basketHeaders(basket).getRequestBuilder().
                post(ClientResponse.class, productItem);
        return this.getBasket(response, basket.getAuthorizationToken());
    }

    public Basket modifyProductItemInBasket(Basket basket, ProductItem productItem) throws ECommerceException {
        ClientResponse response =
                new Builder(this.basketResource.path(basket.getBasket_id() + "/items/" + productItem.getItem_id()).getRequestBuilder()).
                jsonHeaders().
                originHeader().
                basketHeaders(basket).getRequestBuilder().
                method("PATCH", ClientResponse.class, productItem);
        return this.getBasket(response, basket.getAuthorizationToken());
    }

    public Basket removeProductItemFromBasket(Basket basket, ProductItem productItem) throws ECommerceException {
        ClientResponse response =
                new Builder(this.basketResource.path(basket.getBasket_id() + "/items/" + productItem.getItem_id()).getRequestBuilder()).
                jsonHeaders().
                originHeader().
                basketHeaders(basket).getRequestBuilder().
                delete(ClientResponse.class);
        return this.getBasket(response, basket.getAuthorizationToken());
    }

    public Product getProduct(String productId) {
        Product product =
                this.productResource.path("/" + productId).
                queryParam("expand", "availability,links,options,images,prices,variations").
                accept(MediaType.APPLICATION_JSON).
                get(Product.class);
        if ( product.getFault() != null ) {
            this.throwException(product.getFault());
        }
        return product;
    }

    public Category getCategory(String categoryId) {
        return this.categoryResource.path("/" + categoryId).
                queryParam("levels", "0").
                accept(MediaType.APPLICATION_JSON).
                get(Category.class);
    }

    public List<Category> getTopLevelCategories(int levels) {
        return this.getCategories("root", levels);
    }

    public List<Category> getCategories(String categoryId, int levels) {
        Category category = this.categoryResource.path(categoryId).
                queryParam("levels", Integer.toString(levels)).
                accept(MediaType.APPLICATION_JSON).
                get(Category.class);
        return category.getCategories();
    }

    public void getCategoryTree(List<Category> categories) {
        for ( Category category : categories ) {
            if ( category.getCategories() == null ) {
                category.setCategories(this.getCategories(category.getId(), 1));
            }
            if ( category.getCategories() != null ) {
                getCategoryTree(category.getCategories());
            }
        }
    }

    public ProductSearchResult searchProductsByCategory(String categoryId, int count) {
        return this.productSearchResource.
                queryParam("refine_1", "cgid=" +categoryId).
                queryParam("count", Integer.toString(count)).
                accept(MediaType.APPLICATION_JSON).
                get(ProductSearchResult.class);

    }

    public ProductSearchResult searchProductsByCategory(String categoryId, int count, Map<String,String> refinements ) {
        WebResource searchResource = this.productSearchResource.
                queryParam("refine_1", "cgid=" +categoryId);

        int refineId = 2;
        for ( String refinementId : refinements.keySet() ) {
            searchResource = searchResource.queryParam("refine_" + refineId, refinementId + "=" + refinements.get(refinementId));
            refineId++;
        }

        return searchResource.
                queryParam("count", Integer.toString(count)).
                accept(MediaType.APPLICATION_JSON).
                get(ProductSearchResult.class);

    }

    public ProductSearchResult search(String searchPhrase, String categoryId, int start, int count, Map<String,String> refinements) {
        WebResource searchResource = this.productSearchResource;
        if ( searchPhrase != null ) {
            // TODO: Need to do URL encode here? Does Jersey solves this for us????
            searchResource = searchResource.queryParam("q", searchPhrase); //URLEncoder.encode(searchPhrase));
        }
        if ( start != 0 ) {
            searchResource = searchResource.queryParam("start", Integer.toString(start));
        }

        int refineId = 1;
        if ( categoryId != null ) {
            searchResource = searchResource.queryParam("refine_1", "cgid=" +categoryId);
            refineId++;
        }

        if ( refinements != null ) {
            for ( String refinementId : refinements.keySet() ) {
                searchResource = searchResource.queryParam("refine_" + refineId, refinementId + "=" + refinements.get(refinementId));
                refineId++;
            }
        }

        return searchResource.
                queryParam("count", Integer.toString(count)).
                queryParam("expand", "availability,images,prices,variations").
                accept(MediaType.APPLICATION_JSON).
                get(ProductSearchResult.class);
    }

    public ProductSearchResult getNext(ProductSearchResult searchResult) {
        if ( searchResult.getNext() != null ) {
            return this.client.resource(searchResult.getNext()).
                    accept(MediaType.APPLICATION_JSON).
                    get(ProductSearchResult.class);
        }
        return null;
    }

    public ProductSearchResult getPrevious(ProductSearchResult searchResult) {
        if (searchResult.getPrevious() != null) {
            return this.client.resource(searchResult.getPrevious()).
                    accept(MediaType.APPLICATION_JSON).
                    get(ProductSearchResult.class);
        }
        return null;
    }

}

package com.sdl.ecommerce.demandware.api;

import com.sdl.ecommerce.demandware.api.model.*;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.ApacheHttpClientConfig;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import com.tridion.util.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demandware Shop Client v.14.8
 *
 * @author nic
 */
@Component
//@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // TODO: Is it needed to have the scope to REQUEST here???
public class DemandwareShopClient {

    static private Log log = LogFactory.getLog(DemandwareShopClient.class);

    static final String BASE_URL_PATH = "/dw/shop/v14_8";

    @Value("${demandware.shopUrl}")
    private String shopUrl;

    @Value("${demandware.clientId}")
    private String clientId;

    private String shopBaseUrl;
    private Client client;
    private WebResource basketResource;
    private WebResource productResource;
    private WebResource productSearchResource;
    private WebResource categoryResource;

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

        this.basketResource = this.client.resource(this.shopBaseUrl + "/basket/this").queryParam("client_id", this.clientId);
        this.productResource = this.client.resource(this.shopBaseUrl + "/products").queryParam("client_id", this.clientId);
        this.productSearchResource = this.client.resource(this.shopBaseUrl + "/product_search").queryParam("client_id", this.clientId);
        this.categoryResource = this.client.resource(this.shopBaseUrl + "/categories").queryParam("client_id", this.clientId);
    }

    // TODO: Migrate to Spring REST templates instead to align to the solution made for the Hybris connector???
    /* Example:
        RestTemplate restTemplate = this.createRestTemplate();
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("clientId", this.clientId);

        Basket basket = restTemplate.getForObject(this.shopBaseUrl + "/basket/this?client_id={clientId}", Basket.class, vars);
     */


    public Basket getBasket() {
        ClientResponse response = this.basketResource.
                accept(MediaType.APPLICATION_JSON).
                get(ClientResponse.class);
        Basket basket = response.getEntity(Basket.class);
        basket.setEtag(response.getHeaders().getFirst("ETag"));

        return basket;
    }

    public Basket addProductToBasket(String productId) {
        ProductItem product = new ProductItem();
        product.setProduct_id(productId);
        product.setQuantity(1.0f);
        return this.addProductToBasket(product);
    }

    public Basket addProductToBasket(ProductItem product) {
        return this.basketResource.path("/add").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                post(Basket.class, product);
    }

    public Product getProduct(String productId) {
        return this.productResource.path("/" + productId).
                queryParam("expand", "availability,links,options,images,prices,variations").
                accept(MediaType.APPLICATION_JSON).
                get(Product.class);
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

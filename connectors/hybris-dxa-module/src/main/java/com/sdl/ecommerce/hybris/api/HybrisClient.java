package com.sdl.ecommerce.hybris.api;

import com.sdl.ecommerce.hybris.api.model.*;
import com.tridion.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * Hybris Client
 *
 * @author nic
 */
@Component
public class HybrisClient {

    @Value("${hybris.url}")
    private String hybrisRestUrl;

    @Value("${hybris.username}")
    private String username;

    @Value("${hybris.password}")
    private String password;

    @Value("${hybris.activeServiceCatalog}")
    private String activeServiceCatalog;

    // TODO: Proxy the product images
    @Value("${hybris.mediaUrlPrefix}")
    private String mediaUrlPrefix;

    @Value("${hybris.excludeFacets}")
    private String excludeFacets = "";
    private List<String> excludeFacetList = new ArrayList<>();


    public HybrisClient() {}

    public HybrisClient(String hybrisRestUrl, String username, String password, String activeServiceCatalog) {
        this.hybrisRestUrl = hybrisRestUrl;
        this.username = username;
        this.password = password;
        this.activeServiceCatalog = activeServiceCatalog;
        //SSLUtilities.trustAllHttpsCertificates();   TODO: NEEDED???
    }

    @PostConstruct
    public void initialize() {
        StringTokenizer tokenizer = new StringTokenizer(excludeFacets, ", ");
        while ( tokenizer.hasMoreTokens() ) {
            this.excludeFacetList.add(tokenizer.nextToken());
        }
    }

    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate;
        if ( this.username != null ) {
            SimpleClientHttpRequestFactory requestFactory
                    = new SimpleClientHttpRequestFactory() {
                @Override
                protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                    super.prepareConnection(connection, httpMethod);

                    //Basic Authentication for Police API
                    String authorisation = username + ":" + password;
                    byte[] encodedAuthorisation = Base64.encode(authorisation.getBytes());
                    connection.setRequestProperty("Authorization", "Basic " + new String(encodedAuthorisation));
                }
            };
            restTemplate = new RestTemplate(requestFactory);
        }
        else {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }

    public List<Category> getAllCategories() {

        RestTemplate restTemplate = this.createRestTemplate();
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("serviceUrl", this.hybrisRestUrl + this.activeServiceCatalog);
        vars.put(
                "options",
                "CATEGORIES,SUBCATEGORIES");

        CatalogVersion catalog = restTemplate.getForObject(
                "{serviceUrl}?options={options}",
                CatalogVersion.class, vars);
        return catalog.getCategories();
    }

    public Category getCategory(String categoryId) {
        RestTemplate restTemplate = this.createRestTemplate();
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("categoryid", categoryId);
        vars.put("serviceUrl", this.hybrisRestUrl + this.activeServiceCatalog);
        vars.put(
                "options",
                "PRODUCTS");

        Category category = restTemplate.getForObject(
                "{serviceUrl}/categories/{categoryid}?options={options}",
                Category.class, vars);
        this.processImages(category.getProducts());
        return category;
    }

    public SearchResult search(String searchPhrase, int pageSize, int currentPage) {
        return this.search(searchPhrase, pageSize, currentPage, null, null);
    }

    public SearchResult search(String searchPhrase, int pageSize, int currentPage, String sort, List<FacetPair> facets) {

        RestTemplate restTemplate = this.createRestTemplate();

        if ( searchPhrase == null ) {
            searchPhrase = "";
        }
        if ( sort == null ) {
            sort = "relevance";
        }

        String query = URLEncoder.encode(searchPhrase) + ":" + sort;
        if ( facets != null && facets.size() > 0 ) {
            for ( FacetPair facet : facets) {
                query += ":" + facet.getId() + ":" + facet.getValue();
            }
        }

        Map<String, Object> vars = new HashMap<>();
        vars.put("serviceUrl", this.hybrisRestUrl);
        vars.put("query", query);
        vars.put("pageSize", pageSize);
        vars.put("currentPage", currentPage);

        SearchResult result = restTemplate.getForObject(
                "{serviceUrl}/products?query={query}&pageSize={pageSize}&currentPage={currentPage}",
                SearchResult.class, vars);
        this.processImages(result.getProducts());
        this.processFacets(result.getFacets());
        return result;
    }

    public Product getProduct(String productId) {

        RestTemplate restTemplate = this.createRestTemplate();

        Map<String, String> vars = new HashMap<String, String>();
        vars.put("productid", productId);
        vars.put("serviceUrl", this.hybrisRestUrl);
        vars.put(
                "options",
                "BASIC,DESCRIPTION,GALLERY,CATEGORIES,PROMOTIONS,STOCK,REFERENCES,PRICE");

        // Add CLASSIFICATION,REVIEW as option when displaying of features is needed

        // TODO: Is needed????
        /*
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        MediaType mediaType = new MediaType("application", "json",
                Charset.forName("UTF-8"));
        supportedMediaTypes.add(mediaType);
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(supportedMediaTypes);
        messageConverters.add(jacksonConverter);
        restTemplate.setMessageConverters(messageConverters);
         */


        Product product = restTemplate.getForObject(
                "{serviceUrl}/products/{productid}?options={options}",
                Product.class, vars);
        this.processImages(product);
        return product;
    }

    private void processImages(List<Product> products) {
        for ( Product product : products ) {
            processImages(product);
        }
    }

    private void processImages(Product product) {
        for ( Image image : product.getImages() ) {
            String imageUrl = image.getUrl();
            imageUrl = this.mediaUrlPrefix + imageUrl;
            image.setUrl(imageUrl);
        }
    }

    private void processFacets(List<Facet> facets) {
        for ( String excludeFacetId : this.excludeFacetList ) {
            for ( int i=0; i < facets.size(); i++ ) {
                Facet facet = facets.get(i);
                if ( facet.getId().equals(excludeFacetId) ) {
                    facets.remove(facet);
                    break;
                }
            }
        }
    }

    private HttpEntity getHeaders(String sessionID)
    {
        HttpHeaders requestHeaders = new HttpHeaders();
        if(sessionID != null)
        {
            requestHeaders.add("Cookie", sessionID);
        }
        HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
        return requestEntity;
    }

    private ResponseEntity getCartResponse(String sessionID)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity cartResponse = restTemplate.exchange(
                this.hybrisRestUrl + "/cart",
                HttpMethod.GET,
                getHeaders(sessionID),
                Cart.class);
        return cartResponse;
    }

    public String createCart() {
        // TODO Auto-generated method stub
        ResponseEntity cartResponse = getCartResponse(null);
        HttpHeaders responseHeaders = cartResponse.getHeaders();
        String sessionID = (String) responseHeaders.get("Set-Cookie").get(0);
        return sessionID;
    }

    @SuppressWarnings("rawtypes")
    public Cart getCart(String sessionID) {
        ResponseEntity cartResponse = getCartResponse(sessionID);
        Cart cart = (Cart) cartResponse.getBody();
        return cart;
    }


    @SuppressWarnings("rawtypes")
    public Cart addItemToCart(String sessionID, String productId, int amount) throws Exception {
        Cart c =(Cart) getCart(sessionID);
        for(Entry productEntry : c.getEntries())
        {
            if(productEntry.getProduct().getCode().equals(productId))
            {
                return this.modifyItemInCart(sessionID, productId, productEntry.getQuantity()+amount);
            }
        }
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("productId", productId);
        vars.put("qty", String.valueOf(amount));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity cartResponse = restTemplate.exchange(
                this.hybrisRestUrl + "/cart/entry?code={productId}&qty={qty}",
                HttpMethod.POST,
                getHeaders(sessionID),
                String.class,
                vars);

        if(is2xxSuccessful(cartResponse.getStatusCode()))
        {
            return getCart(sessionID);
        }
        else
        {
            throw new Exception(cartResponse.getStatusCode().toString());
        }
    }

    public Cart removeItemFromCart(String sessionID, String productId) throws Exception {

        Cart c =(Cart) getCart(sessionID);
        int entryId = -1;
        for(Entry productEntry : c.getEntries())
        {
            if(productEntry.getProduct().getCode().equals(productId))
            {
                entryId= productEntry.getEntryNumber();
            }
        }

        Map<String, String> vars = new HashMap<String, String>();
        vars.put("entryId", String.valueOf(entryId));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity cartResponse = restTemplate.exchange(
                this.hybrisRestUrl + "/cart/entry/{entryId}",
                HttpMethod.DELETE,
                getHeaders(sessionID),
                String.class,
                vars);
        if(is2xxSuccessful(cartResponse.getStatusCode()))
        {
            return getCart(sessionID);
        }
        else
        {
            throw new Exception(cartResponse.getStatusCode().toString());
        }
    }

    public Cart modifyItemInCart(String sessionID, String productId,
                                   int amount) throws Exception {

        Cart c =(Cart) getCart(sessionID);
        int entryId = -1;
        for(Entry productEntry : c.getEntries())
        {
            if(productEntry.getProduct().getCode().equals(productId))
            {
                entryId= productEntry.getEntryNumber();
            }
        }

        Map<String, String> vars = new HashMap<String, String>();
        vars.put("entryId", String.valueOf(entryId));
        vars.put("qty", String.valueOf(amount));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> cartResponse = restTemplate.exchange(
                this.hybrisRestUrl + "/cart/entry/{entryId}?qty={qty}",
                HttpMethod.PUT,
                getHeaders(sessionID),
                String.class,
                vars);
        if(is2xxSuccessful(cartResponse.getStatusCode()))
        {
            return getCart(sessionID);
        }
        else
        {
            throw new Exception(cartResponse.getStatusCode().toString());
        }
    }

    private boolean is2xxSuccessful(HttpStatus status) {
        return status.value() >= 200 && status.value() < 300;
    }



}

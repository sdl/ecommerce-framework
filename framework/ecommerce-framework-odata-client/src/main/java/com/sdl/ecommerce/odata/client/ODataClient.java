package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.odata.client.*;
import com.sdl.odata.client.api.ODataClientComponentsProvider;
import com.sdl.odata.client.api.ODataClientQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * OData Client
 *
 * @author nic
 */
@Component
public class ODataClient {

    @Value("${ecommerce.odata.serviceUri}")
    private String serviceUri = "http://localhost:8097/ecommerce.svc";

    private Map<String, DefaultODataClient> clients = new HashMap<>();
    private Map<String, DefaultODataClient> clientsNoEncoding = new HashMap<>();

    @Autowired
    private LocalizationService localizationService;

    private List<String> modelClasses = new ArrayList<>();

    /**
     * Register OData model class
     * @param modelClass
     */
    public void registerModelClass(Class modelClass) {
         this.modelClasses.add(modelClass.getName());
    }

    /**
     * Create client
     * @param locale
     * @param encodeUrl
     * @param clientMap
     * @return client
     */
    protected DefaultODataClient createClient(String locale, boolean encodeUrl, Map<String, DefaultODataClient> clientMap) {
        synchronized ( clientMap ) {
            DefaultODataClient client = clientMap.get(locale); // synchronized double check
            if ( client == null ) {
                client = new DefaultODataClient();
                clientMap.put(locale, client);
                client.encodeURL(encodeUrl);
                ODataClientComponentsProvider provider = new ODataV4ClientComponentsProvider(modelClasses,
                        new ClientPropertiesBuilder().withServiceUri(serviceUri + "/" + locale).build());
                client.configure(provider);
            }
            return client;
        }
    }

    /**
     * Get client for a specific locale.
     * @return client
     */
    protected DefaultODataClient getClient() {
        String locale = localizationService.getLocale();
        DefaultODataClient client = this.clients.get(locale);
        if ( client == null ) {
            client = createClient(locale, true, this.clients);
        }
        return client;
    }

    /**
     * Get a non-encoding client for a specific locale.
     * @return client
     */
    protected DefaultODataClient getClientWithNoEncoding() {
        String locale = localizationService.getLocale();
        DefaultODataClient client = this.clientsNoEncoding.get(locale);
        if ( client == null ) {
            client = createClient(locale, false, this.clientsNoEncoding);
        }
        return client;
    }

    /**
     * Get OData entity from a OData query
     * @param query
     * @return entity
     */
    public Object getEntity(ODataClientQuery query) {
        if ( query instanceof ECommerceODataClientQuery ) {
            if ( ( (ECommerceODataClientQuery) query).getSelectedProperty() != null ) {
                return this.getClientWithNoEncoding().getEntity(Collections.<String, String>emptyMap(), query);
            }
        }
        /*else if ( query instanceof AbstractODataFunctionClientQuery ) {
            return this.getClientWithNoEncoding().getEntity(Collections.emptyMap(), query);
        } */
        return this.getClient().getEntity(Collections.<String, String>emptyMap(), query);
    }

    /**
     * Get list of OData entities from a OData query
     * @param query
     * @return entities
     */
    public List<?> getEntities(ODataClientQuery query) {
        return this.getClient().getEntities(Collections.<String, String>emptyMap(), query);
    }

}

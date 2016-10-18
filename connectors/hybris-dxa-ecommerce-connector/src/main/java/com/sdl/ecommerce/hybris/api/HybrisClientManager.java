package com.sdl.ecommerce.hybris.api;

import com.sdl.ecommerce.api.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Hybris Client Manager
 * Manage hybris clients per localization.
 * @author nic
 */
@Component
public class HybrisClientManager {

    // TODO: If any of these properties has changes through Tridion, the DXA server needs to be restarted

    @Autowired
    private LocalizationService localizationService;

    @Value("${hybris.url}")
    private String hybrisUrl;

    @Value("${hybris.username}")
    private String username;

    @Value("${hybris.password}")
    private String password;

    // TODO: Proxy the product images
    @Value("${hybris.mediaUrlPrefix}")
    private String mediaUrlPrefix;

    private Map<String, HybrisClient> clients = new HashMap<>();

    public HybrisClient getInstance() {
        HybrisClient hybrisClient = this.clients.get(this.localizationService.getLocale());
        if ( hybrisClient == null ) {
            hybrisClient = this.createClient();
        }
        return hybrisClient;
    }

    private synchronized HybrisClient createClient() {
        HybrisClient hybrisClient = new HybrisClientImpl(this.hybrisUrl + this.getSiteId(),
                                                        this.username,
                                                        this.password,
                                                        this.getActiveServiceCatalog(),
                                                        this.getLanguage(),
                                                        this.getCurrency(),
                                                        this.mediaUrlPrefix,
                                                        this.getExcludeFacets());
        this.clients.put(this.localizationService.getLocale(), hybrisClient);
        return hybrisClient;

    }

    private String getSiteId() {
        return this.localizationService.getLocalizedConfigProperty("hybris-siteId");
    }

    private String getActiveServiceCatalog() {
        return this.localizationService.getLocalizedConfigProperty("hybris-activeServiceCatalog");
    }

    private String getLanguage() {
        return this.localizationService.getLocalizedConfigProperty("hybris-language");
    }

    private String getCurrency() {
        return this.localizationService.getLocalizedConfigProperty("hybris-currency");
    }

    private List<String> getExcludeFacets() {
        String excludeFacets = this.localizationService.getLocalizedConfigProperty("hybris-excludeFacets");
        List<String> excludeFacetList = new ArrayList<>();
        if ( excludeFacets != null ) {
            StringTokenizer tokenizer = new StringTokenizer(excludeFacets, ", ");
            while (tokenizer.hasMoreTokens()) {
                excludeFacetList.add(tokenizer.nextToken());
            }
        }
        return excludeFacetList;
    }



}

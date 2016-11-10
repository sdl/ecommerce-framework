package com.sdl.ecommerce.dxa;

import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.webapp.common.api.WebRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * Localization Service Implementation.
 * Hides the DXA localization from the E-Commerce connectors
 *
 * @author nic
 */
@Component
public class LocalizationServiceImpl implements LocalizationService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalizationServiceImpl.class);

    @Autowired
    private WebRequestContext webRequestContext;

    @Override
    public String getLocale() {
        return this.webRequestContext.getLocalization().getCulture();
    }

    @Override
    public String getLocalizedConfigProperty(String name) {
        return this.webRequestContext.getLocalization().getConfiguration("e-commerce." + name);
    }

    @Override
    public Map<URI, Object> getAllClaims() {

        // TODO: We need to build a better solution that works both for SDL Tridion 2013 SP1 and SDL Web 8 without different dependencies

        try {
            Class contextClass = Class.forName("com.tridion.ambientdata.AmbientDataContext");
            Method getClaimStore = contextClass.getMethod("getCurrentClaimStore");
            Object claimStore = getClaimStore.invoke(null);
            if (claimStore == null) {
                return Collections.emptyMap();
            }
            Method getAll = claimStore.getClass().getMethod("getAll");
            return (Map<URI, Object>) getAll.invoke(claimStore);
        } catch (Exception e) {
            LOG.error("Could not get ADF claims.", e);
        }
        return null;
    }
}

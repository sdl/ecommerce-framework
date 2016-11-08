package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.impl.GenericProductDetailResult;
import com.sdl.ecommerce.odata.model.*;
import com.sdl.odata.client.BasicODataClientQuery;
import com.sdl.odata.client.api.ODataClientQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * OData Product Detail Service
 *
 * @author nic
 */
@Component
public class ODataProductDetailService implements ProductDetailService {

    @Autowired
    private ODataClient odataClient;

    @PostConstruct
    public void initialize() {
        this.odataClient.registerModelClass(ODataProduct.class);
        this.odataClient.registerModelClass(ODataProductPrice.class);
        this.odataClient.registerModelClass(ODataProductAttribute.class);
        this.odataClient.registerModelClass( ODataProductVariant.class);
        this.odataClient.registerModelClass(ODataProductVariantAttribute.class);
        this.odataClient.registerModelClass(ODataProductVariantAttributeType.class);
        this.odataClient.registerModelClass(ODataProductVariantAttributeValueType.class);
    }

    @Override
    public ProductDetailResult getDetail(String productId) throws ECommerceException {
        ODataClientQuery query = new BasicODataClientQuery.Builder()
                .withEntityType(ODataProduct.class)
                .withEntityKey("'" + productId + "'")
                .build();
        ODataProduct product = (ODataProduct) this.odataClient.getEntity(query);
        return new GenericProductDetailResult(product, product.getBreadcrumbs(), product.getPromotions());
    }
}

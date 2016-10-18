package com.sdl.ecommerce.odata.datasource;

import com.sdl.ecommerce.api.CartService;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.ProductQueryService;
import com.sdl.ecommerce.api.edit.EditService;
import com.sdl.odata.api.ODataException;
import com.sdl.odata.api.edm.model.EntityDataModel;
import com.sdl.odata.api.parser.ODataUri;
import com.sdl.odata.api.processor.datasource.DataSource;
import com.sdl.odata.api.processor.link.ODataLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Product Data Source
 *
 * @author nic
 */
@Component
public class ProductDataSource implements DataSource {

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private EditService editService; // TODO: Move this to another data source?

    @Autowired (required = false)
    private CartService cartService; // TODO: Move this to another data source?

    public ProductDetailService getProductDetailService() {
        return productDetailService;
    }

    public ProductQueryService getProductQueryService() {
        return productQueryService;
    }

    public ProductCategoryService getProductCategoryService() {
        return productCategoryService;
    }

    public EditService getEditService() {
        return editService;
    }

    public CartService getCartService() {
        return cartService;
    }

    @Override
    public Object create(ODataUri oDataUri, Object o, EntityDataModel entityDataModel) throws ODataException {
        return null;
    }

    @Override
    public Object update(ODataUri oDataUri, Object o, EntityDataModel entityDataModel) throws ODataException {
        return null;
    }

    @Override
    public void delete(ODataUri oDataUri, EntityDataModel entityDataModel) throws ODataException {

    }

    @Override
    public void createLink(ODataUri oDataUri, ODataLink oDataLink, EntityDataModel entityDataModel) throws ODataException {

    }

    @Override
    public void deleteLink(ODataUri oDataUri, ODataLink oDataLink, EntityDataModel entityDataModel) throws ODataException {

    }

    /* OData v2.1.0
    @Override
    public TransactionalDataSource startTransaction() {
        return null;
    }
    */
}

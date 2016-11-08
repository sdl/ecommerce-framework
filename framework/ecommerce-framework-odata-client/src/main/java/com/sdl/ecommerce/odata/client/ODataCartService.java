package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.CartService;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.odata.model.ODataCart;
import com.sdl.ecommerce.odata.model.ODataCartItem;
import com.sdl.odata.client.FunctionImportClientQuery;
import com.sdl.odata.client.api.ODataClientQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * OData Cart Service
 *
 * @author nic
 */
@Component
public class ODataCartService implements CartService {

    @Autowired
    private ODataClient odataClient;

    @PostConstruct
    public void initialize() {
        this.odataClient.registerModelClass(ODataCart.class);
        this.odataClient.registerModelClass(ODataCartItem.class);
    }

    @Override
    public Cart createCart() throws ECommerceException {

        FunctionImportClientQuery.Builder queryBuilder = new FunctionImportClientQuery.Builder()
                .withFunctionName("CreateCart")
                .withEntityType(ODataCart.class);
        ODataClientQuery oDataQuery = queryBuilder.build();
        return (Cart) this.odataClient.getEntity(oDataQuery);
    }

    @Override
    public Cart addProductToCart(String cartId, String sessionId, String productId, int quantity) throws ECommerceException {

        FunctionImportClientQuery.Builder queryBuilder = new FunctionImportClientQuery.Builder()
                .withFunctionName("AddProductToCart")
                .withEntityType(ODataCart.class)
                .withFunctionParameter("cartId", "'" + cartId + "'")
                .withFunctionParameter("productId", "'" + productId + "'")
                .withFunctionParameter("quantity", Integer.toString(quantity));
        if ( sessionId != null ) {
            queryBuilder = queryBuilder.withFunctionParameter("sessionId", "'" + sessionId + "'");
        }
        ODataClientQuery oDataQuery = queryBuilder.build();
        return (Cart) this.odataClient.getEntity(oDataQuery);
    }

    @Override
    public Cart removeProductFromCart(String cartId, String sessionId, String productId) throws ECommerceException {
        FunctionImportClientQuery.Builder queryBuilder = new FunctionImportClientQuery.Builder()
                .withFunctionName("RemoveProductFromCart")
                .withEntityType(ODataCart.class)
                .withFunctionParameter("cartId", "'" + cartId + "'")
                .withFunctionParameter("productId", "'" + productId + "'");
        if ( sessionId != null ) {
            queryBuilder = queryBuilder.withFunctionParameter("sessionId", "'" + sessionId + "'");
        }
        ODataClientQuery oDataQuery = queryBuilder.build();
        return (Cart) this.odataClient.getEntity(oDataQuery);
    }
}

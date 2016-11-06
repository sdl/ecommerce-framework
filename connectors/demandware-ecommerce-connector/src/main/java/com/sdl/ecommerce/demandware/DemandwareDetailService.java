package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientImpl;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientManager;
import com.sdl.ecommerce.demandware.model.DemandwareProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Demandware Detail Service.
 * Get product details from Demandware.
 *
 * @author nic
 */
@Component
public class DemandwareDetailService implements ProductDetailService {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private DemandwareShopClientManager shopClientManager;

    @Override
    public ProductDetailResult getDetail(String productId) throws ECommerceException {

        com.sdl.ecommerce.demandware.api.model.Product dwreProduct = this.shopClientManager.getInstance().getProduct(productId);
        Category primaryCategory = null;
        if ( dwreProduct.getPrimary_category_id() != null ) {
            primaryCategory = this.categoryService.getCategoryById(dwreProduct.getPrimary_category_id());
        }
        Product product = new DemandwareProduct(primaryCategory, dwreProduct);
        return new DemandwareDetailResult(product);
    }
}

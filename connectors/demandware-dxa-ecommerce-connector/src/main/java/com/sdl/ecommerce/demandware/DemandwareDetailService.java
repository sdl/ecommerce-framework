package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.demandware.api.DemandwareShopClient;
import com.sdl.ecommerce.demandware.model.DemandwareProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DemandwareDetailService
 *
 * @author nic
 */
@Component
public class DemandwareDetailService implements ProductDetailService {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private DemandwareShopClient shopClient;

    @Override
    public ProductDetailResult getDetail(String productId) throws ECommerceException {

        com.sdl.ecommerce.demandware.api.model.Product dwreProduct = this.shopClient.getProduct(productId);
        Category primaryCategory = this.categoryService.getCategoryById(dwreProduct.getPrimary_category_id());
        Product product = new DemandwareProduct(primaryCategory, dwreProduct);
        return new DemandwareDetailResult(product);
    }
}

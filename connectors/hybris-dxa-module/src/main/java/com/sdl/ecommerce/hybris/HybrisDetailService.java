package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.hybris.api.HybrisClient;
import com.sdl.ecommerce.hybris.model.HybrisProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * HybrisDetailService
 *
 * @author nic
 */
@Component
public class HybrisDetailService implements ProductDetailService {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private HybrisClient hybrisClient;

    @Override
    public ProductDetailResult getDetail(String productId) throws ECommerceException {

        com.sdl.ecommerce.hybris.api.model.Product hybrisProduct = this.hybrisClient.getProduct(productId);
        Product product = this.getProductDetail(hybrisProduct);
        return new HybrisDetailResult(product);
    }

    private Product getProductDetail(com.sdl.ecommerce.hybris.api.model.Product hybrisProduct) {

        Product product = new HybrisProduct(hybrisProduct);
        for (com.sdl.ecommerce.hybris.api.model.Category hybrisCategory : hybrisProduct.getCategories() ) {
            Category category = this.categoryService.getCategoryById((String) hybrisCategory.getAdditionalProperties().get("code"));
            // TODO: Here we get hits on categories not part of the branch we do navigation. How to handle? Convert that a facet???
            if ( category != null ) {
                product.getCategories().add(category);
            }
        }
        return product;
    }
}

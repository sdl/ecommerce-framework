using System.Collections.Generic;

namespace SDL.ECommerce.Api.Model
{
/**
 * Products Promotion
 *
 * @author nic
 */
    public interface ProductsPromotion : IPromotion 
    {
        /**
         * Get list of products enumerated in this promotion
         * @return products
         */
        List<IProduct> Products();
    }
}

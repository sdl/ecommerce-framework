using SDL.ECommerce.Ecl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.Hybris.Ecl
{
    class HybrisMountpoint : Mountpoint
    {
        protected override ProductItem CreateProductItem(int publicationId, Category parentCategory, Product product)
        {
            return new HybrisProductItem(publicationId, parentCategory, product);
        }
    }
}

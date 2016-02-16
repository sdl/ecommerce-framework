using SDL.ECommerce.Ecl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.DemandWare.Ecl
{
    class DemandWareMountpoint : Mountpoint
    {
        protected override ProductItem CreateProductItem(int publicationId, Category parentCategory, Product product)
        {
            return new DemandWareProductItem(publicationId, parentCategory, product);
        }
    }
}

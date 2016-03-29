using SDL.ECommerce.Ecl;

namespace SDL.Fredhopper.Ecl
{
    class FredhopperMountpoint : Mountpoint
    {
        protected override ProductItem CreateProductItem(int publicationId, Category parentCategory, Product product)
        {
            return new FredhopperProductItem(publicationId, parentCategory, product);
        }
    }
}

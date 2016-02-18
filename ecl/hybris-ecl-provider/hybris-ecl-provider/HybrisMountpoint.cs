using SDL.ECommerce.Ecl;

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

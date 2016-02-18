using System.Collections.Generic;

namespace SDL.ECommerce.Ecl
{
    public interface Category
    {
        string CategoryId { get; }
        string Title { get; }
        Category Parent { get; }
        IList<Category> Categories { get; }
    }

    public interface Product
    {
        string Id { get; }
        string Name { get; }
        ProductImage Thumbnail { get; }
    }

    public interface ProductImage
    {
        string Url { get; }
        string Mime { get; }
    }

}

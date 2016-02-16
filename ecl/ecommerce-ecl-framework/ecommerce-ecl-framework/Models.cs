using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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

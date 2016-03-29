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

    public class StandardProductImage : ProductImage
    {
        private string url;
        private string mime;

        public StandardProductImage(string url, string mime)
        {
            this.url = url;
            this.mime = mime;
        }

        public string Url { get { return this.url; } }
        public string Mime { get { return this.mime; } }
    }

}

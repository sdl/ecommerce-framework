using SDL.ECommerce.Ecl;
using System.Collections.Generic;
using System.Linq;

namespace SDL.Fredhopper.Ecl
{
    
    public class FredhopperCategory : Category
    {

        public FredhopperCategory(string categoryId, string title, Category parent)
        {
            CategoryId = categoryId;
            Title = title;
            Parent = parent;
            Categories = new List<Category>();
        }

        public string CategoryId { get; internal set; }
        public string Title { get; internal set; }
        public Category Parent { get; internal set; }
        public IList<Category> Categories { get; internal set; }
          
    }
    

  
    /*
    public class FredhopperProduct : Product
    {
       

      


        public ProductImage Thumbnail
        {
            get
            {                
               
            }
        }

        private ProductImage productThumbnail = null;

    }
    */

        /*
    public class FredhopperProductImage : ProductImage
    {
        private string mediaUrl;
        private string mime;

       

        public string Url
        {
            get { return this.mediaUrl + productImage.url; }
        }

        public string Mime
        {
            get { return mime; }
        }
    }
    */

}

using SDL.ECommerce.Ecl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.Hybris.Ecl
{

    public class HybrisCategory : Category
    {
        private SDL.ECommerce.Hybris.API.Model.Category hybrisCategory;

        public HybrisCategory(SDL.ECommerce.Hybris.API.Model.Category hybrisCategory)
        {
            this.hybrisCategory = hybrisCategory;
        }

        public string CategoryId
        {
            get { return this.hybrisCategory.id; }
        }

        public string Title
        {
            get { return this.hybrisCategory.name != null ? this.hybrisCategory.name : ""; }
        }

        public Category Parent
        {
            get { return new HybrisCategory(this.hybrisCategory.Parent); }
        }

        public IList<Category> Categories
        {
            // TODO: Cache this
            get { return this.hybrisCategory.subcategories.Select(category => (Category) new HybrisCategory(category)).ToList(); }
        }

    }

    public class HybrisRootCategory : Category
    {
        private List<Category> categories;

        public HybrisRootCategory(List<SDL.ECommerce.Hybris.API.Model.Category> hybrisTopLevelCategories)
        {
            this.categories = hybrisTopLevelCategories.Select(category => (Category) new HybrisCategory(category)).ToList();
        }

         public string CategoryId
        {
            get { return "root"; }
        }

        public string Title
        {
            get { return "Root"; }
        }

        public Category Parent
        {
            get { return null; }
        }

        public IList<Category> Categories
        {
            get { return this.categories; }
        }
    }
  

    public class HybrisProduct : Product
    {
        private SDL.ECommerce.Hybris.API.Model.Product hybrisProduct;
        private string mediaUrl;

        public HybrisProduct(SDL.ECommerce.Hybris.API.Model.Product hybrisProduct, string mediaUrl)
        {
            this.hybrisProduct = hybrisProduct;
            this.mediaUrl = mediaUrl;
        }

        public string Id
        {
            get { return this.hybrisProduct.code; }
        }

        public string Name
        {
            get { return this.hybrisProduct.name; }
        }

        public string Description
        {
            get { return this.hybrisProduct.description; }
        }

        public string Manufacturer
        {
            get { return this.hybrisProduct.manufacturer; }
        }

        public bool Purchasable
        {
            get { return this.hybrisProduct.purchasable; }
        }

        public string Price
        {
            get { return this.hybrisProduct.price.formattedValue; }
        }

        public int StockLevel
        {
            get { return this.hybrisProduct.stock.stockLevel; }
        }

        public float AverageRating
        {
            get { return this.hybrisProduct.averageRating; }
        }


        public ProductImage Thumbnail
        {
            get
            {                
                if (productThumbnail == null )
                {
                    var thumbnail = this.hybrisProduct.thumbnailImage;
                    if (thumbnail != null)
                    {
                        productThumbnail = new HybrisProductImage(this.hybrisProduct.thumbnailImage, this.mediaUrl);
                    }                   
                }
                return productThumbnail;
            }
        }

        private ProductImage productThumbnail = null;

    }

    public class HybrisProductImage : ProductImage
    {
        private SDL.ECommerce.Hybris.API.Model.Image productImage;
        private string mediaUrl;
        private string mime;

        public HybrisProductImage(SDL.ECommerce.Hybris.API.Model.Image productImage, string mediaUrl)
        {
            this.productImage = productImage;
            this.mediaUrl = mediaUrl;
            if ( this.productImage.url.Contains(".jpg") )
            {
                this.mime = "image/jpeg";
            }
            else
            {
                // TODO: Add more mime types here!!!
            }
        }

        public string Url
        {
            get { return this.mediaUrl + productImage.url; }
        }

        public string Mime
        {
            get { return mime; }
        }
    }

}

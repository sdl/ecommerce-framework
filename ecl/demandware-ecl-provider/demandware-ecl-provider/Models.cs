using SDL.ECommerce.Ecl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.DemandWare.Ecl
{

    public class DemandWareCategory : Category
    {

        public string CategoryId
        {
            get { return id; }
        }

        public string Title
        {
            get { return name == null? id : name; }
        }

        public Category Parent
        {
            
            get {
                return this.parent;
                /*
                if (parent_category_id != null)
                {
                    return new DemandWareCategory() { id = parent_category_id };
                }
                else
                {
                    return null;
                }*/
            }
        }

        public IList<Category> Categories
        {
            get { return this.categories == null ? null : this.categories.Cast<Category>().ToList(); }
        }

        // TODO: Separate more clearer between DWRE and generic properties

        // TODO: Use internal here???

        public Category parent;
        public string id;
        public string parent_category_id;
        public string name;
        public string description;
        public string image;
        public string thumbnail;

        public string page_title;
        public string page_description;
        public string page_keywords;
        public IList<DemandWareCategory> categories;
    }

    public class ProductSearchResult
    {
        public int count;
        public int total;
        public IList<DemandWareProduct> hits;
    }

    public class DemandWareProduct : Product
    {
        public string Id
        {
            get { return product_id; }
        }

        public string Name
        {
            get { return product_name; }
        }

        public ProductImage Thumbnail
        {
            get
            {
                if (productThumbnail == null)
                {
                    productThumbnail = new DemandWareProductImage(this.image.link, "image/jpeg");
                }
                return productThumbnail;
            }
        }

        private ProductImage productThumbnail = null;

        public string category_id;
        public string product_id;
        public string product_name;
        public Image image;
        public float price;
        public string description;
        public string brand;

        public DemandWareProduct() { }
        public DemandWareProduct(ProductDetail productDetail)
        {
            // TODO: Merge these two class definitions
            this.category_id = productDetail.primary_category_id;
            this.product_id = productDetail.id;
            this.product_name = productDetail.name;
            this.image = productDetail.image_groups[0].images[0];
            this.price = productDetail.price;
            this.description = productDetail.short_description;
            this.brand = productDetail.brand;
        }
    }

    public class DemandWareProductImage : ProductImage
    {
        private string url;
        private string mime;

        public DemandWareProductImage(string url, string mime)
        {
            this.url = url;
            this.mime = mime;
        }

        public string Url
        {
            get { return url; }
        }

        public string Mime
        {
            get { return mime; }
        }
    }

    public class ProductDetail
    {
        public string id;
        public string name;
        public string brand;
        public bool orderable;
        public float price;
        public string primary_category_id;
        public string short_description;
        public string long_description;
        public IList<ImageGroup> image_groups;
    }

    public class ImageGroup
    {
        public IList<Image> images;
        public string variation_value;
        public string view_type;
    }

    public class Image
    {
        public string link;
        public string title;
        public string alt;
    }
}

using Microsoft.VisualStudio.TestTools.UnitTesting;
using SDL.Ecommerce.Hybris.API;
using SDL.ECommerce.Ecl;
using SDL.ECommerce.Hybris.API.Model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Security;
using System.Text;
using System.Threading.Tasks;

namespace SDL.Hybris.Ecl
{
    [TestClass]
    public class UnitTests
    {
        //const string HYBRIS_URL = "http://184.72.105.227:9001/rest/v1/electronics";
        const string HYBRIS_URL = "http://10.211.55.2:9001/rest/v1/electronics";
        //const string HYBRIS_URL = "http://powertools.sdl-instance.demo.hybris.com/rest/v1/powertools";
        const string HYBRIS_LOGIN = "admin";
        const string HYBRIS_PWD = "nimda";
        //const string ACTIVE_CATALOG_VERSION = "catalogs/powertoolsProductCatalog/Online";
        const string ACTIVE_CATALOG_VERSION = "/catalogs/electronicsProductCatalog/Online";
        const string HYBRIS_MEDIA_URL = "http://10.211.55.2:9001";
        //const string HYBRIS_MEDIA_URL = "http://powertools.sdl-instance.demo.hybris.com";

        private ProductCatalog CreateProductCatalog()
        {
            return new HybrisProductCatalog(HYBRIS_URL, HYBRIS_LOGIN, HYBRIS_PWD, ACTIVE_CATALOG_VERSION, HYBRIS_MEDIA_URL);
        }

        [TestMethod]
        public void TestGetCategories()
        {
            var productCatalog = CreateProductCatalog();
            var categories = productCatalog.GetAllCategories();
            Console.WriteLine("Categories:");
            PrintCategoryList(categories, 1);
        }

        private void PrintCategoryList(ECommerce.Ecl.Category category, int level)
        {
            foreach ( var subcategory in category.Categories)
            {
                for ( int i=0; i < level; i++ )
                {
                    Console.Write("-");
                }
                Console.WriteLine(subcategory.Title + "(" + subcategory.CategoryId + ")");
                PrintCategoryList(subcategory, level+1);
            }
        }

        [TestMethod]
        public void TestGetCategory()
        {
            var productCatalog = CreateProductCatalog();
            var products = productCatalog.GetProducts("576", 0);
            Console.WriteLine("Category products:");
            foreach (var product in products)
            {
                Console.WriteLine(" " + product.Name + " (" + product.Id + ")");               
                if (product.Thumbnail != null)
                {
                    Console.WriteLine("   Image Thumbnail: " + product.Thumbnail.Url + ", " + product.Thumbnail.Mime);
                }
            }
        }

        [TestMethod]
        public void TestGetCategory2()
        {
            var hybrisClient = new HybrisClient(HYBRIS_URL, HYBRIS_LOGIN, HYBRIS_PWD, ACTIVE_CATALOG_VERSION);
            var category = hybrisClient.GetCategory("576");
            foreach ( var product in category.products )
            {
                Console.WriteLine("Product ID: " + product.code);
                Console.WriteLine("Product Name: " + product.name);
                if (product.thumbnailImage != null)
                {
                    Console.WriteLine("Thumbnail: " + product.thumbnailImage.url);
                }
            }
        }

        [TestMethod]
        public void TestSearchCategoryProducts()
        {
            var hybrisClient = new HybrisClient(HYBRIS_URL, HYBRIS_LOGIN, HYBRIS_PWD, ACTIVE_CATALOG_VERSION);
            var facets = new List<FacetPair>();
            facets.Add(new FacetPair("category", "576"));
            var result = hybrisClient.Search(null, 10, 0, null);
            foreach (var product in result.products)
            {
                Console.WriteLine("Product ID: " + product.code);
                Console.WriteLine("Product Name: " + product.name);
                if (product.thumbnailImage != null)
                {
                    Console.WriteLine("Thumbnail: " + product.thumbnailImage.url);
                }
            }
        }


        [TestMethod]
        public void TestGetProduct()
        {
            var productCatalog = CreateProductCatalog();
            HybrisProduct product = (HybrisProduct) productCatalog.GetProduct("816262");
            Console.WriteLine("Product Name: " + product.Name);
            Console.WriteLine("Product Thumbnail URL: " + product.Thumbnail.Url);
            Console.WriteLine("Product Thumbnail MIME: " + product.Thumbnail.Mime);

            Console.WriteLine("Description:" + SecurityElement.Escape(product.Description));
            Console.WriteLine("Manufacturer:" + product.Manufacturer);
            Console.WriteLine("Purchasable: " + product.Purchasable.ToString());
            Console.WriteLine("Price: " + product.Price);
            Console.WriteLine("StockLevel" + product.StockLevel);
            Console.WriteLine("AverageRating" + product.AverageRating);

        }

        [TestMethod]
        public void TestReadProductImage()
        {
            var productCatalog = CreateProductCatalog();
            SDL.ECommerce.Ecl.Product product = productCatalog.GetProduct("816262");
            using (WebClient webClient = new WebClient())
            {
                using (Stream stream = new MemoryStream(webClient.DownloadData(product.Thumbnail.Url)))
                {
                    Console.WriteLine("Content Length:" + stream.Length + ", Type: " + product.Thumbnail.Mime);
                }
            }
        }
    }
}

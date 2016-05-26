using Microsoft.VisualStudio.TestTools.UnitTesting;
using SDL.Ecommerce.Hybris.API;
using SDL.ECommerce.Ecl;
using SDL.ECommerce.Hybris.API.Model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Security;

namespace SDL.Hybris.Ecl
{
    /// <summary>
    /// The tests are using the standard Hybris Electronics demo shop.
    /// Sample test properties are given in 'eclprovider-sample.runsettings'.
    /// </summary>
    [TestClass]
    public class UnitTests
    {
   
        public TestContext TestContext { get; set; }

        private ProductCatalog CreateProductCatalog()
        {
            string baseUrl = TestContext.Properties["hybrisBaseUrl"].ToString();
            string username = TestContext.Properties["hybrisUsername"].ToString();
            string password = TestContext.Properties["hybrisPassword"].ToString();
            string activeCatalogVersion = TestContext.Properties["hybrisActiveCatalogVersion"].ToString();
            string mediaUrl = TestContext.Properties["hybrisMediaUrl"].ToString();
            return new HybrisProductCatalog(baseUrl, username, password, activeCatalogVersion, mediaUrl);
        }

        private HybrisClient CreateHybrisClient()
        {
            string baseUrl = TestContext.Properties["hybrisBaseUrl"].ToString();
            string username = TestContext.Properties["hybrisUsername"].ToString();
            string password = TestContext.Properties["hybrisPassword"].ToString();
            string activeCatalogVersion = TestContext.Properties["hybrisActiveCatalogVersion"].ToString();
            return new HybrisClient(baseUrl, username, password, activeCatalogVersion);
        }

        [TestMethod]    
        public void TestGetCategories()
        {
            var productCatalog = CreateProductCatalog();
            var categories = productCatalog.GetAllCategories();
            Console.WriteLine("Categories:");
            //PrintCategoryList(categories, 1);
            PrintCategoryFlatList(categories);
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

        private void PrintCategoryFlatList(ECommerce.Ecl.Category category)
        {
            foreach (var subcategory in category.Categories)
            {
              
                Console.WriteLine(GetCategoryFlatTitle(subcategory));
                PrintCategoryFlatList(subcategory);
            }
        }

        private string GetCategoryFlatTitle(ECommerce.Ecl.Category category)
        {
            // Display the full path of the category as title
            //
            String categoryPath = "";
            ECommerce.Ecl.Category currentCategory = category;
            while (currentCategory != null)
            {
                if (currentCategory.Title != null)
                {
                    if (categoryPath.Length > 0l)
                    {
                        categoryPath = "->" + categoryPath;
                    }
                    categoryPath = currentCategory.Title + categoryPath;
                }
                currentCategory = currentCategory.Parent;
            }
            return categoryPath;
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

        [TestMethod]
        public void TestGetCategoryViaClient()
        {
            var hybrisClient = CreateHybrisClient();
            var category = hybrisClient.GetCategory("576");
            foreach (var product in category.products)
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
        public void TestSearchCategoryProductsViaClient()
        {
            var hybrisClient = CreateHybrisClient();
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
    }
}

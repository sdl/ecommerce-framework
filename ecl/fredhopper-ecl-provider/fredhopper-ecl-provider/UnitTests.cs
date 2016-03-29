using Microsoft.VisualStudio.TestTools.UnitTesting;
using SDL.ECommerce.Ecl;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Security;

namespace SDL.Fredhopper.Ecl
{
    /// <summary>
    /// The tests are using the standard Fredhopper demo shop.
    /// Sample test properties are given in 'eclprovider-sample.runsettings'.
    /// </summary>
    [TestClass]
    public class UnitTests
    {
   
        public TestContext TestContext { get; set; }

        private ProductCatalog CreateProductCatalog()
        {
            return new FredhopperProductCatalog(null);
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
            if ( category.Categories == null ) { return; }
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
            /*
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
            */
        }

        [TestMethod]
        public void TestGetProduct()
        {
            /*
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
            */

        }

        [TestMethod]
        public void TestReadProductImage()
        {
            /*
            var productCatalog = CreateProductCatalog();
            SDL.ECommerce.Ecl.Product product = productCatalog.GetProduct("816262");
            using (WebClient webClient = new WebClient())
            {
                using (Stream stream = new MemoryStream(webClient.DownloadData(product.Thumbnail.Url)))
                {
                    Console.WriteLine("Content Length:" + stream.Length + ", Type: " + product.Thumbnail.Mime);
                }
            }
            */
        }

        [TestMethod]
        public void TestGetCategoryNames()
        {
            var client = new FredhopperClient();
            var categories = client.GetRootCategories();
            foreach ( var category in categories )
            {
                Console.WriteLine("Category: " + category);
            }
        }

    }
}

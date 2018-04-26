using Microsoft.VisualStudio.TestTools.UnitTesting;
using SDL.ECommerce.Ecl;
using System;
using System.Xml.Linq;
using System.IO;
using System.Net;
using System.Security;
using System.Collections.Generic;

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
            var endpointAddress = TestContext.Properties["EndpointAddress"];
            
            return new FredhopperProductCatalog(
                XElement.Parse(
                    "<Configuration xmlns=\"http://sdl.com/ecl/ecommerce\">" +
                    "<EndpointAddress>" + endpointAddress + "</EndpointAddress>" +
                    "<MaxItems>100</MaxItems>" +
                    "<CategoryMaxDepth>3</CategoryMaxDepth>" +
                    "<PublicationConfigurations>" +
                    "  <PublicationConfiguration publicationIds = \"1,2,3\" fallback=\"true\">" +
                    "    <Locale>en-GB</Locale>" +
                    "    <Universe>catalog01</Universe>" +
                    "    <ModelMappings>name=name;description=description;price=price;thumbnailUrl=_thumburl;primaryImageUrl=_imageurl</ModelMappings>" + 
                    "  </PublicationConfiguration>" +
                    "</PublicationConfigurations></Configuration>"));
        }

        [TestMethod]    
        public void TestGetCategories()
        {           
            var productCatalog = CreateProductCatalog();
            var categories = productCatalog.GetAllCategories();
            Console.WriteLine("Categories:");
            PrintCategoryList(categories, 1);          
        }

        private void PrintCategoryList(Category category, int level)
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
            var productCatalog = CreateProductCatalog();
            var result = productCatalog.GetProducts("catalog01_18661_17638", 0);
            Console.WriteLine("Number of result pages: " + result.NumberOfPages);
            Console.WriteLine("Category products:");
            foreach (var product in result.Products)
            {
                Console.WriteLine(" " + product.Name + " (" + product.Id + ")");               
                if (product.Thumbnail != null)
                {
                    Console.WriteLine("   Image Thumbnail: " + product.Thumbnail.Url + ", " + product.Thumbnail.Mime);
                }
            }
           
        }

        [TestMethod]
        public void TestGetRootCategory()
        {
            var productCatalog = CreateProductCatalog();
            var result = productCatalog.GetProducts("catalog01_18661", 0);
            Console.WriteLine("Total number of products: " + result.Total);
            Console.WriteLine("Number of result pages: " + result.NumberOfPages);
        }

        [TestMethod]
        public void TestSearch()
        {
            var productCatalog = CreateProductCatalog();
            var result = productCatalog.Search("Black", "catalog01_18661_62776", 0);
            Console.WriteLine("Products found:");
            foreach (var product in result.Products)
            {
                Console.WriteLine(" " + product.Name + " (" + product.Id + ")");
            }
            Console.WriteLine("Number of result pages: " + result.NumberOfPages);
        }

        [TestMethod]
        public void TestGetProduct()
        {
            var productCatalog = CreateProductCatalog();
            FredhopperProduct product = (FredhopperProduct) productCatalog.GetProduct("008010231960");
            Console.WriteLine("Product Name: " + product.Name);
            Console.WriteLine("Product Thumbnail URL: " + product.Thumbnail?.Url);
            Console.WriteLine("Product Thumbnail MIME: " + product.Thumbnail?.Mime);
            Console.WriteLine("Description:" + SecurityElement.Escape(product.Description));         
            Console.WriteLine("Price: " + product.Price);
            foreach ( var attribute in product.AdditionalAttributes )
            {
                var key = attribute.Key.Substring(0, 1).ToUpper() + attribute.Key.Substring(1);

                if (attribute.Value is List<string>)
                {
                    Console.WriteLine(key + ": \n\t" + string.Join("\n\t", attribute.Value as List<string>));
                }
                else
                {
                    Console.WriteLine(key + ": " + attribute.Value);
                }
            }
        }

        [TestMethod]
        public void TestReadProductImage()
        {
         
            var productCatalog = CreateProductCatalog();
            Product product = productCatalog.GetProduct("061010828997");
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

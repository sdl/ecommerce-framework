using Microsoft.VisualStudio.TestTools.UnitTesting;
using SDL.ECommerce.Api;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.OData
{
    [TestClass]
    public class UnitTests
    {

        protected ECommerceClient ECommerceClient
        {
            get
            {
                return new ECommerceClient("http://preview:8097/ecommerce.svc", "en_GB");
            }
        }

        [TestMethod]
        public void TestGetTopLevelCategories()
        {
            var categories = ECommerceClient.CategoryService.GetTopLevelCategories();
            Console.WriteLine("  Top level categories:");
            Console.WriteLine("#########################");
            foreach ( var category in categories )
            {
                Console.WriteLine("Category: " + category.Name + " (" + category.Id + ")");
            }
        }

        [TestMethod]
        public void TestGetSpecificCategory()
        {
            var categoryService = ECommerceClient.CategoryService;
            var category = categoryService.GetCategoryById("catalog01_18661_128622");
            var category2 = categoryService.GetCategoryById("catalog01_18661_128622");
            Assert.IsTrue(category == category2, "Category data is not cached correctly");
           
            Console.WriteLine("  Category info (" + category.Id + ")");
            Console.WriteLine("#############################");
            Console.WriteLine("Category Name: " + category.Name);
            Console.WriteLine("Subcategories: ");
          
            foreach ( var subCategory in category.Categories )
            {
                Console.WriteLine("  " + subCategory.Name + "(" + subCategory.Id + ")");
            }
            
        }

        [TestMethod]
        public void TestGetCategoryByPath()
        {
            var category = ECommerceClient.CategoryService.GetCategoryByPath("/women/accessories/watches");
            Console.WriteLine("  Category info (" + category.Id + ")");
            Console.WriteLine("#############################");
            Console.WriteLine("Category Name: " + category.Name);
        }

        [TestMethod]
        public void TestSearchWithSearchPhrase()
        {
            var query = new Query { SearchPhrase = "red" };
            var result = ECommerceClient.QueryService.Query(query);
            PrintQueryResult(result);
        }

        [TestMethod]
        public void TestSearchWithSearchPhraseAndCategory()
        {
            var query = new Query { CategoryId = "catalog01_18661", SearchPhrase = "red" };
            var result = ECommerceClient.QueryService.Query(query);
            PrintQueryResult(result);
        }

        [TestMethod]
        public void TestSearchWithFacets()
        {
            var query = new Query {
                CategoryId = "catalog01_18661",
                Facets = { new FacetParameter("brand", "dkny" ) }
            };
            var result = ECommerceClient.QueryService.Query(query);
            PrintQueryResult(result);
        }

        [TestMethod]
        public void TestGetProductDetails()
        {
            // TODO: Update with product detail result instead

            var product = ECommerceClient.DetailService.GetDetail("008010111647");
            Console.WriteLine("  Product: " + product.Id);
            Console.WriteLine("#########################");
            Console.WriteLine("Name: " + product.Name);
            Console.WriteLine("Description: " + product.Description);
            Console.WriteLine("Price: " + product.Price);
            Console.WriteLine("Image URL: " + product.PrimaryImageUrl);
            Console.WriteLine("Attributes: ");
            foreach ( var attribute in product.Attributes )
            {
                if ( attribute.Value.GetType() == typeof(string) )
                {
                    Console.WriteLine(attribute.Key + ": " + attribute.Value);
                }
                else
                {
                    IList<string> values = (IList<string>) attribute.Value;
                    Console.WriteLine(attribute.Key + ": " + string.Join(",", values.ToArray()));
                }
               
            }
        }

        void PrintQueryResult(ProductQueryResult result)
        {
            Console.WriteLine("  Search Results:");
            Console.WriteLine("####################");
            Console.WriteLine("Total Count: " + result.TotalCount);
            Console.WriteLine("First " + result.ViewSize + " products:");
            foreach (var product in result.Products)
            {
                Console.WriteLine("Product ID: " + product.Id + ", Name: " + product.Name);
            }
            foreach (var facetGroup in result.FacetGroups)
            {
                Console.WriteLine("  FacetGroup: " + facetGroup.Title);
                foreach (var facet in facetGroup.Facets)
                {
                    Console.WriteLine("   " + facet.Title + " (" + facet.Count + ")");
                }
            }
        }
    }
}

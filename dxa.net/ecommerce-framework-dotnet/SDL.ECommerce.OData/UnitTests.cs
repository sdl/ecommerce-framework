using Microsoft.VisualStudio.TestTools.UnitTesting;
using SDL.ECommerce.Api;
using SDL.ECommerce.Api.Model;
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
                return new ECommerceClient("http://preview:8097/ecommerce.svc", "en-US");
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
            var queryService = ECommerceClient.QueryService;
            var query = new Query { CategoryId = "catalog01_18661", SearchPhrase = "red" };
            var result = queryService.Query(query);
            PrintQueryResult(result);
            var nextResult = queryService.Query(query.Next(result));
            Console.WriteLine("\n======================================================");
            Console.WriteLine("      NEXT " + nextResult.ViewSize + " PRODUCTS ");
            Console.WriteLine("======================================================\n");
            PrintQueryResult(nextResult);
        }

        [TestMethod]
        public void TestSearchCategory()
        {
            var query = new Query { CategoryId = "catalog01_18661" };
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
        public void TestSearches()
        {
            var queryService = ECommerceClient.QueryService;
            Console.WriteLine("Search request #1:");
            var query = new Query
            {
                CategoryId = "catalog01_18661"
            };
            var result = queryService.Query(query);
            Console.WriteLine("  Breadcrumbs: " + result.Breadcrumbs.Count);
            Console.WriteLine("Search request #2:");
            var query2 = new Query
            {
                CategoryId = "catalog01_18661",
                Facets = { new FacetParameter("brand", "dkny") }
            };
            var result2 = queryService.Query(query2);
            Console.WriteLine("  Breadcrumbs: " + result2.Breadcrumbs.Count);
            Console.WriteLine("Search request #3:");
            var query3 = new Query
            {
                CategoryId = "catalog01_18661"
            };
            var result3 = queryService.Query(query);
            Console.WriteLine("  Breadcrumbs: " + result3.Breadcrumbs.Count);



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
            Console.WriteLine("Categories: ");
            foreach ( var category in product.Categories )
            {
                Console.WriteLine("  " + category.Name);
            }
            Console.WriteLine("Breadcrumbs: ");
            foreach ( var breadcrumb in product.Breadcrumbs )
            {
                Console.WriteLine("  " + breadcrumb.Title);
            }
            Console.WriteLine("Promotions: ");
            foreach ( var promotion  in product.Promotions )
            {
                Console.WriteLine("  " + product.Name);
            }
        }

        [TestMethod]
        public void TestGetInContextMenu()
        {
            var menu = ECommerceClient.EditService.GetInContextMenuItems(new Query { CategoryId = "catalog01_18661", SearchPhrase = "red" });
            Console.WriteLine("  In-Context Edit Menu");
            Console.WriteLine("#########################");
            Console.WriteLine("Title: " + menu.Title);
            foreach ( var menuItem in menu.MenuItems )
            {
                Console.WriteLine("  " + menuItem.Title + " => " + menuItem.Url);
            }
        }

        void PrintQueryResult(IProductQueryResult result)
        {
            Console.WriteLine("  Search Results:");
            Console.WriteLine("####################");
            Console.WriteLine("Total Count: " + result.TotalCount);
            Console.WriteLine("Start index: " + result.StartIndex);
            Console.WriteLine("Current set: " + result.CurrentSet);
            Console.WriteLine("First " + result.ViewSize + " products:");
            foreach (var product in result.Products)
            {
                Console.WriteLine("Product ID: " + product.Id + ", Name: " + product.Name);
            }
            Console.WriteLine(" Facets:");
            Console.WriteLine("--------------------------");
            foreach (var facetGroup in result.FacetGroups)
            {
                Console.WriteLine("  FacetGroup: " + facetGroup.Title);
                Console.WriteLine("  Edit URL: " + facetGroup.EditUrl);
                foreach (var facet in facetGroup.Facets)
                {
                    Console.WriteLine("   " + facet.Title + " (" + facet.Count + ")");
                }
            }
            Console.WriteLine(" Promotions:");
            Console.WriteLine("--------------------------");
            foreach ( var promotion in result.Promotions )
            {
                Console.WriteLine("Name: " + promotion.Name);
            }
            Console.WriteLine(" Breadcrumbs:");
            Console.WriteLine("--------------------------");
            foreach ( var breadcrumb in result.Breadcrumbs )
            {
                Console.WriteLine("  Breadcrumb: " + breadcrumb.Title + " (category: " + breadcrumb.IsCategory + ")");
            }
        }
    }
}

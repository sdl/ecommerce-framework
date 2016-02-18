using RestSharp;
using SDL.ECommerce.Hybris.API.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Runtime.Caching;

namespace SDL.Ecommerce.Hybris.API
{
    /// <summary>
    /// Client to Hybris OCC v1 REST interface
    /// </summary>
    internal class HybrisClient
    {

        private readonly string uri;
        private readonly string login;
        private readonly string password;
        private readonly string activeCatalogVersion;

        private RestClient client;

        // TODO: Have the cache expiry times configurable
        private ObjectCache cache;


        public HybrisClient(string uri, string login, string password, string activeCatalogVersion)
        {
            this.uri = uri;
            this.login = login;
            this.password = password;
            this.activeCatalogVersion = activeCatalogVersion;

            this.client = new RestClient(this.uri);
            this.client.Authenticator = new HttpBasicAuthenticator(this.login, this.password);
            this.cache = MemoryCache.Default;
        }

        public List<Category> GetAllCategories()
        {
            string cacheKey = "ALL_CATEGORIES";
            List<Category> categories = this.cache.Get(cacheKey) as List<Category>;
            if (categories == null)
            {
                var request = new RestRequest(this.activeCatalogVersion, Method.GET);
                request.AddParameter("options", "CATEGORIES,SUBCATEGORIES");
                request.RequestFormat = DataFormat.Xml;
                var response = this.client.Execute<CatalogVersion>(request);
                this.SetParentCategories(response.Data.categories, null);
                categories = response.Data.categories;
                this.cache.Add(cacheKey, categories, DateTime.Now.AddMinutes(5));
            }
            return categories;
        }

        /// <summary>
        /// Gets the category.
        /// </summary>
        /// <param name="categoryCode">The category code.</param>
        /// <returns>the Category</returns>
        public Category GetCategory(string categoryCode)
        {
            string cacheKey = "CAT_" + categoryCode;
            Category category = this.cache.Get(cacheKey) as Category;
            if (category == null)
            {
                var request = new RestRequest(this.activeCatalogVersion + "/categories/{categoryCode}", Method.GET);
                request.AddParameter("options", "PRODUCTS");
                request.AddUrlSegment("categoryCode", categoryCode);
                var response = this.client.Execute<Category>(request);
                category = response.Data;
                this.cache.Add(cacheKey, category, DateTime.Now.AddMinutes(5));
            }
            return category;
        }

        public SearchResult Search(string searchPhrase, int pageSize, int currentPage = 0, string sort = null, List<FacetPair> facets = null)
        {
            if ( searchPhrase == null )
            {
                searchPhrase = "";
            }
            if ( sort == null )
            {
                sort = "";
            }
            string query = RestSharp.Contrib.HttpUtility.UrlEncode(searchPhrase) + ":" + sort;
            if ( facets != null && facets.Count() > 0 )
            {
                foreach ( var facet in facets )
                {
                    query += facet.Id + ":" + facet.Value;
                }
            }
            var request = new RestRequest("/products?query", Method.GET);
            request.AddParameter("query", query);
            request.AddParameter("pageSize", pageSize);
            request.AddParameter("currentPage", currentPage);
            var response = this.client.Execute<SearchResult>(request);
            return response.Data;
        }

        public Product GetProduct(string productUri)
        {
            string cacheKey = "PROD_" + productUri;
            Product product = this.cache.Get(cacheKey) as Product;
            if (product == null)
            {
                var request = new RestRequest("/products/{productUri}", Method.GET);
                request.AddUrlSegment("productUri", productUri);
                request.AddParameter("options", "BASIC,DESCRIPTION,GALLERY,CATEGORIES,PROMOTIONS,STOCK,REVIEW,CLASSIFICATION,REFERENCES,PRICE");
                var response = this.client.Execute<Product>(request);
                if (response.StatusCode == HttpStatusCode.OK && response.Data != null)
                {
                    product = response.Data;
                    this.cache.Add(cacheKey, product, DateTime.Now.AddMinutes(5));
                }
                else
                {
                    throw new Exception(response.ErrorMessage);
                }
            }
            return product;
        }

        /// <summary>
        /// Sets the parent categories.
        /// </summary>
        /// <param name="list">The list.</param>
        /// <param name="parent">The parent.</param>
        private void SetParentCategories(List<Category> list, Category parent)
        {
            foreach (var category in list)
            {
                category.Parent = parent;
                this.SetParentCategories(category.subcategories, category);
            }
        }
    }
}

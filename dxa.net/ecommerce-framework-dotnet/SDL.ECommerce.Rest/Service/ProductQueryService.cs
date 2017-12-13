using SDL.ECommerce.Api.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SDL.ECommerce.Api.Model;
using RestSharp;
using SDL.ECommerce.Rest.Model;
using Newtonsoft.Json;

namespace SDL.ECommerce.Rest.Service
{
    /// <summary>
    /// Product Query Service
    /// </summary>
    class ProductQueryService : IProductQueryService
    {

        private RestClient restClient;

        public ProductQueryService(RestClient restClient)
        {
            this.restClient = restClient;
        }

        public IProductQueryResult Query(Query query)
        {

            var request = new RestRequest("/product/query", Method.GET);
            // Category
            //
            if (query.Category != null)
            {
                request.AddParameter("categoryId", query.Category.Id);
            }
            else if (query.CategoryId != null)
            {
                request.AddParameter("categoryId", query.CategoryId);
            }

            // Search phrase
            //
            if (query.SearchPhrase != null)
            {
                request.AddParameter("searchPhrase", query.SearchPhrase);
            }

            // Facets
            //
            if (query.Facets != null && query.Facets.Count > 0)
            {
                var facetsString = new StringBuilder();
                for (int i = 0; i < query.Facets.Count; i++)
                {
                    var facet = query.Facets[i];
                    facetsString.Append(facet.ToUrl());
                    if (i + 1 < query.Facets.Count)
                    {
                        facetsString.Append("&");
                    }
                }
                request.AddParameter("facets", facetsString.ToString());
            }
            if (query.StartIndex != null)
            {
                request.AddParameter("startIndex", query.StartIndex);
            }
            if (query.ViewSize != null)
            {
                request.AddParameter("viewSize", query.ViewSize);
            }
            if (query.ViewType != null)
            {
                request.AddParameter("viewType", query.ViewType.ToString());
            }

            // TODO: Add Async I/O here !!!!
            var response = this.restClient.Execute(request);
            if ( response.StatusCode == System.Net.HttpStatusCode.OK )
            {
                return JsonConvert.DeserializeObject<ProductQueryResult>(response.Content);
            }
            throw new Exception("Failure when doing product query. Error Code: " + response.StatusCode + ", Error Message: " + response.ErrorMessage);
        }
    }
}

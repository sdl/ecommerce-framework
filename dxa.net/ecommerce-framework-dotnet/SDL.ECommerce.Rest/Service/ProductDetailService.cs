using Newtonsoft.Json;
using RestSharp;
using SDL.ECommerce.Api.Service;
using SDL.ECommerce.Rest.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Rest.Service
{
    /// <summary>
    /// E-Commerce Product Detail Service
    /// </summary>
    public class ProductDetailService : IProductDetailService
    {
        private RestClient restClient;
        private IProductCategoryService productCategoryService;

        public ProductDetailService(RestClient restClient, IProductCategoryService productCategoryService)
        {
            this.restClient = restClient;
            this.productCategoryService = productCategoryService;
        }

        public IProduct GetDetail(string productId)
        {
            return GetDetail(productId, null);
        }

        public IProduct GetDetail(string productId, IDictionary<string, string> variantAttributes)
        {
            var request = new RestRequest("/product/" + productId, Method.GET);
            if ( variantAttributes != null )
            {
                foreach ( var variantAttribute in variantAttributes )
                {
                    request.AddParameter(variantAttribute.Key, variantAttribute.Value);
                }
            }
            var response = this.restClient.Execute(request);
            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                var product = JsonConvert.DeserializeObject<Product>(response.Content);
                product.ProductCategoryService = productCategoryService;
                return product;
            }
            else if ( response.StatusCode == System.Net.HttpStatusCode.NotFound)
            {
                return null;
            }
            throw new Exception("Could not get product details. Error Code: " + response.StatusCode + ", Error Message: " + response.ErrorMessage); 
        }
    }
}

using Sdl.Web.Delivery.DiscoveryService;
using Sdl.Web.Delivery.Service;
using SDL.ECommerce.Api;
using SDL.ECommerce.Api.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.OData
{
    // Temporary solution for the client config
    // TODO: Use the standard approach
    //
    class SimpleServiceConfiguration : IServiceConfiguration
    {

        public string ClientId
        {
            get
            {
                throw new NotImplementedException();
            }
        }

        public string ClientSecret
        {
            get
            {
                throw new NotImplementedException();
            }
        }

        public bool IsOAuthEnabled
        {
            get
            {
                return false;
            }
        }

        public Uri ServiceEndpoint
        {
            get; set;
            
        }

        public int Timeout
        {
            get; set;
        }

        public Uri TokenServiceEndpoint
        {
            get
            {
                throw new NotImplementedException();
            }
        }
    }

    /// <summary>
    /// ECommerce Client. Is the single entry point for all kind interactions with the E-Commerce OData micro service.
    /// </summary>
    public class ECommerceClient : IECommerceClient
    {
        private ODataV4Service service;
        private ProductCategoryService categoryService;
        private ProductQueryService queryService;
        private ProductDetailService detailService;
       
        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="endpointAddress"></param>
        /// <param name="locale"></param>
        public ECommerceClient(String endpointAddress, String locale)
        {
            var serviceConfig = new SimpleServiceConfiguration();
            serviceConfig.ServiceEndpoint = new Uri(endpointAddress + "/" + locale);
            serviceConfig.Timeout = 1000;

            IOAuthTokenProvider defaultTokenProvider = DiscoveryServiceProvider.DefaultTokenProvider;
            service = new ODataV4Service(new ECommerce(serviceConfig.ServiceEndpoint), serviceConfig, defaultTokenProvider);

        }

        /// <summary>
        /// Get category service
        /// </summary>
        public IProductCategoryService CategoryService
        {
            get
            {
                if (categoryService == null)
                {
                    categoryService = new ProductCategoryService(this.service);
                }
                return categoryService;
            }
        }

        /// <summary>
        /// Get query service
        /// </summary>
        public IProductQueryService QueryService
        {
            get
            {
                if ( queryService == null )
                {
                    queryService = new ProductQueryService(this.service);
                }
                return queryService;
            }
        }

        public IProductDetailService DetailService
        {
            get
            {
                if ( detailService == null )
                {
                    detailService = new ProductDetailService(this.service);
                }
                return detailService;
            }
        }

    }
}

using Sdl.Web.Delivery.DiscoveryService;
using Sdl.Web.Delivery.Service;
using SDL.ECommerce.Api;
using SDL.ECommerce.Api.Service;
using System;

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
        private IECommerceODataV4Service odataService;
        private IProductCategoryService categoryService;
        private IProductQueryService queryService;
        private IProductDetailService detailService;
        private ICartService cartService;
        private IEditService editService;
       
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
            this.odataService = new ECommerceODataV4Service(new ECommerceODataService(serviceConfig.ServiceEndpoint), serviceConfig, defaultTokenProvider);
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
                    categoryService = new ProductCategoryService(this.odataService);
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
                    queryService = new ProductQueryService(this.odataService);
                }
                return queryService;
            }
        }

        /// <summary>
        /// Get product detail service
        /// </summary>
        public IProductDetailService DetailService
        {
            get
            {
                if ( detailService == null )
                {
                    detailService = new ProductDetailService(this.odataService);
                }
                return detailService;
            }
        }

        /// <summary>
        /// Get cart service
        /// </summary>
        public ICartService CartService
        {
            get
            {
                if ( cartService == null )
                {
                    cartService = new CartService(this.odataService);
                }
                return cartService;
            }
        }

        /// <summary>
        /// Get edit service
        /// </summary>
        public IEditService EditService
        {
            get
            {
                if ( editService == null )
                {
                    editService = new EditService(this.odataService);
                }
                return editService;
            }
        }
    }
}

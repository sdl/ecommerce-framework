using SDL.ECommerce.Api;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SDL.ECommerce.Api.Service;
using RestSharp;
using SDL.ECommerce.Rest.Service;

namespace SDL.ECommerce.Rest
{
    public class ECommerceClient : IECommerceClient
    {

        private RestClient restClient;
        private IECommerceCacheProvider cacheProvider;
        private int categoryExpiryTimeout;

        private IProductCategoryService categoryService;
        private IProductDetailService productDetailService;
        private IProductQueryService productQueryService;
        private ICartService cartService;
        private IEditService editService;

        // TODO: Add dependency injection here!!!!

        public ECommerceClient(string endpointAddress, 
                               string locale, 
                               IECommerceCacheProvider cacheProvider,
                               int categoryExpiryTimeout)
        {
            this.restClient = new RestClient(endpointAddress + "/rest/v1/" + locale);
            this.cacheProvider = cacheProvider;
            this.categoryExpiryTimeout = categoryExpiryTimeout;
        }

        public ICartService CartService
        {
            get
            {
                if ( cartService == null)
                {
                    cartService = new CartService(this.restClient);
                }
                return cartService;
            }
        }

        public IProductCategoryService CategoryService
        {
            get
            {
                if ( categoryService == null )
                {
                    categoryService = new ProductCategoryService(this.restClient, this.categoryExpiryTimeout); 
                }
                return categoryService;
            }
        }

        public IProductDetailService DetailService
        {
            get
            {
               if ( productDetailService == null)
                {
                    productDetailService = new ProductDetailService(this.restClient, CategoryService, this.cacheProvider);
                }
                return productDetailService;
            }
        }

        public IEditService EditService
        {
            get
            {
                if ( editService == null)
                {
                    editService = new EditService(this.restClient, this.cacheProvider);
                }
                return editService;
            }
        }

        public IProductQueryService QueryService
        {
            get
            {
                if ( productQueryService == null )
                {
                    productQueryService = new ProductQueryService(this.restClient, this.cacheProvider);
                }
                return productQueryService;
            }
        }
    }
}

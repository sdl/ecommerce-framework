using SDL.ECommerce.Api;
using System;
using SDL.ECommerce.Api.Service;
using RestSharp;
using SDL.ECommerce.Rest.Service;

namespace SDL.ECommerce.Rest
{
    public class ECommerceClient : IECommerceClient
    {
        private readonly Func<Type, object> dependencies;
        private readonly RestClient restClient;
        private readonly IECommerceCacheProvider cacheProvider;
        private readonly int categoryExpiryTimeout;
        private readonly bool useSanitizedPathNames;

        private IProductCategoryService categoryService;
        private IProductDetailService productDetailService;
        private IProductQueryService productQueryService;
        private ICartService cartService;
        private IEditService editService;

        public ECommerceClient(string endpointAddress, 
                               string locale, 
                               IECommerceCacheProvider cacheProvider,
                               int categoryExpiryTimeout,
                               bool useSanitizedPathNames,
                               Func<Type, object> dependencies = null)
        {
            this.restClient = new RestClient(endpointAddress + "/rest/v1/" + locale);
            this.cacheProvider = cacheProvider;
            this.categoryExpiryTimeout = categoryExpiryTimeout;
            this.useSanitizedPathNames = useSanitizedPathNames;
            this.dependencies = dependencies;
        }

        public ICartService CartService
        {
            get
            {
                if ( cartService == null)
                {
                    cartService = Resolve<ICartService>() ?? new CartService(this.restClient);
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
                    categoryService = Resolve<IProductCategoryService>() ?? new ProductCategoryService(this.restClient, this.categoryExpiryTimeout, this.useSanitizedPathNames); 
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
                    productDetailService = Resolve<IProductDetailService>() ?? new ProductDetailService(this.restClient, CategoryService, this.cacheProvider);
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
                    editService = Resolve<IEditService>() ?? new EditService(this.restClient, this.cacheProvider);
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
                    productQueryService = Resolve<IProductQueryService>() ?? new ProductQueryService(this.restClient, this.cacheProvider);
                }
                return productQueryService;
            }
        }

        private T Resolve<T>()
        {
            var dependency = dependencies?.Invoke(typeof(T));

            if (dependency == null)
            {
                return default(T);
            }

            return (T)dependency;
        }
    }
}

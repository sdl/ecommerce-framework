using Microsoft.OData.Client;

using Sdl.Web.Delivery.Service;

namespace SDL.ECommerce.OData
{
    public class ECommerceODataV4Service : ODataV4Service, IECommerceODataV4Service
    {
        public ECommerceODataV4Service(ECommerceODataService service, IServiceConfiguration serviceConfig)
            : base(service, serviceConfig)
        {
        }

        public ECommerceODataV4Service(ECommerceODataService service, IServiceConfiguration serviceConfig, IOAuthTokenProvider tokenProvider)
            : base(service, serviceConfig, tokenProvider)
        {
        }

        public DataServiceQuery<Category> Categories => (Service as ECommerceODataService)?.Categories;

        public DataServiceQuery<Product> Products => (Service as ECommerceODataService)?.Products;

        public DataServiceQuery<Cart> Carts => (Service as ECommerceODataService)?.Carts;

        public DataServiceQuery<ProductQueryResult> ProductQueryResults => (Service as ECommerceODataService)?.ProductQueryResults;

        public DataServiceQuery<EditMenu> EditMenus => (Service as ECommerceODataService)?.EditMenus;
    }
}
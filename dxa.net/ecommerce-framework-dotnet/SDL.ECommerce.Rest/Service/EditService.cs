using SDL.ECommerce.Api.Service;
using SDL.ECommerce.Api.Model;
using RestSharp;
using Newtonsoft.Json;
using SDL.ECommerce.Rest.Model;
using SDL.ECommerce.Api;

namespace SDL.ECommerce.Rest.Service
{
    class EditService : IEditService
    {
        private IRestClient restClient;
        private IECommerceCacheProvider cacheProvider;

        public EditService(IRestClient restClient, IECommerceCacheProvider cacheProvider)
        {
            this.restClient = restClient;
            this.cacheProvider = cacheProvider;
        }

        public IEditMenu GetInContextMenuItems(Query query)
        {
            var request = new RestRequest("/editmenu/inContextMenuItems", Method.GET);
            var cacheKey = "";

            // Category
            //
            if (query.Category != null)
            {
                request.AddParameter("categoryId", query.Category.Id);
                cacheKey = query.Category.Id;
            }
            else if (query.CategoryId != null)
            {
                request.AddParameter("categoryId", query.CategoryId);
                cacheKey = query.CategoryId;
            }

            // Search phrase
            //
            if (query.SearchPhrase != null)
            {
                request.AddParameter("searchPhrase", query.SearchPhrase);
                cacheKey = ":" + query.SearchPhrase;
            }

            IEditMenu editMenu;
            if (!cacheProvider.TryGet(CacheRegion.ECommerceInContextMenu, cacheKey, out editMenu))
            {
                var response = this.restClient.Execute(request);
                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {
                    editMenu = JsonConvert.DeserializeObject<EditMenu>(response.Content);
                    cacheProvider.Store<IEditMenu>(CacheRegion.ECommerceInContextMenu, cacheKey, editMenu);
                }
                else
                {
                    // Return NULL which will make the in-context menu not available for editors
                    //
                    return null;
                }              
            }
            return editMenu;
        }
    }
}

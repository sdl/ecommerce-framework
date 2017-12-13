using SDL.ECommerce.Api.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SDL.ECommerce.Api.Model;
using RestSharp;
using Newtonsoft.Json;
using SDL.ECommerce.Rest.Model;

namespace SDL.ECommerce.Rest.Service
{
    class EditService : IEditService
    {
        private RestClient restClient;

        public EditService(RestClient restClient)
        {
            this.restClient = restClient;
        }

        public IEditMenu GetInContextMenuItems(Query query)
        {
            var request = new RestRequest("/editmenu/inContextMenuItems", Method.GET);

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

            var response = this.restClient.Execute(request);
            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
               return JsonConvert.DeserializeObject<EditMenu>(response.Content);             
            }

            // Return NULL which will make the in-context menu not available for editors
            //
            return null; 
        }
    }
}

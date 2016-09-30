using SDL.ECommerce.Api;
using SDL.ECommerce.OData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Configuration;

namespace SDL.ECommerce.DXA
{
    public class ECommerce
    {
        private static IDictionary<string, IECommerceClient> clients = new Dictionary<string,IECommerceClient>();
        public static IECommerceClient GetClient(string locale)
        {
            IECommerceClient client = null;
            if (!clients.TryGetValue(locale, out client))
            {
                client = Create(locale);
                clients.Add(locale, client);
            }
            return client;
        }

        private static IECommerceClient Create(string locale)
        {
            var endpointAddress = WebConfigurationManager.AppSettings["ecommerce-service-uri"];
            // TODO: Get token service data here as well
            return new ECommerceClient(endpointAddress, locale);
        }
        
    }
}

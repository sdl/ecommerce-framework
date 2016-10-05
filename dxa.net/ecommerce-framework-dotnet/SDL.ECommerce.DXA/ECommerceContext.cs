using Sdl.Web.Mvc.Configuration;
using SDL.ECommerce.Api;
using SDL.ECommerce.OData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Configuration;

namespace SDL.ECommerce.DXA
{
    /// <summary>
    /// E-Commerce Context
    /// </summary>
    public class ECommerceContext
    {
        public const string QUERY_RESULT = "QueryResult";
        public const string URL_PREFIX = "UrlPrefix";
        public const string FACETS = "Facets";
        public const string PRODUCT = "Product";

        private static IDictionary<string, IECommerceClient> clients = new Dictionary<string,IECommerceClient>();
        private static IECommerceLinkResolver linkResolver = new DXALinkResolver();

        /// <summary>
        /// Cline to E-Commerce services
        /// </summary>
        public static IECommerceClient Client
        {
            get
            {
                string locale = WebRequestContext.Localization.Culture;
                IECommerceClient client = null;
                if (!clients.TryGetValue(locale, out client))
                {
                    client = Create(locale);
                    clients.Add(locale, client);
                }
                return client;
            }
            
        }

        /// <summary>
        /// Link resolver for E-Commerce items
        /// </summary>
        public static IECommerceLinkResolver LinkResolver
        {
            get
            {
                return linkResolver;
            }
        }

        private static IECommerceClient Create(string locale)
        {
            var endpointAddress = WebConfigurationManager.AppSettings["ecommerce-service-uri"];
            // TODO: Get token service data here as well
            return new ECommerceClient(endpointAddress, locale);
        }


        public static object Get(string key)
        {
            return HttpContext.Current == null ? null : HttpContext.Current.Items["ECOM-" + key];
        }

        public static object Set(string key, object value)
        {
            if (HttpContext.Current != null)
            {
                HttpContext.Current.Items["ECOM-" + key] = value;
            }
            return value;
        }

        public static string LocalizePath(string url)
        {
            string path = WebRequestContext.Localization.Path;
            if (!String.IsNullOrEmpty(path))
            {
                if (path.EndsWith("/"))
                {
                    url = path + (url.StartsWith("/") ? url.Substring(1) : url);
                }
                else
                {
                    url = path + (url.StartsWith("/") ? url : '/' + url);
                }
            }
            return url;
        }
    }
}

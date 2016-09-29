using Sdl.Web.Delivery.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.OData
{
    /// <summary>
    /// E-Commerce Product Query Service
    /// </summary>
    // TODO: Implement an interface here from the API project
    //
    public class ProductQueryService
    {
        private ODataV4Service service;

        /// <summary>
        /// Constructor (only available internally)
        /// </summary>
        /// <param name="service"></param>
        internal ProductQueryService(ODataV4Service service)
        {
            this.service = service;
        }

        /// <summary>
        /// Execute query towards the product catalog.
        /// </summary>
        /// <param name="query"></param>
        /// <returns></returns>
        public ProductQueryResult Query(Query query)
        {
            ODataV4ClientFunction func = new ODataV4ClientFunction("ProductQuery");
           
            // Category
            //
            if ( query.Category != null )
            {
                func.WithParam("categoryId", query.Category.Id);
            }
            else if ( query.CategoryId != null )
            {
                func.WithParam("categoryId", query.CategoryId);
            }

            // Search phrase
            //
            if (query.SearchPhrase != null)
            {
                func.WithParam("searchPhrase", query.SearchPhrase);
            }

            // Facets
            //
            if ( query.Facets != null )
            {
                var facetsString = new StringBuilder();
                for (int i = 0; i < query.Facets.Count; i++)
                {
                    var facet = query.Facets[i];
                    facetsString.Append(facet.ToUrl());
                    if (i + 1 < query.Facets.Count )
                    {
                        facetsString.Append("&");
                    }
                }
                func.WithParam("facets", facetsString.ToString());
            }
            
            // TODO: Add pagination here!!!

            return Enumerable.FirstOrDefault<ProductQueryResult>(this.service.Execute<ProductQueryResult>(func));
        }
    }
}

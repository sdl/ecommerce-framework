using System.Collections.Generic;
using System.Web;

using SDL.ECommerce.Api;

namespace SDL.ECommerce.DXA.Servants
{
    public class HttpContextServant : IHttpContextServant
    {
        public IList<FacetParameter> GetFacetParametersFromRequest(HttpContextBase httpContext)
        {
            var facetParameters = new List<FacetParameter>();

            var queryParams = httpContext.Request.QueryString;
            foreach (var key in queryParams.Keys) // TODO: Use AllKeys here
            {
                string paramName = key.ToString();
                if (!paramName.Equals("q") && !paramName.Equals("startIndex"))
                {
                    // TODO:  Use a global facet map here instead to validate against
                    var paramValue = queryParams[paramName];
                    facetParameters.Add(new FacetParameter(paramName, paramValue));
                }
            }

            return facetParameters;
        }
    }
}
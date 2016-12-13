using System.Collections.Generic;
using System.Web;
using System.Linq;

using SDL.ECommerce.Api;

namespace SDL.ECommerce.DXA.Servants
{
    public class HttpContextServant : IHttpContextServant
    {
        private readonly string[] _excludedParameters = { "q", "startIndex" };

        public IList<FacetParameter> GetFacetParametersFromRequest(HttpContextBase httpContext)
        {
            var facetParameters = new List<FacetParameter>();

            var queryParams = httpContext.Request.QueryString;

            foreach (var key in queryParams.AllKeys.Except(_excludedParameters))
            {
                // TODO:  Use a global facet map here instead to validate against
                var paramValue = queryParams[key];
                facetParameters.Add(new FacetParameter(key, paramValue));
            }

            return facetParameters;
        }
    }
}
using com.fredhopper.lang.query;
using SDL.ECommerce.Ecl;
using SDL.Fredhopper.Ecl.FredhopperWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.Fredhopper.Ecl
{
    public class FredhopperClient
    {
        private FASWebServiceClient fhClient;

        public FredhopperClient()
        {
            this.fhClient = new FASWebServiceClient();

            // TODO: Set the endpoint here in a correct way
            // TODO: Add access credentials here
            /*
            CredentialCache credCache = new CredentialCache();
            NetworkCredential netCred = new NetworkCredential("_username_", "_password_", "");
            credCache.Add(new Uri(webservice.Url), "Basic", netCred);
            webservice.Credentials = credCache;
            */

           
        }

        public IList<Category> GetRootCategories()
        {
            Query query = new Query("fh_location=//catalog01/en-GB");
            page fhPage = this.fhClient.getAll(query.toString());
            universe universe = this.GetUniverse(fhPage);

            var categories = new List<Category>();
            var facetmap = universe.facetmap[0];
            var filters = facetmap.filter;
            foreach ( var filter  in filters )
            {
                if ( filter.basetype == attributeTypeFormat.cat )
                {
                    foreach ( var section in filter.filtersection )
                    {
                        var category = new FredhopperCategory(section.value.Value, section.link.name, null);
                        categories.Add(category);
                    }
                }
            }
            return categories;
        }

        private universe GetUniverse(page fhPage)
        {
            foreach ( var universe in fhPage.universes )
            {
                if ( universe.type == universeType.selected )
                {
                    return universe;

                }
            }
            return null;
        }

        /*
        
            Build the query
Query query = new Query("fh_location=//catalog01/en_US");
        */
    }
}

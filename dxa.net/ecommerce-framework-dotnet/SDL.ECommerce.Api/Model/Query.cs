using SDL.ECommerce.Api;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
  
    public class Query
    {  
        public Query()
        {
            Facets = new List<FacetParameter>();
        }        
        public ICategory Category { get; set; }
        public string CategoryId { get; set; }
        public string SearchPhrase { get; set; }
        public IList<FacetParameter> Facets { get; set; }
        public int? ViewSize { get; set; }   
    }
}

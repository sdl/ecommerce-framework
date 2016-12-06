using SDL.ECommerce.Api.Model;

using System.Collections.Generic;
using System.Linq;

namespace SDL.ECommerce.OData
{
    public partial class FacetGroup : IFacetGroup
    {
        ICollection<IFacet> IFacetGroup.Facets
        {
            get
            {
                return this.Facets.Cast<IFacet>().ToList();
            }
        }

        bool IFacetGroup.IsCategory
        {
            get
            {
                return (bool) this.IsCategory;
            }
        }
    }
}

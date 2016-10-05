using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SDL.ECommerce.Api;

namespace SDL.ECommerce.OData
{
    public partial class Location : ILocation
    {
        ICategoryRef ILocation.CategoryRef()
        {
            return this.CategoryRef;
        }

        List<FacetParameter> ILocation.Facets()
        {
            throw new NotImplementedException();
        }

        IProductRef ILocation.ProductRef()
        {
            return this.ProductRef;
        }

        string ILocation.StaticUrl()
        {
            return this.StaticUrl;
        }
    }
}

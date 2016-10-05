using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    public interface IBreadcrumb
    {
        string Title { get; }
        bool IsCategory { get; }
        ICategoryRef CategoryRef { get; } 
        IFacet Facet { get; }
    }
}

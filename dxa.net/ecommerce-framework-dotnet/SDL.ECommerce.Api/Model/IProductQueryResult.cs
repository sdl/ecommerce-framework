using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    /// <summary>
    /// Product query result interface.
    /// </summary>
    public interface IProductQueryResult
    {
        // TODO: Make a generic Result base interface so it can be shared with detail results

        int TotalCount { get; }

        int StartIndex { get; }

        int ViewSize { get; }

        int CurrentSet { get; }

        ILocation RedirectLocation { get; }

        ICollection<IQuerySuggestion> QuerySuggestions { get; }

        ICollection<IFacetGroup> FacetGroups { get; }

        ICollection<IProduct> Products { get; }

        ICollection<IPromotion> Promotions { get; }

        ICollection<IBreadcrumb> Breadcrumbs { get; }

        // ICollection<IPromotion> Promotions { get; }
    }
}

using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api
{
    /// <summary>
    /// Interface for E-Commerce Link Resolver.
    /// </summary>
    public interface IECommerceLinkResolver
    {
        /// <summary>
        /// Get link to category page.
        /// </summary>
        /// <param name="category"></param>
        /// <returns></returns>
        string GetCategoryLink(ICategory category);

        /// <summary>
        /// Get facet link.
        /// </summary>
        /// <param name="facet"></param>
        /// <returns></returns>
        string GetFacetLink(IFacet facet);

        /// <summary>
        /// Get an absolute facet link using a base category path (for flyouts etc).
        /// </summary>
        /// <param name="facet"></param>
        /// <param name="navigationBasePath"></param>
        /// <returns></returns>
        string GetAbsoluteFacetLink(IFacet facet, string navigationBasePath);

        /// <summary>
        /// Get breadcrumb link.
        /// </summary>
        /// <param name="breadcrumb"></param>
        /// <returns></returns>
        string GetBreadcrumbLink(IBreadcrumb breadcrumb);

        /// <summary>
        /// Get link to location, normally a category + a set of facets).
        /// </summary>
        /// <param name="location"></param>
        /// <returns></returns>
        string GetLocationLink(ILocation location);

        /// <summary>
        /// Get link to product detail page.
        /// </summary>
        /// <param name="product"></param>
        /// <returns></returns>
        string GetProductDetailLink(IProduct product);

    }
}

using System.Collections.Generic;
using SDL.ECommerce.Api;
using SDL.ECommerce.Api.Model;

/**
 * E-Commerce Product
 *
 * @author nic
 */
public interface IProduct
{

    /// <summary>
    /// Get product ID/SKU.
    /// </summary>
    string Id { get; }

    /// <summary>
    /// Get name of the product. This can be a localized name based on current language.
    /// </summary>
    string Name { get; }

    /// <summary>
    /// Get product price.
    /// </summary>
    IProductPrice Price { get; }

    /// <summary>
    /// Get product description. This can be localized based on the current language.
    /// </summary>
    string Description { get; }

    // TODO: Have images based on type, primary, thumbnail, zoom etc

    /// <summary>
    /// Get URL to thumbnail image.
    /// </summary>
    string ThumbnailUrl { get; }

    /// <summary>
    /// Get URL to primary image (used on detail pages etc).
    /// </summary>
    string PrimaryImageUrl { get; }

    /**
     * Get categories the product belong to.
     * @return list of categories
     */
    //List<ICategory> Categories { get; }

    /**
     * Get product facets.
     * @return list of product facets
     */
    // TODO: Convert this to an interface instead???
    //List<FacetParameter> Facets();

    /// <summary>
    /// Get all additional attributes of the product. Can for example be used in compare views etc.
    /// </summary>
    IDictionary<string, object> Attributes { get; }

    // TODO: Have breadcrumbs and promotions here???
}
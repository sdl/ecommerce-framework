using SDL.ECommerce.Api;
using System;
using System.Collections.Generic;

using SDL.ECommerce.Api.Model;
using System.Text;

namespace SDL.ECommerce.DXA
{
    public class DXALinkResolver : IECommerceLinkResolver
    {
        public string GetAbsoluteFacetLink(IFacet facet, string navigationBasePath)
        {
            string link;
            if ( facet.IsCategory )
            {
                var category = ECommerceContext.Client.CategoryService.GetCategoryById(facet.Value);
                link = this.GetNonContextualCategoryLink(category);
                link += this.GetFacetLink((IList<FacetParameter>)null);
            }
            else // facet
            {
                if ( facet.IsSelected )
                {
                    link = navigationBasePath + this.GetRemoveFacetLink(facet, null);
                }
                else
                {
                    link = navigationBasePath + this.GetAddFacetLink(facet, null);
                }
            }
            return link;
        }

        public string GetBreadcrumbLink(IBreadcrumb breadcrumb)
        {
            if (breadcrumb.IsCategory)
            {
                return ECommerceContext.Get(ECommerceContext.URL_PREFIX) + breadcrumb.CategoryRef.Path;
            }
            else
            {
                // Facet
                IList<FacetParameter> selectedFacets = (IList<FacetParameter>) ECommerceContext.Get(ECommerceContext.FACETS);
                return this.GetRemoveFacetLink(breadcrumb.Facet, selectedFacets);
            }
        }

        public string GetNonContextualCategoryLink(ICategory category)
        {
            var urlPrefix = ECommerceContext.LocalizePath("/c"); // Always use the category lister path  
            return urlPrefix + GetCategoryAbsolutePath(category);
        }

        public string GetCategoryLink(ICategory category)
        {
            string urlPrefix = (string) ECommerceContext.Get(ECommerceContext.URL_PREFIX);
            if ( urlPrefix == null || urlPrefix.Length == 0 )
            {
                urlPrefix = ECommerceContext.LocalizePath("/c"); // Fallback on default
            }
            return urlPrefix + GetCategoryAbsolutePath(category);
        }

        public string GetFacetLink(IFacet facet)
        {
            string link;
            IList<FacetParameter> selectedFacets = (IList<FacetParameter>) ECommerceContext.Get(ECommerceContext.FACETS);
            if ( facet.IsCategory )
            {
                var category = ECommerceContext.Client.CategoryService.GetCategoryById(facet.Value);
                link = this.GetCategoryLink(category);
                link += this.GetFacetLink(selectedFacets);
            }
            else // facet
            {
                if ( facet.IsSelected )
                {
                    link = this.GetRemoveFacetLink(facet, selectedFacets);
                }
                else
                {
                    link = this.GetAddFacetLink(facet, selectedFacets);
                }
            }
            return link;
        }

        public string GetFacetLink(IList<FacetParameter> selectedFacets)
        {
            if (selectedFacets == null || selectedFacets.Count == 0) { return ""; }
            StringBuilder sb = new StringBuilder();
            bool firstParam = true;
            foreach (var facet in selectedFacets)
            {
                if (firstParam)
                {
                    sb.Append("?");
                    firstParam = false;
                }
                else
                {
                    sb.Append("&");
                }
                sb.Append(facet.ToUrl());
            }
            return sb.ToString();
        }

        public string GetLocationLink(ILocation location)
        {
            if (location == null) { return ""; }
            if (location.StaticUrl != null)
            {
                return location.StaticUrl;
            }
            if (location.ProductRef != null)
            {
                return this.GetProductDetailLink(location.ProductRef.Id, location.ProductRef.Name);
            }
            StringBuilder link = new StringBuilder();
            if (location.CategoryRef != null)
            {
                string urlPrefix = (string) ECommerceContext.Get(ECommerceContext.URL_PREFIX);
                link.Append(urlPrefix + location.CategoryRef.Path);
            }
            if (location.Facets != null && location.Facets.Count > 0)
            {
                link.Append("?");
                for (int i = 0; i < location.Facets.Count; i++)
                {
                    FacetParameter facet = location.Facets[i];
                    link.Append(facet.ToUrl());
                    if (i + 1 < location.Facets.Count)
                    {
                        link.Append("&");
                    }
                }
            }
            return link.ToString();
        }

        public string GetProductDetailLink(IProduct product)
        {
            // TODO: Add support to resolve to dynamic links if product page is CMS made
            string productId;
            if ( product.VariantId != null )
            {
                productId = product.VariantId;
            }
            else
            {
                productId = product.Id;
            }
            return GetProductDetailLink(productId, product.Name);
        }

        protected string GetProductDetailLink(string productId, string productName)
        {
            // Handle some special characters on product IDs
            //
            productId = productId.Replace("+", "__plus__");

            if (productName != null)
            {
                // Generate a SEO friendly URL
                //
                String seoName = productName.ToLower().
                                             Replace(" ", "-").
                                             Replace("'", "").
                                             Replace("--", "").
                                             Replace("/", "-").
                                             Replace("®", "").
                                             Replace("&", "").
                                             Replace("\"", "");
                return ECommerceContext.LocalizePath("/p/") + seoName + "/" + productId;
            }
            return ECommerceContext.LocalizePath("/p/") + productId;
        }

        public string GetProductDetailVariantLink(IProduct product, string variantAttributeId, string variantAttributeValueId)
        {

            String productId = product.Id;

            // Approach one: Use product variant model (if available)
            //
            if (product.Variants != null && product.Variants.Count > 0)
            {

                var selectedAttributes = new Dictionary<string, string>();
                selectedAttributes.Add(variantAttributeId, variantAttributeValueId);
                if (product.VariantAttributes != null)
                {
                    foreach (var attribute in product.VariantAttributes)
                    {
                        if (!attribute.Id.Equals(variantAttributeId))
                        {
                            selectedAttributes.Add(attribute.Id, attribute.ValueId);
                        }
                    }
                }

                // Get matching variant based on the selected attributes
                //
                foreach (var variant in product.Variants)
                {
                    int matchingAttributes = 0;
                    foreach (String selectedAttributeId in selectedAttributes.Keys)
                    {
                        String selectedAttributeValueId = selectedAttributes[selectedAttributeId];
                        foreach (var attribute in variant.Attributes)
                        {
                            if (attribute.Id.Equals(selectedAttributeId) && attribute.ValueId.Equals(selectedAttributeValueId))
                            {
                                matchingAttributes++;
                                break;
                            }
                        }
                    }
                    if (matchingAttributes == selectedAttributes.Count)
                    {
                        productId = variant.Id;
                        break;
                    }
                }
            }
            // Approach two: Use variant attribute types (if available)
            //
            else if (product.VariantAttributeTypes != null && product.VariantAttributes != null)
            {

                var selectedAttributes = new Dictionary<string,string>();
                selectedAttributes.Add(variantAttributeId, variantAttributeValueId);
                foreach (var attributeType in product.VariantAttributeTypes)
                {
                    if (!attributeType.Id.Equals(variantAttributeId))
                    {
                        foreach (var valueType in attributeType.Values)
                        {
                            if (valueType.IsSelected)
                            {
                                selectedAttributes.Add(attributeType.Id, valueType.Id);
                            }
                        }
                    }
                }

                // Use the selected attributes to build a URL with the variant attributes as query parameters
                //
                String link = this.GetProductDetailLink(productId, product.Name);
                bool firstAttribute = true;
                foreach (var selectedAttributeId in selectedAttributes.Keys)
                {
                    if (firstAttribute)
                    {
                        link += "?";
                        firstAttribute = false;
                    }
                    else
                    {
                        link += "&";
                    }
                    link += selectedAttributeId + "=" + selectedAttributes[selectedAttributeId];
                }
                return link;
            }


            return this.GetProductDetailLink(productId, product.Name);
        }


        protected string GetCategoryAbsolutePath(ICategory category)
        {
            String path = "";
            ICategory currentCategory = category;
            while (currentCategory != null)
            {
                path = currentCategory.PathName + "/" + path;
                currentCategory = currentCategory.Parent;
            }
            return path;
        }

        protected string GetAddFacetLink(IFacet facet, IList<FacetParameter> selectedFacets)
        {

            StringBuilder sb = new StringBuilder();
            bool firstParam = true;
            bool foundFacet = false;
            if (selectedFacets != null)
            {
                foreach (var facetParam in selectedFacets)
                {
                    if (firstParam)
                    {
                        sb.Append("?");
                        firstParam = false;
                    }
                    else
                    {
                        sb.Append("&");
                    }
                    if (facetParam.Name.Equals(facet.Id) && !facetParam.ContainsValue(facet.Value))
                    {
                        sb.Append(facetParam.AddValueToUrl(facet.Value));
                        foundFacet = true;
                    }
                    else
                    {
                        sb.Append(facetParam.ToUrl());
                    }
                }
            }
            if (!foundFacet)
            {
                if (firstParam)
                {
                    sb.Append("?");
                }
                else
                {
                    sb.Append("&");
                }
                sb.Append(GetFacetUrl(facet));
            }
            return sb.ToString();
        }

        protected string GetRemoveFacetLink(IFacet facet, IList<FacetParameter> selectedFacets)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("?");
            bool firstParam = true;
            if (selectedFacets != null)
            {
                foreach (var facetParam in selectedFacets)
                {
                    string facetString;
                    if (facetParam.Name.Equals(facet.Id) && facetParam.ContainsValue(facet.Value))
                    {
                        facetString = facetParam.RemoveValueFromUrl(facet.Value);
                    }
                    else
                    {
                        facetString = facetParam.ToUrl();
                    }
                    if ( String.IsNullOrEmpty(facetString) )
                    {
                        continue;
                    }

                    if (firstParam)
                    {
                        firstParam = false;
                    }
                    else
                    {
                        sb.Append("&");
                    }
                    sb.Append(facetString);
                }
            }
          
            return sb.ToString();
        }

        protected static string GetFacetUrl(IFacet facet)
        {

            // TODO: This is already done by the FacetParameter class...

            String name = facet.Id;
            String value = facet.Value;
            switch (facet.Type )
            {
                case FacetType.RANGE:
                    value = facet.Values[0] + "-" + facet.Values[1];
                    break;
                case FacetType.MULTISELECT:
                    value = "";
                    for (int i = 0; i < facet.Values.Count; i++)
                    {
                        String facetValue = facet.Values[i];
                        value += facetValue;
                        if (i < facet.Values.Count - 1)
                        {
                            value += "|";
                        }
                    }
                    break;
                case FacetType.LESS_THAN:
                    value = "<" + facet.Value;
                    break;
                case FacetType.GREATER_THAN:
                    value = ">" + facet.Value;
                    break;
                case FacetType.SINGLEVALUE:
                default:
                    name += "_val";
                    break;

            }
            return name + "=" + value;
        }
    }
}
 
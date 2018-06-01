using Sdl.Web.Common.Configuration;
using Sdl.Web.Common.Interfaces;
using Sdl.Web.Common.Logging;
using Sdl.Web.Common.Models;
using Sdl.Web.Tridion.Mapping;
using SDL.ECommerce.Api;
using SDL.ECommerce.DXA.Factories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web;

namespace SDL.ECommerce.DXA
{
    /// <summary>
    /// Rich text processor that are able to process product & category links.
    /// Is an extension of the OOTB rich text processor.
    /// To use this processor, please update your Unity.config.
    /// </summary>
    public class ECommerceRichTextProcessor : IRichTextProcessor
    {
        private static readonly Regex PRODUCT_URI_REGEX = new Regex("ecom:product:(?<productId>[^:]*):uri", RegexOptions.Compiled);
        private static readonly Regex CATEGORY_URI_REGEX = new Regex("ecom:category:(?<categoryId>[^:]*):uri", RegexOptions.Compiled);

        private readonly IECommerceLinkResolver _linkResolver;
        private readonly IRichTextProcessor _defaultRichTextProcessor;

        public ECommerceRichTextProcessor()
        {
            _linkResolver = DependencyFactory.Current.Resolve<IECommerceLinkResolver>();

            // TODO: Make it possible to inject the fallback rich text processor
            _defaultRichTextProcessor = new DefaultRichTextProcessor();
        }

        public RichText ProcessRichText(string xhtml, Localization localization)
        {
            xhtml = PRODUCT_URI_REGEX.Replace(xhtml, m => ResolveProductUrl(m.Groups["productId"].Value));
            xhtml = CATEGORY_URI_REGEX.Replace(xhtml, m => ResolveCategoryUrl(m.Groups["categoryId"].Value));
            return _defaultRichTextProcessor.ProcessRichText(xhtml, localization);
        }

        private string ResolveProductUrl(string productId)
        {
            var product = ECommerceContext.Client.DetailService.GetDetail(productId);
            return _linkResolver.GetProductDetailLink(product);
        }

        private string ResolveCategoryUrl(string categoryId)
        {
            var category = ECommerceContext.Client.CategoryService.GetCategoryById(categoryId);
            return _linkResolver.GetCategoryLink(category);
        }
        
    }
}
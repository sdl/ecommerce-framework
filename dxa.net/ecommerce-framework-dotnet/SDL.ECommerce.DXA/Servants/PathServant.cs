namespace SDL.ECommerce.DXA.Servants
{
    using System;
    using System.Collections.Generic;

    using Sdl.Web.Common.Configuration;

    using SDL.ECommerce.Api.Model;

    public class PathServant
    {
        public IEnumerable<string> GetSearchPath(string url, ICategory category, Localization localization)
        {
            var searchPath = new List<string>();

            var basePath = ECommerceContext.LocalizePath("/categories/", localization);
            var categoryPath = basePath;
            var currentCategory = category;
            while (currentCategory != null)
            {
                searchPath.Add(categoryPath + currentCategory.Id);
                currentCategory = currentCategory.Parent;
            }
            var urlTokens = url.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
            foreach (var token in urlTokens)
            {
                categoryPath += token;
                searchPath.Insert(0, categoryPath);
                categoryPath += "-";
            }
            searchPath.Add(basePath + "generic");
            return searchPath;
        }
    }
}
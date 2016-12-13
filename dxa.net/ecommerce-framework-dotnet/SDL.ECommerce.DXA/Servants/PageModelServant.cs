namespace SDL.ECommerce.DXA.Servants
{
    using System.Collections.Generic;

    using Sdl.Web.Common;
    using Sdl.Web.Common.Configuration;
    using Sdl.Web.Common.Interfaces;
    using Sdl.Web.Common.Logging;
    using Sdl.Web.Common.Models;
    using Sdl.Web.Mvc.Configuration;

    public class PageModelServant : IPageModelServant
    {
        public PageModel ResolveTemplatePage(IEnumerable<string> searchPath, IContentProvider contentProvider, Localization localization)
        {
            PageModel templatePage = null;
            foreach (var templatePagePath in searchPath)
            {
                try
                {
                    Log.Info("Trying to find page template: " + templatePagePath);
                    templatePage = contentProvider.GetPageModel(templatePagePath, localization);
                }
                catch (DxaItemNotFoundException) { }
                if (templatePage != null)
                {
                    break;
                }
            }

            return templatePage;
        }

        public void SetTemplatePage(PageModel model)
        {
            if (model != null)
            {
                WebRequestContext.PageModel = model;
            }
        }
    }
}
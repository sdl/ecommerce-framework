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
        public PageModel ResolveTemplatePage(IEnumerable<string> urlSegments, IContentProvider contentProvider, Localization localization)
        {
            PageModel model = null;

            foreach (var urlSegment in urlSegments)
            {
                try
                {
                    Log.Info("Trying to find page template: " + urlSegment);

                    model = contentProvider.GetPageModel(urlSegment, localization);
                }
                catch (DxaItemNotFoundException) { }

                if (model != null)
                {
                    break;
                }
            }

            return model;
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
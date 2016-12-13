namespace SDL.ECommerce.DXA.Servants
{
    using System.Collections.Generic;

    using Sdl.Web.Common.Configuration;
    using Sdl.Web.Common.Interfaces;
    using Sdl.Web.Common.Models;

    public interface IPageModelServant
    {
        PageModel ResolveTemplatePage(IEnumerable<string> urlSegments, IContentProvider contentProvider, Localization localization);

        void SetTemplatePage(PageModel model);
    }
}
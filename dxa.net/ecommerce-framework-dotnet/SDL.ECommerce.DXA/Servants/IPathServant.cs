namespace SDL.ECommerce.DXA.Servants
{
    using System.Collections.Generic;

    using Sdl.Web.Common.Configuration;

    using SDL.ECommerce.Api.Model;

    public interface IPathServant
    {
        IEnumerable<string> GetSearchPath(string url, ICategory category, Localization localization);
    }
}
namespace SDL.ECommerce.DXA.Servants
{
    using System.Collections.Generic;

    using SDL.ECommerce.Api.Model;

    public interface IPathServant
    {
        IEnumerable<string> GetSearchPath(string url, ICategory category);
    }
}
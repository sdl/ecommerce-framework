namespace SDL.ECommerce.Hybris.API.Model
{
    using System;
    using System.Collections.Generic;
    using System.Diagnostics.CodeAnalysis;

    /// <summary>
    /// Catalog version
    /// </summary>
    [SuppressMessage("StyleCop.CSharp.NamingRules", "SA1300:ElementMustBeginWithUpperCaseLetter", Justification = "auto generated class needs to map to json file")]
    public class CatalogVersion
    {
        public string id { get; set; }
        public DateTime lastModified { get; set; }
        public string url { get; set; }
        public List<Category> categories { get; set; }
    }
}

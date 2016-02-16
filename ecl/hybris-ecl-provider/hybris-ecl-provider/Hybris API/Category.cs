namespace SDL.ECommerce.Hybris.API.Model
{
    using System;
    using System.Collections.Generic;
    using System.Diagnostics.CodeAnalysis;

    /// <summary>
    /// Category class
    /// </summary>
    [SuppressMessage("StyleCop.CSharp.NamingRules", "SA1300:ElementMustBeginWithUpperCaseLetter", Justification = "auto generated class needs to map to json file")]
    public class Category
    {
        public string code { get; set; }
        public string id { get; set; }
        public DateTime lastModified { get; set; }
        public string name { get; set; }
        public string url { get; set; }
        public List<Category> subcategories { get; set; }
        public List<Product> products { get; set; }
        public Category Parent { get; set; }
    }
}

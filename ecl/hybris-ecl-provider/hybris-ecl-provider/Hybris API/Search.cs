namespace SDL.ECommerce.Hybris.API.Model
{
    using System.Collections.Generic;
    using System.Diagnostics.CodeAnalysis;

    [SuppressMessage("StyleCop.CSharp.NamingRules", "SA1300:ElementMustBeginWithUpperCaseLetter", Justification = "auto generated class needs to map to json file")]
    public class SearchResult
    {
        public List<Product> products { get; set; }
        public Pagination pagination { get; set; }
        public List<Sort> sorts { get; set; }
        public Currentquery currentQuery { get; set; }
        public List<Facet> facets { get; set; }
        public List<object> breadcrumbs { get; set; }
        public string freeTextSearch { get; set; }
    }

    public class Pagination
    {
        public string sort { get; set; }
        public int pageSize { get; set; }
        public int currentPage { get; set; }
        public int totalResults { get; set; }
        public int totalPages { get; set; }
    }

    public class Currentquery
    {
        public Query query { get; set; }
        public string url { get; set; }
    }

    public class Query
    {
        public string value { get; set; }
    }

    public class Sort
    {
        public bool selected { get; set; }
        public string name { get; set; }
        public string code { get; set; }
    }

    public class Facet
    {
        public bool multiSelect { get; set; }
        public bool category { get; set; }
        public List<Value> values { get; set; }
        public int priority { get; set; }
        public string name { get; set; }
        public List<Topvalue> topValues { get; set; }
    }

    public class Value
    {
        public string count { get; set; }
        public string name { get; set; }
        public string query { get; set; }
        public string selected { get; set; }
    }

    public class Topvalue
    {
        public string count { get; set; }
        public string name { get; set; }
        public string query { get; set; }
        public string selected { get; set; }
    }

    public class FacetPair
    {
        public FacetPair(string id, string value)
        {
            this.Id = id;
            this.Value = value;
        }
        public string Id;
        public string Value;
    }

}

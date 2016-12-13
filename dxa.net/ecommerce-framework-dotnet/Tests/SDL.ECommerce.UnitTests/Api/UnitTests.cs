using System;

using Xunit;

using SDL.ECommerce.Api;

namespace SDL.ECommerce.UnitTests.Api
{
    public class UnitTests
    {
        [Fact]
        public void TestFacetParameter()
        {
            var facet = new FacetParameter("brand", "adidas|dkny");
            Console.WriteLine("Facet: " + facet.Name + ", type: " + facet.Type);
            Console.WriteLine("Contains 'adidas': " + facet.ContainsValue("adidas"));
        }
    }
}
using System;

using Microsoft.VisualStudio.TestTools.UnitTesting;

using SDL.ECommerce.Api;

namespace SDL.ECommerce.UnitTests.Api
{
    [TestClass]
    public class UnitTests
    {
        [TestMethod]
        public void TestFacetParameter()
        {
            var facet = new FacetParameter("brand", "adidas|dkny");
            Console.WriteLine("Facet: " + facet.Name + ", type: " + facet.Type);
            Console.WriteLine("Contains 'adidas': " + facet.ContainsValue("adidas"));
        }
    }
}
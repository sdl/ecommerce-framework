using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;

namespace SDL.ECommerce.Api
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

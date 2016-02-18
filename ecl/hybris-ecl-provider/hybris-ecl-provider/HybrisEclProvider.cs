using SDL.ECommerce.Ecl;
using System.AddIn;
using System.Xml.Linq;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.Hybris.Ecl
{
    [AddIn("Hybris-ECL-Provider", Version = "1.0.0.0")]
    public class HybrisEclProvider : EclProvider
    {

        protected override ProductCatalog CreateProductCatalog(XElement configuration)
        {
            return new HybrisProductCatalog(configuration);
        }

        public override IContentLibraryContext CreateContext(IEclSession tridionUser)
        {
            return new HybrisMountpoint();
        }
    }
}

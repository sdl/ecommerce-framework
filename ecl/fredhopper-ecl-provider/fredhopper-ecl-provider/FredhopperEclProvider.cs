using SDL.ECommerce.Ecl;
using System.AddIn;
using System.Xml.Linq;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.Fredhopper.Ecl
{
    [AddIn("Fredhopper-ECL-Provider", Version = "1.0.0.0")]
    public class FredhopperEclProvider : EclProvider
    {

        protected override ProductCatalog CreateProductCatalog(XElement configuration)
        {
            return new FredhopperProductCatalog(configuration);
        }

        public override IContentLibraryContext CreateContext(IEclSession tridionUser)
        {
            return new FredhopperMountpoint();
        }
    }
}

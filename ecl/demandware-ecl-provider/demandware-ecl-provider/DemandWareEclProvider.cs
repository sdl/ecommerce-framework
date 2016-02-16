using SDL.ECommerce.Ecl;
using System;
using System.AddIn;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.DemandWare.Ecl
{
    [AddIn("DemandWare-ECL-Provider", Version = "1.0.0.0")]
    public class DemandWareEclProvider : EclProvider
    {

        protected override ProductCatalog CreateProductCatalog(XElement configuration)
        {
            return new DemandWareProductCatalog(configuration);
        }

        public override IContentLibraryContext CreateContext(IEclSession tridionUser)
        {
            return new DemandWareMountpoint();
        }
    }
}

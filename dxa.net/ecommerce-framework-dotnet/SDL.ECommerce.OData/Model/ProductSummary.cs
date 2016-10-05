using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SDL.ECommerce.Api.Model;

namespace SDL.ECommerce.OData
{
    public partial class ProductSummary : IProduct
    {
        IProductPrice IProduct.Price
        {
            get
            {
                return this.Price;
            }
        }

        /***** METHODS NOT AVAILABLE FOR PRODUCT SUMMARY ONLY IN DETAIL PRODUCT INFO ******/

        public IDictionary<string, object> Attributes
        {
            get
            {
                throw new NotImplementedException();
            }
        }

        public string Description
        {
            get
            {
                throw new NotImplementedException();
            }
        }

        public string PrimaryImageUrl
        {
            get
            {
                throw new NotImplementedException();
            }
        }

    }
}

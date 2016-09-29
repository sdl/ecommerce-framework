using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.OData
{
    public partial class ProductPrice : IProductPrice
    {
        string IProductPrice.Currency
        {
            get
            {
                return this.Currency;
            }          
        }

        string IProductPrice.FormattedPrice
        {
            get
            {
                return this.FormattedPrice;
            }
           
        }

        float? IProductPrice.Price
        {
            get
            {
                return this.Price;
            }           
        }
    }
}

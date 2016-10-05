using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.OData
{
    public partial class ContentArea : IContentArea
    {
        ILocation IContentArea.Location
        {
            get
            {
                return this.Location;
            }
        }

        int IContentArea.X1
        {
            get
            {
                return (int)this.X1;
            }
        }

        int IContentArea.X2
        {
            get
            {
                return (int)this.X2;
            }
        }

        int IContentArea.Y1
        {
            get
            {
                return (int)this.Y1;
            }
        }

        int IContentArea.Y2
        {
            get
            {
                return (int)this.Y2;
            }
        }
    }
}

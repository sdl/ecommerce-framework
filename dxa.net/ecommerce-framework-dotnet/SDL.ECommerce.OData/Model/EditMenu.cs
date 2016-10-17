using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.OData
{
    public partial class EditMenu : IEditMenu
    {
        IList<IMenuItem> IEditMenu.MenuItems
        {
            get
            {
                return this.MenuItems.Cast<IMenuItem>().ToList();
            }
        }
    }
}

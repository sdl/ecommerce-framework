using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api
{
    /**
     * ProductRef
     *
     * @author nic
     */
    public class ProductRef
    {

        private String id;
        private String name;

        public ProductRef(IProduct product)
        {
            id = product.Id;
            name = product.Name;
        }

        public ProductRef(String id, String name)
        {
            this.id = id;
            this.name = name;
        }

        public String Id()
        {
            return id;
        }

        public String Name()
        {
            return name;
        }
    }
}

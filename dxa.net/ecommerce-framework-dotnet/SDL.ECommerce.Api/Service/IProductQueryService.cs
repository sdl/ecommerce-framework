using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Service
{
    /// <summary>
    /// E-Commerce Product Query Service
    /// </summary>
    public interface IProductQueryService
    {
        /// <summary>
        /// Submit the query to the query service
        /// </summary>
        /// <param name="query"></param>
        /// <returns></returns>
        IProductQueryResult Query(Query query);
    }
}

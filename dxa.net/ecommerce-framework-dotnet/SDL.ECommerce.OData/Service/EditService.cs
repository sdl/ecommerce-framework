﻿using SDL.ECommerce.Api.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SDL.ECommerce.Api.Model;
using Sdl.Web.Delivery.Service;

namespace SDL.ECommerce.OData
{
    public class EditService : IEditService
    {
        private ODataV4Service service;

        /// <summary>
        /// Constructor (only available internally)
        /// </summary>
        /// <param name="service"></param>
        internal EditService(ODataV4Service service)
        {
            this.service = service;
        }

        public IEditMenu GetInContextMenuItems(Query query)
        {
            ODataV4ClientFunction func = new ODataV4ClientFunction("GetInContextEditMenu");
            func.AllowCaching = true;

            // Category
            //
            if (query.Category != null)
            {
                func.WithParam("categoryId", query.Category.Id);
            }
            else if (query.CategoryId != null)
            {
                func.WithParam("categoryId", query.CategoryId);
            }

            // Search phrase
            //
            if (query.SearchPhrase != null)
            {
                func.WithParam("searchPhrase", query.SearchPhrase);
            }

            return Enumerable.FirstOrDefault<EditMenu>(this.service.Execute<EditMenu>(func));
        }
    }
}

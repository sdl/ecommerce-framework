using Sdl.Web.Common.Logging;
using Sdl.Web.Common.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SDL.ECommerce.DXA.Models
{
    /// <summary>
    /// E-Commerce ECL Item
    /// </summary>
    [SemanticEntity(CoreVocabulary, "ExternalContentLibraryStubSchemafredhopper")]
    // TODO: How do we handle mapping to other ECL schemas in a nice way?
    public class ECommerceEclItem : EclItem
    {
        /// <summary>
        /// External ID
        /// </summary>
        public string ExternalId
        {
            get
            {
                var id = this.GetEclExternalMetadataValue("Id") as string;
                if ( id == null )
                {
                    id = FileName;
                }
                return id;
            }
        }

        /// <summary>
        /// External Name
        /// </summary>
        public string ExternalName
        {
            get
            {
                return this.GetEclExternalMetadataValue("Name") as string;
            }
        }
    }
}
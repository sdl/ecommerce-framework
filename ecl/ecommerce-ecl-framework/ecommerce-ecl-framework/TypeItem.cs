using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.ECommerce.Ecl
{
    class TypeItem : IContentLibraryListItem, IContentLibraryItem
    {
        private readonly IEclUri id;
        private readonly string typeName;

        public TypeItem(int publicationId, String typeName)
        {
            this.typeName = typeName;
            this.id = EclProvider.HostServices.CreateEclUri(publicationId, EclProvider.MountPointId, 
                      "Type_" + typeName, DisplayTypeId, EclItemTypes.Folder);
        }

        public bool CanGetUploadMultimediaItemsUrl
        {
            get { return false; }
        }

        public bool CanSearch
        {
            get { return false; }
        }

        public string DisplayTypeId
        {
            get { return "category" /*"type"*/; }
        }

        public string IconIdentifier
        {
            // TODO: Can I get an unique look on the type folders?
            get { return null; }
        }

        public IEclUri Id
        {
            get { return id; }
        }

        public bool IsThumbnailAvailable
        {
            get { return false; }
        }

        public DateTime? Modified
        {
            get { return null; }
        }

        public string ThumbnailETag
        {
            get { return null; }
        }

        public string Title
        {
            get { return this.typeName; }
            set { throw new NotSupportedException(); }
        }

        // allow override of dispatch
        public virtual string Dispatch(string command, string payloadVersion, string payload, out string responseVersion)
        {
            throw new NotSupportedException();
        }

        public IEclUri ParentId
        {
            get
            {
                // Return root folder
                //
                return EclProvider.HostServices.CreateEclUri(Id.PublicationId, Id.MountPointId);
            }
        }

        public string ModifiedBy
        {
            get { return CreatedBy; }
        }

        public string CreatedBy
        {
            get { return null; }
        }

        public DateTime? Created
        {
            get { return null; }
        }

        public string MetadataXml
        {
            get { return null; } 
            set { throw new NotSupportedException(); }
        }

        public ISchemaDefinition MetadataXmlSchema
        {
            get
            {
               return null;
            }
        }

        public bool CanUpdateTitle
        {
            get { return false; }
        }

        public bool CanGetViewItemUrl
        {
            get { return true; }
        }

        public bool CanUpdateMetadataXml
        {
            get { return false; }
        }

        public IContentLibraryItem Save(bool readback)
        {
            // as saving isn't supported, the result of saving is always the item itself
            return readback ? this : null;
        }
    }
}

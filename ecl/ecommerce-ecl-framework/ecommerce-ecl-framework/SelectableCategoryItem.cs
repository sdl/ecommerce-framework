using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.ECommerce.Ecl
{
    public class SelectableCategoryItem : SelectableItem
    {
   
        protected Category category;

        public SelectableCategoryItem(int publicationId, Category category ) : base(publicationId, category.CategoryId, category.Title)
        {
            this.category = category;
        }

        public override string Title
        {
            get {
                // Display the full path of the category as title
                //
                String categoryPath = "";
                Category currentCategory = this.category;
                while ( currentCategory != null )
                {
                    if ( categoryPath.Length > 0 )
                    {
                        categoryPath = "->" + categoryPath;
                    }
                    categoryPath = currentCategory.Title + categoryPath;
                    currentCategory = currentCategory.Parent;
                }
                return categoryPath;
            }
            set { throw new NotSupportedException(); }
        }

        public override string DisplayTypeId
        {
            get { return "category"; }
        }

        public override string GetDirectLinkToPublished(IList<ITemplateAttribute> attributes)
        {
            return this.category.CategoryId;
        }

        public override string GetTemplateFragment(IList<ITemplateAttribute> attributes)
        {
            // TODO: What to output here??
            return category.Title;
       }

        protected override void GetMetadata(Dictionary<string, object> metadata)
        {
            // Doing nothing. No additional metadata is exposed for category items
        }

        protected override void BuildMetadataXmlSchema(ISchemaDefinition schema)
        {
            // Doing nothing. No additional metadata is exposed for category items
        }

        public override IEclUri ParentId
        {
            get
            {
                // return folder uri 
                return EclProvider.HostServices.CreateEclUri(
                    Id.PublicationId,
                    Id.MountPointId,
                    "Type_Categories",
                    "type",
                    EclItemTypes.Folder);
            }
        }

    }
}

using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.ECommerce.Ecl
{
    public abstract class ProductItem : SelectableItem
    {
        protected readonly IEclUri id;
        protected Category category;
        protected Product product;

        public ProductItem(int publicationId, Category category, Product product) : base(publicationId, product.Id, product.Name)
        {
            this.category = category;
            this.product = product;
            this.id = EclProvider.HostServices.CreateEclUri(publicationId, EclProvider.MountPointId,
                     product.Id, DisplayTypeId, EclItemTypes.File);
        }

        public override string DisplayTypeId
        {
            get { return "product"; }
        }

        public override bool IsThumbnailAvailable
        {
            get { return true; } // return this.product.Thumbnail != null; }
        }

        public override IContentResult GetContent(IList<ITemplateAttribute> attributes)
        {
            if ( this.product.Thumbnail != null)
            {
                using (WebClient webClient = new WebClient())
                {
                    Stream stream = new MemoryStream(webClient.DownloadData(this.product.Thumbnail.Url));
                    stream.Position = 0;
                    return EclProvider.HostServices.CreateContentResult(stream, stream.Length, this.product.Thumbnail.Mime);
                }
            }
            /*
             * TODO: Add no image here
             * else
            {
                using (Stream bitmapStream = System.IO.File.OpenRead(Path.Combine(Provider.AddInFolder, "themes\\NoImage.png")))
                {
                    MemoryStream memStream = new MemoryStream();
                    memStream.SetLength(bitmapStream.Length);
                    bitmapStream.Read(memStream.GetBuffer(), 0, (int)bitmapStream.Length);
                    return Provider.HostServices.CreateContentResult(memStream, bitmapStream.Length, "image/png");
                }
            }
             */

            return null;           
        }

        public override string GetDirectLinkToPublished(IList<ITemplateAttribute> attributes)
        {
            if ( this.product.Thumbnail != null )
            {
                return this.product.Thumbnail.Url;
            }
            return null;
        }

        public override string MimeType
        {
            get 
            { 
                if ( this.product.Thumbnail != null )
                {
                    return this.product.Thumbnail.Mime;
                }
                return null; 
            }
        }

        public override IEclUri ParentId
        {
            get
            {
                // return folder uri 
                return EclProvider.HostServices.CreateEclUri(
                    Id.PublicationId,
                    Id.MountPointId,
                    category != null ? category.CategoryId : "0",
                    "category",
                    EclItemTypes.Folder);
            }
        }

    }
}

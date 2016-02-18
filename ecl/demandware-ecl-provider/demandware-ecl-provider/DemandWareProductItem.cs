using SDL.ECommerce.Ecl;
using System.Collections.Generic;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.DemandWare.Ecl
{
    class DemandWareProductItem : ProductItem
    {
        public DemandWareProductItem(int publicationId, Category category, Product product) : base(publicationId, category, product) {}

        protected override void GetMetadata(Dictionary<string, object> metadata)
        {
            DemandWareProduct detailedProduct = (DemandWareProduct) EclProvider.ProductCatalog.GetProduct(this.product.Id);
            metadata.Add("Brand", detailedProduct.brand);
            metadata.Add("Price", detailedProduct.price);
        }

        protected override void BuildMetadataXmlSchema(ISchemaDefinition schema)
        {
            schema.Fields.Add(EclProvider.HostServices.CreateSingleLineTextFieldDefinition("Brand", "Brand", 0, 1));
            //schema.Fields.Add(EclProvider.HostServices.CreateMultiLineTextFieldDefinition("Description", "Description", 0, 1, 7));
            schema.Fields.Add(EclProvider.HostServices.CreateNumberFieldDefinition("Price", "Price", 0, 1));
        }
    }
}

using SDL.ECommerce.Ecl;
using System.Collections.Generic;
using System.Security;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.Fredhopper.Ecl
{
    class FredhopperProductItem : ProductItem
    {
        public FredhopperProductItem(int publicationId, Category category, Product product) : base(publicationId, category, product) {}

        protected override void GetMetadata(Dictionary<string, object> metadata)
        {
            var fhProduct = (FredhopperProduct) this.product;
            metadata.Add("Description", SecurityElement.Escape(fhProduct.Description));
            metadata.Add("Price", fhProduct.Price);
            foreach (var attribute in fhProduct.AdditionalAttributes)
            {
                var name = getSchemaAttributeName(attribute.Key);
                metadata.Add(name, attribute.Value);
            }
        }

        protected override void BuildMetadataXmlSchema(ISchemaDefinition schema)
        {
            schema.Fields.Add(EclProvider.HostServices.CreateMultiLineTextFieldDefinition("Description", "Description", 0, 1, 7));
            schema.Fields.Add(EclProvider.HostServices.CreateSingleLineTextFieldDefinition("Price", "Price", 0, 1));
            var fhProduct = (FredhopperProduct)this.product;
            foreach ( var attribute in fhProduct.AdditionalAttributes )
            {
                var name = getSchemaAttributeName(attribute.Key);
                if (attribute.Value.GetType() == typeof(string) )
                {   
                    schema.Fields.Add(EclProvider.HostServices.CreateSingleLineTextFieldDefinition(name, name, 0, 1));                      
                }
                else if ( attribute.Value.GetType() == typeof(List<string>))
                {
                    schema.Fields.Add(EclProvider.HostServices.CreateSingleLineTextFieldDefinition(name, name, 0, 100));
                }
            }
        }

        private string getSchemaAttributeName(string attributeName)
        {
            return attributeName.Substring(0, 1).ToUpper() + attributeName.Substring(1);
        }
    }
}

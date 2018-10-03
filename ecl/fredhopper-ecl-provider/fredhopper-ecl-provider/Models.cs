using SDL.ECommerce.Ecl;
using System.Collections.Generic;
using SDL.Fredhopper.Ecl.FredhopperWS;
using System.Security;

namespace SDL.Fredhopper.Ecl
{
    
    /// <summary>
    /// Fredhopper Category
    /// </summary>
    public class FredhopperCategory : Category
    {

        public FredhopperCategory(string categoryId, string title, Category parent)
        {
            CategoryId = categoryId;
            Title = title;
            Parent = parent;
            Categories = new List<Category>();
        }

        public string CategoryId { get; internal set; }
        public string Title { get; internal set; }
        public Category Parent { get; internal set; }
        public IList<Category> Categories { get; internal set; }

        public Category RootCategory
        {
            get
            {
                Category category = this;
                while (category.Parent != null)
                {
                    category = category.Parent;
                }
                return category;
            }
        }
          
    }
  
    /// <summary>
    /// Fredhopper Product
    /// </summary>
    public class FredhopperProduct : Product
    {
        private item fhItem;
        private IDictionary<string, object> attributes = new Dictionary<string,object>();
        private IDictionary<string, object> additionalAttributes = null;
        private IDictionary<string, string> modelMappings;
        private IList<Category> categories = new List<Category>();
        private ProductImage productThumbnail = null;

        public FredhopperProduct(item fhItem, IDictionary<string,string> modelMappings)
        {
            this.fhItem = fhItem;
            this.modelMappings = modelMappings;
            foreach ( var attribute in this.fhItem.attribute )
            {
                if ( attribute.isnull || (attribute.value?.Length ?? 0) == 0 )
                {
                    continue;
                }
                var name = attribute.name;
                object value;
                if ( attribute.basetype == attributeTypeFormat.set || attribute.basetype == attributeTypeFormat.list || attribute.basetype == attributeTypeFormat.cat )
                {
                    List<ProductAttributeValue> valueList = new List<ProductAttributeValue>();
                    foreach ( var attrValue in attribute.value )
                    {
                        valueList.Add(new ProductAttributeValue { Value = attrValue.nonml, PresentationValue = attrValue.Value });
                        if (attribute.basetype == attributeTypeFormat.cat)
                        {
                            // Create category from the attribute
                            //
                            categories.Add(new FredhopperCategory(attrValue.nonml, attrValue.Value, null));
                    }
                    }
                    value = valueList;
                }
                else
                {
                    value = new ProductAttributeValue { Value = attribute.value[0].nonml, PresentationValue = attribute.value[0].Value };
                }
                if (!attributes.ContainsKey(name))
                {
                    attributes.Add(name, value);
                }
                else
                {
                    // Merge the values into one list
                    //
                    var valueList = attributes[name] as List<ProductAttributeValue>;
                    if (valueList == null)
                    {
                        var singleValue = (ProductAttributeValue) attributes[name];
                        valueList = new List<ProductAttributeValue>();
                        valueList.Add(singleValue);
                        attributes.Remove(name);
                        attributes.Add(name, valueList);
                    }
                    if (value.GetType() == typeof(List<ProductAttributeValue>))
                    {
                        valueList.AddRange((List<ProductAttributeValue>)value);
                    }
                    else
                    {
                        valueList.Add((ProductAttributeValue)value);
                    }
                }
               
            }

            var imageUrl = GetModelAttributeValue("thumbnailUrl");
            if (imageUrl == null)
            {
                // Fallback on primary image Url
                //
                imageUrl = GetModelAttributeValue("primaryImageUrl");
            }
            if (imageUrl != null)
            {
                imageUrl = imageUrl.Replace("\\", "/");
                this.productThumbnail = new StandardProductImage(imageUrl);
            }
        }

        public string Id
        {
            get
            {
                return this.fhItem.id;
            }
        }

        public string Name
        {
            get
            {
                return GetModelAttributeValue("name");
            }
        }

        public string Description
        {
            get
            {
                return GetModelAttributeValue("description");
            }
        }

        public string Price
        {
            get
            {
                return GetModelAttributeValue("price", false);
            }
        }

        public ProductImage Thumbnail
        {
            get
            {
                return this.productThumbnail;
            }
        }

        public IDictionary<string,object> AdditionalAttributes
        {
            get
            {
                if ( this.additionalAttributes == null )
                {
                    this.additionalAttributes = new Dictionary<string, object>();
                    foreach (var attribute in this.attributes)
                    {
                        if (!this.modelMappings.Values.Contains(attribute.Key))
                        {
                            this.additionalAttributes.Add(attribute);
                        }
                    }
                }             
                return this.additionalAttributes;
            }
        }

        public IList<Category> Categories
        {
            get
            {
                return categories;
            }
        }

        private string GetModelAttributeValue(string name, bool usePresentationValue = true)
        {
            string fhAttribute;
            this.modelMappings.TryGetValue(name, out fhAttribute);
            string fhStringValue = null;
            if ( fhAttribute != null )
            {
                object fhValue;
                this.attributes.TryGetValue(fhAttribute, out fhValue);
                if ( fhValue != null )
                {
                    ProductAttributeValue value = null;
                    if (fhValue.GetType() == typeof(ProductAttributeValue))
                    {
                        value = (ProductAttributeValue) fhValue;
                    }
                    else if (fhValue.GetType() == typeof(List<ProductAttributeValue>))
                    {
                        List<ProductAttributeValue> list = (List<ProductAttributeValue>) fhValue;
                        if (list.Count > 0)
                        {
                            // Pick the first value
                            value = list[0];
                        }
                    }
                    if ( value != null )
                    {
                        return usePresentationValue == true ? value.PresentationValue : value.Value;
                    }
                }           
            }
            return null;
        }

    }

    public class ProductAttributeValue
    {
        public string Value { get; set; }
        public string PresentationValue { get; set; }

        public string ToXml()
        {
            return
                "<Value>" + Value + "</Value>" +
                "<PresentationValue>" + SecurityElement.Escape(PresentationValue) + "</PresentationValue>";
        }
    }
   
  
}

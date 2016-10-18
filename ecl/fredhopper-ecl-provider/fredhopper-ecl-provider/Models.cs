using SDL.ECommerce.Ecl;
using System.Collections.Generic;
using System.Web;
using SDL.Fredhopper.Ecl.FredhopperWS;
using java.util;
using System;

namespace SDL.Fredhopper.Ecl
{
    
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
    

    // TODO: Use model mappings to expose configured values to CME
  
    public class FredhopperProduct : Product
    {
        private item fhItem;
        private IDictionary<string, object> attributes = new Dictionary<string,object>();
        private IDictionary<string, object> additionalAttributes = null;
        private IDictionary<string, string> modelMappings;
        private ProductImage productThumbnail = null;
        private IList<string> categories = null;

        public FredhopperProduct(item fhItem, IDictionary<string,string> modelMappings)
        {
            this.fhItem = fhItem;
            this.modelMappings = modelMappings;
            foreach ( var attribute in this.fhItem.attribute )
            {
                if ( attribute.value.Length == 0 )
                {
                    continue;
                }
                var name = attribute.name;
                object value;
                if ( attribute.basetype == attributeTypeFormat.set || attribute.basetype == attributeTypeFormat.list )
                {
                    List<string> valueList = new List<string>();
                    foreach ( var attrValue in attribute.value )
                    {
                        valueList.Add(attrValue.Value);
                    }
                    value = valueList;
                }
                else if ( attribute.basetype == attributeTypeFormat.cat )
                {
                    name = "categoryId";
                    List<string> valueList = new List<string>();
                    foreach (var attrValue in attribute.value)
                    {
                        valueList.Add(attrValue.nonml);
                    }
                    value = valueList;
                    this.categories = valueList;
                }
                else
                {
                    value = attribute.value[0].Value;
                }
                if (!attributes.ContainsKey(name))
                {
                    attributes.Add(name, value);
                }
                else
                {
                    // Merge the values into one list
                    //
                    var valueList = attributes[name] as List<string>;
                    if (valueList == null)
                    {
                        var singleValue = (string)attributes[name];
                        valueList = new List<string>();
                        valueList.Add(singleValue);
                        attributes.Remove(name);
                        attributes.Add(name, valueList);
                    }
                    if (value.GetType() == typeof(List<string>))
                    {
                        valueList.AddRange((List<string>)value);
                    }
                    else
                    {
                        valueList.Add((string)value);
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
                    this.productThumbnail = new StandardProductImage(imageUrl);
                }
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
                return GetModelAttributeValue("price");
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

        public IList<string> Categories
        {
            get
            {
                return this.categories;
            }
        }

        private string GetModelAttributeValue(string name)
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
                    if (fhValue.GetType() == typeof(string))
                    {
                        fhStringValue = (string)fhValue;
                    }
                    else if (fhValue.GetType() == typeof(List<string>))
                    {
                        List<string> list = (List<string>)fhValue;
                        if (list.Count > 0)
                        {
                            // Pick the first value
                            fhStringValue = list[0];
                        }
                    }
                }           
            }
            return fhStringValue;
        }

    }
   
  
}

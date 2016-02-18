using System.Diagnostics.CodeAnalysis;

namespace SDL.ECommerce.Hybris.API.Model
{
    using System;
    using System.Collections.Generic;
    using System.Linq;


    [SuppressMessage("StyleCop.CSharp.NamingRules", "SA1300:ElementMustBeginWithUpperCaseLetter", Justification = "auto generated class needs to map to json file")]
    public class Product
    {
   
        public List<object> productReferences { get; set; }
        public List<Classification> classifications { get; set; }
        public float averageRating { get; set; }
        public bool purchasable { get; set; }
        public Stock stock { get; set; }
        public string description { get; set; }
        public string name { get; set; }
        public List<Baseoption> baseOptions { get; set; }
        public List<Review> reviews { get; set; }
        public string code { get; set; }
        public string url { get; set; }
        public Price price { get; set; }
        public int numberOfReviews { get; set; }
        public string manufacturer { get; set; }
        public List<Image> images { get; set; }
        public List<Category> categories { get; set; }
        public List<object> potentialPromotions { get; set; }

        public Image thumbnailImage
        {
            get
            {
                if (this.images != null)
                {
                    var img = this.images.FirstOrDefault(i => i.format.Equals("thumbnail"));
                    if (img != null)
                    {
                        return img;
                    }
                }

                return null;
            }
        }
    }

    public class Stock
    {
        public Stocklevelstatus stockLevelStatus { get; set; }
        public int stockLevel { get; set; }
    }

    public class Stocklevelstatus
    {
        public string code { get; set; }
        public string codeLowerCase { get; set; }

        public override string ToString()
        {
            return this.code;
        }
    }

    public class Price
    {
        public string currencyIso { get; set; }
        public string priceType { get; set; }      
        public string value { get; set; }
        public string formattedValue { get; set; }
    }

    public class Classification
    {
        public string name { get; set; }
        public List<Feature> features { get; set; }
        public string code { get; set; }
    }

    public class Feature
    {
        public bool range { get; set; }
        public string name { get; set; }
        public Featureunit featureUnit { get; set; }
        public List<Featurevalue> featureValues { get; set; }
        public bool comparable { get; set; }
        public string code { get; set; }
    }

    public class Featureunit
    {
        public string symbol { get; set; }
        public string unitType { get; set; }
        public string name { get; set; }

        public override string ToString()
        {
            return this.symbol;
        }
    }

    public class Featurevalue
    {
        public string value { get; set; }

        public override string ToString()
        {
            return this.value;
        }
    }

    public class Review
    {
        public string headline { get; set; }
        public Principal principal { get; set; }
        public float rating { get; set; }
        public DateTime date { get; set; }
        public string comment { get; set; }
    }

    public class Principal
    {
        public string uid { get; set; }
        public string name { get; set; }

        public override string ToString()
        {
            return this.name;
        }
    }

    public class Image
    {
        public string imageType { get; set; }
        public string format { get; set; }
        public string altText { get; set; }
        public string url { get; set; }
        public int galleryIndex { get; set; }
    }

    public class Baseoption
    {
        public Selected selected { get; set; }
        public string variantType { get; set; }
    }

    public class Selected
    {
        public List<Variantoptionqualifier> variantOptionQualifiers { get; set; }
        public Stock stock { get; set; }
        public Price priceData { get; set; }
        public string code { get; set; }
        public string url { get; set; }
    }

    public class Variantoptionqualifier
    {
        public string name { get; set; }
        public string value { get; set; }
        public string qualifier { get; set; }
        public Image image { get; set; }
    }

}





using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
using System.Runtime.Caching;
using System.Xml.Linq;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.ECommerce.Ecl
{
    /// <summary>
    /// Base class for E-Commerce ECL providers.
    /// Each concrete implementation need to specify the following attribute:
    /// [AddIn("<ECL PROVIDER NAME>", Version = "<VERSION>")]
    /// </summary>
    public abstract class EclProvider : IContentLibrary
    {
        public static readonly XNamespace EcommerceEclNs = "http://sdl.com/ecl/ecommerce"; 
        private static readonly string IconBasePath = Path.Combine(AddInFolder, "Themes");

        static int DEFAULT_CATEGORY_CACHE_TIME = 60*60; // Default 1 hour cache
        private static int categoryCacheTime;

        internal static string MountPointId { get; private set; }
        public static IHostServices HostServices { get; private set; }
        public static ProductCatalog ProductCatalog { get; private set; }

        /// <summary>
        /// Get root category
        /// </summary>
        internal static Category GetRootCategory(int publicationId) {
            var cacheKey = "RootCategories:" + publicationId;          
            Category rootCategory = MemoryCache.Default.Get(cacheKey) as Category;
            if (rootCategory == null )
            {               
                rootCategory = ProductCatalog.GetAllCategories(publicationId);
                MemoryCache.Default.Add(cacheKey, rootCategory, DateTime.Now.AddSeconds(categoryCacheTime));
       
            }
            return rootCategory;

        }

        public static int GetIntConfigurationValue(XElement configuration, string configName, int defaultValue)
        {
            var value = configuration.Element(EclProvider.EcommerceEclNs + configName);
            if (value != null)
            {
                return Int32.Parse(value.Value);
            }
            else
            {
                return defaultValue;
            }
        }

        internal static string AddInFolder
        {
            get
            {
                string codeBase = Assembly.GetExecutingAssembly().CodeBase;
                UriBuilder uri = new UriBuilder(codeBase);
                string path = Uri.UnescapeDataString(uri.Path);
                return Path.GetDirectoryName(path);
            }
        }

        /// <summary>
        /// Get a specific category by its identity
        /// </summary>
        /// <param name="categoryId"></param>
        /// <returns></returns>
        internal static Category GetCategory(string categoryId, int publicationId)
        {
            return FindCategoryById(GetRootCategory(publicationId), categoryId);
        }

        /// <summary>
        /// Get all available catagories in a flat list
        /// </summary>
        /// <returns></returns>
        internal static List<Category> GetAllCategories(int publicationId)
        {
            var allCategories = new List<Category>();
            GetCategories(GetRootCategory(publicationId), allCategories);
            /*
            allCategories.Sort(delegate(Category x, Category y)
            {
                return x.Title.CompareTo(y.Title);
            });
            */
            return allCategories;
        }

        private static void GetCategories(Category category, List<Category> categories)
        {
            foreach (var subCategory in category.Categories)
            {
                categories.Add(subCategory);
                GetCategories(subCategory, categories);
            }
        }

        internal static List<string> GetAllCategoryIds(int publicationId)
        {
            List<string> allCategoryIds = new List<string>();
            getCategoryIds(GetRootCategory(publicationId), allCategoryIds);
            allCategoryIds.Sort();
            return allCategoryIds;
        }

        private static void getCategoryIds(Category category, List<string> categoryIds)
        {
            foreach ( var subCategory in category.Categories )
            {
                categoryIds.Add(subCategory.CategoryId);
                getCategoryIds(subCategory, categoryIds);
            }
        }

        private static Category FindCategoryById(Category category, string categoryId)
        {
            foreach ( var subCategory in category.Categories )
            {
                if (subCategory.CategoryId.Equals(categoryId)) return subCategory;
                else
                {
                    var cat = FindCategoryById(subCategory, categoryId);
                    if (cat != null) return cat;
                }
            }
            return null;
        }

        internal static byte[] GetIconImage(string iconIdentifier, int iconSize)
        {
            int actualSize;
            // get icon directly from default theme folder
            return HostServices.GetIcon(IconBasePath, "_Default", iconIdentifier, iconSize, out actualSize);
        }

        /// <summary>
        /// Initialize the ECL provider
        /// </summary>
        /// <param name="mountPointId"></param>
        /// <param name="configurationXmlElement"></param>
        /// <param name="hostServices"></param>
        public void Initialize(string mountPointId, string configurationXmlElement, IHostServices hostServices)
        {
            MountPointId = mountPointId;
            HostServices = hostServices;

            // read ExtenalContentLibrary.xml for this mountpoint
            XElement config = XElement.Parse(configurationXmlElement);

            // Initialize the product catalog with config
            //
            ProductCatalog = this.CreateProductCatalog(config);

            // Read category cache time (which generically managed by the E-Commerce framework)
            //
            categoryCacheTime = GetIntConfigurationValue(config, "CategoryCacheTime", DEFAULT_CATEGORY_CACHE_TIME);
        }

        /// <summary>
        /// Create a new instance of the product catalog. Needs to be implemented by concrete subclass.
        /// </summary>
        /// <param name="configuration"></param>
        /// <returns></returns>
        protected abstract ProductCatalog CreateProductCatalog(XElement configuration);

        public abstract IContentLibraryContext CreateContext(IEclSession session);

        public IList<IDisplayType> DisplayTypes
        {
            get
            {
                return new List<IDisplayType>
                {
                    //HostServices.CreateDisplayType("type", "Catalog Type", EclItemTypes.Folder),
                    HostServices.CreateDisplayType("category", "Product Category", EclItemTypes.Folder), 
                    HostServices.CreateDisplayType("category", "Product Category", EclItemTypes.File), 
                    HostServices.CreateDisplayType("product", "Product", EclItemTypes.File)
                };
            }
        }

        public byte[] GetIconImage(string theme, string iconIdentifier, int iconSize)
        {
            return GetIconImage(iconIdentifier, iconSize);
        }

        public void Dispose()
        {
        }
    }


   
}

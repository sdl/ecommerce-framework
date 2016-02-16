using SDL.ECommerce.Ecl;
using System;
using System.AddIn;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.ECommerce.Ecl
{
    /*
     * Each concrete implementation need to specify the following attribute:
     * [AddIn("<ECL PROVIDER NAME>", Version = "<VERSION>")]
     */
    public abstract class EclProvider : IContentLibrary
    {
        public static readonly XNamespace EcommerceEclNs = "http://sdl.com/ecl/ecommerce"; // TODO: Have each concrete implementation as a suffix to the generic NS
        private static readonly string IconBasePath = Path.Combine(AddInFolder, "Themes");

        internal static string MountPointId { get; private set; }
        public static IHostServices HostServices { get; private set; }
        public static ProductCatalog ProductCatalog { get; private set; }
        private static Category rootCategory = null;
        internal static Category RootCategory {
            get
            {
                if ( rootCategory == null )
                {
                    lock ( EclProvider.EcommerceEclNs )
                    {
                        if (rootCategory == null)
                        { 
                            rootCategory = ProductCatalog.GetAllCategories();
                        }
                    }
                }
                return rootCategory;
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

        internal static Category GetCategory(string categoryId)
        {
            return FindCategoryById(RootCategory, categoryId);
        }

        internal static List<Category> GetAllCategories()
        {
            var allCategories = new List<Category>();
            GetCategories(RootCategory, allCategories);
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

        internal static List<string> GetAllCategoryIds()
        {
            List<string> allCategoryIds = new List<string>();
            getCategoryIds(RootCategory, allCategoryIds);
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

        public void Initialize(string mountPointId, string configurationXmlElement, IHostServices hostServices)
        {
            MountPointId = mountPointId;
            HostServices = hostServices;

            // read ExtenalContentLibrary.xml for this mountpoint
            XElement config = XElement.Parse(configurationXmlElement);

            // TODO: This needs to be done in the implementation instead!!!
            // Should the root category always be fetched or...???

            // TODO: We can not do this stuff here. Because that will break the whole ECL init chain. 

            ProductCatalog = this.CreateProductCatalog(config);
            // TODO: Refresh this or cache it ...???
        }

        protected abstract ProductCatalog CreateProductCatalog(XElement configuration);

        public abstract IContentLibraryContext CreateContext(IEclSession tridionUser);

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

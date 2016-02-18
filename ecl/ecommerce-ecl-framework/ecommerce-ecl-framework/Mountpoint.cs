using System;
using System.Collections.Generic;
using System.Linq;
using Tridion.ExternalContentLibrary.V2;

namespace SDL.ECommerce.Ecl
{
    /// <summary>
    /// Mountpoint base class
    /// </summary>
    abstract public class Mountpoint : IContentLibraryContext
    {
        public bool CanGetUploadMultimediaItemsUrl(int publicationId)
        {
            return true;
        }

        public bool CanSearch(int publicationId)
        {
            return false;
        }

        public IList<IContentLibraryListItem> FindItem(IEclUri eclUri)
        {
            // return null so we force it to call GetItem(IEclUri)
            return null;
        }

        public IFolderContent GetFolderContent(IEclUri parentFolderUri, int pageIndex, EclItemTypes itemTypes)
        {
            List<IContentLibraryListItem> items = new List<IContentLibraryListItem>();

            // If root -> list all root categories
            //
            if (parentFolderUri.ItemType == EclItemTypes.MountPoint && itemTypes.HasFlag(EclItemTypes.Folder))
            {
                items.Add(new TypeItem(parentFolderUri.PublicationId, "Categories"));
                items.Add(new TypeItem(parentFolderUri.PublicationId, "Products"));  
            }
            else if (parentFolderUri.ItemType == EclItemTypes.Folder && itemTypes.HasFlag(EclItemTypes.Folder))
            {
                if (parentFolderUri.ItemId.StartsWith("Type_") )
                {
                    if (parentFolderUri.ItemId.Equals("Type_Products"))
                    {
                        foreach (var category in EclProvider.RootCategory.Categories)
                        {
                            if (!String.IsNullOrEmpty(category.CategoryId))
                            {
                                items.Add(new CategoryItem(parentFolderUri.PublicationId, category));
                            }
                        }
                    }
                    else // Type_Categories
                    {
                        /*
                        List<string> allCategories = EclProvider.GetAllCategoryIds();
                        foreach ( var categoryId in allCategories )
                        {
                            items.Add(new SelectableCategoryItem(parentFolderUri.PublicationId, categoryId));
                        }
                         * */
                        var allCategories = EclProvider.GetAllCategories();
                        foreach ( var category in allCategories )
                        {
                            items.Add(new SelectableCategoryItem(parentFolderUri.PublicationId, category));
                        }

                    }
                }
                else
                {
                    // TODO: Always use the product catalog for retrieving the category???

                    var parentCategory = EclProvider.GetCategory(parentFolderUri.ItemId);
                    if (parentCategory != null)
                    {
                        foreach (var category in parentCategory.Categories)
                        {
                            if (!String.IsNullOrEmpty(category.CategoryId))
                            {
                                // TODO: Have possibility to have concrete category items for each provider
                                items.Add(new CategoryItem(parentFolderUri.PublicationId, category));
                            }
                        }

                        var products = EclProvider.ProductCatalog.GetProducts(parentFolderUri.ItemId, parentFolderUri.PublicationId);
                        if (products != null)
                        {
                            // TODO: Have some kind of pagination here???

                            // At a leaf category -> list all products in that category
                            //
                            foreach (var product in products)
                            {
                               items.Add(this.CreateProductItem(parentFolderUri.PublicationId, parentCategory, product));
                            }
                        }
                    }
                }
            }
            
            return EclProvider.HostServices.CreateFolderContent(parentFolderUri, items, CanGetUploadMultimediaItemsUrl(parentFolderUri.PublicationId), CanSearch(parentFolderUri.PublicationId));
         
        }

        /// <summary>
        /// Create new product ECL item. Needs to be implemented by concrete subclass.
        /// </summary>
        /// <param name="publicationId"></param>
        /// <param name="parentCategory"></param>
        /// <param name="product"></param>
        /// <returns></returns>
        protected abstract ProductItem CreateProductItem(int publicationId, Category parentCategory, Product product);

        public IContentLibraryItem GetItem(IEclUri eclUri)
        {
            if (eclUri.ItemType == EclItemTypes.File )
            {
                if ( eclUri.SubType.Equals("product") )
                {
                    string productId = eclUri.ItemId;
                    return this.CreateProductItem(eclUri.PublicationId, null, EclProvider.ProductCatalog.GetProduct(productId, eclUri.PublicationId));
                }
                else // selectable category
                {
                    string categoryId = eclUri.ItemId;
                    var category = EclProvider.GetCategory(categoryId);
                    return new SelectableCategoryItem(eclUri.PublicationId, category);
                }
            }
            else if (eclUri.ItemType == EclItemTypes.Folder)
            {
                if (eclUri.ItemId.StartsWith("Type_"))
                {
                    string typeName = eclUri.ItemId.Replace("Type_", "");
                    return new TypeItem(eclUri.PublicationId, typeName); 
                }
                else
                {
                    string categoryId = eclUri.ItemId;
                    if ( categoryId == null )
                    {
                        throw new Exception("Undefined category for ECL URI: " + eclUri);
                    }
                    var category = EclProvider.GetCategory(categoryId);
                    if ( category == null )
                    {
                        // Use the root category as fallback to avoid errors when browsing in the ECL catalog
                        //
                        category = EclProvider.RootCategory;
                    }

                    return new CategoryItem(eclUri.PublicationId, category);
                }
                
            }
            throw new NotSupportedException();
        }

        public IList<IContentLibraryItem> GetItems(IList<IEclUri> eclUris)
        {
            List<IContentLibraryItem> items = new List<IContentLibraryItem>();
            
            IEnumerable<string> uniqueIds = (from uri in eclUris
                                                  where uri.ItemType == EclItemTypes.File 
                                                  select uri.ItemId).Distinct();
            foreach (string id in uniqueIds)
            {
                string itemId = id;
                var urisForProduct = from uri in eclUris
                                   where uri.ItemType == EclItemTypes.File && uri.ItemId == itemId
                                   select uri;

                foreach (IEclUri eclUri in urisForProduct)
                {
                    items.Add(GetItem(eclUri));
                }
            }
            
            return items;
        }

        public byte[] GetThumbnailImage(IEclUri eclUri, int maxWidth, int maxHeight)
        {
            return null;
        }

        public string GetUploadMultimediaItemsUrl(IEclUri parentFolderUri)
        {
            throw new NotSupportedException();
        }

        public string GetViewItemUrl(IEclUri eclUri)
        {
            throw new NotSupportedException();
        }

        public virtual string IconIdentifier
        {
            get { return "ecommerce"; }
        }

        public IFolderContent Search(IEclUri contextUri, string searchTerm, int pageIndex, int numberOfItems)
        {
            throw new NotSupportedException();
        }

        public string Dispatch(string command, string payloadVersion, string payload, out string responseVersion)
        {
            throw new NotSupportedException();
        }

        public void StubComponentCreated(IEclUri eclUri, string tcmUri)
        {
        }

        public void StubComponentDeleted(IEclUri eclUri, string tcmUri)
        {
        }

        public void Dispose()
        {
        }
    }
}

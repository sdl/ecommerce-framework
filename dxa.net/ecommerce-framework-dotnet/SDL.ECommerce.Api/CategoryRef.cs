using System;

namespace SDL.ECommerce.Api.Model
{
    /**
     * CategoryRef
     *
     * @author nic
     */
    public class CategoryRef
    {

        private String id;
        private String name;
        private String path;

        public CategoryRef(ICategory category)
        {
            this.id = category.Id;
            this.name = category.Name;
            this.path = getCategoryAbsolutePath(category);
        }

        public CategoryRef(String id, String name, String path)
        {
            this.id = id;
            this.name = name;
            this.path = path;
        }

        public static String getCategoryAbsolutePath(ICategory category)
        {
            String path = "";
            ICategory currentCategory = category;
            while (currentCategory != null)
            {
                path = currentCategory.PathName + "/" + path;
                currentCategory = currentCategory.Parent;
            }
            return "/" + path;
        }

        public String Id()
        {
            return id;
        }

        public String Name()
        {
            return name;
        }

        public String Path()
        {
            return path;
        }
    }
}

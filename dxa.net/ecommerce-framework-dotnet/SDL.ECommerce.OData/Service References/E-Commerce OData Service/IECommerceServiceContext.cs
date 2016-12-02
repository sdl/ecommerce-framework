namespace SDL.ECommerce.OData
{
    public interface IECommerceServiceContext
    {
        Microsoft.OData.Client.DataServiceQuery<Category> Categories { get; }
        Microsoft.OData.Client.DataServiceQuery<Product> Products { get; }
        Microsoft.OData.Client.DataServiceQuery<Cart> Carts { get; }
        Microsoft.OData.Client.DataServiceQuery<ProductQueryResult> ProductQueryResults { get; }
        Microsoft.OData.Client.DataServiceQuery<EditMenu> EditMenus { get; }
    }
}
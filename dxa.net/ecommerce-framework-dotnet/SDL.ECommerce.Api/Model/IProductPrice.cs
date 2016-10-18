namespace SDL.ECommerce.Api.Model
{
    /// <summary>
    /// Product Price
    /// </summary>
    public interface IProductPrice
    {
        //
        /// <summary>
        /// Get price in numeric format
        /// </summary>
        float? Price { get; }

        /// <summary>
        /// Currency.
        /// </summary>
        ///  TODO: Have some kind of ISO code here???
        string Currency { get; }

        /// <summary>
        /// Get formatted price to be used in presentation views. This can be localized based on current locale.
        /// </summary>
        string FormattedPrice { get; }
    }
}

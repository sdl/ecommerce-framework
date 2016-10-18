namespace SDL.ECommerce.Api.Model
{
    /// <summary>
    ///  Promotion
    /// Base interface for all kind of promotions driven by an E-Commerce system.
    /// </summary>
    public interface IPromotion : IEditable
    {
        /// <summary>
        /// Get unique identifier of the promotion.
        /// </summary>
        /// <returns></returns>
        string Id { get; }

        /// <summary>
        /// Get name of the promotion.
        /// </summary>
        string Name { get; }

        /// <summary>
        /// Get title of the promotion. This can be localized based on current langauge.
        /// </summary>
        string Title { get; }

    }
}

namespace SDL.ECommerce.Api.Model
{
    public interface IProductVariantAttribute
    {
        /// <summary>
        /// Attribute ID
        /// </summary>
        string Id { get; }

        /// <summary>
        /// Attribute name (presented to the user and can be localized)
        /// </summary>
        string Name { get; }

        /// <summary>
        /// Value ID
        /// </summary>
        string ValueId { get; }

        /// <summary>
        /// Value (presented to the user and can be localized)
        /// </summary>
        string Value { get; }
    }
}

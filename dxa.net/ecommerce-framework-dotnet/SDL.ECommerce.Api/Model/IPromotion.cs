namespace SDL.ECommerce.Api.Model
{
    /**
     * Promotion
     * Base interface for all kind of promotions driven by an E-Commerce system.
     *
     * @author nic
     */
    public interface IPromotion
    {

        /**
         * Get unique identifier of the promotion.
         *
         * @return id
         */
        string Id();

        /**
         * Get name of the promotion.
         *
         * @return name
         */
        string Name();

        /**
         * Get title of the promotion. This can be localized based on current langauge.
         * @return title
         */
        string Title();

    }
}

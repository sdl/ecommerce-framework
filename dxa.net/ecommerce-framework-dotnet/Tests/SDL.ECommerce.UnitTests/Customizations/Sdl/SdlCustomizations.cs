namespace SDL.ECommerce.UnitTests.Customizations.Sdl
{
    using Ploeh.AutoFixture;

    public class SdlCustomizations : CompositeCustomization
    {
        public SdlCustomizations()
            : base(new LocalizationCustomization(),
                   new PageModelCustomization())
        {
        }
    }
}
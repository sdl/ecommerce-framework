namespace SDL.ECommerce.UnitTests.Customizations.Sdl
{
    using global::Sdl.Web.Common.Configuration;

    using Ploeh.AutoFixture;

    public class LocalizationCustomization : ICustomization
    {
        public void Customize(IFixture fixture)
        {
            fixture.Customize<Localization>(composer =>
                                                {
                                                    return composer.Without(p => p.SiteLocalizations);
                                                });
        }
    }
}
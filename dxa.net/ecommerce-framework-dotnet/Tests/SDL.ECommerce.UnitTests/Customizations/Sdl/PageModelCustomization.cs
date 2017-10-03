namespace SDL.ECommerce.UnitTests.Customizations.Sdl
{
    using System.Reflection;

    using global::Sdl.Web.Common.Models;

    using Newtonsoft.Json;

    using Ploeh.AutoFixture;
    using Ploeh.AutoFixture.Kernel;

    public class PageModelCustomization : ICustomization
    {
        public void Customize(IFixture fixture)
        {
            fixture.Customizations.Add(new PropertyBuilder());

            fixture.Customize<PageModel>(composer =>
                                             {
                                                 return composer.Without(p => p.Id);
                                             });
        }

        public class PropertyBuilder : ISpecimenBuilder
        {
            public object Create(object request, ISpecimenContext context)
            {
                var pi = request as PropertyInfo;

                if (pi == null)
                {
                    return new NoSpecimen();
                }

                return pi.IsDefined(typeof(JsonIgnoreAttribute)) ? new OmitSpecimen() : context.Resolve(pi.PropertyType);
            }
        }
    }
}
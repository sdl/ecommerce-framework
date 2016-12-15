namespace SDL.ECommerce.UnitTests
{
    using System;
    using System.Collections.Generic;

    using Ploeh.AutoFixture;
    using Ploeh.AutoFixture.Kernel;

    using SDL.ECommerce.Api;
    using SDL.ECommerce.DXA.Providers;
    using SDL.ECommerce.DXA.Servants;

    public class DependencyTestProvider
    {
        internal static void SetTestDependencies(ISpecimenBuilder fixture)
        {
            var dependencies = new Dictionary<Type, object>
                                   {
                                           { typeof(IECommerceClient), fixture.Create<IECommerceClient>() },
                                           { typeof(IECommerceLinkResolver), fixture.Create<IECommerceLinkResolver>() },
                                           { typeof(IHttpContextServant), fixture.Create<IHttpContextServant>() },
                                           { typeof(IPageModelServant), fixture.Create<IPageModelServant>() },
                                           { typeof(IPathServant), fixture.Create<IPathServant>() }
                                   };

            DependencyResolverProvider.Set(type => !dependencies.ContainsKey(type) ? null : dependencies[type]);
        }

        internal static void ResetDependencies()
        {
            DependencyResolverProvider.Reset();
        }
    }
}
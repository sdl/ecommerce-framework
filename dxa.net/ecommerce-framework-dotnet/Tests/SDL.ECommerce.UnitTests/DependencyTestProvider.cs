namespace SDL.ECommerce.UnitTests
{
    using System;
    using System.Collections.Generic;

    using Ploeh.AutoFixture;

    using SDL.ECommerce.Api;
    using SDL.ECommerce.DXA.Providers;
    using SDL.ECommerce.DXA.Servants;

    internal class DependencyTestProvider : IDisposable
    {
        public DependencyTestProvider(IFixture fixture)
        {
            var dependencies = new Dictionary<Type, object>
                                   {
                                           { typeof(IECommerceClient), fixture.Freeze<IECommerceClient>() },
                                           { typeof(IECommerceLinkResolver), fixture.Freeze<IECommerceLinkResolver>() },
                                           { typeof(IHttpContextServant), fixture.Freeze<IHttpContextServant>() },
                                           { typeof(IPageModelServant), fixture.Freeze<IPageModelServant>() },
                                           { typeof(IPathServant), fixture.Freeze<IPathServant>() }
                                   };

            DependencyResolverProvider.Set(type => !dependencies.ContainsKey(type) ? null : dependencies[type]);
        }


        public void Dispose()
        {
            DependencyResolverProvider.Reset();
        }
    }
}
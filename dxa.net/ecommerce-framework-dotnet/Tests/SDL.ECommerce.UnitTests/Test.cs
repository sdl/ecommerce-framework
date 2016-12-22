namespace SDL.ECommerce.UnitTests
{
    using System;

    using Ploeh.AutoFixture;
    using Ploeh.AutoFixture.AutoNSubstitute;

    using SDL.ECommerce.UnitTests.Customizations;
    using SDL.ECommerce.UnitTests.Customizations.Sdl;

    public abstract class Test
    {
        public IFixture Fixture { get; }

        protected Test()
        {
            Fixture = new Fixture().Customize(new AutoNSubstituteCustomization())
                                   .Customize(new MvcCustomization())
                                   .Customize(new SdlCustomizations());
        }
    }

    public abstract class Test<T> : Test
    {
        private T _sut;

        public T SUT
        {
            get
            {
                if (_sut == null)
                {
                    _sut = Fixture.Freeze<T>();
                }

                return _sut;
            }
        }
    }
}
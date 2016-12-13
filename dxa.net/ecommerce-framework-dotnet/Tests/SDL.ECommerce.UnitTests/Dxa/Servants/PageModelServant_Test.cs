namespace SDL.ECommerce.UnitTests.Dxa.Servants
{
    using System.Linq;
    using System.Web;

    using FakeHttpContext;

    using NSubstitute;
    using NSubstitute.ExceptionExtensions;

    using Ploeh.AutoFixture;

    using Sdl.Web.Common;
    using Sdl.Web.Common.Configuration;
    using Sdl.Web.Common.Interfaces;
    using Sdl.Web.Common.Models;

    using SDL.ECommerce.DXA.Servants;

    using Xunit;

    public class PageModelServant_Test : Test<PageModelServant>
    {
        private readonly Localization _localization;

        public PageModelServant_Test()
        {
            _localization = Fixture.Create<Localization>();
        }

        public class WhenResolvingPageTemplateResultsInNoMatch : MultipleAssertTest<PageModelServant_Test>
        {
            private readonly PageModel _result;

            public WhenResolvingPageTemplateResultsInNoMatch()
            {
                var paths = Fixture.CreateMany<string>(3);

                var contentProvider = Substitute.For<IContentProvider>();

                contentProvider.GetPageModel(Arg.Any<string>(), Parent._localization)
                               .Throws(new DxaItemNotFoundException(Fixture.Create<string>()));

                _result = Parent.SUT.ResolveTemplatePage(paths, contentProvider, Parent._localization);
            }

            [Fact]
            public void ThenReturnNull()
            {
                Assert.Null(_result);
            }
        }

        public class WhenResolvingPageTemplateResultsInMatch : MultipleAssertTest<PageModelServant_Test>
        {
            private readonly PageModel _result;

            private readonly PageModel _model;

            public WhenResolvingPageTemplateResultsInMatch()
            {
                var paths = Fixture.CreateMany<string>(3).ToList();

                _model = Fixture.Create<PageModel>();

                var contentProvider = Substitute.For<IContentProvider>();

                contentProvider.GetPageModel(paths.Skip(1)
                                                  .First(), Parent._localization)
                               .Returns(_model);

                _result = Parent.SUT.ResolveTemplatePage(paths, contentProvider, Parent._localization);
            }

            [Fact]
            public void ThenReturnNull()
            {
                Assert.Equal(_model, _result);
            }
        }

        public class WhenSettingPageTemplate : MultipleAssertTest<PageModelServant_Test>
        {
            [Fact]
            public void ThenTheModelShouldBeSet()
            {
                var model = Fixture.Create<PageModel>();

                using (new FakeHttpContext())
                {
                    Parent.SUT.SetTemplatePage(model);

                    Assert.Equal(model, HttpContext.Current.Items["PageModel"]);
                }
            }
        }
    }
}
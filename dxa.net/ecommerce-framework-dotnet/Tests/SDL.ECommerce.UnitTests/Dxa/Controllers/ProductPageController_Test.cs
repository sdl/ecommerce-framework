namespace SDL.ECommerce.UnitTests.Dxa.Controllers
{
    using System.Collections;
    using System.Collections.Generic;
    using System.Collections.Specialized;
    using System.Web;
    using System.Web.Mvc;

    using FakeHttpContext;

    using NSubstitute;

    using Ploeh.AutoFixture;

    using Sdl.Web.Common.Configuration;
    using Sdl.Web.Common.Interfaces;
    using Sdl.Web.Common.Models;

    using SDL.ECommerce.Api;
    using SDL.ECommerce.DXA.Controllers;
    using SDL.ECommerce.DXA.Servants;

    using Xunit;

    public class ProductPageController_Test : Test<ProductPageController>
    {
        private readonly Localization _localization;

        private readonly Url _url;

        public ProductPageController_Test()
        {
            _localization = Fixture.Create<Localization>();

            _url = Fixture.Create<Url>();
        }

        public class WhenCallingProductPageWithValidUrlWithoutQueryString : MultipleAssertTest<ProductPageController_Test>
        {
            private readonly PageModel _pageModel;

            private readonly IDictionary _httpContextItems;

            private readonly ViewResult _result;

            private readonly IProduct _product;

            public WhenCallingProductPageWithValidUrlWithoutQueryString()
            {
                _pageModel = Fixture.Create<PageModel>();

                Fixture.Freeze<IPageModelServant>()
                       .ResolveTemplatePage(Arg.Any<IEnumerable<string>>(), Arg.Any<IContentProvider>())
                       .Returns(_pageModel);

                _product = Fixture.Create<IProduct>();

                _product.Name.Returns(Fixture.Create<string>());

                Fixture.Freeze<IECommerceClient>()
                       .DetailService.GetDetail(Parent._url.OneLevel.Replace("/", string.Empty))
                       .Returns(_product);
                
                using (new FakeHttpContext())
                {
                    HttpContext.Current.Items.Add("Localization", Parent._localization);

                    ProductPageController controller;

                    using (new DependencyTestProvider(Fixture))
                    {
                        controller = Parent.SUT.Value;
                    }

                    controller.Request.QueryString.Returns(new NameValueCollection());
                        
                    _result = controller.ProductPage(Parent._url.OneLevel) as ViewResult;

                    _httpContextItems = HttpContext.Current.Items;
                }
            }

            [Fact]
            public void TheResolvedTemplatePageShouldBeReturned()
            {
                Assert.Equal(_pageModel, _result.Model);
            }

            [Fact]
            public void TemplateTitleSholdBeModelName()
            {
                Assert.Equal(_product.Name, ((PageModel)_result.Model).Title);
            }
            
            [Fact]
            public void TheProductShouldBeSetInTheContext()
            {
                Assert.Equal(_product, _httpContextItems["ECOM-Product"]);
            }

            [Fact]
            public void TheUrlPrrefixShouldBeSetInTheContext()
            {
                Assert.Equal($"{Parent._localization.Path}/c", _httpContextItems["ECOM-UrlPrefix"]);
            }

            [Fact]
            public void ControllerRouteValueShouldBePage()
            {
                Assert.Equal("Page", Parent.SUT.Value.RouteData.Values["Controller"]);
            }
        }
    }
}
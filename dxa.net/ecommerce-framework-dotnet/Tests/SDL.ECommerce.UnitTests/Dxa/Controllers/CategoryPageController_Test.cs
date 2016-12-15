namespace SDL.ECommerce.UnitTests.Dxa.Controllers
{
    using System.Collections;
    using System.Collections.Generic;
    using System.Linq;
    using System.Web;
    using System.Web.Mvc;

    using FakeHttpContext;

    using NSubstitute;

    using Ploeh.AutoFixture;

    using Sdl.Web.Common.Configuration;
    using Sdl.Web.Common.Interfaces;
    using Sdl.Web.Common.Models;

    using SDL.ECommerce.Api;
    using SDL.ECommerce.Api.Model;
    using SDL.ECommerce.DXA.Controllers;
    using SDL.ECommerce.DXA.Servants;

    using Xunit;
    
    public class CategoryPageController_Test : Test
    {
        private readonly string _url;

        private readonly Localization _localization;

        public CategoryPageController_Test()
        {
            _url = Fixture.Create<string>();

            _localization = Fixture.Create<Localization>();
        }

        public class WhenCallingCategoryPageWithValidUrl : MultipleAssertTest<CategoryPageController_Test>
        {
            private readonly PageModel _pageModel;

            private readonly IDictionary _httpContextItems;

            private readonly PageModel _resultModel;

            private readonly ICategory _category;
            
            private readonly IList<FacetParameter> _parameters;
            
            public WhenCallingCategoryPageWithValidUrl()
            {
                Fixture.Freeze<IECommerceClient>()
                       .QueryService.Query(Arg.Any<ECommerce.Api.Model.Query>())
                       .RedirectLocation.Returns((ILocation)null);

                _pageModel = Fixture.Create<PageModel>();

                Fixture.Freeze<IPageModelServant>()
                       .ResolveTemplatePage(Arg.Any<IEnumerable<string>>(), Arg.Any<IContentProvider>(), Arg.Any<Localization>())
                       .Returns(_pageModel);

                using (new FakeHttpContext())
                {
                    HttpContext.Current.Items.Add("Localization", Parent._localization);

                    using (new DependencyTestProvider(Fixture))
                    {
                        var controller = Fixture.Create<CategoryPageController>();

                        var result = controller.CategoryPage(Parent._url);

                        _resultModel = ((ViewResult)result).Model as PageModel;
                    }
                    
                    _httpContextItems = HttpContext.Current.Items;
                }

                _category = Fixture.GetStub<IECommerceClient>()
                                   .CategoryService.GetCategoryByPath(Parent._url);

                _parameters = Fixture.GetStub<IHttpContextServant>()
                                        .GetFacetParametersFromRequest(Arg.Any<HttpContextBase>());
            }

            [Fact]
            public void CategoryWillBeSetInTheHttpContextCurrentQueryItem()
            {
                Assert.Equal(_category, ((ECommerce.Api.Model.Query)_httpContextItems["ECOM-CurrentQuery"]).Category);
            }

            [Fact]
            public void FacetsWillBeSetInTheHttpContextCurrentQueryItemItem()
            {
                Assert.Equal(_parameters, ((ECommerce.Api.Model.Query)_httpContextItems["ECOM-CurrentQuery"]).Facets);
            }

            [Fact]
            public void StartIndexWillBeSetInTheHttpContextCurrentQueryItemItem()
            {
                var startIndex = Fixture.GetStub<IHttpContextServant>()
                                        .GetStartIndex(Arg.Any<HttpContextBase>());

                Assert.Equal(startIndex, ((ECommerce.Api.Model.Query)_httpContextItems["ECOM-CurrentQuery"]).StartIndex);
            }

            [Fact]
            public void SearchResultWillBeSetInTheHttpContext()
            {
                var query = Fixture.GetStub<IECommerceClient>()
                                   .QueryService.Query(Arg.Any<ECommerce.Api.Model.Query>());

                Assert.Equal(query, _httpContextItems["ECOM-QueryResult"]);
            }

            [Fact]
            public void UrlPrefixWillBeSetInTheHttpContext()
            {
                var url = $"{Parent._localization.Path}/c";

                Assert.Equal(url, _httpContextItems["ECOM-UrlPrefix"]);
            }

            [Fact]
            public void FacetsWillBeSetInTheHttpContext()
            {
                Assert.Equal(_parameters, _httpContextItems["ECOM-Facets"]);
            }

            [Fact]
            public void CategoryWillBeSetInTheHttpContext()
            {
                Assert.Equal(_category, _httpContextItems["ECOM-Category"]);
            }

            [Fact]
            public void CategoryUrlShouldBeUsedWhenGettingSearchPath()
            {
                Fixture.GetStub<IPathServant>()
                       .Received(1)
                       .GetSearchPath(Parent._url, Arg.Any<ICategory>(), Arg.Any<Localization>());
            }

            [Fact]
            public void CategoryShouldBeUsedWhenGettingSearchPath()
            {
                Fixture.GetStub<IPathServant>()
                       .Received(1)
                       .GetSearchPath(Arg.Any<string>(), _category, Arg.Any<Localization>());
            }

            [Fact]
            public void CategoryNameIsSetAsModelTemplate()
            {
                Assert.Equal(_category.Name, _resultModel.Title);
            }

            [Fact]
            public void TheModelIsSet()
            {
                Fixture.GetStub<IPageModelServant>()
                       .Received(1)
                       .SetTemplatePage(_pageModel);
            }

            [Fact]
            public void QueryContributorsShouldBeSet()
            {
                Fixture.GetStub<IPageModelServant>()
                       .Received(1)
                       .GetQueryContributions(Arg.Is<PageModel>(model => model.Title == _pageModel.Title), Arg.Is<ECommerce.Api.Model.Query>(model => model.Category == _category));
            }

            [Fact]
            public void QueryIsCalledWithTheCreatedQuery()
            {
                Fixture.GetStub<IECommerceClient>()
                       .Received()
                       .QueryService.Query(Arg.Is<ECommerce.Api.Model.Query>(model => model.Category == _category));
            }
        }
    }
}
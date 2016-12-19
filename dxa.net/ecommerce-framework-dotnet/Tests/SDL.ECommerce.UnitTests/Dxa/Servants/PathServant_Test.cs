namespace SDL.ECommerce.UnitTests.Dxa.Servants
{
    using System.Linq;
    using System.Web;

    using FakeHttpContext;

    using NSubstitute;

    using Ploeh.AutoFixture;

    using Sdl.Web.Common.Configuration;

    using SDL.ECommerce.Api.Model;
    using SDL.ECommerce.DXA.Servants;

    using Xunit;

    public class PathServant_Test : Test<PathServant>
    {
        private readonly Localization _localization;

        private readonly string _url;

        private readonly string _urlPart1;

        private readonly string _urlPart2;

        public PathServant_Test()
        {
            _urlPart1 = Fixture.Create<string>();

            _urlPart2 = Fixture.Create<string>();

            _url = $"{_urlPart1}/{_urlPart2}";

            _localization = Fixture.Create<Localization>();
        }

        public class WhenGettingSearchPathForCategory : MultipleAssertTest<PathServant_Test>
        {
            private readonly ICategory _category;

            private readonly string[] _result;

            public WhenGettingSearchPathForCategory()
            {
                _category = Substitute.For<ICategory>();

                _category.Id.Returns(Fixture.Create<string>());

                _category.Parent.Returns(Fixture.Create<ICategory>());

                _category.Parent.Id.Returns(Fixture.Create<string>());

                _category.Parent.Parent.Returns(default(ICategory));
                using (new FakeHttpContext())
                {
                    HttpContext.Current.Items.Add("Localization", Parent._localization);

                    _result = Parent.SUT.GetSearchPath(Parent._url, _category)
                                    .ToArray();
                }
            }

            [Fact]
            public void PathWithBothUrlPartsShouldBeReturned()
            {
                Assert.Contains($"{GetBasePath()}{Parent._urlPart1}-{Parent._urlPart2}", _result);
            }

            [Fact]
            public void PathWithFirstUrlPartShouldBeReturned()
            {
                Assert.Contains($"{GetBasePath()}{Parent._urlPart1}", _result);
            }

            [Fact]
            public void PathWithCurrentCategoryIdShouldBeReturned()
            {
                Assert.Contains($"{GetBasePath()}{_category.Id}", _result);
            }

            [Fact]
            public void PathWithCurrentCategoryParentIdShouldBeReturned()
            {
                Assert.Contains($"{GetBasePath()}{_category.Parent.Id}", _result);
            }

            [Fact]
            public void PathWithGenericShouldBeReturned()
            {
                Assert.Contains($"{GetBasePath()}generic", _result);
            }

            [Fact]
            public void PathWithBothUrlPartsShouldBeReturnedAsFirstResult()
            {
                Assert.Equal(_result[0], $"{GetBasePath()}{Parent._urlPart1}-{Parent._urlPart2}");
            }

            [Fact]
            public void PathWithFirstUrlPartShouldBeReturnedAsSecondResult()
            {
                Assert.Equal(_result[1], $"{GetBasePath()}{Parent._urlPart1}");
            }

            [Fact]
            public void PathWithCurrentCategoryIdShouldBeReturnedAsThirdResult()
            {
                Assert.Equal(_result[2], $"{GetBasePath()}{_category.Id}");
            }

            [Fact]
            public void PathWithCurrentCategoryParentIdShouldBeReturnedAsForthResult()
            {
                Assert.Equal(_result[3], $"{GetBasePath()}{_category.Parent.Id}");
            }

            [Fact]
            public void PathWithGenericShouldBeReturnedLast()
            {
                Assert.Equal(_result.Last(), $"{GetBasePath()}generic");
            }

            private string GetBasePath()
            {
                return $"{Parent._localization.Path}/categories/";
            }
        }
    }
}
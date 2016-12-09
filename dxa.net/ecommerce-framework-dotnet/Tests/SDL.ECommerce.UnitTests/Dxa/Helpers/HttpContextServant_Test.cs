namespace SDL.ECommerce.UnitTests.Dxa.Helpers
{
    using System.Collections.Generic;
    using System.Collections.Specialized;
    using System.Linq;
    using System.Web;

    using NSubstitute;

    using Ploeh.AutoFixture;

    using SDL.ECommerce.Api;
    using SDL.ECommerce.DXA.Servants;

    using Xunit;

    public class HttpContextServant_Test : Test
    {
        private readonly IEnumerable<KeyValuePair<string, string>> _parameters;

        private readonly HttpContextBase _context;

        public HttpContextServant_Test()
        {
            _parameters = Fixture.CreateMany<KeyValuePair<string, string>>(2);

            _context = Substitute.For<HttpContextBase>();

            var parameters = new NameValueCollection();

            foreach (var keyValuePair in _parameters)
            {
                parameters.Add(keyValuePair.Key, keyValuePair.Value);
            }

            _context.Request.QueryString.Returns(parameters);
        }

        public class WhenGettingFacetParameters : MultipleAssertTest<HttpContextServant_Test>
        {
            private readonly IList<FacetParameter> _result;

            public WhenGettingFacetParameters()
            {
                var servant = Fixture.Create<HttpContextServant>();

                _result = servant.GetFacetParametersFromRequest(Parent._context);
            }

            [Fact]
            public void TwoFacetsShouldBeCreated()
            {
                Assert.Equal(2, _result.Count);
            }

            [Fact]
            public void TheFirstParameterShouldBeMapped()
            {
                var parameter = Parent._parameters.First();

                var facet = _result.First();

                Assert.Equal(parameter.Key, facet.Name);
                Assert.Equal(parameter.Value, facet.Values.First());
            }

            [Fact]
            public void TheSecondParameterShouldBeMapped()
            {
                var parameter = Parent._parameters.Last();

                var facet = _result.Last();

                Assert.Equal(parameter.Key, facet.Name);
                Assert.Equal(parameter.Value, facet.Values.First());
            }
        }

        public class WhenHavingQAsParameter : MultipleAssertTest<HttpContextServant_Test>
        {
            private readonly IList<FacetParameter> _result;

            public WhenHavingQAsParameter()
            {
                var servant = Fixture.Create<HttpContextServant>();

                Parent._context.Request.QueryString.Add("q", Fixture.Create<string>());

                _result = servant.GetFacetParametersFromRequest(Parent._context);
            }

            [Fact]
            public void TwoFacetsShouldBeCreated()
            {
                Assert.Equal(2, _result.Count);
            }

            [Fact]
            public void QShouldNotBeAFacetName()
            {
                Assert.False(_result.Any(f => f.Name == "q"));
            }
        }

        public class WhenHavingStartIndexAsParameter : MultipleAssertTest<HttpContextServant_Test>
        {
            private readonly IList<FacetParameter> _result;

            public WhenHavingStartIndexAsParameter()
            {
                var servant = Fixture.Create<HttpContextServant>();

                Parent._context.Request.QueryString.Add("startIndex", Fixture.Create<string>());

                _result = servant.GetFacetParametersFromRequest(Parent._context);
            }

            [Fact]
            public void TwoFacetsShouldBeCreated()
            {
                Assert.Equal(2, _result.Count);
            }

            [Fact]
            public void QShouldNotBeAFacetName()
            {
                Assert.False(_result.Any(f => f.Name == "startIndex"));
            }
        }
    }
}
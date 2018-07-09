using System.Web.Mvc;
using NSubstitute;
using Ploeh.AutoFixture;
using SDL.ECommerce.Api;
using SDL.ECommerce.Api.Model;
using SDL.ECommerce.Api.Service;
using SDL.ECommerce.DXA.Models;
using SDL.ECommerce.UnitTests;
using SDL.ECommerce.UnitTests.Dxa.Fakes;
using Xunit;

public class BaseEComWidgetController_Test : Test<FakeBaseEcomWidgetController>
{
    [Fact]
    public void WhenInvokingProductLister_ThenTotalCountShouldBeMappedToNavigationData()
    {
        //Arrange
        var expectedTotalCount = Fixture.Create<int>();
        var expectedViewSize = 40;

        var queryService = Fixture.Freeze<IProductQueryService>();
        queryService.Query(Arg.Any<Query>()).TotalCount.Returns(expectedTotalCount);
        queryService.Query(Arg.Any<Query>()).ViewSize.Returns(expectedViewSize);

        var categoryService = Fixture.Freeze<IProductCategoryService>();
        categoryService.GetCategoryByPath(Arg.Any<string>()).Returns((ICategory)null);

        var eCommerceClient = new FakeECommerceClient
        {
            CategoryService = categoryService,
            QueryService = queryService
        };
        
        Fixture.Register<IECommerceClient>(() => eCommerceClient);

        var entity = Fixture.Create<ProductListerWidget>();

        //Act
        ViewResult result;
        using (new DependencyTestProvider(Fixture))
        {
            result = SystemUnderTest.ProductLister(entity) as ViewResult;
        }

        //Assert
        var model = (ProductListerWidget)result?.Model;
        Assert.Equal(model?.NavigationData.TotalCount, expectedTotalCount);
    }
}
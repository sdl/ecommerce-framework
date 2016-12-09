namespace SDL.ECommerce.UnitTests
{
    using Ploeh.AutoFixture;

    public abstract class Test
    {
        public IFixture Fixture { get; }

        protected Test()
        {
            Fixture = new Fixture();
        }
    }
}
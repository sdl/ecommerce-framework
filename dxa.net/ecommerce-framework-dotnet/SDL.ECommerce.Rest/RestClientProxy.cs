namespace SDL.ECommerce.Rest
{
    using System.Net;

    public class RestClientProxy
    {
        public virtual IWebProxy Proxy => null;
    }
}
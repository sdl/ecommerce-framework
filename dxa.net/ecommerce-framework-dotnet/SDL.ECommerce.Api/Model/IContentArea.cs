using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    public interface IContentArea
    {
        ILocation Location { get; }
        int X1 { get; }
        int Y1 { get; }
        int X2 { get; }
        int Y2 { get; }
    }
}

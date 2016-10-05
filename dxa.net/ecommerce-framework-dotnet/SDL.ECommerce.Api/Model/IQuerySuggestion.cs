using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    public interface IQuerySuggestion
    {
        string Original { get; }
        string Suggestion { get; }
    }
}

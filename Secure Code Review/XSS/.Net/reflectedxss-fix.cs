//Backend 
using Microsoft.AspNetCore.Mvc;

namespace SecureApp.Controllers
{
    public class SearchController : Controller
    {
        [HttpGet]
        public IActionResult Index(string query)
        {
            // Store the raw input, but Razor will handle encoding automatically
            ViewBag.Query = query;
            return View();
        }
    }
}



Frontend 

<!DOCTYPE html>
<html>
<head>
    <title>Search</title>
</head>
<body>
    <h1>Search Page</h1>

    <form method="get" action="/Search/Index">
        <label for="query">Enter Search Query:</label>
        <input type="text" id="query" name="query" />
        <button type="submit">Search</button>
    </form>

    <h2>Search Results</h2>
    <p>Your search query: @ViewBag.Query</p> <!-- Razor automatically encodes the output -->
</body>
</html>










////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
............................................................................................................................................


Explanation of Fixes

    Avoid @Html.Raw:
        Remove @Html.Raw(ViewBag.Query) and use @ViewBag.Query instead. Razor automatically encodes the output to prevent execution of malicious input.

    Encoded Output Example:
        Input: <script>alert('XSS')</script>
        Rendered Output: &lt;script&gt;alert('XSS')&lt;/script&gt;
        The browser displays it as plain text instead of executing it.

    Validate Input:
        Ensure user input meets expected formats (e.g., a search query should not contain <script> tags).
        Use server-side validation to enforce input constraints.

    Use Content Security Policy (CSP):
        Configure a CSP header to restrict the execution of unauthorized scripts:

        Response.Headers.Add("Content-Security-Policy", "default-src 'self'; script-src 'self';");

Additional Steps for Security

    Input Validation:
        Reject or sanitize invalid inputs on the server side.
    Output Encoding Libraries:
        Use libraries like Microsoft's AntiXSS for additional encoding.


















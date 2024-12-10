Backend view
using Microsoft.AspNetCore.Mvc;

namespace VulnerableApp.Controllers
{
    public class SearchController : Controller
    {
        [HttpGet]
        public IActionResult Index(string query)
        {
            // Directly include the user input in the response without encoding
            ViewBag.Query = query;
            return View();
        }
    }
}




Frontend View

Razor View (Index.cshtml):

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
    <p>Your search query: @Html.Raw(ViewBag.Query)</p> <!-- Vulnerable to Reflected XSS -->
</body>
</html>































............................................................................................................................................
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


How This Code is Vulnerable

    User Input is Directly Rendered:
        The query parameter from the URL is directly output in the Razor view using @Html.Raw(ViewBag.Query), allowing potentially malicious input to be executed.

    Example of Exploitation:
        URL: http://localhost:5000/Search/Index?query=<script>alert('XSS')</script>
        The malicious input <script>alert('XSS')</script> is reflected in the response and rendered as:

    <p>Your search query: <script>alert('XSS')</script></p>

    When the page loads, the script is executed, showing an alert box.

Impact:

    An attacker can exploit this to execute arbitrary JavaScript in the victim's browser, leading to:
        Cookie theft (e.g., document.cookie).
        Unauthorized actions on behalf of the victim (CSRF attacks).
        Phishing or defacement of the webpage.











      














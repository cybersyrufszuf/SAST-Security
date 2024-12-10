// server side
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Web;

namespace SecureApp.Controllers
{
    public class CommentsController : Controller
    {
        // In-memory storage for comments (simulating a database)
        private static List<string> comments = new List<string>();

        [HttpGet]
        public IActionResult Index()
        {
            ViewBag.Comments = comments;
            return View();
        }

        [HttpPost]
        public IActionResult Submit(string comment)
        {
            // Sanitize the input before storing (optional)
            string sanitizedComment = HttpUtility.HtmlEncode(comment);
            comments.Add(sanitizedComment);

            return RedirectToAction("Index");
        }
    }
}








Razor View (Index.cshtml):

<!DOCTYPE html>
<html>
<head>
    <title>Comments</title>
</head>
<body>
    <h1>Comments</h1>

    <form method="post" action="/Comments/Submit">
        <label for="comment">Add a Comment:</label>
        <input type="text" id="comment" name="comment" />
        <button type="submit">Submit</button>
    </form>

    <h2>Submitted Comments</h2>
    <ul>
        @foreach (var comment in ViewBag.Comments)
        {
            <li>@comment</li> <!-- Razor automatically HTML encodes the content -->
        }
    </ul>
</body>
</html>










....................................................................................................................................................................................
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Explanation of Fixes

    Sanitize Input:
        Use HttpUtility.HtmlEncode to sanitize the user input before storing it in the comments list. This ensures that potentially malicious characters like <, >, " are escaped.

    Use Razor's Default Encoding:
        Replace @Html.Raw(comment) with @comment. Razor automatically encodes any output, preventing malicious scripts from being executed.

    Safe Output Example:
        Input: <script>alert('XSS')</script>
        Rendered Output: &lt;script&gt;alert('XSS')&lt;/script&gt;
        Browser displays it as plain text, and no script is executed.

    Additional Steps for Security:
        Validate user input on both client and server to ensure it meets expected formats (e.g., no special characters in a comment).
        Implement a Content Security Policy (CSP) header to restrict the execution of unauthorized scripts:

Response.Headers.Add("Content-Security-Policy", "default-src 'self'; script-src 'self';");
























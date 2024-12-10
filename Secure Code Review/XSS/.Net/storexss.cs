using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;

namespace VulnerableApp.Controllers
{
    public class CommentsController : Controller
    {
        // In-memory storage for comments (simulating a database)
        private static List<string> comments = new List<string>();

        [HttpGet]
        public IActionResult Index()
        {
            // Display the comments
            ViewBag.Comments = comments;
            return View();
        }

        [HttpPost]
        public IActionResult Submit(string comment)
        {
            // Directly store the user input without validation or encoding
            comments.Add(comment);

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
            <li>@Html.Raw(comment)</li> <!-- Vulnerable to Stored XSS -->
        }
    </ul>
</body>
</html>



















...........................................................................................................................................
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////      
      


How This Code is Vulnerable

    Storing User Input Without Validation or Encoding:
        The application stores user input (comment) in the comments list without sanitization or encoding.
        Example: If an attacker submits <script>alert('XSS')</script>, it is stored as-is in the comments list.

    Unsafe Output Rendering:
        The Razor view uses @Html.Raw(comment) to display comments. This renders the HTML content directly in the browser.
        If the stored comment includes malicious JavaScript, it will be executed when the page is loaded.

    Example of Exploitation:
        An attacker submits <script>alert('XSS')</script> as a comment.
        When any user visits the comments page, the malicious script runs, showing an alert box or executing harmful code.

    Impact:
        Malicious scripts can:
            Steal user cookies or session tokens.
            Perform unauthorized actions on behalf of the user.
            Redirect users to malicious websites or deface the page.











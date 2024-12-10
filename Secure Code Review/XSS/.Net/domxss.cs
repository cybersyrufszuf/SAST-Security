Backend
using Microsoft.AspNetCore.Mvc;

namespace xssApp.Controllers
{
    public class ProfileController : Controller
    {
        [HttpGet]
        public IActionResult Index()
        {
            return View();
        }
    }
}








# Frontend

<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
    <h1>User Profile</h1>
    <p id="message"></p>

    <script>
        // Retrieve the `name` parameter from the URL
        const urlParams = new URLSearchParams(window.location.search);
        const name = urlParams.get("name");

        // Directly inject user input into the DOM without sanitization
        document.getElementById("message").innerHTML = "Welcome, " + name + "!";
    </script>
</body>
</html>






























...........................................................................................................................................
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


How This Code is Vulnerable

    Direct DOM Manipulation with Untrusted Input:
        The name parameter from the URL is directly extracted using URLSearchParams and injected into the DOM using innerHTML without sanitization.
        Example: An attacker can craft a URL like:

    http://localhost:5000/Profile/Index?name=<script>alert('XSS')</script>

Execution of Malicious Code:

    The browser interprets the injected <script> tag in the innerHTML as JavaScript and executes it.
    This allows an attacker to execute arbitrary scripts in the context of the victim's browser.

Impact:

    The attacker can:
        Steal sensitive information (e.g., cookies, session tokens).
        Perform unauthorized actions on behalf of the victim.
        Display phishing messages or deface the application.



















using System;
using System.IO;
using System.Net;
using Microsoft.AspNetCore.Mvc;

namespace VulnerableApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FetchController : ControllerBase
    {
        [HttpGet]
        public IActionResult GetExternalData([FromQuery] string url)
        {
            if (string.IsNullOrEmpty(url))
            {
                return BadRequest("URL parameter is required.");
            }

            try
            {
                // Directly use the user-provided URL to make an HTTP request
                var request = WebRequest.Create(url);
                using (var response = request.GetResponse())
                using (var reader = new StreamReader(response.GetResponseStream()))
                {
                    var data = reader.ReadToEnd();
                    return Ok(data);
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error fetching data: {ex.Message}");
            }
        }
    }
}

















////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
............................................................................................................................................


  How This snipped Code is Vulnerable

    User-Controlled Input:
        The url parameter is taken directly from the query string and passed to WebRequest.Create() without validation or restrictions.
        Example: An attacker could call the endpoint like this:

    GET /api/fetch?url=http://localhost:8080/admin

Impact:

    Access Internal Resources: The attacker could access internal/private network resources that are not meant to be exposed to the internet.
    Exfiltrate Data: If the internal service returns sensitive data, it can be leaked to the attacker.
    Leverage Internal Services: Attackers can interact with internal APIs or services, potentially triggering unintended actions.

Example Exploit:

    A crafted request to http://localhost:5000/api/fetch?url=http://127.0.0.1:6379 could allow the attacker to probe internal services (e.g., Redis, AWS metadata service).








How to Fix This Vulnerability

    Validate the Input URL:
        Ensure the URL adheres to a predefined format or allowlist of domains.

    Restrict Access to Internal IPs:
        Block requests to private IP ranges or loopback addresses.

    Use a Safe HTTP Client:
        Use HttpClient with proper validation and constraints instead of directly working with WebRequest.
















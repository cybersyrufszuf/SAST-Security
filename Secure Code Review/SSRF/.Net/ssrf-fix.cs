using System;
using System.IO;
using System.Net.Http;
using System.Net;
using Microsoft.AspNetCore.Mvc;

namespace SecureApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FetchController : ControllerBase
    {
        private static readonly string[] AllowedDomains = { "example.com", "trusted.com" };

        [HttpGet]
        public IActionResult GetExternalData([FromQuery] string url)
        {
            if (string.IsNullOrEmpty(url))
            {
                return BadRequest("URL parameter is required.");
            }

            try
            {
                // Validate the URL
                if (!IsUrlAllowed(url))
                {
                    return BadRequest("Invalid or disallowed URL.");
                }

                // Use HttpClient to fetch data
                using (var httpClient = new HttpClient())
                {
                    var response = httpClient.GetStringAsync(url).Result;
                    return Ok(response);
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error fetching data: {ex.Message}");
            }
        }

        private bool IsUrlAllowed(string url)
        {
            try
            {
                var uri = new Uri(url);

                // Validate against allowed domains
                foreach (var domain in AllowedDomains)
                {
                    if (uri.Host.EndsWith(domain, StringComparison.OrdinalIgnoreCase))
                    {
                        return true;
                    }
                }

                // Prevent access to internal/private IPs
                var hostEntry = Dns.GetHostEntry(uri.Host);
                foreach (var ipAddress in hostEntry.AddressList)
                {
                    if (IPAddress.IsLoopback(ipAddress) || IsPrivateIP(ipAddress))
                    {
                        return false;
                    }
                }
            }
            catch
            {
                return false;
            }
            return false;
        }

        private bool IsPrivateIP(IPAddress ipAddress)
        {
            byte[] bytes = ipAddress.GetAddressBytes();
            return (bytes[0] == 10) || // 10.0.0.0 - 10.255.255.255
                   (bytes[0] == 172 && (bytes[1] >= 16 && bytes[1] <= 31)) || // 172.16.0.0 - 172.31.255.255
                   (bytes[0] == 192 && bytes[1] == 168); // 192.168.0.0 - 192.168.255.255
        }
    }
}















///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
...........................................................................................................................................
Explanation of how this Above snipped code Fixes

    Domain Allowlist:
        The AllowedDomains array ensures that only specific domains like example.com and trusted.com are allowed.

    Block Internal IPs:
        The IsPrivateIP function blocks requests to private IP ranges (e.g., 10.x.x.x, 192.168.x.x) and loopback addresses (127.x.x.x).

    Use HttpClient:
        A safe and modern HTTP client is used instead of the legacy WebRequest.

    Error Handling:
        Gracefully handles invalid or malicious URLs by returning an appropriate error response.

Additional Recommendations

    Timeouts:
        Set timeouts on HTTP requests to prevent hanging connections.

    Limit Response Size:
        Restrict the maximum size of responses to avoid excessive resource consumption.

    Content-Type Validation:
        Validate that the response's Content-Type matches expected types (e.g., application/json).

    Use Firewall Rules:
        Employ firewall rules to block access to sensitive internal resources.









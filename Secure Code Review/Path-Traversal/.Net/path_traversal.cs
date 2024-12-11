using Microsoft.AspNetCore.Mvc;
using System.IO;

namespace FileDownloadApp.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class DownloadController : ControllerBase
    {
        [HttpGet("download")]
        public IActionResult DownloadFile([FromQuery] string filename)
        {
                                                                                                                                                     // Get the file path from the current directory (potentially vulnerable to path traversal)
            string filePath = Path.Combine(Directory.GetCurrentDirectory(), filename);

            // Check if the file exists and return it
            if (System.IO.File.Exists(filePath))
            {
                var bytes = System.IO.File.ReadAllBytes(filePath);
                return File(bytes, "application/octet-stream", filename);
            }
            else
            {
                return NotFound("File not found");
            }
        }
    }
}





















How is it Vulnerable?

The vulnerability in .NET implementations arises from path traversal attacks, where an attacker manipulates the filename parameter to access files outside the intended directory.
Key Issues:

    Unchecked filename Input:
        Both versions directly use the filename parameter provided by the user to construct the file path.
        If the user provides input like ../../../../etc/passwd (in UNIX systems), the constructed path can point to sensitive files outside the intended directory.

    Improper File Path Construction:
        The Path.Combine (in .NET) and os.path.join (in Python) functions do not inherently validate or restrict the constructed paths to the intended directory.

    Potential Disclosure of Sensitive Files:
        If an attacker successfully accesses a sensitive file (e.g., system configuration files or credentials), it could lead to severe security breaches.

Exploitation Example:

If an attacker sends the following HTTP request:

GET /download?filename=../../../../etc/passwd

    In the Python version, os.path.join(os.getcwd(), "../../../../etc/passwd") would resolve to /etc/passwd (on UNIX systems).
    In the .NET version, Path.Combine(Directory.GetCurrentDirectory(), "../../../../etc/passwd") would resolve similarly.

If such a file exists and the application allows it, it will be sent to the attacker.


















































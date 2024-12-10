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






















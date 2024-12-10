using Microsoft.AspNetCore.Mvc;
using System;
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
            // Define a base directory for file storage
            string safeBasePath = Path.Combine(Directory.GetCurrentDirectory(), "files");

            // Ensure the "files" directory exists
            if (!Directory.Exists(safeBasePath))
            {
                return NotFound("Base directory not found.");
            }

            // Check for null or empty filename
            if (string.IsNullOrWhiteSpace(filename))
            {
                return BadRequest("Filename cannot be empty.");
            }

            try
            {
                // Combine the base path with the provided filename
                string safeFilePath = Path.GetFullPath(Path.Combine(safeBasePath, filename));

                // Ensure the resolved path is within the allowed base directory
                if (!safeFilePath.StartsWith(safeBasePath, StringComparison.Ordinal))
                {
                    return BadRequest("Invalid file path.");
                }

                // Check if the file exists
                if (!System.IO.File.Exists(safeFilePath))
                {
                    return NotFound("File not found.");
                }

                // Read the file and return it as a response
                var fileBytes = System.IO.File.ReadAllBytes(safeFilePath);
                return File(fileBytes, "application/octet-stream", filename);
            }
            catch (Exception ex)
            {
                // Log the exception and return a generic error
                Console.WriteLine($"Error occurred: {ex.Message}");
                return StatusCode(500, "An error occurred while processing the request.");
            }
        }
    }
}

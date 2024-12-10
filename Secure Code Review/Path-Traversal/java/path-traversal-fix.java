import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileDownloadController {

    // Define a safe base directory for files
    private static final String SAFE_BASE_DIR = System.getProperty("user.dir") + File.separator + "files";

    @GetMapping("/download")
    public void downloadFile(@RequestParam String filename, HttpServletResponse response) throws IOException {
        // Check for null or empty filename
        if (filename == null || filename.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Filename cannot be empty");
            return;
        }

        try {
            // Resolve the requested file path
            Path requestedFilePath = Paths.get(SAFE_BASE_DIR, filename).normalize();

            // Ensure the resolved path is within the allowed base directory
            if (!requestedFilePath.startsWith(SAFE_BASE_DIR)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file path");
                return;
            }

            File file = requestedFilePath.toFile();

            // Check if the file exists and is a regular file
            if (file.exists() && file.isFile()) {
                // Send the file as a response
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                response.setContentType("application/octet-stream");
                Files.copy(requestedFilePath, response.getOutputStream());
                response.getOutputStream().flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            }
        } catch (Exception ex) {
            // Handle unexpected errors
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request");
        }
    }
}

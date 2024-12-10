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

    @GetMapping("/download")
    public void downloadFile(@RequestParam String filename, HttpServletResponse response) throws IOException {
        // Construct the file path (vulnerable to path traversal)
        Path filePath = Paths.get(System.getProperty("user.dir"), filename);

        File file = filePath.toFile();
        
        if (file.exists() && file.isFile()) {
            // Send the file as a response
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            response.setContentType("application/octet-stream");
            Files.copy(filePath, response.getOutputStream());
            response.getOutputStream().flush();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
        }
    }
}

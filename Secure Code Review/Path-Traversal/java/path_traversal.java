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
        // Construct the file path                                                                                                                                                                                       (vulnerable to path traversal)
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













////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
.....................................................................................................................................................................................................



How is it Vulnerable?

This Java implementation suffers from the path traversal vulnerability due to the following issues:

    Direct Use of User Input:
        The filename parameter is directly used to construct the file path (Paths.get(System.getProperty("user.dir"), filename)).

    Path Traversal Exploitation:
        An attacker can supply input like ../../../../etc/passwd or ..\..\..\..\windows\system32\config\sam to navigate out of the intended directory and access sensitive files on the server.

    File Access Without Validation:
        The code only checks whether the file exists but doesn’t validate whether the file is within an allowed directory.

    Potential Disclosure of Sensitive Files:
        If a sensitive file (e.g., configuration files or OS-level files) exists and is accessible, it could be exposed to attackers.

Exploitation Example

If an attacker sends the following request:

GET /download?filename=../../../../etc/passwd

    On UNIX-based systems, the file path would resolve to /etc/passwd, allowing an attacker to download it if the server process has access.

Similarly:

GET /download?filename=..\..\..\..\windows\system32\config\sam

    On Windows, the file path could resolve to sensitive system files.





















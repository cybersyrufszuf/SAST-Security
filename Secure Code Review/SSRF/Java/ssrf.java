import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/fetchData")
public class SSRFServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl = request.getParameter("url"); // User-controlled input
        
        if (targetUrl == null || targetUrl.isEmpty()) {
            response.getWriter().println("No URL provided!");
            return;
        }

        try {
                                                                                                                                            // Directly use the user-provided URL to make a request
            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseContent = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            // Return the response from the requested URL
            response.getWriter().println(responseContent.toString());
        } catch (Exception e) {
            response.getWriter().println("Error fetching URL: " + e.getMessage());
        }
    }
}



















............................................................................................................................................
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


How This above snipped Code is Vulnerable to SSRF

    User-Controlled Input for URL:
        The targetUrl parameter is directly used to make an HTTP request without validation or restrictions.
        An attacker can provide any URL, including internal/private network endpoints.

    Exploitation:
        Access Internal Resources: An attacker could access internal systems (e.g., http://localhost:8080/admin) that are not directly exposed to the internet.
        Leverage Private IPs: Access internal services like http://192.168.1.1 or http://127.0.0.1 to exploit vulnerable applications or services.
        Trigger Data Exfiltration: Combine SSRF with other vulnerabilities to exfiltrate sensitive data.

    Example Exploit:
        URL: http://yourserver.com/fetchData?url=http://localhost:8080/admin
        The attacker can access sensitive internal endpoints or services.








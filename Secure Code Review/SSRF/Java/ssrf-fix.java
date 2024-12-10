import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.InetAddress;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/fetchData")
public class SecureSSRFServlet extends HttpServlet {

    // Allowlist of trusted domains
    private static final String[] ALLOWED_DOMAINS = {"example.com", "trusted.com"};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUrl = request.getParameter("url"); // User input
        
        if (targetUrl == null || targetUrl.isEmpty()) {
            response.getWriter().println("No URL provided!");
            return;
        }

        try {
            // Validate the URL
            URL url = new URL(targetUrl);
            if (!isUrlAllowed(url)) {
                response.getWriter().println("Invalid or disallowed URL.");
                return;
            }

            // Proceed with the request if URL is valid
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseContent = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            response.getWriter().println(responseContent.toString());
        } catch (Exception e) {
            response.getWriter().println("Error fetching URL: " + e.getMessage());
        }
    }

    // Validate the URL against allowed domains and block internal IPs
    private boolean isUrlAllowed(URL url) {
        try {
            // Validate against the allowlist
            String host = url.getHost();
            for (String allowedDomain : ALLOWED_DOMAINS) {
                if (host.endsWith(allowedDomain)) {
                    return true;
                }
            }

            // Prevent access to internal/private IP ranges
            InetAddress address = InetAddress.getByName(host);
            if (address.isSiteLocalAddress() || address.isLoopbackAddress()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}





















...........................................................................................................................................
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Explanation of Fixes Above snipped code

    Allowlist Validation:
        The isUrlAllowed method ensures only URLs pointing to trusted domains like example.com and trusted.com are allowed.
        This mitigates access to unauthorized or malicious domains.

    Block Internal IP Addresses:
        The InetAddress class is used to block requests to private or loopback IP ranges (e.g., 127.0.0.1 or 192.168.x.x).

    Input Validation:
        Check the structure and format of the URL before processing it.
        Avoid processing untrusted user input without verification.

Additional Recommendations

    Time-Out Settings:
        Limit the time for HTTP connections to prevent hanging requests.

    HTTP Methods:
        Restrict requests to safe methods like GET. Avoid exposing sensitive actions like POST.

    Content Security Policy (CSP):
        Enforce strict policies on where resources can be loaded from.
























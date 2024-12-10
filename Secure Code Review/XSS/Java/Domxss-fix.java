import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/search")
public class SecureDOMXSSServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        response.getWriter().println("<html>");
        response.getWriter().println("<head><title>Search</title></head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h1>Search</h1>");
        response.getWriter().println("<input type='text' id='query' />");
        response.getWriter().println("<button onclick='doSearch()'>Search</button>");
        response.getWriter().println("<div id='result'></div>");

        // Secure JavaScript with proper encoding
        response.getWriter().println("<script>");
        response.getWriter().println("function doSearch() {");
        response.getWriter().println("  var params = new URLSearchParams(window.location.search);");
        response.getWriter().println("  var query = params.get('query');");
        response.getWriter().println("  if (query) {");
        response.getWriter().println("    var sanitizedQuery = document.createTextNode(query);"); // Use text nodes instead of innerHTML
        response.getWriter().println("    var resultDiv = document.getElementById('result');");
        response.getWriter().println("    resultDiv.textContent = 'Results for: ';");
        response.getWriter().println("    resultDiv.appendChild(sanitizedQuery);");
        response.getWriter().println("  }");
        response.getWriter().println("}");
        response.getWriter().println("</script>");

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}






























/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
.............................................................................................................................................................................................

Explanation of Fixes

    Use textContent or document.createTextNode():
        Replacing innerHTML with textContent or appending sanitized text nodes ensures the input is treated as plain text rather than HTML, neutralizing potential XSS payloads.

    Sanitize Input:
        While textContent and createTextNode inherently escape input, validating and sanitizing the input before use adds an additional layer of protection.

    Avoid Parsing Query Strings Manually:
        Use the URLSearchParams API, which safely extracts query parameters without risking manual parsing errors.

    Example of Safe Output:
        Input: <script>alert('XSS')</script>
        Rendered in Browser: Results for: <script>alert('XSS')</script> (as plain text, not executed).

Best Practices to Prevent DOM-Based XSS

    Minimize Dynamic Content Injection:
        Avoid injecting untrusted user input into the DOM. Use safe methods like textContent.

    Use Encoding Libraries:
        Consider libraries like OWASP Java Encoder or DOMPurify for additional sanitization if user input must be used.

    Implement Content Security Policy (CSP):
        Configure a CSP header to restrict script execution:

    response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self';");

Validate Input:

    Validate user input on both the server side and client side to ensure it meets expected formats.














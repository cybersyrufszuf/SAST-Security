import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.text.StringEscapeUtils; // Use a library for escaping HTML

@WebServlet("/greet")
public class SecureXSSServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        // Escape the user input to neutralize HTML/JavaScript code
        String escapedName = StringEscapeUtils.escapeHtml4(name);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Welcome, " + escapedName + "!</h1>"); // Properly escaped output
        out.println("</body>");
        out.println("</html>");
    }
}
























////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
.........................................................................................................................................................................................................................................

  
Explanation of Fixes:

    Escape User Input:
        The StringEscapeUtils.escapeHtml4() method from Apache Commons Text ensures that any potentially harmful HTML characters (e.g., <, >, &) in the name parameter are converted into their safe equivalents (&lt;, &gt;, &amp;).
        This prevents the browser from interpreting the input as actual HTML or JavaScript.

    Example of Safe Output:
        Input: <script>alert('XSS')</script>
        Escaped Output: &lt;script&gt;alert(&#39;XSS&#39;)&lt;/script&gt;
        Rendered in Browser: <script>alert('XSS')</script> (as plain text, not executed).

    Content Security Policy (Optional):
        Consider implementing a Content Security Policy (CSP) header to restrict the execution of scripts:

    response.setHeader("Content-Security-Policy", "default-src 'self';");

Input Validation:

    In addition to escaping, validate the input to ensure it adheres to expected formats:

        if (!name.matches("^[a-zA-Z0-9\\s]+$")) {
            name = "Guest"; // Default to a safe value if input is invalid
        }

































































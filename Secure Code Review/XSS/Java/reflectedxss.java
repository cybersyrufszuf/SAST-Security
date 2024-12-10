import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/greet")
public class VulnerableXSSServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name"); // User-supplied input

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Directly embedding the user input into the HTML response
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Welcome, " + name + "!</h1>"); // Vulnerable to XSS
        out.println("</body>");
        out.println("</html>");
    }
}

























/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
..........................................................................................................................................................

How This Code is Vulnerable

    Unvalidated User Input:
        The name parameter is taken directly from the HTTP request and included in the HTML response without any validation or sanitization.
        If an attacker provides malicious input (e.g., <script>alert('XSS')</script>), it will be executed in the victim's browser.

    Example of Exploitation:
        URL: http://localhost:8080/greet?name=<script>alert('XSS')</script>
        When a victim visits this URL, the browser will render the following response:

    <html>
        <body>
            <h1>Welcome, <script>alert('XSS')</script>!</h1>
        </body>
    </html>

    This causes the browser to execute the <script> tag, triggering the alert dialog.

Impact:

    The attacker can inject malicious scripts that steal cookies, session tokens, or sensitive information, or perform actions on behalf of the victim.





















































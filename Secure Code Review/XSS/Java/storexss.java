import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/comments")
public class VulnerableStoredXSSServlet extends HttpServlet {
    private static List<String> comments = new ArrayList<>(); // Stores user-submitted comments

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String comment = request.getParameter("comment"); // User-supplied input
        comments.add(comment); // Store the comment without sanitization

        response.sendRedirect("/comments"); // Redirect to view comments
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>Comments</h1>");
        out.println("<form method='POST' action='/comments'>");
        out.println("Comment: <input type='text' name='comment' />");
        out.println("<button type='submit'>Submit</button>");
        out.println("</form>");
        out.println("<ul>");

        // Display stored comments without escaping user input
        for (String comment : comments) {
            out.println("<li>" + comment + "</li>"); // Vulnerable to stored XSS
        }

        out.println("</ul>");
        out.println("</body></html>");
    }
}


















////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
.................................................................................................................................................................
  

How This Code is Vulnerable

    User Input is Stored and Displayed Without Validation or Escaping:
        The comment parameter is directly added to the comments list and then displayed on the page without any sanitization or escaping.
        If an attacker submits a malicious comment (e.g., <script>alert('XSS')</script>), it is stored and executed every time the comments section is loaded.

    Example of Exploitation:
        An attacker submits the following comment:

    <script>alert('XSS')</script>

    Every user who visits the comments page will see an alert pop-up triggered by the malicious script.

Impact:

    Malicious scripts could:
        Steal session cookies or sensitive data.
        Perform actions on behalf of users (e.g., CSRF).
        Deface the application or redirect users to malicious websites.






































































import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.text.StringEscapeUtils; // For escaping HTML

@WebServlet("/comments")
public class SecureStoredXSSServlet extends HttpServlet {
    private static List<String> comments = new ArrayList<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String comment = request.getParameter("comment");

        // Escape the user input to neutralize HTML/JavaScript code
        String sanitizedComment = StringEscapeUtils.escapeHtml4(comment);
        comments.add(sanitizedComment); // Store the sanitized comment

        response.sendRedirect("/comments");
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

        // Display sanitized comments
        for (String comment : comments) {
            out.println("<li>" + comment + "</li>");
        }

        out.println("</ul>");
        out.println("</body></html>");
    }
}



















Explanation of Fixes:

    Escape User Input:
        The StringEscapeUtils.escapeHtml4() method ensures that any potentially harmful characters in the comment (e.g., <, >, &, ') are converted to their HTML-safe equivalents.

    Sanitized Comment Storage:
        Only sanitized input is stored in the comments list, preventing malicious scripts from being saved.

    Display Escaped Output:
        When displaying the comments, escaped strings are used, ensuring that the browser interprets them as plain text rather than executable HTML or JavaScript.

    Example of Safe Output:
        Input: <script>alert('XSS')</script>
        Escaped Output: &lt;script&gt;alert(&#39;XSS&#39;)&lt;/script&gt;
        Rendered in Browser: <script>alert('XSS')</script> (as plain text).
















































































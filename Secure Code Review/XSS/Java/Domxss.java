import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/search")
public class VulnerableDOMXSSServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        response.getWriter().println("<html>");
        response.getWriter().println("<head><title>Search</title></head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h1>Search</h1>");
        response.getWriter().println("<input type='text' id='query' />");
        response.getWriter().println("<button onclick='doSearch()'>Search</button>");
        response.getWriter().println("<div id='result'></div>");

        // Vulnerable JavaScript that directly uses unsanitized user input
        response.getWriter().println("<script>");
        response.getWriter().println("function doSearch() {");
        response.getWriter().println("  var query = window.location.search.substring(1).split('=')[1];"); // Extract user input from URL
        response.getWriter().println("  document.getElementById('result').innerHTML = 'Results for: ' + query;"); // Vulnerable to DOM-Based XSS
        response.getWriter().println("}");
        response.getWriter().println("</script>");

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}



















How This Code is Vulnerable

    Untrusted User Input Manipulates the DOM:
        The JavaScript retrieves the query parameter from the URL using window.location.search and directly appends it to the DOM with innerHTML.
        If the input contains malicious code (e.g., <script>alert('XSS')</script>), the browser executes the injected script.

    Example of Exploitation:
        URL: http://localhost:8080/search?query=<script>alert('XSS')</script>
        When the victim opens this URL, the browser will render the following:

    <div id="result">Results for: <script>alert('XSS')</script></div>

    This triggers the malicious JavaScript and displays an alert box.

Impact:

    An attacker can execute arbitrary JavaScript in the victim's browser, leading to:
        Theft of cookies or session tokens.
        Keylogging or phishing attacks.
        Malicious redirects or defacement of the page.
















































































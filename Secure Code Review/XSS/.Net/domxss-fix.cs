#Frontend

<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
    <h1>User Profile</h1>
    <p id="message"></p>

    <script>
        // Retrieve the `name` parameter from the URL
        const urlParams = new URLSearchParams(window.location.search);
        const name = urlParams.get("name");

        // Sanitize user input by escaping special characters
        function sanitize(input) {
            const div = document.createElement("div");
            div.appendChild(document.createTextNode(input));
            return div.innerHTML;
        }

        // Safely insert sanitized input into the DOM
        document.getElementById("message").textContent = "Welcome, " + (name ? sanitize(name) : "Guest") + "!";
    </script>
</body>
</html>




















.........................................................................................................................................
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

Explanation of Fixes

    Input Sanitization:
        The sanitize function creates a new DOM element, adds the user input as a text node, and retrieves the escaped output.
        This ensures special characters like <, >, and " are safely encoded, preventing malicious code execution.

    Use of textContent:
        Instead of innerHTML, use textContent to inject user-provided data into the DOM. textContent inserts text as plain content, so it cannot execute HTML or JavaScript.

    Safe Output Example:
        Malicious Input: <script>alert('XSS')</script>
        Rendered Output: Welcome, &lt;script&gt;alert('XSS')&lt;/script&gt;
        The browser displays the input as plain text without executing it.

Additional Security Measures

    Avoid Unnecessary Client-Side Rendering:
        If possible, process user input on the server side and send sanitized data to the client.

    Content Security Policy (CSP):
        Implement a CSP header to block inline scripts and restrict script execution:

    Response.Headers.Add("Content-Security-Policy", "default-src 'self'; script-src 'self';");

Validation:

    Validate user input on the server side before it is ever sent to the client.

Security Libraries:

    Use security libraries like DOMPurify for robust sanitization if the application requires rendering HTML from untrusted sources.      





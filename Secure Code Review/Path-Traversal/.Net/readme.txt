How is it Vulnerable?

The vulnerability in .NET implementations arises from path traversal attacks, where an attacker manipulates the filename parameter to access files outside the intended directory.
Key Issues:

    Unchecked filename Input:
        This versions directly use the filename parameter provided by the user to construct the file path.
        If the user provides input like ../../../../etc/passwd (in UNIX systems), the constructed path can point to sensitive files outside the intended directory.

    Improper File Path Construction:
        The Path.Combine (in .NET) and os.path.join (in Python) functions do not inherently validate or restrict the constructed paths to the intended directory.

    Potential Disclosure of Sensitive Files:
        If an attacker successfully accesses a sensitive file (e.g., system configuration files or credentials), it could lead to severe security breaches.

Exploitation Example:

If an attacker sends the following HTTP request:

GET /download?filename=../../../../etc/passwd

    In the Python version, os.path.join(os.getcwd(), "../../../../etc/passwd") would resolve to /etc/passwd (on UNIX systems).
    In the .NET version, Path.Combine(Directory.GetCurrentDirectory(), "../../../../etc/passwd") would resolve similarly.

If such a file exists and the application allows it, it will be sent to the attacker.








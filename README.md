# SAST-Security
Secure Code review
Static Application Security Testing (SAST) Secure Code Review

Overview

Welcome to the Static Application Security Testing (SAST) Secure Code Review repository. This repository provides a comprehensive guide for identifying, analyzing, and mitigating vulnerabilities in Python, Java, and .NET (C#) code. It includes examples of vulnerable code snippets, explanations of how each vulnerability can be exploited, and secure coding practices to mitigate these issues.

The purpose of this repository is to help developers, security engineers, and code reviewers understand common vulnerabilities and improve their code's security posture through secure coding techniques and SAST.

Repository Structure

The repository is structured by language and vulnerability category:

.
|-- SQL
|   |-- .Net
|   |-- Java
|   |-- Python

|-- XSS
|   |-- .Net
|   |-- Java
|   |-- Python

|-- SSRF
    |-- .Net
    |-- Java
    |-- Python

Each folder contains:

Vulnerable Code: A code snippet demonstrating the vulnerability.

Explanation: A detailed explanation of why the code is vulnerable.

Secure Code: A corrected version of the code with secure practices applied.

Documentation: Additional resources, remediation steps, and best practices.

Vulnerabilities Covered

This repository covers a wide range of vulnerabilities in Python, Java, and .NET (C#):

1. Path Traversal

Description: Exploits improper validation of user input to access files outside the intended directory.

Example Languages: Python, Java, C# (.NET)

Fixes: Input sanitization, canonical path resolution, and directory restrictions.

2. SQL Injection

Description: Allows attackers to manipulate SQL queries by injecting malicious input.

Example Languages: Python, Java, C# (.NET)

Fixes: Use of parameterized queries and prepared statements.

3. Cross-Site Scripting (XSS)

Types Covered:

Reflected XSS

Stored XSS

DOM-based XSS

Description: Exploits insecure handling of untrusted user input to execute malicious scripts in a user’s browser.

Example Languages: Python (Flask), Java (Servlets), C# (.NET Web Applications)

Fixes: Input sanitization, output encoding, and use of security libraries.

4. Server-Side Request Forgery (SSRF)

Description: Exploits server-side applications that fetch remote resources based on user input.

Example Languages: Python, Java, C# (.NET)

Fixes: Restrict outbound requests, validate URLs, and whitelist allowed domains.

5. Authentication and Authorization Flaws

Description: Exploits improper user authentication or access control mechanisms.

Example Languages: Python (Flask), Java (Spring Boot), C# (.NET Identity)

Fixes: Use secure authentication protocols, enforce role-based access control (RBAC), and avoid hardcoding credentials.

6. Insecure Deserialization

Description: Exploits insecure deserialization of untrusted data to execute arbitrary code.

Example Languages: Python, Java, C#

Fixes: Validate input data, implement strict object validation, and avoid using insecure serialization formats.

7. Command Injection

Description: Allows attackers to execute arbitrary system commands through insecure input handling.

Example Languages: Python, Java, C#

Fixes: Use parameterized APIs and avoid concatenating user input in command executions.

8. Insecure File Upload

Description: Exploits insecure handling of uploaded files to execute arbitrary code or overwrite critical files.

Example Languages: Python (Flask), Java (Servlets), C# (.NET Web API)

Fixes: Restrict file types, validate file names, and store files securely outside of the webroot.

9. XML External Entity (XXE) Attacks

Description: Exploits improper XML parsing to access sensitive data or execute remote requests.

Example Languages: Java, C#

Fixes: Disable DTDs, use safe XML parsers, and validate XML input.

Example: Vulnerable Code and Fixes

Below is an example structure for the documentation:

Vulnerability: SQL Injection in Python

# Vulnerable Code:

import sqlite3

connection = sqlite3.connect('example.db')
cursor = connection.cursor()

username = input("Enter username: ")
password = input("Enter password: ")

// Vulnerable query
query = f"SELECT * FROM users WHERE username = '{username}' AND password = '{password}'"
cursor.execute(query)

if cursor.fetchone():
    print("Login successful")
else:
    print("Login failed")

Why It’s Vulnerable:

User input is directly concatenated into the SQL query, making it vulnerable to injection attacks.

An attacker can input admin' OR '1'='1 to bypass authentication.

Secure Code:

import sqlite3

connection = sqlite3.connect('example.db')
cursor = connection.cursor()

username = input("Enter username: ")
password = input("Enter password: ")

// Secure query with parameterized input
query = "SELECT * FROM users WHERE username = ? AND password = ?"
cursor.execute(query, (username, password))

if cursor.fetchone():
    print("Login successful")
else:
    print("Login failed")

Fix:

Use parameterized queries (?) to prevent SQL injection.

Avoid string concatenation for user inputs in queries.

Contributing

We welcome contributions to expand the repository with additional vulnerabilities, secure coding practices, or support for more languages.

How to Contribute:

Fork this repository.

Create a new branch for your changes.

Add your code snippet, explanation, and fix under the appropriate folder.

Submit a pull request with a detailed description of your contribution.

License

This repository is licensed under the MIT License. See LICENSE for more details.

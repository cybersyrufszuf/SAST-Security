from flask import Flask, request, send_file
import os

app = Flask(__name__)

@app.route('/download', methods=['GET'])
def download_file():
    filename = request.args.get('filename')

    # Vulnerable path traversal
    filepath = os.path.join(os.getcwd(), filename)

    # Check if file exists and send it
    if os.path.exists(filepath):
        return send_file(filepath, as_attachment=True)
    else:
        return "File not found", 404

if __name__ == '__main__':
    app.run(debug=True)














//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
..........................................................................................................................................

How this above Code is Vulnerable to Path Traversal

User-Controlled Input:

    The filename parameter is taken directly from the request query string (request.args.get('filename')).
    The os.path.join(os.getcwd(), filename) call constructs the file path based on this user-provided input.

Path Traversal Attack:

    An attacker can manipulate the filename parameter to include special characters like ../, which allows navigation to directories outside the intended folder.
    Example malicious request:

/download?filename=../../etc/passwd

This request could access sensitive system files like /etc/passwd on Linux systems.


















































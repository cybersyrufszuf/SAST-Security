from flask import Flask, request, send_file, abort
import os

app = Flask(__name__)

UPLOAD_DIRECTORY = 'uploads'

@app.route('/download', methods=['GET'])
def download_file():
    filename = request.args.get('filename')

    # Securely sanitize and validate the filename
    safe_filename = os.path.basename(filename)
    filepath = os.path.join(UPLOAD_DIRECTORY, safe_filename)

    # Ensure the file is within the upload directory
    if os.path.commonprefix([UPLOAD_DIRECTORY, filepath]) == UPLOAD_DIRECTORY and os.path.exists(filepath):
        return send_file(filepath, as_attachment=True)
    else:
        abort(404)

if __name__ == '__main__':
    app.run(debug=True)
















////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
.............................................................................................................................................



Key Security Features of the Code

    Sanitizing and Validating the Filename:
        The os.path.basename(filename) function extracts only the filename portion, removing any directory traversal components like ../ or /.
        Example:
            Input: ../../etc/passwd
            Output: passwd
        This ensures that any attempt to manipulate paths is neutralized.

    Restricting to a Specific Directory:
        The UPLOAD_DIRECTORY is the only allowed directory for file access.
        The code constructs the filepath by joining the sanitized filename with UPLOAD_DIRECTORY.

    Path Validation Using os.path.commonprefix:
        The os.path.commonprefix([UPLOAD_DIRECTORY, filepath]) == UPLOAD_DIRECTORY check ensures that the resolved filepath stays within the UPLOAD_DIRECTORY.
        Even if an attacker manages to manipulate the path somehow, this check prevents access to files outside the allowed directory.
        Example:
            Malicious input: ../../etc/passwd
            After sanitization: passwd
            Final path: <UPLOAD_DIRECTORY>/passwd
            Common prefix: <UPLOAD_DIRECTORY> (valid)

    Checking File Existence:
        The os.path.exists(filepath) ensures that the requested file exists before serving it. This prevents unnecessary errors or unauthorized behavior.

    Abort on Invalid Access:
        If the file doesn't exist or is outside the allowed directory, the code uses abort(404) to return a 404 error, ensuring unauthorized access attempts are handled gracefully.









from flask import Flask, request
import os
import werkzeug
from werkzeug.utils import secure_filename

app = Flask(__name__)

# Directory to save uploaded files
UPLOAD_FOLDER = "./uploads"
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

# Allowed file extensions
ALLOWED_EXTENSIONS = {'txt', 'png', 'jpg', 'jpeg', 'pdf'}

# Limit upload size (e.g., 2MB)
app.config['MAX_CONTENT_LENGTH'] = 2 * 1024 * 1024  # 2MB

def allowed_file(filename):
    # Check if file extension is allowed
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return "No file part", 400

    file = request.files['file']

    if file.filename == '':
        return "No selected file", 400

    if file and allowed_file(file.filename):
        # Use secure_filename to sanitize the filename
        filename = secure_filename(file.filename)
        filepath = os.path.join(UPLOAD_FOLDER, filename)
        file.save(filepath)
        return f"File uploaded to {filepath}"
    return "File type not allowed", 400

if __name__ == "__main__":
    app.run(debug=True)
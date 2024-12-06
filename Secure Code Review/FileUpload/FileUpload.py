from flask import Flask, request
import os

app = Flask(__name__)

# Directory to save uploaded files
UPLOAD_FOLDER = "./uploads"
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

@app.route('/upload', methods=['POST'])
def upload_file():
    # Get the uploaded file
    file = request.files['file']
    if file:
        # Save the file without validation
        filepath = os.path.join(UPLOAD_FOLDER, file.filename)
        file.save(filepath)
        return f"File uploaded to {filepath}"
    return "No file uploaded"

if __name__ == "__main__":
    app.run(debug=True)






    
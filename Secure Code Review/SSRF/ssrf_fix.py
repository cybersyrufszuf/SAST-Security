import requests
from urllib.parse import urlparse

# Secure function
def fetch_data(url):
    # Parse the URL to validate it
    parsed_url = urlparse(url)
    
    # Define an allowlist of domains
    allowed_domains = ["example.com", "api.example.com"]
    
    # Ensure the URL's hostname is in the allowlist
    if parsed_url.hostname not in allowed_domains:
        raise ValueError("Invalid URL: Access to this domain is not allowed")
    
    # Fetch data from the validated URL
    response = requests.get(url)
    return response.text

# Example usage
try:
    user_input = "http://api.example.com/data"  # Valid input
    print(fetch_data(user_input))
    
    # Malicious input would now raise an error
    malicious_input = "http://169.254.169.254/latest/meta-data/"
    print(fetch_data(malicious_input))  # Will raise ValueError
except ValueError as e:
    print(f"Error: {e}")
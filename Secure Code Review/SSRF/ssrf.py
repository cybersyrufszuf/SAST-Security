import requests

# Vulnerable function
def fetch_data(url):
    # Fetch data from a user-supplied URL
    response = requests.get(url)
    return response.text

# Example usage
user_input = "http://internal-service.local/secret"  # Malicious input
print(fetch_data(user_input))
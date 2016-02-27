from flask import Flask, render_template, request
import requests
import sys

api = 'http://localhost:3000/'
reportEndpoint = 'reports/'
try:
    api_key_file = open('google_api_key', 'r')
except FileNotFoundError:
    print "API key file not found. Exiting..."
    sys.exit(-1)
api_key = api_key_file.read().rstrip("\n\r")
api_key_file.close()

# EB looks for an 'application' callable by default.
application = Flask(__name__)

@application.route('/')
def get_root():
    url = api + reportEndpoint
    request = requests.get(url)
    return render_template('map.html', reports=request.json(), api_key=api_key)

# run the application.
if __name__ == '__main__':
    # Setting debug to True enables debug output. This line should be
    # removed before deploying a production application.
    application.run(debug = True)


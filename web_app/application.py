from flask import Flask, render_template, request
import requests

api = 'https://api.github.com/'
userURL = api + 'users/'

# EB looks for an 'application' callable by default.
application = Flask(__name__)

weight_map = [
        {'key': 'public_repos', 'weight': 10},
        {'key': 'public_gists', 'weight': 1},
        {'key': 'followers', 'weight': 20},
        {'key': 'following', 'weight': 1}
        ]

messages = ["bad",
        "weak",
        "lame",
        "passable",
        "decent",
        "worthy",
        "good",
        "great",
        "amazing"]

max_rank = 1000.0

@application.route('/')
def get_root():
    return render_template('index.html')

@application.route('/rank')
def get_predict():
    user = request.args.get('user')
    rank = None
    if (user != None and user != ""):
        rank = calculate_rank(user)
    index = min(len(messages)-1, int((rank/max_rank) * (len(messages) - 1)))
    message = messages[index]

    return render_template('rank.html',
            rank=rank,
            message=message)

def calculate_rank(user):
    url = userURL + user
    request = requests.get(url)
    user = request.json()
    score = 0
    for element in weight_map:
        score += element['weight'] * int(user[element['key']])
    return score

# run the application.
if __name__ == '__main__':
    # Setting debug to True enables debug output. This line should be
    # removed before deploying a production application.
    application.run(debug = True)


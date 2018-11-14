import sys
import os

sys.path.append(os.path.dirname(os.path.dirname(os.path.realpath(__file__))))

from flask_restful import Api
from flask import Flask
from api.classifier import Classifier

app = Flask(__name__)
api = Api(app)

api.add_resource(Classifier, '/classifier')

if __name__ == "__main__":
    app.run(debug=True, host='192.168.43.180')
from json import dumps
from flask_restful import reqparse, request, Resource
from api.identity_movement import classify
from intra.acquisition import data_processing

parser = reqparse.RequestParser()
parser.add_argument('series')

class Classifier(Resource):
    def post(self):
        body = request.json
        pred = classify(data_processing(body))
        return {'predict': pred}
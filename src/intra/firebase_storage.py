import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import xlsxwriter

cred = credentials.Certificate('keys/serviceAccount.json')
firebase_admin.initialize_app(cred)

db = firestore.client()

def getCollection(collection):
    coll = db.collection(collection)
    docs = coll.get()
    return docs
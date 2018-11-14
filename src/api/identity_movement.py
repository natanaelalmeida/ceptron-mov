from sklearn.externals import joblib
from sklearn.preprocessing import LabelEncoder, normalize

labels = [
    'andando', 
    'andando_passos_longos', 
    'correndo'
]

def classify(input):
    model = joblib.load('data/models/model_perform.joblib')
    input = normalize(input)    
    return enconding(model.predict(input))[0]

def enconding(x):
    encodend = LabelEncoder()
    encodend.fit(labels)
    return encodend.inverse_transform(x)
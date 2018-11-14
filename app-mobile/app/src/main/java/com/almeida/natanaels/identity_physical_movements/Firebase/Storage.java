package com.almeida.natanaels.identity_physical_movements.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final FirebaseFirestore db;

    public Storage() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void save(String collection, Map<String, Object> values){
        try{
            getCollection(collection).add(values)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("FirebaseStorage", "Error", e);
                }
            });

        } catch (Exception e){
            Log.e("FirebaseStorage", e.getMessage());
        }
    }

    public void classification(final String collection) {
        try {

            DocumentReference  doc = getCollection("classification").document(collection);
            doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();

                        Map<String, Object> data = new HashMap<>();
                        if(doc != null && doc.exists()) {
                            data = doc.getData();
                        }

                        if(data == null) {
                            data = new HashMap<>();
                        }

                        Object obj = data.get("quantity");
                        Integer quantity = obj != null ? Integer.parseInt(obj.toString()) : 0;
                        data.put("quantity", quantity + 1);
                        getCollection("classification").document(collection).set(data);
                    }
                }
            });

        } catch (Exception e) {
            Log.e("FirebaseStorage", e.getMessage());
        }
    }

    private CollectionReference getCollection(String collection){
        return db.collection(collection);
    }
}

package com.technolosea.sailwinder;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbLayer {
    private static final String TAG = "DbLayer";
    private List<Mark> marks = new ArrayList();
    FirebaseFirestore db;

    public DbLayer() {
        db = FirebaseFirestore.getInstance();

    }

    public void updateMarks() {
        db.collection("gates")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Mark m = new Mark();
                            m.coordinate1 = document.get("coordinate1", GeoPoint.class);
                            m.coordinate2 = document.get("coordinate2", GeoPoint.class);
                            m.time = document.getTimestamp("time");
                            m.gateType = GateType.valueOf(document.getString("gateType"));
                            m.order = document.getLong("order");
                            getMarks().add(m);
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
    }

    public void addTrackPoint(String userId, GeoPoint coordinate, Timestamp timeStamp) {
        // Create a new user with a first and last name
        Map<String, Object> track = new HashMap<>();
        track.put("coordinate", coordinate);
        track.put("time", timeStamp);
        track.put("userId", userId);

        // Add a new document with a generated ID
        db.collection("track")
            .add(track)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                }
            });
    }

    public List<Mark> getMarks() {
        return marks;
    }
}

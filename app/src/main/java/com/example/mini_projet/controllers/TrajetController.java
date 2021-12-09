package com.example.mini_projet.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mini_projet.models.Trajet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class TrajetController {

    public TrajetController(){ }

    public void ajouterTrajet(Trajet trajet){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference dr = db.collection("trajet").document("trajets");
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    dr.update("trajets", FieldValue.arrayUnion(trajet));
                }else{
                    Log.e("erreur", "Erreur de connexion à Firebase");
                }
            }
        });
    }
}

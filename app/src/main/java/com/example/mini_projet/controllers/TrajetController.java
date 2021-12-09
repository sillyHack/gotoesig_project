package com.example.mini_projet.controllers;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import com.example.mini_projet.AjouterTrajetActivity;
import com.example.mini_projet.models.Trajet;
import com.example.mini_projet.models.TrajetDocument;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class TrajetController {

    public TrajetController(){ }

    public void ajouterTrajet(Trajet trajet){
        // get the firebase instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // get the document ref
        DocumentReference dr = db.collection("trajet").document("trajets");
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    // add element to the array
                    dr.update("trajets", FieldValue.arrayUnion(trajet));
                }else{
                    Log.e("error", "an error occured while adding new traject");
                }
            }
        });
    }

}

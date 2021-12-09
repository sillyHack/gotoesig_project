package com.example.mini_projet.controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mini_projet.documents.UserDocument;
import com.example.mini_projet.gui.InscriptionActivity;
import com.example.mini_projet.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UserController {

    public UserController() { }

    public void ajouterUser(User user){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference dr = db.collection("user").document("users");
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    dr.update("users", FieldValue.arrayUnion(user));
                }else{
                    Log.e("erreur", "Erreur de connexion à Firebase");
                }
            }
        });
    }

    public void verifierUser(String email, String motDePasse, Activity activity, Class maClass) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cr = db.collection("user");
        DocumentReference dr = cr.document("users");
        dr.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    List<User> users = document.toObject(UserDocument.class).users;

                    for(int i = 0 ; i < users.size(); i++) {
                        if(users.get(i).getEmail().matches(email) & users.get(i).getMotDePasse().matches(motDePasse)) {
                            Intent monIntent = new Intent(activity, maClass);
                            monIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(monIntent);
                        }
                    }
                }
            }
        });
    }
}

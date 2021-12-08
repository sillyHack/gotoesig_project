package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mini_projet.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nom = null, prenom = null, email = null, ville = null, telephone = null, motDePasse = null;
    private String checkNom = null, checkPrenom = null, checkEmail = null, checkVille = null, checkTel = null, checkMdp = null;
    private Button btnValider = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_inscription);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        ville = findViewById(R.id.ville);
        telephone = findViewById(R.id.telephone);
        motDePasse = findViewById(R.id.motDePasse);
        btnValider = findViewById(R.id.btnValider);

        btnValider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        checkNom = nom.getText().toString();
        checkPrenom = prenom.getText().toString();
        checkEmail = email.getText().toString();
        checkVille = ville.getText().toString();
        checkTel = telephone.getText().toString();
        checkMdp = motDePasse.getText().toString();

        User U = new User(checkNom, checkPrenom, checkEmail, checkVille, checkTel, checkMdp, 0);

        if(!checkNom.matches("") & !checkPrenom.matches("") & !checkEmail.matches("") & !checkVille.matches("") & !checkTel.matches("") & !checkMdp.matches("") & v == btnValider) {
            db.collection("user").add(U).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(InscriptionActivity.this, "Vous êtes inscrit", Toast.LENGTH_LONG).show();
                    Intent monIntent = new Intent(InscriptionActivity.this, ConnexionActivity.class);
                    startActivity(monIntent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("GotoEsig", "Erreur de connexion à Firebase" + e.toString());
                }
            });
        } else {
            Toast.makeText(InscriptionActivity.this, "Erreur d'inscription", Toast.LENGTH_LONG).show();
        }
    }
}
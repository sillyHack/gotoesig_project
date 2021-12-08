package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email = null, motDePasse = null;
    private Button btnConnexion = null, btnInscription = null;
    private String checkEmail = null, checkMdp =null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_connexion);

        email = findViewById(R.id.email);
        motDePasse = findViewById(R.id.motDePasse);
        btnConnexion = findViewById(R.id.btnConnexion);
        btnInscription = findViewById(R.id.btnInscription);

        btnConnexion.setOnClickListener(this);
        btnInscription.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        checkEmail = email.getText().toString();
        checkMdp = motDePasse.getText().toString();

        if(!checkEmail.matches("") & !checkMdp.matches("") & v == btnConnexion) {
            CollectionReference userRef = db.collection("user");
            Query query = userRef.whereEqualTo("email", checkEmail);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            String conn_email = document.getString("email");
                            String conn_mdp = document.getString("motDePasse");

                            if(checkEmail.matches(conn_email) & checkMdp.matches(conn_mdp)) {
                                Intent monIntent = new Intent(ConnexionActivity.this, AccueilActivity.class);
                                startActivity(monIntent);
                            }
                        }
                    } else {
                        Log.w("GotoEsig", "Erreur de connexion à Firebase" + task.getException());
                    }
                }
            });
        } else if(v == btnInscription) {
            Intent monIntent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
            startActivity(monIntent);
        } else {
            Toast.makeText(ConnexionActivity.this, "Erreur de connexion", Toast.LENGTH_LONG).show();
        }
    }
}
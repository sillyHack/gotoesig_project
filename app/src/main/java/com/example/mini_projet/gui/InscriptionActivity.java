package com.example.mini_projet.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mini_projet.R;
import com.example.mini_projet.controllers.UserController;
import com.example.mini_projet.models.User;

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etNom = null, etPrenom = null, etEmail = null, etVille = null, etTelephone = null, etMotDePasse = null;
    private Button btnValider = null;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_inscription);

        etNom = findViewById(R.id.nom);
        etPrenom = findViewById(R.id.prenom);
        etEmail = findViewById(R.id.email);
        etMotDePasse = findViewById(R.id.motDePasse);
        btnValider = findViewById(R.id.btnValider);

        btnValider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(!etNom.getText().toString().matches("") & !etPrenom.getText().toString().matches("") & !etEmail.getText().toString().matches("")  & !etMotDePasse.getText().toString().matches("") & v == btnValider) {

            user = new User();

            user.setNom(etNom.getText().toString());
            user.setPrenom(etPrenom.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.setMotDePasse(etMotDePasse.getText().toString());

            UserController uc = new UserController();
            uc.ajouterUser(user);
            Toast.makeText(InscriptionActivity.this, "Vous êtes inscrit", Toast.LENGTH_LONG).show();
            Intent monIntent = new Intent(InscriptionActivity.this, ConnexionActivity.class);
            startActivity(monIntent);
        } else {
            Toast.makeText(InscriptionActivity.this, "Erreur d'inscription", Toast.LENGTH_LONG).show();
        }
    }
}
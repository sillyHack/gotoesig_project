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

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail = null, etMotDePasse = null;
    private Button btnConnexion = null, btnInscription = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_connexion);

        etEmail = findViewById(R.id.email);
        etMotDePasse = findViewById(R.id.motDePasse);
        btnConnexion = findViewById(R.id.btnConnexion);
        btnInscription = findViewById(R.id.btnInscription);

        btnConnexion.setOnClickListener(this);
        btnInscription.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(!etEmail.getText().toString().matches("") & !etMotDePasse.getText().toString().matches("") & v == btnConnexion) {
            UserController uc = new UserController();
            uc.verifierUser(etEmail.getText().toString(), etMotDePasse.getText().toString(), ConnexionActivity.this, AccueilActivity.class);

        } else if(v == btnInscription) {
            Intent monIntent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
            startActivity(monIntent);
        } else {
            Toast.makeText(ConnexionActivity.this, "Erreur de connexion", Toast.LENGTH_LONG).show();
        }
    }
}
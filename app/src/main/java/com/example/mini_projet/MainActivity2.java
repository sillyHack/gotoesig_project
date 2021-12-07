package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    EditText nom = null, prenom = null, email = null, ville = null, telephone = null, mot_de_passe = null;
    String checkNom = null, checkPrenom = null, checkEmail = null, checkVille = null, checkTel = null,  checkMdp = null;
    Button btnValider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main2);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        ville = findViewById(R.id.ville);
        telephone = findViewById(R.id.telephone);
        mot_de_passe = findViewById(R.id.mot_de_passe);
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
        checkMdp = mot_de_passe.getText().toString();

        if(!checkNom.matches("") & !checkPrenom.matches("") & !checkEmail.matches("") & !checkVille.matches("") & !checkTel.matches("") & !checkMdp.matches("") & v == btnValider) {
            Intent monIntent = new Intent(this, MainActivity.class);
            startActivity(monIntent);
        }
    }
}
package com.example.mini_projet.gui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mini_projet.R;
import com.example.mini_projet.controllers.UserController;
import com.example.mini_projet.models.User;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNom = null, etPrenom = null, etEmail = null, etMotDePasse = null;
    private Button btnValider = null;
    User user = new User();

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
        if (!etNom.getText().toString().matches("") && !etPrenom.getText().toString().matches("") && !etEmail.getText().toString().matches("") && !etMotDePasse.getText().toString().matches("") && v == btnValider) {

            user = new User();
            UserController uc = new UserController();

            user.setNom(etNom.getText().toString());
            user.setPrenom(etPrenom.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.setVille(" ");
            user.setTelephone(getNumeroTel());
            user.setMotDePasse(etMotDePasse.getText().toString());
            user.setScore(0);

            uc.ajouterUser(user);
            Toast.makeText(InscriptionActivity.this, "Vous êtes inscrit", Toast.LENGTH_LONG).show();
            Intent monIntent = new Intent(InscriptionActivity.this, ConnexionActivity.class);
            startActivity(monIntent);
        } else {
            Toast.makeText(InscriptionActivity.this, "Erreur d'inscription", Toast.LENGTH_LONG).show();
        }
    }

    public String getNumeroTel() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        }
        String numero_tel = tm.getLine1Number();
        return numero_tel;
    }
}
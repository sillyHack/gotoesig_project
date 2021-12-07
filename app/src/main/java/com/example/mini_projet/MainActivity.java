package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email = null, mot_de_passe = null;
    Button btnConnexion = null, btnInscription = null;
    String checkEmail = null, checkMdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        mot_de_passe = findViewById(R.id.mot_de_passe);
        btnConnexion = findViewById(R.id.btnConnexion);
        btnInscription = findViewById(R.id.btnInscription);

        btnConnexion.setOnClickListener(this);
        btnInscription.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        checkEmail = email.getText().toString();
        checkMdp = mot_de_passe.getText().toString();

        if(!checkEmail.matches("") & !checkMdp.matches("") & v == btnConnexion) {
            Intent monIntent = new Intent(this, MainActivity3.class);
            startActivity(monIntent);
        } else if(v == btnInscription){
            Intent monIntent = new Intent(this, MainActivity2.class);
            startActivity(monIntent);
        }
    }
}
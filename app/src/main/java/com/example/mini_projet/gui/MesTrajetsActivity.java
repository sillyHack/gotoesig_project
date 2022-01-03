package com.example.mini_projet.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mini_projet.R;
import com.example.mini_projet.documents.TrajetDocument;
import com.example.mini_projet.models.Trajet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class MesTrajetsActivity extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawerLayout;
    private ListView lvTrajets;
    private Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mes_trajets);

        // getting the components
        lvTrajets = findViewById(R.id.lvTrajets);
        btnRetour = findViewById(R.id.btnRetour);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference dr = db.collection("trajet").document("trajets");
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    List<Trajet> listTrajets = task.getResult().toObject(TrajetDocument.class).trajets;
                    lvTrajets.setAdapter(new CustomListAdapter(MesTrajetsActivity.this, (ArrayList<Trajet>) listTrajets));
                }else{
                    Log.w("gotoEsig", "Erreur lors de la recuperation de la liste des trajets");
                }
            }
        });

        // set the listeners
        lvTrajets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Trajet t = (Trajet) lvTrajets.getItemAtPosition(position);
                Toast.makeText(MesTrajetsActivity.this, "Selected :" + " " + t.getDate(), Toast.LENGTH_SHORT).show();
            }
        });
        btnRetour.setOnClickListener(this);
    }

    public void ClickMenu(View v) {
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View v) {
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private static void redirectActivity(Activity activity, Class maClass) {
        Intent monIntent = new Intent(activity, maClass);
        monIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(monIntent);
    }

    public void ClickProfil(View v) {
        redirectActivity(this, AccueilActivity.class);
    }

    public void ClickAjoutTrajet(View v) {
        redirectActivity(this,AjouterTrajetActivity.class);
    }

    public void ClickMesTrajets(View v) {
        redirectActivity(this,MesTrajetsActivity.class);
    }

    public void ClickChercherTrajet(View v) {
        redirectActivity(this,AccueilActivity.class);
    }

    public void ClickEvaluerTrajet(View v) {
        redirectActivity(this,AccueilActivity.class);
    }

    public void ClickStatistiques(View v) {
        redirectActivity(this,AccueilActivity.class);
    }

    public void ClickQuitter(View v) {
        redirectActivity(this,AccueilActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnRetour){
            redirectActivity(this, AccueilActivity.class);
        }
    }
}
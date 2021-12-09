package com.example.mini_projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mini_projet.controllers.TrajetController;
import com.example.mini_projet.models.Trajet;
import com.example.mini_projet.models.TrajetDocument;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AjouterTrajetActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    DrawerLayout drawerLayout;
    Trajet trajet = null;
    private Spinner spinMT = null;
    private EditText etPtDepart = null;
    private EditText etPtArrivee = null;
    private Spinner spinDay = null;
    private Spinner spinMonth = null;
    private Spinner spinYear = null;
    private EditText etHeure = null;
    private EditText etRetardTolere = null;
    private EditText etNbPlacesDispo = null;
    private Switch swCompAuto = null;
    private EditText etContribution = null;
    private Button btnValiderAjoutTrajet = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ajouter_trajet);

        // getting the components
        drawerLayout = findViewById(R.id.drawer_layout_ajout_trajet);
        spinMT = findViewById(R.id.spinMT);
        etPtDepart = findViewById(R.id.etPtDepart);
        etPtArrivee = findViewById(R.id.etPtArrivee);
        spinDay = findViewById(R.id.spinDay);
        spinMonth = findViewById(R.id.spinMonth);
        spinYear = findViewById(R.id.spinYear);
        etHeure = findViewById(R.id.etHeure);
        etRetardTolere = findViewById(R.id.etRetardTolere);
        etNbPlacesDispo = findViewById(R.id.etNbPlacesDispo);
        swCompAuto = findViewById(R.id.swCompAuto);
        etContribution = findViewById(R.id.etContribution);
        btnValiderAjoutTrajet = findViewById(R.id.btnValiderAjoutTrajet);

        /** Setting the spinner **/
        // get the firebase instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // get the document ref
        DocumentReference docRef = db.collection("trajet").document("moyensTransport");
        /*DocumentReference dr = db.collection("trajet").document("trajets");
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Trajet t1 = new Trajet();
                    t1.setNbPlacesDispo("17");
                    DocumentSnapshot document = task.getResult();
                    List<Trajet> trajets = document.toObject(TrajetDocument.class).trajets;
                    List<String> myList = new ArrayList<>();
                    myList.add(trajets.get(0).getMoyenTransport());
                    myList.add(trajets.get(0).getPtArrivee());
                    Log.w("test", "element:"+myList.get(0));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AjouterTrajetActivity.this, android.R.layout.simple_spinner_dropdown_item, myList);
                    spinMT.setAdapter(adapter);

                    // add element to the array
                    dr.update("trajets", FieldValue.arrayUnion(t1));
                }else{
                    Log.w("error", "an error occured");
                }
            }
        });*/

        // get the list of transport ways
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    List<String> listMT = (List<String>) task.getResult().get("moyensTransp");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AjouterTrajetActivity.this, android.R.layout.simple_spinner_dropdown_item, listMT);
                    spinMT.setAdapter(adapter);
                }else{
                    Log.w("gotoEsig", "Error while getting the Transport ways list");
                }
            }
        });
        /** Set the day spinner **/
        List<String> listDays = new ArrayList<>();
        for(int i=1; i<=31; i++){
            listDays.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(AjouterTrajetActivity.this, android.R.layout.simple_spinner_dropdown_item, listDays);
        spinDay.setAdapter(adapterDays);

        /** Set the month spinner **/
        List<String> listMonths = new ArrayList<>();
        for(int i=1; i<=12; i++){
            listMonths.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterMonths = new ArrayAdapter<String>(AjouterTrajetActivity.this, android.R.layout.simple_spinner_dropdown_item, listMonths);
        spinMonth.setAdapter(adapterMonths);

        /** Set the year spinner **/
        List<String> listYears = new ArrayList<>();
        for(int i=2021; i<=2025; i++){
            listYears.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterYears = new ArrayAdapter<String>(AjouterTrajetActivity.this, android.R.layout.simple_spinner_dropdown_item, listYears);
        spinYear.setAdapter(adapterYears);

        /** Disable the contribution and autoroute field **/
        swCompAuto.setEnabled(false);
        etContribution.setEnabled(false);

        // set the listeners
        btnValiderAjoutTrajet.setOnClickListener(this);
        spinMT.setOnItemSelectedListener(this);
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
        redirectActivity(this,AccueilActivity.class);
    }

    public void ClickAjoutTrajet(View v) {
        redirectActivity(this,AjouterTrajetActivity.class);
    }

    public void ClickMesTrajets(View v) {
        redirectActivity(this,AccueilActivity.class);
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
        if(view.getId() == R.id.btnValiderAjoutTrajet){
            String numbers[] = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
            List<String> numbersList = new ArrayList<>(Arrays.asList(numbers));
            String day, month, year = null;
            if(numbersList.contains(spinDay.getSelectedItem().toString())){
                day = "0"+spinDay.getSelectedItem().toString();
            }else{
                day = spinDay.getSelectedItem().toString();
            }

            if(numbersList.contains(spinMonth.getSelectedItem().toString())){
                month = "0"+spinMonth.getSelectedItem().toString();
            }else{
                month = spinMonth.getSelectedItem().toString();
            }
            year = spinYear.getSelectedItem().toString();
            //now we set the full date
            String date = day+"/"+month+"/"+year;

            // we create the 'trajet'
            trajet = new Trajet();
            trajet.setMoyenTransport(spinMT.getSelectedItem().toString());
            trajet.setPtDepart(etPtDepart.getText().toString());
            trajet.setPtArrivee(etPtArrivee.getText().toString());
            trajet.setDate(date);
            trajet.setHeure(etHeure.getText().toString());
            trajet.setRetardTolere(etRetardTolere.getText().toString());
            trajet.setNbPlacesDispo(etNbPlacesDispo.getText().toString());
            trajet.setComprendAutoroute(swCompAuto.isChecked());
            trajet.setContribution(etContribution.getText().toString());
            // Instanciation of the controller
            TrajetController tc = new TrajetController();
            // Adding the 'trajet'
            tc.ajouterTrajet(trajet);
            Toast.makeText(this, "trajet ajouté avec succès!", Toast.LENGTH_SHORT).show();
            redirectActivity(this, AccueilActivity.class);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String moyenTransport = (String) parent.getItemAtPosition(position);
        if(moyenTransport.equals("vehicule")){
            swCompAuto.setEnabled(true);
            etContribution.setEnabled(true);
        }else{
            etContribution.setText("");
            swCompAuto.setChecked(false);
            swCompAuto.setEnabled(false);
            etContribution.setEnabled(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
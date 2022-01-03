package com.example.mini_projet.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.mini_projet.R;
import com.example.mini_projet.controllers.TrajetController;
import com.example.mini_projet.models.Trajet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjouterTrajetActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    DrawerLayout drawerLayout;
    Trajet trajet = null;
    private Spinner spinMT = null;
    private EditText etPtDepart = null;
    private Spinner spinDay = null;
    private Spinner spinMonth = null;
    private Spinner spinYear = null;
    private EditText etHeure = null;
    private EditText etRetardTolere = null;
    private EditText etNbPlacesDispo = null;
    private Switch swCompAuto = null;
    private EditText etContribution = null;
    private Button btnValiderAjoutTrajet = null;
    AlertDialog.Builder builder;

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
        for(int i=2022; i<=2025; i++){
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
        redirectActivity(this, AccueilActivity.class);
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

    public void showDialog(Trajet t){
        builder = new AlertDialog.Builder(this);

        //Setting message manually and performing action on button click
        builder.setMessage("Confirmez-vous l'enregistrement de ce trajet ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        // Instanciation of the controller
                        TrajetController tc = new TrajetController();
                        // Adding the 'trajet'
                        tc.ajouterTrajet(t);
                        Toast.makeText(getApplicationContext(),"trajet ajouté avec succès!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();
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
            trajet.setDate(date);
            trajet.setHeure(etHeure.getText().toString());
            trajet.setRetardTolere(etRetardTolere.getText().toString());
            trajet.setNbPlacesDispo(etNbPlacesDispo.getText().toString());
            trajet.setComprendAutoroute(swCompAuto.isChecked());
            trajet.setContribution(etContribution.getText().toString());

            OkHttpClient client = new OkHttpClient();

            Request requestCoordPtDepart = new Request.Builder()
                    .url("https://api.openrouteservice.org/geocode/autocomplete?api_key=5b3ce3597851110001cf6248c8d7034edd1140f3a0751d6d3a6e7c12&text="+ trajet.getPtDepart() +"&boundary.rect.min_lon=0.9928593563226462&boundary.rect.min_lat=49.359600944261054&boundary.rect.max_lon=1.15439688855147&boundary.rect.max_lat=49.48095804254113")
                    .build();

            Request requestCoordPtArrivee = new Request.Builder()
                    .url("https://api.openrouteservice.org/geocode/autocomplete?api_key=5b3ce3597851110001cf6248c8d7034edd1140f3a0751d6d3a6e7c12&text="+ trajet.getPtArrivee() +"&boundary.rect.min_lon=0.9928593563226462&boundary.rect.min_lat=49.359600944261054&boundary.rect.max_lon=1.15439688855147&boundary.rect.max_lat=49.48095804254113")
                    .build();

            client.newCall(requestCoordPtDepart).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    final String webContent = response.body().string();
                    if(!response.isSuccessful()){
                        Log.e("error", "api error occured!");
                        AjouterTrajetActivity.this.runOnUiThread(() -> {
                            Toast.makeText(AjouterTrajetActivity.this, "adresse du point de depart introuvable", Toast.LENGTH_SHORT).show();
                        });
                    }else{
                        try {
                            JSONObject jsonObject = new JSONObject(webContent);
                            JSONArray jsonArray = new JSONArray(jsonObject.getString("features"));
                            JSONObject geoObject = jsonArray.getJSONObject(0);
                            JSONObject coordObject = new JSONObject(geoObject.getString("geometry"));
                            String coordinates = coordObject.getString("coordinates").replace("[", "").replace("]", "");
                            String lon = coordinates.split(",")[0];
                            String lat = coordinates.split(",")[1];
                            trajet.setLatPtDepart(lat);
                            trajet.setLonPtDepart(lon);
                            Log.d("coord_depart:", "lon: " + lon + ", lat:" + lat);


                            client.newCall(requestCoordPtArrivee).enqueue(new Callback() {
                                @Override
                                public void onFailure(Request request, IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Response response) throws IOException {
                                    final String webContent = response.body().string();
                                    if(!response.isSuccessful()){
                                        Log.e("error", "api error occured!");
                                        AjouterTrajetActivity.this.runOnUiThread(() -> {
                                            Toast.makeText(AjouterTrajetActivity.this, "adresse introuvable", Toast.LENGTH_SHORT).show();
                                        });
                                    }else{
                                        try {
                                            JSONObject jsonObject = new JSONObject(webContent);
                                            JSONArray jsonArray = new JSONArray(jsonObject.getString("features"));
                                            JSONObject geoObject = jsonArray.getJSONObject(0);
                                            JSONObject coordObject = new JSONObject(geoObject.getString("geometry"));
                                            String coordinates = coordObject.getString("coordinates").replace("[", "").replace("]", "");
                                            String lon = coordinates.split(",")[0];
                                            String lat = coordinates.split(",")[1];
                                            trajet.setLatPtArrivee(lat);
                                            trajet.setLonPtArrivee(lon);
                                            Log.d("coord_arrivee-esig:", "lon: " + lon + ", lat:" + lat);

                                            // getting duration(sec) and distance(km)
                                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                                            JSONObject parameter = new JSONObject();
                                            Double[][] coords = {
                                                    {Double.valueOf(trajet.getLonPtDepart()),Double.valueOf(trajet.getLatPtDepart())},
                                                    {Double.valueOf(trajet.getLonPtArrivee()),Double.valueOf(trajet.getLatPtArrivee())}
                                            };
                                            try {
                                                parameter.put("locations",new JSONArray(coords));
                                                parameter.put("metrics", new JSONArray(new Object[] {"distance", "duration"}));
                                                parameter.put("units","km");
                                                Log.d("string_json", parameter.toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            RequestBody body = RequestBody.create(JSON, parameter.toString());
                                            Request requestDistance = new Request.Builder()
                                                    .url("https://api.openrouteservice.org/v2/matrix/driving-car")
                                                    .post(body)
                                                    .addHeader("content-type", "application/json; charset=utf-8")
                                                    .addHeader("Authorization", "5b3ce3597851110001cf6248c8d7034edd1140f3a0751d6d3a6e7c12")
                                                    .build();

                                            client.newCall(requestDistance).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(Request request, IOException e) {
                                                    Log.e("response_error", "an error occured");
                                                }

                                                @Override
                                                public void onResponse(Response response) throws IOException {
                                                    final String webContent = response.body().string();
                                                    if(!response.isSuccessful()){
                                                        Log.e("error", "api error occured!");
                                                        AjouterTrajetActivity.this.runOnUiThread(() -> {
                                                            Toast.makeText(AjouterTrajetActivity.this, "Erreur lors de la recuperation de la distance", Toast.LENGTH_SHORT).show();
                                                        });
                                                    }else{
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(webContent);
                                                            JSONArray jsonArrayDistance = new JSONArray(jsonObject.getString("distances"));
                                                            JSONArray jsonArrayDuration = new JSONArray(jsonObject.getString("durations"));
                                                            Log.d("duration", jsonArrayDuration.get(0).toString().split(",")[1].replace("]", ""));
                                                            Log.d("distance", jsonArrayDistance.get(0).toString().split(",")[1].replace("]", ""));
                                                            trajet.setDistance(jsonArrayDistance.get(0).toString().split(",")[1].replace("]", ""));
                                                            trajet.setDuree(jsonArrayDuration.get(0).toString().split(",")[1].replace("]", ""));

                                                            AjouterTrajetActivity.this.runOnUiThread(() -> {
                                                                showDialog(trajet);
                                                            });

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                }
                                            });

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            AjouterTrajetActivity.this.runOnUiThread(() -> {
                                                Toast.makeText(AjouterTrajetActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                                            });
                                            //return;
                                        }
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AjouterTrajetActivity.this.runOnUiThread(() -> {
                                Toast.makeText(AjouterTrajetActivity.this, "Verifiez l'adresse du point de depart", Toast.LENGTH_SHORT).show();
                            });
                            //return;
                        }
                    }
                }
            });
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
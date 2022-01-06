package com.example.mini_projet.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mini_projet.R;
import com.example.mini_projet.documents.TrajetDocument;
import com.example.mini_projet.models.Trajet;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChercherUnTrajetActivity extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawerLayout;
    private EditText etPtDepartSearch;
    private Spinner spinDaySearch;
    private Spinner spinMonthSearch;
    private Spinner spinYearSearch;
    private Button btnRechercher;
    private TextView tvAucunResultat;
    private ListView lvTrajets;
    private Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chercher_un_trajet);
        Map_Fragment fragment = new Map_Fragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

        // getting the components
        drawerLayout = findViewById(R.id.drawer_layout_chercher_un_trajet);
        etPtDepartSearch = findViewById(R.id.etPtDepartSearch);
        spinDaySearch = findViewById(R.id.spinDaySearch);
        spinMonthSearch = findViewById(R.id.spinMonthSearch);
        spinYearSearch = findViewById(R.id.spinYearSearch);
        btnRechercher = findViewById(R.id.btnRechercher);
        tvAucunResultat = findViewById(R.id.tvAucunResultat);
        lvTrajets = findViewById(R.id.lvTrajets);
        btnValider = findViewById(R.id.btnValider);

        /** Set the day spinner **/
        List<String> listDays = new ArrayList<>();
        for(int i=1; i<=31; i++){
            listDays.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(ChercherUnTrajetActivity.this, android.R.layout.simple_spinner_dropdown_item, listDays);
        spinDaySearch.setAdapter(adapterDays);

        /** Set the month spinner **/
        List<String> listMonths = new ArrayList<>();
        for(int i=1; i<=12; i++){
            listMonths.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterMonths = new ArrayAdapter<String>(ChercherUnTrajetActivity.this, android.R.layout.simple_spinner_dropdown_item, listMonths);
        spinMonthSearch.setAdapter(adapterMonths);

        /** Set the year spinner **/
        List<String> listYears = new ArrayList<>();
        for(int i=2022; i<=2025; i++){
            listYears.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterYears = new ArrayAdapter<String>(ChercherUnTrajetActivity.this, android.R.layout.simple_spinner_dropdown_item, listYears);
        spinYearSearch.setAdapter(adapterYears);

        // on met le message 'aucun resultat' en invisible par defaut
        tvAucunResultat.setVisibility(View.INVISIBLE);

        // set the listeners
        btnRechercher.setOnClickListener(this);
        lvTrajets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Trajet t = (Trajet) lvTrajets.getItemAtPosition(position);
                Toast.makeText(ChercherUnTrajetActivity.this, "Selected :" + " " + t.getPtDepart(), Toast.LENGTH_SHORT).show();
                /*ChercherUnTrajetActivity.this.runOnUiThread(() -> {
                    redirectActivity(ChercherUnTrajetActivity.this, MapsActivity.class);
                });*/
                OkHttpClient client = new OkHttpClient();

                Request requestGeoJson = new Request.Builder()
                        .url("https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248c8d7034edd1140f3a0751d6d3a6e7c12&start=8.681495,49.41461&end=8.687872,49.420318")
                        .build();

                Bundle b = new Bundle();
                b.putString("latDpt", t.getLatPtDepart());
                b.putString("lonDpt", t.getLonPtDepart());
                b.putString("latArr", t.getLatPtArrivee());
                b.putString("lonArr", t.getLonPtArrivee());
                fragment.setArguments(b);
                Log.d("FRAGMENT_SIZE", String.valueOf(getSupportFragmentManager().getFragments().size()));
                if(getSupportFragmentManager().getFragments().size() != 0) {
                    getSupportFragmentManager().getFragments().clear();
                    getSupportFragmentManager().beginTransaction().detach(fragment).commit();
                }
                else
                    getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fragment).commit();
                //getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().getFragments().get(1));
            }
        });
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
        redirectActivity(this, ProfilActivity.class);
    }

    public void ClickAjoutTrajet(View v) {
        redirectActivity(this,AjouterTrajetActivity.class);
    }

    public void ClickMesTrajets(View v) {
        redirectActivity(this,MesTrajetsActivity.class);
    }

    public void ClickChercherTrajet(View v) {
        redirectActivity(this,ChercherUnTrajetActivity.class);
    }

    public void ClickEvaluerTrajet(View v) {
        redirectActivity(this,AccueilActivity.class);
    }

    public void ClickStatistiques(View v) {
        redirectActivity(this,AccueilActivity.class);
    }

    public void ClickQuitter(View v) {
        redirectActivity(this,ConnexionActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnRechercher){
            // get the searched adress
            String adresseSearched = etPtDepartSearch.getText().toString();
            // format the date
            String numbers[] = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
            List<String> numbersList = new ArrayList<>(Arrays.asList(numbers));
            String day, month, year = null;
            if(numbersList.contains(spinDaySearch.getSelectedItem().toString())){
                day = "0"+spinDaySearch.getSelectedItem().toString();
            }else{
                day = spinDaySearch.getSelectedItem().toString();
            }

            if(numbersList.contains(spinMonthSearch.getSelectedItem().toString())){
                month = "0"+spinMonthSearch.getSelectedItem().toString();
            }else{
                month = spinMonthSearch.getSelectedItem().toString();
            }
            year = spinYearSearch.getSelectedItem().toString();
            //now we set the full date
            String dateSearched = day+"/"+month+"/"+year;

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference dr = db.collection("trajet").document("trajets");
            dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        List<Trajet> listTrajets = task.getResult().toObject(TrajetDocument.class).trajets;
                        List<Trajet> listTrajetsSearched = new ArrayList<>();
                        for(Trajet t : listTrajets){
                            if(t.getDate().equals(dateSearched) && t.getPtDepart().contains(adresseSearched))
                                listTrajetsSearched.add(t);
                        }
                        if(listTrajetsSearched.size() != 0) {
                            lvTrajets.setAdapter(new CustomListAdapter(ChercherUnTrajetActivity.this, (ArrayList<Trajet>) listTrajetsSearched));
                            tvAucunResultat.setVisibility(View.INVISIBLE);
                            btnValider.setVisibility(View.VISIBLE);
                        }else{
                            tvAucunResultat.setVisibility(View.VISIBLE);
                            btnValider.setVisibility(View.INVISIBLE);
                            lvTrajets.setAdapter(new CustomListAdapter(ChercherUnTrajetActivity.this, (ArrayList<Trajet>) listTrajetsSearched));
                        }

                    }else{
                        Log.w("gotoEsig", "Erreur lors de la recuperation de la liste des trajets recherchés");
                    }
                }
            });
        }
    }
}
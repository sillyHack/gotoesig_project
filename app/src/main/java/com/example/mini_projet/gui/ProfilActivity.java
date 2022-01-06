package com.example.mini_projet.gui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mini_projet.R;
import com.example.mini_projet.controllers.UserController;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;

public class ProfilActivity extends AppCompatActivity {

    public static String Image = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private TextView tvNomComplet = null, tvVille = null, tvTelephone = null, tvScore = null;
    private ImageView photo_profil = null;
    DrawerLayout drawerLayout;
    private ImageView photo = null;

    public Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profil);

        drawerLayout = findViewById(R.id.drawer_layout);

        tvNomComplet = findViewById(R.id.tvNomComplet);
        tvNomComplet.setText("Nom : " + UserController.Nom + " " + UserController.Prenom);

        tvVille = findViewById(R.id.tvVille);
        tvVille.setText("Ville : " + UserController.Ville);

        tvTelephone = findViewById(R.id.tvTelephone);
        tvTelephone.setText("Telephone : " + UserController.Tel);

        tvScore = findViewById(R.id.tvScore);
        tvScore.setText("Score : " + UserController.Score);

        photo = findViewById(R.id.photo_profil);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choisirImage();
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
        redirectActivity(this, AjouterTrajetActivity.class);
    }

    public void ClickMesTrajets(View v) {
        redirectActivity(this, MesTrajetsActivity.class);
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

    private void choisirImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            photo.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Téléchargement image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        Uri file = Uri.fromFile(new File("images/" + randomKey));
        StorageReference imageRef = storageReference.child("images/image.jpg");

        imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image téléchargée", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Echec téléchargement", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPourcent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Pourcentage; " + (int) progressPourcent + "%");
            }
        });
    }
}
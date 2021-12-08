package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity3 extends AppCompatActivity{

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main3);

        drawerLayout = findViewById(R.id.drawer_layout);
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
        redirectActivity(this,MainActivity.class);
    }

    public void ClickAjoutTrajet(View v) {
        redirectActivity(this,AjouterTrajetActivity.class);
    }

    public void ClickMesTrajets(View v) {
        redirectActivity(this,MainActivity.class);
    }

    public void ClickChercherTrajet(View v) {
        redirectActivity(this,MainActivity.class);
    }

    public void ClickEvaluerTrajet(View v) {
        redirectActivity(this,MainActivity.class);
    }

    public void ClickStatistiques(View v) {
        redirectActivity(this,MainActivity.class);
    }

    public void ClickQuitter(View v) {
        redirectActivity(this,MainActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}
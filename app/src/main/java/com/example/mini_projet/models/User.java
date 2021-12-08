package com.example.mini_projet.models;

public class User {

    String email;
    String password;
    String nom;
    String prenom;
    String tel;
    String ville;
    float score;

    public User(String email, String password, String nom, String prenom, String tel, String ville, float score){
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.ville = ville;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}

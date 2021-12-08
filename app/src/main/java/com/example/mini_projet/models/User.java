package com.example.mini_projet.models;

public class User {

    String nom;
    String prenom;
    String email;
    String ville;
    String telephone;
    String motDePasse;
    float score;

    public User(String nom, String prenom, String email, String ville, String telephone, String motDePasse, float score){
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.ville = ville;
        this.telephone = telephone;
        this.motDePasse = motDePasse;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String tel) {
        this.telephone = telephone;
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

package com.example.mini_projet.models;

import java.util.Date;

public class Trajet {

    String moyenTransport;
    String ptDepart;
    String latPtDepart;
    String lonPtDepart;
    String ptArrivee = "esigelec";
    String latPtArrivee;
    String lonPtArrivee;
    String date;
    String heure;
    String retardTolere;
    String nbPlacesDispo;
    boolean comprendAutoroute;
    String contribution;
    String distance;
    String duree;
    String etatTrajet = "en cours";
    String emailUser;

    public Trajet(){ }

    public Trajet(String moyenTransport, String ptDepart, String latPtDepart, String lonPtDepart, String ptArrivee, String latPtArrivee, String lonPtArrivee, String date, String heure, String retardTolere, String nbPlacesDispo, boolean comprendAutoroute, String contribution, String distance, String duree, String etatTrajet, String emailUser){
        this.moyenTransport = moyenTransport;
        this.ptDepart = ptDepart;
        this.latPtDepart = latPtDepart;
        this.lonPtDepart = lonPtDepart;
        this.ptArrivee = ptArrivee;
        this.latPtArrivee = latPtArrivee;
        this.lonPtArrivee = lonPtArrivee;
        this.date = date;
        this.heure = heure;
        this.retardTolere = retardTolere;
        this.nbPlacesDispo = nbPlacesDispo;
        this.comprendAutoroute = comprendAutoroute;
        this.contribution = contribution;
        this.distance = distance;
        this.duree = duree;
        this.etatTrajet = etatTrajet;
        this.emailUser = emailUser;
    }

    public String getPtDepart() {
        return ptDepart;
    }

    public void setPtDepart(String ptDepart) {
        this.ptDepart = ptDepart;
    }

    public String getPtArrivee() {
        return ptArrivee;
    }

    public void setPtArrivee(String ptArrivee) {
        this.ptArrivee = ptArrivee;
    }

    public String getMoyenTransport() {
        return moyenTransport;
    }

    public void setMoyenTransport(String moyenTransport) {
        this.moyenTransport = moyenTransport;
    }

    public String getLatPtDepart() {
        return latPtDepart;
    }

    public void setLatPtDepart(String latPtDepart) {
        this.latPtDepart = latPtDepart;
    }

    public String getLonPtDepart() {
        return lonPtDepart;
    }

    public void setLonPtDepart(String lonPtDepart) {
        this.lonPtDepart = lonPtDepart;
    }

    public String getLatPtArrivee() {
        return latPtArrivee;
    }

    public void setLatPtArrivee(String latPtArrivee) {
        this.latPtArrivee = latPtArrivee;
    }

    public String getLonPtArrivee() {
        return lonPtArrivee;
    }

    public void setLonPtArrivee(String lonPtArrivee) {
        this.lonPtArrivee = lonPtArrivee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getRetardTolere() {
        return retardTolere;
    }

    public void setRetardTolere(String retardTolere) {
        this.retardTolere = retardTolere;
    }

    public String getNbPlacesDispo() {
        return nbPlacesDispo;
    }

    public void setNbPlacesDispo(String nbPlacesDispo) {
        this.nbPlacesDispo = nbPlacesDispo;
    }

    public boolean isComprendAutoroute() {
        return comprendAutoroute;
    }

    public void setComprendAutoroute(boolean comprendAutoroute) { this.comprendAutoroute = comprendAutoroute; }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getEtatTrajet() {
        return etatTrajet;
    }

    public void setEtatTrajet(String etatTrajet) {
        this.etatTrajet = etatTrajet;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}

package com.example.mini_projet.models;

import java.util.Date;

public class Trajet {

    String moyenTransport;
    String ptDepart;
    String ptArrivee;
    String date;
    String heure;
    String retardTolere;
    String nbPlacesDispo;
    boolean comprendAutoroute;
    String contribution;

    public Trajet(String moyenTransport, String ptDepart, String ptArrivee, String date, String heure, String retardTolere, String nbPlacesDispo, boolean comprendAutoroute, String contribution){
        this.moyenTransport = moyenTransport;
        this.ptDepart = ptDepart;
        this.ptArrivee = ptArrivee;
        this.date = date;
        this.heure = heure;
        this.retardTolere = retardTolere;
        this.nbPlacesDispo = nbPlacesDispo;
        this.comprendAutoroute = comprendAutoroute;
        this.contribution = contribution;
    }

    public Trajet(){}

    public String getMoyenTransport() {
        return moyenTransport;
    }

    public void setMoyenTransport(String moyenTransport) {
        this.moyenTransport = moyenTransport;
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

    public void setComprendAutoroute(boolean comprendAutoroute) {
        this.comprendAutoroute = comprendAutoroute;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }
}

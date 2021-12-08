package com.example.mini_projet.models;

import java.util.Date;

public class Trajet {

    String moyenTransport;
    String ptDepart;
    String ptArrivee;
    Date date;
    int heure;
    int retardTolere;
    int nbPlacesDispo;
    boolean comprendAutoroute;
    float contribution;

    public Trajet(String moyenTransport, String ptDepart, String ptArrivee, Date date, int heure, int retardTolere, int nbPlacesDispo, boolean comprendAutoroute, float contribution){
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getRetardTolere() {
        return retardTolere;
    }

    public void setRetardTolere(int retardTolere) {
        this.retardTolere = retardTolere;
    }

    public int getNbPlacesDispo() {
        return nbPlacesDispo;
    }

    public void setNbPlacesDispo(int nbPlacesDispo) {
        this.nbPlacesDispo = nbPlacesDispo;
    }

    public boolean isComprendAutoroute() {
        return comprendAutoroute;
    }

    public void setComprendAutoroute(boolean comprendAutoroute) {
        this.comprendAutoroute = comprendAutoroute;
    }

    public float getContribution() {
        return contribution;
    }

    public void setContribution(float contribution) {
        this.contribution = contribution;
    }
}

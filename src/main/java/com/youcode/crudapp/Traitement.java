package com.youcode.crudapp;

public class Traitement {
    private int idTrait;
    private String traitement;
    private double montant;

    public Traitement(int id,String T,double M){
        this.idTrait=id;
        this.traitement=T;
        this.montant=M;
    }

    public int getIdTrait() {
        return idTrait;
    }

    public void setIdTrait(int idTrait) {
        this.idTrait = idTrait;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getTraitement() {
        return traitement;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }
}

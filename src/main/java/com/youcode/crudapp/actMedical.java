package com.youcode.crudapp;

import java.time.LocalDate;
import java.util.InputMismatchException;

public class actMedical {
private int idActMedical;
private String CIN;
private String DD;
private String DF;
private String traitement;
private  String montant;

public actMedical(String cl, String dd, String df, String T, String M){

    this.CIN=cl;
    this.DD=dd;
    this.DF=df;
    this.traitement=T;
    this.montant=M;
}
    public actMedical(int id, String cl, String dd,String df,  String T, String M){
        this.idActMedical=id;
        this.CIN=cl;
        this.DD=dd;
        this.DF=df;
        this.traitement=T;
        this.montant=M;

    }

    public int getIdActMedical() {
        return idActMedical;
    }

    public void setIdActMedical(int idActMedical) {
        this.idActMedical = idActMedical;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getDD() {
        return DD;
    }

    public void setDD(String DD) {
        this.DD = DD;
    }

    public String getDF() {
        return DF;
    }

    public void setDF(String DF) {
        this.DF = DF;
    }

    public String getTraitement() {
        return traitement;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }
}

package com.youcode.crudapp;

//import java.time.LocalDate;

public class client {
    private String CIN;
    private String nom;
    private String prenom;
    private String Tel;
    private String sexe;
    private String dateNaissance;

    public client(String CIN, String nom, String prenom,String tel,String sexe,String date) {
        this.CIN=CIN;
        this.nom=nom;
        this.prenom=prenom;
        this.Tel=tel;
        this.sexe=sexe;
        this.dateNaissance=date;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String clientCIN) {
        this.CIN = clientCIN;
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

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }


    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }
}

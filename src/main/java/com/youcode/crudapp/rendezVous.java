package com.youcode.crudapp;

import java.time.LocalDate;
import java.time.LocalTime;

public class rendezVous {
    private int id_RDV;
    private String CIN;
     private String dateRV;
     private String hour;

    public rendezVous( String cin, String dateRV, String hour) {

        this.dateRV = dateRV;
        this.hour = hour;
        this.CIN=cin;

    }
   public rendezVous(int id_RDV, String cin, String dateRV, String hour) {
       this.id_RDV=id_RDV;
        this.dateRV = dateRV;
        this.hour = hour;
        this.CIN=cin;

    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getDateRV() {
        return dateRV;
    }

    public void setDateRV(String dateRV) {
        this.dateRV = dateRV;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getId_RDV() {
        return id_RDV;
    }

    public void setId_RDV(int id_RDV) {
        this.id_RDV = id_RDV;
    }
}

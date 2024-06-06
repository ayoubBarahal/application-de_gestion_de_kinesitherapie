package com.youcode.crudapp;

//import java.util.Objects;

public class Users {
    private String username;

    private String fonction;
    private String mdp;

public Users(String username, String fonction, String mdp){
    this.username=username;
    this.fonction=fonction;
    this.mdp=mdp;
}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}

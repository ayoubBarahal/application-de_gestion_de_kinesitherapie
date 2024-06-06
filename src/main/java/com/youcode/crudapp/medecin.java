package com.youcode.crudapp;

public class medecin extends Users{

    public medecin(String username,String fonction, String mdp) {
        super(username, fonction, mdp);
    }

    public void createNurse(String username, String mdp){
        new medecin(username,"assistant",mdp);

    }
    public void deleteNurse(String username){

    }
    public void createClient(){}
    public void deleteClient(){}

}

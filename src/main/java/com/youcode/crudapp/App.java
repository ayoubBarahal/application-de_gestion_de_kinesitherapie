package com.youcode.crudapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;



public class App extends Application  {

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception{

        Parent parent = FXMLLoader.load((getClass().getResource("/fxml/login.fxml")));
        Scene scene = new Scene(parent);

        stage.setTitle("kiné-Othman");
        stage.setScene(scene);
        try {
            Image img = new Image((getClass().getResourceAsStream("/fxml/doctorlogo.png")));
            stage.getIcons().add(img);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }
        stage.show();
    }

}

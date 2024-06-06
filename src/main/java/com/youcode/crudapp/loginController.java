package com.youcode.crudapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {
    @FXML

    public ImageView loginimg;
    @FXML
    TextField userName;
    @FXML
    PasswordField passWord;
    @FXML
    Button btnLogin;
    @FXML
    ImageView logo;

    Connect sec = new Connect();

    public void Login(ActionEvent event) throws IOException {
        Parent root;
        Stage stage;
        Scene scene;

        String username = userName.getText().toLowerCase();
        String password = passWord.getText().toLowerCase();
/*
        if(username.equals("younesse") && password.equals("123")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/medecinAcceuill.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }

        else  if(username.equals("ayoub") && password.equals("123")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nurseAcceuil.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            
            stage.show();
        }*/
        try {
                Connection conn = sec.connect();

                PreparedStatement p = conn.prepareStatement("SELECT fonction FROM Users WHERE username =? AND mdp =?");

                p.setString(1, username);
                p.setString(2, password);

                ResultSet rs = p.executeQuery();
                if (rs.next()) {
                    String function = rs.getString("fonction");
                    System.out.println(function);
                    try {
                        if (function.equalsIgnoreCase("assistant")) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/nurseAcceuil.fxml"));
                            root = loader.load();
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setTitle("kiné-Othman");
                            stage.setScene(scene);
                            stage.show();
                        } else if (function.equalsIgnoreCase("medecin")) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/medecinAcceuill.fxml"));
                            root = loader.load();
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setTitle("kiné-Othman");
                            stage.setScene(scene);
                            stage.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("couldn't load fxml page" + e.getMessage());
                    }
                } else {
                    showAlert("Username or password incorrect.");
                }
            }
        catch (SQLException ex) {
            //throw new RuntimeException(ex);
            ex.printStackTrace();
        }
    }
    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERREUR");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    } // done

    public TextField getUserName(ActionEvent event) {
        return userName;
    }

    public PasswordField getPassWord(ActionEvent event) {
        return passWord;
    }
}



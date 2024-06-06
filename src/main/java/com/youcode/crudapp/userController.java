package com.youcode.crudapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class userController {
    @FXML
    public TextField fonction;

    @FXML
    public TextField mdp;
    @FXML
    public TextField username;
    @FXML
    public ImageView addimg;
    @FXML
    public Button btnAjoutUser;
    @FXML
    public Button exitHome;

    static Connect sec =  new Connect();
    public void addUser(ActionEvent event) throws SQLException {
        String user = username.getText();
        String Mdp = mdp.getText();
        String Fct = fonction.getText();
        if(user.isEmpty() | Mdp.isEmpty() | Fct.isEmpty()){
            showAlertE("champs obligatoires");
        }
        else {
            ajouterUser(user, Mdp, Fct);
        }
    }  // done
    private void ajouterUser(String user, String mdp, String fonction) throws SQLException {
        try {
            Connection conn = sec.connect();
            PreparedStatement p = conn.prepareStatement("INSERT INTO Users values (?,?,?)");
            p.setString(1, user);
            p.setString(2, mdp);
            p.setString(3, fonction);
            int rowsAffected = p.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client with ID " + user + " added successfully.");
                showAlertS("le client client est ajouté avec succès ! ");

            } else {
                System.out.println("Failed to add client with ID " + user + ".");
                showAlertE("Failed to add client ");
            }

            conn.close(); // Close connection
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    } // done
    private void showAlertE(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERREUR");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }  // done
    private void showAlertS(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCES");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }  // done

    public void Home(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAjouterPatient.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    }
}

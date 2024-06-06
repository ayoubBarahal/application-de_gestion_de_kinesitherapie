package com.youcode.crudapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class medecinPatientController implements Initializable {

    @FXML
    public TextField CIN  ;
    @FXML
    public Button Acceuil;
    @FXML
    public Button patient;
    @FXML
    public Button actMedical;
    @FXML
    public Button disconnect;
    @FXML
    public Button ajouter;
    @FXML
    public DatePicker datenaissance;
    @FXML
    public Button modifier;
    @FXML
    public TextField Tel;
    @FXML
    public TextField sexe;
    @FXML
    public TextField prenom;
    @FXML
    public Button btnAjtNurse;
    @FXML
    public TextField nom;
    @FXML
    public TableColumn<client,String> CINcol;
    @FXML
    public TableView<client> tableClient;
    @FXML
    public TableColumn<client,String> Nomcol;
    @FXML
    public TableColumn<client,String> prenomcol;
    @FXML
    public TableColumn<client,Integer> Telcol;
    @FXML
    public TableColumn<client,String> sexecol;
    @FXML
    public TableColumn<client,String> datecol;
    @FXML
    public TextField idPatient;
    @FXML
    public Button supprimerPatient;

    static Connect sec =  new Connect();
    @FXML
    public ComboBox<String> comboSex;


    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        comboSex.setItems(FXCollections.observableArrayList("homme", "femme"));
        CINcol.setCellValueFactory(new PropertyValueFactory<client,String>("CIN"));
        Nomcol.setCellValueFactory(new PropertyValueFactory<client,String>("Nom"));
        prenomcol.setCellValueFactory(new PropertyValueFactory<client,String>("Prenom"));
        Telcol.setCellValueFactory(new PropertyValueFactory<client,Integer>("Tel"));
        sexecol.setCellValueFactory(new PropertyValueFactory<client,String>("sexe"));
        datecol.setCellValueFactory(new PropertyValueFactory<client,String>("dateNaissance"));

        try {
            ObservableList<client> clients = FXCollections.observableArrayList();

            getPatients(clients);

            tableClient.setItems(clients);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    } // done
    public void Disconnect(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();

    } // done
    public void Patient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAjouterPatient.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    } // done
    public void getActMedical(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinActMedical.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    }  // done
    public void Acceuil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAcceuill.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    }  // done
    private void ajouterClient(String clientCIN, String Nom, String Prenom, int Telephone, String sexe, String dateNaissance) {

        try {
            Connection conn = sec.connect();
            PreparedStatement p = conn.prepareStatement("INSERT INTO client VALUES (?, ?, ?, ?, ?, ?)");
            p.setString(1, clientCIN);
            p.setString(2, Nom);
            p.setString(3, Prenom);
            p.setInt(4, Telephone);
            p.setString(5, sexe);
            p.setString(6, dateNaissance);

            int rowsAffected = p.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client with ID " + clientCIN + " added successfully.");
            } else {
                System.out.println("Failed to add client with ID " + clientCIN + ".");
            }

            conn.close(); // Close connection

        } catch(SQLException ee) {
            if (ee.getMessage().contains("UNIQUE constraint failed")) {
                System.err.println("Client with ID " + clientCIN + " already exists.");
            } else {
                System.out.println("Error: " + ee.getMessage());
            }
        }
    }
    public void addClient(ActionEvent event) throws IOException {
      try {
          String Nom = nom.getText();
          String Prenom = prenom.getText();
          String cin = CIN.getText();
          String Sexe = sexe.getText();
          int tel = Integer.parseInt(Tel.getText());
          String DN = String.valueOf(datenaissance.getValue());

          ajouterClient(cin, Nom, Prenom, tel, Sexe, DN);
      }
      catch (NumberFormatException e) {


          showAlert("il est obligatoire de remplir tous les champs.");

        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAjouterPatient.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    }   // done
    public void deletePatient(ActionEvent event) throws IOException, SQLException {
       try {
           String cinPatient = idPatient.getText();

           supprimerPatient(cinPatient);
       }

       catch (NumberFormatException e) {
           if(idPatient == null){
               showAlert("veuillez renseigner le champs.");
           }
       }

       catch (Exception e) {
           // Handle other exceptions
           e.printStackTrace();
           showAlert("Une erreur est survenue. Veuillez réessayer.");
       }

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAjouterPatient.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    }   // done
    private void supprimerPatient(String CIN) {
            try {
                Connection conn = sec.connect();
                PreparedStatement p = conn.prepareStatement("DELETE FROM client WHERE CIN = ?");
                p.setString(1, CIN);

                int rowsAffected = p.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Client with ID " + CIN + " deleted successfully.");
                } else {
                    System.out.println("Client with ID " + CIN + " not found.");
                    showAlert("ce patient n'existe pas dans la base de données.");
                }

                conn.close(); // Close connection

            } catch(SQLException ee) {
                System.out.println("Error: " + ee.getMessage());
            }
        }  // done
    private void getPatients(ObservableList<client> clients) throws SQLException {
        Connection conn = sec.connect();
        PreparedStatement p = conn.prepareStatement("SELECT  * FROM client");
        ResultSet rs = p.executeQuery();

        while (rs.next()) {
            String CIN = rs.getString("CIN");
            String nom = rs.getString("Nom");
            String prenom = rs.getString("Prenom");
            String tel = String.valueOf(rs.getInt("Tel"));
            String sexe = rs.getString("sexe");
            String dateNaissance = rs.getString("dateNaissance");
            //LocalDate dateN = LocalDate.parse(dateNaissance);

            clients.add(new client(CIN, nom, prenom, tel, sexe, dateNaissance));
        }
    } // done || utilisé dans initialize
    public void getPatient(ActionEvent event) {

      client clientSelectionne = tableClient.getSelectionModel().getSelectedItem();

        if (clientSelectionne != null) {

        remplireFormulaire(clientSelectionne);
        supprimerPatient(clientSelectionne.getCIN());
        } else {
            showAlert("Veuillez sélectionner un patient à modifier.");
        }
    }                                                   // NOT done yet !!!!!!!!!!!
    public void addNurse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ajouterUtilisateur.fxml"));
        Parent  root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Doctor area");
        stage.setScene(scene);
        stage.show();
    } // done


    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERREUR");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }  // done
    private void remplireFormulaire(client cl){
        CIN.setText(cl.getCIN());
        nom.setText(cl.getNom());
        prenom.setText(cl.getPrenom());
        Tel.setText(cl.getTel());
        sexe.setText(cl.getSexe());
        datenaissance.setPromptText(String.valueOf(cl.getDateNaissance()));

    }
}


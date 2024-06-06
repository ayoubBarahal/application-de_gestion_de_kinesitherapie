//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.youcode.crudapp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;

public class nurseController implements Initializable {
    @FXML
    public  Button RDV ;
    @FXML
    public TextField CIN;
    @FXML
    public TextField Tel;
    @FXML
    public TextField sexe;
    @FXML
    public DatePicker datenaissance;
    @FXML
    public TextField nom;
    @FXML
    public TextField prenom;
    @FXML
    public Button addRV;
    @FXML
    public Button ajouter;
    @FXML
    public TableView<client> tableClient;
    @FXML
    public TableColumn<client, String> CINcol;
    @FXML
    public TableColumn<client, String> Nomcol;
    @FXML
    public TableColumn<client, String> prenomcol;
    @FXML
    public TableColumn<client, Integer> Telcol;
    @FXML
    public TableColumn<client, String> sexecol;
    @FXML
    public TableColumn<client, String> datecol;
    @FXML
    public Label nbrRDV;
    @FXML
    public Label nbrPatient;
    @FXML
    public Label pourcentageH;
    @FXML
    public Label pourcentageF;
    @FXML
    Button Acceuil;
    @FXML
    Button patient;
    @FXML
    Button disconnect;
    Connect sec = new Connect();
    public int nbrPatient() throws SQLException{
        Connection conn = sec.connect();
        PreparedStatement p = conn.prepareStatement("SELECT  count(*)  AS nbpatient FROM client");
        ResultSet rs = p.executeQuery();
        return rs.getInt("nbpatient");
    }
    //number of men
    public int nbrhomme() throws SQLException{
        Connection conn = sec.connect();
        PreparedStatement p = conn.prepareStatement("SELECT  count(*)  AS nbrhomme FROM client WHERE Sexe='homme' ");
        ResultSet rs = p.executeQuery();
        return rs.getInt("nbrhomme");
    }
    //number of women
    public int nbrfemme() throws SQLException{
        Connection conn = sec.connect();
        PreparedStatement p = conn.prepareStatement("SELECT  count(*)  AS nbrfemme FROM client WHERE Sexe='femme' ");
        ResultSet rs = p.executeQuery();
        return rs.getInt("nbrfemme");
    }
    //number of RDV
    public int nbrRDV() throws SQLException{
        Connection conn = sec.connect();
        PreparedStatement p = conn.prepareStatement("SELECT  count(*)  AS nbrRDV FROM rendezVous");
        ResultSet rs = p.executeQuery();
        return rs.getInt("nbrRDV");
    }
    @FXML
   public void initialize(URL url, ResourceBundle rb) {
        int number=0;
        try{
            number=nbrPatient() ;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        nbrPatient.setText(Integer.toString(number));


        int numberhomme=0;
        try{
            numberhomme=nbrhomme() ;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        double r=  (double) (numberhomme * 100) /number;
        String rp = "" + r + "%" ;
        pourcentageH.setText(rp);
//************************************************************************************
        int numberfemme=0;
        try{
            numberfemme=nbrfemme() ;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        double rf= (double) (numberfemme * 100) /number;
        String rfp = "" + rf + "%" ;
        pourcentageF.setText(rfp);
        //****************************************************************************

        int numberRDV=0;
        try{
            numberRDV=nbrRDV() ;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        nbrRDV.setText(Integer.toString(numberRDV));
        CINcol.setCellValueFactory(new PropertyValueFactory<client, String>("CIN"));
        Nomcol.setCellValueFactory(new PropertyValueFactory<client, String>("Nom"));
        prenomcol.setCellValueFactory(new PropertyValueFactory<client, String>("Prenom"));
        Telcol.setCellValueFactory(new PropertyValueFactory<client, Integer>("Tel"));
        sexecol.setCellValueFactory(new PropertyValueFactory<client, String>("sexe"));
        datecol.setCellValueFactory(new PropertyValueFactory<client, String>("dateNaissance"));

        try {
            ObservableList<client> clients = FXCollections.observableArrayList();

            getPatients(clients);

            tableClient.setItems(clients);
        } catch (SQLException|InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void disconnect(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
    }

    public void deleteRendezVous(ActionEvent event) {
    }
    public void Patient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/nurse-A.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
    }

    public void Acceuil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/nurseAcceuil.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Nurse Area");
        stage.show();
    }
    public void getPatients(ObservableList<client> clients) throws SQLException,InvocationTargetException {
        Connection conn = sec.connect();
        PreparedStatement p = conn.prepareStatement("SELECT  * FROM client");
        ResultSet rs = p.executeQuery();

        // Process the results
        while (rs.next()) {
            String CIN = rs.getString("CIN");
            String nom = rs.getString("Nom");
            String prenom = rs.getString("Prenom");
            String tel = String.valueOf(rs.getInt("Tel"));
            String sexe = rs.getString("sexe");
            String dateNaissance = rs.getString("dateNaissance");
           // LocalDate  dateN = LocalDate.parse(dateNaissance);

            clients.add(new client(CIN, nom, prenom, tel, sexe, dateNaissance));
        }
    }

   /*private void ajouterClient(String clientCIN, String Nom, String Prenom, int Telephone, String sexe, String dateNaissance) {

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

        } catch (SQLException ee) {
            if (ee.getMessage().contains("UNIQUE constraint failed")) {
                System.err.println("Client with ID " + clientCIN + " already exists.");
            } else {
                System.out.println("Error: " + ee.getMessage());
            }
        }
    }

    public void addClient(ActionEvent event) throws InvocationTargetException, IOException {
        try {
            String Nom = nom.getText();
            String Prenom = prenom.getText();
            String cin = CIN.getText();
            String Sexe = sexe.getText();
            int tel = Integer.parseInt(Tel.getText());
            String DN = String.valueOf(datenaissance.getValue());

            ajouterClient(cin, Nom, Prenom, tel, Sexe, DN);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/nurse-A.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Nurse Area");
            stage.show();
        } catch (NumberFormatException e) {
            // Gérez l'exception si la conversion du numéro de téléphone échoue
            showAlert("il est obligatoire de remplir tous les champs.");

        }   // done


    }*/
    private void showAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Champs obligatoire");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
    public void rendezVous (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/gestionRendezVous.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Nurse Area");
        stage.show();
    }
    public void PatientAc(ActionEvent event) throws IOException {
       try {


           FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/nurse-A.fxml"));
           Parent root = loader.load();
           Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.setTitle("Nurse Area");
           stage.show();
       }
       catch (Exception e){
           e.printStackTrace();
           System.out.println("error loading");
       }
    }
}

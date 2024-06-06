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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class medecinController implements Initializable {

    @FXML
    public TableView<client> tableClient;
    @FXML
    public Button Acceuil;
    @FXML
    public TableColumn<client,String> CINcol;
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
    public Label nbrRDV;
    @FXML
    public Label pourcentageH;
    @FXML
    public Label pourcentageF;
    @FXML
    public Label nbrPatient;
    @FXML
    Button actMedical;
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
        double rf= (double) (numberfemme * 100) /number;;
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

    }


    private void getPatients(ObservableList<client> clients) throws SQLException {
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
            //LocalDate dateN = LocalDate.parse(dateNaissance);

            clients.add(new client(CIN, nom, prenom, tel, sexe, dateNaissance));
        }
    }

    public void getActMedical(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinActMedical.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Nurse Area");
        stage.show();

    }

    public void Patient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAjouterPatient.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Doctor Area");
        stage.show();
    }

    public void Disconnect(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Doctor Area");
        stage.show();
    }
    public void Acceuil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAcceuill.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Doctor Area");
        stage.show();
    }
}

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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class actMedicalController implements Initializable {
    @FXML
    public  DatePicker DD;
    @FXML
    public  DatePicker DF ;
    @FXML
    public Button modifier;
    @FXML
    public Button ajouter;
    @FXML
    public Button supprimer;
    @FXML
    public TextField montant;
    
    @FXML
    public TableView<actMedical> actMedicalTable;
    @FXML
    public Button Acceuil;
    @FXML
    public Button patient;
    @FXML
    public Button actMedical;
    @FXML
    public Button disconnect;
    @FXML
    public TableColumn<actMedical,String> colidPatient;
    @FXML
    public TableColumn<com.youcode.crudapp.actMedical, String> colDD;
    @FXML
    public TableColumn<com.youcode.crudapp.actMedical, String> colDF;
    @FXML
    public TableColumn<actMedical, String> colTraitement;
    @FXML
    public TextField idPatient;
    @FXML
    public TextField traitement;
    @FXML
    public TableColumn<actMedical,Double> colMontant;
    @FXML
    public Button Enregistrer;

    Connect sec= new Connect();

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //colidAct.setCellValueFactory(new PropertyValueFactory<actMedical,String>("idActMedical"));
        colidPatient.setCellValueFactory(new PropertyValueFactory<actMedical,String>("CIN"));
        colDD.setCellValueFactory(new PropertyValueFactory<actMedical,String>("DD"));
        colDF.setCellValueFactory(new PropertyValueFactory<actMedical,String>("DF"));
        colTraitement.setCellValueFactory(new PropertyValueFactory<actMedical,String>("traitement"));
        colMontant.setCellValueFactory((new PropertyValueFactory<actMedical,Double>("montant")));

        try {
            ObservableList<actMedical> actMedicaux = FXCollections.observableArrayList();
            getActMedical(actMedicaux);
            actMedicalTable.setItems(actMedicaux);
        } catch (SQLException e) {
           e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void ActMedical(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinActMedical.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    } // done

    public void ModifierAM(ActionEvent event)  {
        actMedical acteSelectionne = actMedicalTable.getSelectionModel().getSelectedItem();

        if (acteSelectionne != null) {
            remplireFormulaire(acteSelectionne);
        } else {
            showAlertE("Veuillez sélectionner un patient à modifier.");
        }
    } // done
    public void EnregistrerAM(ActionEvent event ) throws SQLException, IOException {
        try {
            String cin = idPatient.getText();
            String dd = String.valueOf(DD.getValue());
            String df =String.valueOf(DF.getValue());
            String T = traitement.getText();
            String M = montant.getText();
            LocalDate ddd =  DD.getValue();
            LocalDate dff =  DF.getValue();
            if (cin.isEmpty() | dd.isEmpty() | df.isEmpty() | T.isEmpty() | M.isEmpty()){
                showAlertE("remplir les champs à modifier !");
            }
            if (dff.isBefore(ddd)){
                showAlertE("la date n'est pas logique");
                return;
            }

            save(cin, dd, df, T, Double.parseDouble(M));
        }
        catch (NumberFormatException e) {

            // Gérez l'exception si la conversion du numéro de téléphone échoue
            showAlertE("il est obligatoire de remplir tous les champs.");
            return;

        }
        catch (NullPointerException e){
            e.printStackTrace();
            showAlertE("c'est obligatoire de remplir les cases des dates");
            return;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinActMedical.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
        showAlertS("Acte Medical est Modifiée");


    }

    private void save(String cin, String dd, String df, String t, double v) throws SQLException {
        actMedical acteSelectionne = actMedicalTable.getSelectionModel().getSelectedItem();

        if (acteSelectionne != null) {
            Connection conn = sec.connect();
            remplireFormulaire(acteSelectionne);
            PreparedStatement p = conn.prepareStatement(
                    "UPDATE actMedical SET " +
                            "CIN = ?, " +
                            "DD = ?, " +
                            "DF = ?, " +
                            "traitement = ?, " +
                            "montant = ? " +
                            "WHERE idActMedical = ?"
            );

            p.setString(1, cin);
            p.setString(2, dd);
            p.setString(3, df);
            p.setString(4, t);
            p.setDouble(5, v);
            p.setInt(6, acteSelectionne.getIdActMedical());

            p.executeUpdate();

        } else {
            showAlertE("Veuillez sélectionner un patient à modifier.");
       }
//done
    }

    private void remplireFormulaire(actMedical act) {
   //done
 idPatient.setText(act.getCIN());
    DD.setPromptText(String.valueOf(LocalDate.parse(act.getDD())));
    DF.setPromptText(String.valueOf(LocalDate.parse(act.getDF())));
    traitement.setText(act.getTraitement());
    montant.setText(String.valueOf(act.getMontant()));
} //  works with modifierA
    public void Patient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/resources/fxml/medecinAjouterPatient.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
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

    public void Acceuil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAcceuill.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    } // done
  //  @Override
    private void getActMedical(ObservableList<actMedical> actMedicaux) throws SQLException, IOException {
       Connection conn = sec.connect();
       try{
       /* PreparedStatement p1 = conn.prepareStatement("SELECT  client.CIN FROM client INNER JOIN actMedical ON client.CIN = actMedical.CIN");
        ResultSet rs1 = p1.executeQuery();
        while (rs1.next()){
            String cin = rs1.getString("CIN");

        */
           PreparedStatement p2 = conn.prepareStatement("SELECT  * FROM actMedical");
           ResultSet rs2 = p2.executeQuery();
        while (rs2.next()){
            int id = rs2.getInt("idActMedical");
            String cin = rs2.getString("CIN");
            // le probleme réside ici !!!!!!!!!!!!!!!!
             String DateDebut = rs2.getString("DD");
            String DateFin = rs2.getString("DF");
            String traitement = rs2.getString("traitement");
            String montant = String.valueOf(rs2.getDouble("montant"));

            actMedicaux.add(new actMedical(id,cin,DateDebut,DateFin,traitement,montant));
        }


    }
       catch (NumberFormatException e) {
           e.printStackTrace();
           //showAlertE("Il est obligatoire de remplir tous les champs correctement.");
       }
    } //A REVOIR ET MODIFIER

    private void ajouterAM(String cin, String dd, String  df , String Trait, double montant) throws SQLException{
        try {
            Connection conn = sec.connect();
            PreparedStatement p = conn.prepareStatement("INSERT INTO  actMedical(CIN,DD,DF,traitement,montant) values(?,?,?,?,?) ");
            p.setString(1, cin);
            p.setString(2, dd);
            p.setString(3, df);
            p.setString(4, Trait);
            p.setDouble(5, montant);

            int rowsAffected = p.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client with ID " + cin + " added successfully.");
                showAlertS("l'acte médicale a été ajouté avec succès ! ");

            } else {
                System.out.println("Failed to add client with ID " + cin + ".");
                showAlertE("Failed to add acte médical ");
            }
            conn.close(); // Close connection
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
   } // done
    private Boolean PatientExist(String cin) throws SQLException {
        Connection conn = sec.connect();
        PreparedStatement p= conn.prepareStatement("SELECT * FROM client WHERE CIN=?");
        p.setString(1,cin);
        ResultSet r = p.executeQuery();
        conn.close();
        return r.next();

    }
    public void addAM(ActionEvent event) throws IOException, SQLException, InvocationTargetException {// exception
    try {
       /* String cin = idPatient.getText();
        String ddd = DD.getPromptText();
        String dff =DF.getPromptText();
        String T = String.valueOf(traitement.getText());
        String M = montant.getText();
        LocalDate dd = LocalDate.parse(ddd);
        LocalDate df = LocalDate.parse(dff);*/
        String cin = idPatient.getText();
        if(!PatientExist(cin)){
            showAlertE("le client est inexistant");
        }else{
            LocalDate dd =  DD.getValue();
            LocalDate df =  DF.getValue();
            if (df.isBefore(dd)){
                showAlertE("la date n'est pas logique");
                return;
            }
        String ddText = String.valueOf(dd);
        String dfText = String.valueOf(df);
        //LocalDate dd = LocalDate.parse(ddText, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
       // LocalDate df = LocalDate.parse(dfText, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String T = traitement.getText();

        double M = Double.parseDouble(montant.getText());
        ajouterAM(cin, ddText,dfText , T, M);
    }}
    catch (NumberFormatException e) {
        e.printStackTrace();
        showAlertE("montant invalid");
        // Gérez l'exception si la conversion du numéro de téléphone échoue
        //showAlertE("il est obligatoire de remplir tous les champs.");

    }
    catch (InputMismatchException ee){
        ee.printStackTrace();
        showAlertE("montant invalid");
    }
    catch (NullPointerException eee){
        eee.printStackTrace();
        showAlertE("champs obligatoire");
    }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinActMedical.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    } // done

   private void showAlertS(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    } // done
    private void showAlertE(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    } // done

    public void deleteAM(ActionEvent event) throws SQLException, IOException {
        actMedical acteSelectionne = actMedicalTable.getSelectionModel().getSelectedItem();
        //remplireFormulaire(acteSelectionne);

        int id = acteSelectionne.getIdActMedical();
        Connection conn = sec.connect();
        PreparedStatement p= conn.prepareStatement("DELETE  FROM actMedical where idActMedical=?");
        p.setInt(1,id);
        int R = p.executeUpdate();
         if (R > 0) {
            showAlertS("Rendezvous with ID =" + id + " deleted successfully.");
        } else {
            showAlertE("Rendezvous with ID =" + id + " not found.");
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinActMedical.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();

        conn.close();
    } // done

    public void Acceuil2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAcceuill.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    }

    public void PatientA(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/medecinAjouterPatient.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Doctor Area");
        stage.show();
    }
}

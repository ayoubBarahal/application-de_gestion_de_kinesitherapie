package com.youcode.crudapp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.*;
//import java.text.ParseException;
import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class addRendezVousController implements Initializable {
    @FXML
    public Button disconnect;
    @FXML
    public Button Acceuil;
    @FXML
    public Button addRV;
    @FXML
    public TextField idRDV1;
    @FXML
    public Button ajouterRDV;
    @FXML
    public TableView<rendezVous> tableRDV;

    @FXML
    public TableColumn<rendezVous, String> cinCol;
    @FXML
    public TableColumn<rendezVous, String> dateRDVcol;
    @FXML
    public TableColumn<rendezVous, String> hourcol;
    @FXML
    public ImageView iconRDV;
    @FXML
    public Button btnUpDate;
    @FXML
    public Button supprimerRDV;
    @FXML
    public TextField clientCIN;
    @FXML
    public Button patient;
    @FXML
    public Button patientR;
    @FXML
    public DatePicker dateRDV;
    @FXML
    public TextField heure;

    Connect sec = new Connect();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //idcol.setCellValueFactory(new PropertyValueFactory<rendezVous,Integer>("id_RDV"));
        cinCol.setCellValueFactory(new PropertyValueFactory<rendezVous,String>("CIN"));
        dateRDVcol.setCellValueFactory(new PropertyValueFactory<rendezVous,String>("dateRV"));
        hourcol.setCellValueFactory(new PropertyValueFactory<rendezVous,String>("hour"));
        try {
            ObservableList<rendezVous> RendezVousList = FXCollections.observableArrayList();
            getRDV(RendezVousList);
            tableRDV.setItems(RendezVousList);
        }

        catch (SQLException e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }

    }

    private void getRDV(ObservableList<rendezVous> RDV) throws SQLException{

        Connection conn = sec.connect();
        PreparedStatement p = conn.prepareStatement("SELECT * FROM rendezVous");
        ResultSet rs = p.executeQuery();

        while (rs.next()) {
            int id= rs.getInt("id_RDV");;
            String cin = rs.getString("CIN");
            String dateStr = rs.getString("date");
            String heure = rs.getString("heure");

            RDV.add(new rendezVous(id,cin, dateStr, heure));

        }
        conn.close();
    }

    public void Patient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/nurse-A.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }// done

    private void ajouterRendezVous(String CIN, String date, String hour) {
        try {

            Connection conn = sec.connect();
            PreparedStatement p = conn.prepareStatement("INSERT INTO rendezVous(CIN,date,heure) values(?,?,?);");

            p.setString(1, CIN);
            p.setString(2, date);
            p.setString(3, hour);

            int rowsAffected=p.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("RDV with ID " + CIN + " added successfully.");
                showAlertS("RDV a été ajouté avec succès ! ");

            } else {
                System.out.println("Failed to add RDV with ID " + CIN + ".");
                showAlertE("Failed to add RDV  ");
            }
            conn.close();
        }   // done

        catch (SQLException ee) {
            System.out.print(ee.getMessage());
        }
    } // done utilisé dans addRendezVous
    private Boolean PatientExist(String cin) throws SQLException {
        Connection conn = sec.connect();
        PreparedStatement p= conn.prepareStatement("SELECT * FROM client WHERE CIN=?");
        p.setString(1,cin);
        ResultSet r = p.executeQuery();
        conn.close();
        return r.next();


    }
    public void addRendezVous(ActionEvent event) throws IOException {
        try {
            String cin = clientCIN.getText();
           if(!PatientExist(cin)){
                showAlertE("le client est inexistant");
            }else{
            String hour = heure.getText();
            LocalDate d = dateRDV.getValue();
            String date = String.valueOf(d);
            if (cin.isEmpty() || hour.isEmpty() || date.isEmpty()) {
                showAlertE("Il est obligatoire de remplir tous les champs.");
            } else {
                ajouterRendezVous(cin, date, hour);
            }}
        } catch (Exception e) {
            showAlertE("une erreur s'est produite.");
        }

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/gestionRendezVous.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Nurse Area");
        stage.show();

    }// done

    public void disconnect(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/Fxml/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);

        stage.show();
    } // done

    private void deleteRDV(int id_RDV) {
        try {
            Connection conn = sec.connect();
            PreparedStatement p = conn.prepareStatement("DELETE FROM rendezVous WHERE id_RDV = ?");
            p.setInt(1, id_RDV);


            int rowsAffected = p.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Rendezvous with ID =" + id_RDV + " deleted successfully.");
            } else {
                System.out.println("Rendezvous with ID =" + id_RDV + " not found.");
            }

            conn.close(); // Close connection
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());
        }
    } // done

    public void deleteRendezVous(ActionEvent event) throws IOException {
        rendezVous RDVSelectionne = tableRDV.getSelectionModel().getSelectedItem();
        //remplireFormulaire(RDVSelectionne);
        if(RDVSelectionne==null){
            showAlertE("veuillez séléctionner un RDV");
        }
        try {
            int id = RDVSelectionne.getId_RDV();
            deleteRDV(id);
            showAlertS("le rendez vous est supprimé avec succès");
        } catch (Exception e) {
           e.printStackTrace();
        }finally {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/gestionRendezVous.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Nurse Area");
        stage.show();
        }
    }  // done

    public void Acceuil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/nurseAcceuil.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Nurse Area");
        stage.show();
    }// done

    public void update(ActionEvent event) {
        rendezVous RDVSelectionne = tableRDV.getSelectionModel().getSelectedItem();

        if (RDVSelectionne != null) {
            remplireFormulaire(RDVSelectionne);
            int a = RDVSelectionne.getId_RDV();
            deleteRDV(a);
        } else {
            showAlertE("Veuillez sélectionner un rendez-Vous à modifier.");
        }
    }

    private void updateRendezVous(int id, String date, String hour) throws InvocationTargetException {
        try {

            Connection conn = sec.connect();

            // Prepare the SQL statement to update the appointment
            String sql = "UPDATE rendezVous SET date = ?, heure = ? WHERE id_RDV = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Set the parameters for the prepared statement
            pstmt.setString(1, date);
            pstmt.setString(2, hour);
            pstmt.setInt(3, id);

            // Execute the update
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Appointment with ID = " + id + " updated successfully.");
            } else {
                System.out.println("Appointment with ID = " + id + " not found.");
            }

            conn.close(); // Close connection
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());
        }
    }  // not used !!!!

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

    public void RendezVous1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/gestionRendezVous.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Nurse Area");
        stage.show();
    }

    public void PatientR(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/nurse-A.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Nurse Area");
        stage.show();
    }

    private void remplireFormulaire(rendezVous cl) {
        clientCIN.setText(cl.getCIN());
        dateRDV.setPromptText(cl.getDateRV());
        heure.setText(cl.getHour());
    }
}




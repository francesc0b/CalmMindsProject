package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import main.Model.Client;
import main.Model.Contract;
import main.Model.Counselor;
import main.Resources.DBConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by blakejoynes on 12/5/16.
 */



public class assignmentController implements Initializable {


    @FXML
    private DatePicker contractStartPicker;
    @FXML private DatePicker contractEndPicker;
    @FXML private ComboBox counselorCBox;
    private Connection connection;
    public int clientSSN;
    public Contract contract;
    public Counselor counselor;
    ClientGuiController clientController;


    /**
     * Initializes the assignment information for a client based on the Client's SSN. Still needs work.
     *
     * @param location ignored
     * @param resources ignored
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = DBConnect.getConnection();
      contractEndPicker.setValue(null);
      contractStartPicker.setValue(null);
   try {
       FXMLLoader loader = new FXMLLoader();
       Parent root = loader.load(getClass().getResource("../View/clientConfig.fxml"));
       clientController = loader.getController();
       //clientController.getClientSSN(clientSSN);

   }catch (IOException e){

   }
        try{
        Counselor newCounselor = new Counselor();
        ArrayList<String> specializations = new ArrayList<>();
        ObservableList<String> counselors = FXCollections.observableArrayList();

        String query = "SELECT p.fname,p.lname,specialization FROM Person p,counsel_specialization cs,counselor c WHERE p.SSN = c.SSN AND cs.c_id = c.c_id AND p.SSN = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, String.valueOf(clientSSN));


        ResultSet rs = ps.executeQuery();

        do{
            newCounselor.setFirstName(rs.getString("fname"));
            newCounselor.setLastName(rs.getString("lname"));
            newCounselor.setSpecialization(rs.getString("specialization"));
            specializations.add(newCounselor.getSpecialization());
            counselors.add(newCounselor.getFirstName() + " " + newCounselor.getLastName() + " " + specializations.toString());

        }while(rs.next());

        counselorCBox.setItems(counselors);



    }catch(SQLException e){
        e.printStackTrace();
    }


}



    public void assignCounselor(){
System.out.println("Hello");
    }




}

package main.Controller;/**
 * Created by Kevin on 11/27/2016.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Model.Client;
import main.Resources.DBConnect;
import java.sql.Connection;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientGuiController implements Initializable{

    private static final int ssnLength = 9;
    public TextField historyDate;
    public TextField clientAddress;
    public TextField clientHousePhone;
    public TextField clientCellPhone;
    public TextField clientSsn;
    public TextArea queryResults;
    public Button searchButton;
    public TextField clientFirstName;
    public TextField clientMidInit;
    public TextField historyTreatment;
    public TextField clientLastName;
    public ChoiceBox clientSex;
    public TextField therapistId;
    public TextField therapyType;
    public TextField policyNumber;
    public TextField sessionType;
    public Label message;
    private Connection connection;
    private Client client;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
     connection = DBConnect.getConnection();
     client = new Client();
     clientSex.setItems(FXCollections.observableArrayList("Select Sex", new Separator(), "Male", "Female")

     );
     clientSex.getSelectionModel().selectFirst();

    }




    public void removeClient(ActionEvent actionEvent) {
        // We will only allow them to remove a full record by ssn


    }

   @FXML public void searchBySSN(ActionEvent actionEvent) {
        String clientSSN = clientSsn.getText();

        try {
            String query = "SELECT * FROM Person WHERE type = 'client' AND SSN = ? ";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, clientSSN);

            ResultSet rs = ps.executeQuery();
            while ( rs.next() )
            {
                client.setSSN( rs.getInt("SSN") );
                client.setFname( rs.getString("fName") );
                client.setmName(rs.getString("mInit"));
                client.setlName(rs.getString("lName"));
                client.setSex(rs.getString("sex"));
                client.setAddress(rs.getString("address"));
                client.setHousePhoneNum(rs.getString("housePhoneNum"));
                client.setCellPhoneNum(rs.getString("cellPhoneNum"));

                message.setText("Record Retrieved!");
            }
            updateFields(actionEvent);
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            message.setText(e.toString());
        }
    }

    @FXML public void updateFields(ActionEvent actionEvent){
      clientFirstName.setText(client.getfName());
      clientMidInit.setText(client.getmName());
      clientLastName.setText(client.getlName());
      clientHousePhone.setText(client.getHousePhoneNum());
      clientCellPhone.setText(client.getCellPhoneNum());
      clientAddress.setText(client.getAddress());

      if(client.getSex().equals("F")) {
          clientSex.setValue("Female");
      }else{
          clientSex.setValue("Male");
      }


      queryResults.setText(client.getfName() + " " + client.getmName() + " " + client.getlName());


    }




    public void submitQuery(ActionEvent actionEvent) {
        // same as the counselor buttons
    }



}
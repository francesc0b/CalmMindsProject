package main.Controller;/**
 * Created by Kevin on 11/27/2016.
 */

import com.sun.prism.paint.Paint;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Model.Client;
import main.Model.Contract;
import main.Model.Counselor;
import main.Resources.DBConnect;

import java.awt.*;
import java.sql.*;


import java.net.URL;
import java.util.Optional;
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
    public TextField insCompanyName;
    public TextField insPolicyNumber;
    public TextField sessionType;
    public Label message;
    private Connection connection;
    private Client client;
    private Contract contract;
    private Counselor counselor;
    public Label counselorIDLbl;
    public Label counselorNameLbl;
    public Label contractStartLbl;
    public Label contractEndLbl;
    public Button saveBtn;
    public Button updateBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
     connection = DBConnect.getConnection();
     client = new Client();
     contract = new Contract();
     counselor = new Counselor();
     clientSex.setItems(FXCollections.observableArrayList("Select Sex", new Separator(), "Male", "Female")

     );
     clientSex.getSelectionModel().selectFirst();

    }


//Adds new client to database
    @FXML public void addClient(ActionEvent actionEvent){
        try {

            String queryCheck = "SELECT * FROM Person WHERE type = 'client' AND SSN = ? ";
            PreparedStatement ps = connection.prepareStatement(queryCheck);
            ps.setString(1, clientSsn.getText());


            ResultSet rs = ps.executeQuery();

           if(rs.next()) {
           message.setText("This record already exists!!");
           ps.close();
           }else{

               giveClientInfo();
               String query = "INSERT INTO Person (SSN,fName,mInit,lName,housePhoneNum,cellPhoneNum,address,sex,type) VALUES (?,?,?,?,?,?,?,?,?)";
               ps = connection.prepareStatement(query);
               ps.setInt(1, client.getSSN());
               ps.setString(2, client.getfName());
               ps.setString(3, client.getmName());
               ps.setString(4, client.getlName());
               ps.setString(5, client.getHousePhoneNum());
               ps.setString(6, client.getCellPhoneNum());
               ps.setString(7, client.getAddress());
               ps.setString(8, client.getSex());


               ps.setString(9, "client");


               ps.executeUpdate();

               populateInsurance();

               populateTextBox();
               ps.close();
           }



            }catch(SQLException e){
                e.printStackTrace();
            }
        }


        @FXML public void updateClient(ActionEvent actionEvent){
            giveClientInfo();
            try {
                String query = "UPDATE Person set fName=?, mInit=?, lName=?,housePhoneNum=?,cellPhoneNum=?,address=?,sex=? WHERE SSN ='" + client.getSSN() + "'";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, client.getfName());
                ps.setString(2, client.getmName());
                ps.setString(3, client.getlName());
                ps.setString(4, client.getHousePhoneNum());
                ps.setString(5, client.getCellPhoneNum());
                ps.setString(6, client.getAddress());
                ps.setString(7, client.getSex());

                ps.execute();

                if(!insCompanyName.getText().equals("") && !insPolicyNumber.getText().equals("") ) {
                    String query2 = "UPDATE client_insurance SET name = ?,policyNum=? WHERE SSN ='" + client.getSSN() + "'";
                    ps = connection.prepareStatement(query2);
                    ps.setString(1, client.getInsName());
                    ps.setString(2, String.valueOf(client.getInsPolicyNum()));
                    ps.executeUpdate();

                }else{
                    populateInsurance();
                }
                message.setTextFill(Color.BLACK);
                message.setText("Record Updated!");

                populateTextBox();



                ps.close();
            }catch(SQLException e){
                e.printStackTrace();
            }

        }


    public void removeClient(ActionEvent actionEvent) {
        // We will only allow them to remove a full record by ssn

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this record for " + client.getfName() + " " + client.getlName() + "?",ButtonType.NO,ButtonType.YES);
        alert.setTitle("Delete Record");
        alert.setHeaderText("Record Deletion");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.YES) {

            try {
                String query = "DELETE FROM Person WHERE SSN = ? ";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1,client.getSSN());

                ps.executeUpdate();
                ps.close();
                clearData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{

            alert.close();
        }

    }


   @FXML public void searchBySSN(ActionEvent actionEvent) {
       String clientSSN = clientSsn.getText();

       if (!clientSSN.equals("")) {

           try {
               String query = "SELECT * FROM Person WHERE type = 'client' AND SSN = ? ";
               PreparedStatement ps = connection.prepareStatement(query);
               ps.setString(1, clientSSN);


               ResultSet rs = ps.executeQuery();
               if (rs != null && rs.next()) {

                   do{
                       client.setSSN(rs.getInt("SSN"));
                       client.setFname(rs.getString("fName"));
                       client.setmName(rs.getString("mInit"));
                       client.setlName(rs.getString("lName"));
                       client.setSex(rs.getString("sex"));
                       client.setAddress(rs.getString("address"));
                       client.setHousePhoneNum(rs.getString("housePhoneNum"));
                       client.setCellPhoneNum(rs.getString("cellPhoneNum"));

                       message.setTextFill(Color.BLACK);
                       message.setText("Record Retrieved!");
                   }while (rs.next());

                   populateInsurance();
                   updateAssignmentInfo();
                   updateFields(actionEvent);
                   ps.close();
               }else{
                   message.setTextFill(Color.RED);

                   message.setText("Not a valid SSN!");
               }

               } catch(SQLException e){
                   e.printStackTrace();
                   message.setText(e.toString());
               }


       }else{
           message.setTextFill(Color.RED);
           message.setText("Please enter an SSN!");

       }
   }



    @FXML public void updateFields(ActionEvent actionEvent){
      clientFirstName.setText(client.getfName());
      clientMidInit.setText(client.getmName());
      clientLastName.setText(client.getlName());
      clientHousePhone.setText(client.getHousePhoneNum());
      clientCellPhone.setText(client.getCellPhoneNum());
      clientAddress.setText(client.getAddress());
      insCompanyName.setText(client.getInsName());
      insPolicyNumber.setText(String.valueOf(client.getInsPolicyNum()));
      counselorIDLbl.setText(String.valueOf(counselor.getCID()));
      counselorNameLbl.setText(counselor.getfName() + " " + counselor.getmName() + " " + counselor.getlName());
      contractStartLbl.setText(String.valueOf(contract.getDateStarted()));
      contractEndLbl.setText(String.valueOf(contract.getDateTerminated()));

        if(client.getSex().equals("F")) {
          clientSex.setValue("Female");
      }else{
          clientSex.setValue("Male");
      }

      populateTextBox();
    }

    @FXML public void populateTextBox(){
        queryResults.appendText("Name: " + client.getfName() + " " + client.getmName() + " " + client.getlName() + "\n");
        queryResults.appendText("House Phone #: " + client.getHousePhoneNum() + "\n" + "Cell Phone #: " + client.getCellPhoneNum() + "\n"  );
        queryResults.appendText("Address: " + client.getAddress());
        queryResults.appendText("\n \n");
        queryResults.appendText("Ins. Policy Name & Number: " + client.getInsName() + " " + client.getInsPolicyNum());


    }


    public void giveClientInfo(){
        client.setSSN(Integer.parseInt(clientSsn.getText()));
        client.setFname(clientFirstName.getText());
        client.setmName(clientMidInit.getText());
        client.setlName(clientLastName.getText());
        client.setAddress(clientAddress.getText());
        client.setHousePhoneNum(clientHousePhone.getText());
        client.setCellPhoneNum(clientCellPhone.getText());

        if(clientSex.getSelectionModel().getSelectedItem().toString() == "Female")
        client.setSex("F");
        else if(clientSex.getSelectionModel().getSelectedItem().toString() == "Male"){
            client.setSex("M");
        }else{
            message.setText("Not a valid choice!");
        }
        client.setInsName(insCompanyName.getText());
        client.setInsPolicyNum(Integer.parseInt(insPolicyNumber.getText()));
    }

    @FXML public void populateInsurance(){
        try{
            String query = "SELECT * FROM client_insurance WHERE SSN = ? ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(client.getSSN()));



            ResultSet rs = ps.executeQuery();
            if(!rs.next()){

                String query2 = "INSERT INTO client_insurance (SSN,name,policyNum) VALUES (?,?,?)";
                ps = connection.prepareStatement(query2);
                ps.setString(1, String.valueOf(client.getSSN()));
                ps.setString(2, client.getInsName());
                ps.setString(3,String.valueOf(client.getInsPolicyNum()));
                ps.executeUpdate();


            }else {
                while (rs.next()) {
                    client.setInsName(rs.getString("name"));
                    client.setInsPolicyNum(rs.getInt("policyNum"));
                }
            }

            }catch(SQLException e){
            e.printStackTrace();
        }


    }



    @FXML public void updateAssignmentInfo(){
        try{
            String query = "SELECT ca.dateStarted,ca.counID,p.fName,p.mInit,p.lName,ct.dateTerminated FROM contract_assignment ca,counselor c,contract ct, Person p WHERE ca.contractID = ct.contractID AND ca.counID = c.c_id AND c.SSN = p.SSN AND p.type = 'counselor' AND ca.clientSSN = ? ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(client.getSSN()));



            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contract.setDateStarted(rs.getDate("dateStarted"));
                counselor.setCID(rs.getInt("counID"));
                counselor.setFname(rs.getString("fName"));
                counselor.setmName(rs.getString("mInit"));
                counselor.setlName(rs.getString("lName"));
                contract.setDateTerminated(rs.getDate("dateTerminated"));

            }


        }catch(SQLException e){
            e.printStackTrace();
        }


    }




public void clearData(){

    if(!clientSsn.getText().equals("")) {
        clientSsn.setText("");
        clientFirstName.setText("");
        clientMidInit.setText("");
        clientLastName.setText("");
        clientHousePhone.setText("");
        clientCellPhone.setText("");
        clientAddress.setText("");
        insCompanyName.setText("");
        insPolicyNumber.setText("");
        counselorIDLbl.setText("");
        counselorNameLbl.setText("");
        contractStartLbl.setText("");
        contractEndLbl.setText("");
        clientSex.getSelectionModel().selectFirst();
        queryResults.setText("");

        message.setTextFill(Color.BLACK);

        message.setText("The data has been cleared!");
    }else{
        message.setTextFill(Color.BLACK);

        message.setText("There is no data to be cleared!");

    }
}




}
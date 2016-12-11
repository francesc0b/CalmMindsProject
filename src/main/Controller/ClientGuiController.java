package main.Controller;/**
 * Created by Kevin on 11/27/2016.
 */

import com.sun.prism.paint.Paint;
//import com.sun.tools.javac.util.Name;
import javafx.application.Application;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Model.*;
import main.Resources.DBConnect;

import java.awt.*;
import java.io.IOException;
import java.sql.*;


import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientGuiController implements Initializable{

    private static final int ssnLength = 9;
    public TextField historyDate;
    @FXML private TextField clientAddress;
    @FXML private TextField clientHousePhone;
    @FXML private TextField clientCellPhone;
    @FXML private TextField clientSsn;
    @FXML private TextArea queryResults;
    @FXML private TextField clientFirstName;
    @FXML private TextField clientMidInit;
    @FXML private TextField historyTreatment;
    @FXML private TextField clientLastName;
    @FXML private ChoiceBox clientSex;
    @FXML private TextField insCompanyName;
    @FXML private TextField insPolicyNumber;
    @FXML private Label message;

    @FXML private DatePicker dateOfBirthPicker;

    private Connection connection;
    public Client client;
    public Contract contract;
    public Counselor counselor;
    public ClientHistory history;
    public Schedule schedule;

    @FXML private Label counselorIDLbl;
    @FXML private Label counselorNameLbl;
    @FXML private Label contractStartLbl;
    @FXML private Label contractEndLbl;
    @FXML private Button saveBtn;
    @FXML private Button updateBtn;
    @FXML private TableColumn<ClientHistory,Date> dateDiagnosedCol;
    @FXML private TableColumn<ClientHistory,String> historyDetailsCol;
    @FXML private TableColumn<ClientHistory,String> previousTreatmentCol;
    @FXML private TableColumn<ClientHistory,String> treatmentTypeCol;
    @FXML private TableColumn<Schedule,String> availabilityCol;
    @FXML private TableColumn<Schedule,String> counselNameCol;
    @FXML private TableColumn<Schedule,String> sessionDateCol;
    @FXML private TableColumn<Schedule,String> sessionTypeCol;
    @FXML private TableColumn<Schedule,String> violationCol;
    @FXML private TableColumn<Schedule,String> therapyTypeCol;
    @FXML private TableView<ClientHistory> clientHistoryTable;
    @FXML private TableView<Schedule> scheduleTable;
    private ObservableList<ClientHistory> historydata;
    private ObservableList<Schedule> scheddata;


    /**
     *Intializes the connection, classes and table columns
     *
     * @param location ignored
     * @param resources ignored
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     connection = DBConnect.getConnection();
     client = new Client();
     contract = new Contract();
     counselor = new Counselor();
     history = new ClientHistory();

        clientSex.setItems(FXCollections.observableArrayList("Select Sex", new Separator(), "Male", "Female")

     );
     clientSex.getSelectionModel().selectFirst();

        dateDiagnosedCol.setCellValueFactory(new PropertyValueFactory<>("dateDiagnosed"));
        historyDetailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));
        previousTreatmentCol.setCellValueFactory(new PropertyValueFactory<>("previousTreatment"));
        treatmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("treatmentType"));


        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        counselNameCol.setCellValueFactory(new PropertyValueFactory<>("leadCounselorName"));
        sessionDateCol.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        sessionTypeCol.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        therapyTypeCol.setCellValueFactory(new PropertyValueFactory<>("therapyType"));
        violationCol.setCellValueFactory(new PropertyValueFactory<>("violation"));

    }


    /**
     * Adds new client to database
     *
     * @param event ignored
     */

    @FXML public void addClient(ActionEvent event){
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
               String query = "INSERT INTO Person (SSN,fName,mInit,lName,housePhoneNum,cellPhoneNum,address,sex,type,dateOfBirth) VALUES (?,?,?,?,?,?,?,?,?,?)";
               ps = connection.prepareStatement(query);
               ps.setInt(1, client.getSSN());
               ps.setString(2, client.getFirstName());
               ps.setString(3, client.getMiddleInit());
               ps.setString(4, client.getLastName());
               ps.setString(5, client.getHousePhoneNum());
               ps.setString(6, client.getCellPhoneNum());
               ps.setString(7, client.getAddress());
               ps.setString(8, client.getSex());
               ps.setString(9, "client");
               ps.setString(10,client.getDateOfBirth().toString());


               ps.executeUpdate();

               populateInsurance();

               populateTextBox();
               message.setText("Record added successfully!");
               ps.close();
              // counselorAssignmentAlert(event);
           }



            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    /**
     * Updates the client if any text fields are changed
     *
     * @param actionEvent
     */
        @FXML public void updateClient(ActionEvent actionEvent){
            giveClientInfo();
            try {
                String query = "UPDATE Person set fName=?, mInit=?, lName=?,housePhoneNum=?,cellPhoneNum=?,address=?,sex=?,dateOfBirth=? WHERE SSN ='" + client.getSSN() + "'";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, client.getFirstName());
                ps.setString(2, client.getMiddleInit());
                ps.setString(3, client.getLastName());
                ps.setString(4, client.getHousePhoneNum());
                ps.setString(5, client.getCellPhoneNum());
                ps.setString(6, client.getAddress());
                ps.setString(7, client.getSex());
                ps.setString(8,client.getDateOfBirth().toString());
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

    /**
     * Removes client from database
     *
     * @param actionEvent
     */

    public void removeClient(ActionEvent actionEvent) {
        // We will only allow them to remove a full record by ssn

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this record for " + client.getFirstName() + " " + client.getLastName() + "?",ButtonType.NO,ButtonType.YES);
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

    /**
     * Search for client by SSN
     *
     * @param actionEvent Update the fields after the search is complete
     */

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
                       client.setFirstName(rs.getString("fName"));
                       client.setMiddleInit(rs.getString("mInit"));
                       client.setLastName(rs.getString("lName"));
                       client.setSex(rs.getString("sex"));
                       client.setAddress(rs.getString("address"));
                       client.setHousePhoneNum(rs.getString("housePhoneNum"));
                       client.setCellPhoneNum(rs.getString("cellPhoneNum"));
                       client.setDateOfBirth(rs.getDate("dateOfBirth"));

                       message.setTextFill(Color.BLACK);
                       message.setText("Record Retrieved!");
                   }while (rs.next());

                   populateInsurance();
                   updateAssignmentInfo();
                   populateClientHistory();
                   populateClientSchedule();
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

    /**
     * Updates fields on the form
     *
     * @param actionEvent ignored
     */

    @FXML public void updateFields(ActionEvent actionEvent){
      clientFirstName.setText(client.getFirstName());
      clientMidInit.setText(client.getMiddleInit());
      clientLastName.setText(client.getLastName());
      clientHousePhone.setText(client.getHousePhoneNum());
      clientCellPhone.setText(client.getCellPhoneNum());
      clientAddress.setText(client.getAddress());
      insCompanyName.setText(client.getInsName());
      insPolicyNumber.setText(String.valueOf(client.getInsPolicyNum()));
      counselorIDLbl.setText(String.valueOf(counselor.getCounselorID()));
      counselorNameLbl.setText(counselor.getFirstName() + " " + counselor.getMiddleInit() + " " + counselor.getLastName());
      contractStartLbl.setText(String.valueOf(contract.getDateStarted()));
      contractEndLbl.setText(String.valueOf(contract.getDateTerminated()));
      dateOfBirthPicker.setValue(LocalDate.parse(client.getDateOfBirth().toString()));

        if(client.getSex().equals("F")) {
          clientSex.setValue("Female");
      }else{
          clientSex.setValue("Male");
      }

      populateTextBox();
    }


    /**
     * Populates the textbox
     */

    @FXML public void populateTextBox() {

        if (queryResults.getText().isEmpty()) {
            queryResults.appendText("Name: " + client.getFirstName() + " " + client.getMiddleInit() + " " + client.getLastName() + "\n");
            queryResults.appendText("House Phone #: " + client.getHousePhoneNum() + "\n" + "Cell Phone #: " + client.getCellPhoneNum() + "\n");
            queryResults.appendText("Address: " + client.getAddress() + "\n");
            queryResults.appendText("Date of Birth: " + client.getDateOfBirth());
            queryResults.appendText("\n \n");
            queryResults.appendText("Ins. Policy Name: " + client.getInsName() + "\nPolicy Number: " + client.getInsPolicyNum());
        } else {
            queryResults.setText("");
            populateTextBox();
        }
    }


    /**
     * If a new client is being established, it stores all the info into an instance of the Client class
     */

    public void giveClientInfo(){
        client.setSSN(Integer.parseInt(clientSsn.getText()));
        client.setFirstName(clientFirstName.getText());
        client.setMiddleInit(clientMidInit.getText());
        client.setLastName(clientLastName.getText());
        client.setAddress(clientAddress.getText());
        client.setHousePhoneNum(clientHousePhone.getText());
        client.setCellPhoneNum(clientCellPhone.getText());
        client.setDateOfBirth(Date.valueOf(dateOfBirthPicker.getValue()));
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

    /**
     * Sets the insurance information fields based on the Client SSN
     */

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
                do {
                    client.setInsName(rs.getString("name"));
                    client.setInsPolicyNum(rs.getInt("policyNum"));
                }while (rs.next());
            }

            }catch(SQLException e){
            e.printStackTrace();
        }


    }

    /**
     * Sets the Assignment information for a client based on the contract and counselor information
     */


    @FXML public void updateAssignmentInfo(){
        try{
            String query = "SELECT ct.dateStarted,ct.counID,p.fName,p.mInit,p.lName,ct.dateTerminated FROM counselor c,contract ct, Person p WHERE  ct.counID = c.c_id AND c.SSN = p.SSN AND p.type = 'counselor' AND ct.clientSSN = ? ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(client.getSSN()));



            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contract.setDateStarted(rs.getDate("dateStarted"));
                counselor.setCounselorID(rs.getInt("counID"));
                counselor.setFirstName(rs.getString("fName"));
                counselor.setMiddleInit(rs.getString("mInit"));
                counselor.setLastName(rs.getString("lName"));
                contract.setDateTerminated(rs.getDate("dateTerminated"));

            }


        }catch(SQLException e){
            e.printStackTrace();
        }


    }


    /**
     * Populates the client history table view
     */

    @FXML public void populateClientHistory(){
   historydata = FXCollections.observableArrayList();

        try{
            String query = "SELECT * FROM client_history WHERE SSN = ? ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(client.getSSN()));


            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                history.setDateDiagnosed(rs.getDate("dateDiagnosed"));
                history.setHistoryDetails(rs.getString("details"));
                history.setPreviousTreatment(rs.getString("previousTreatment"));
                history.setTreatmentType(rs.getString("treatmentType"));
                historydata.add(history);
            }

            clientHistoryTable.setItems(historydata);
            clientHistoryTable.columnResizePolicyProperty();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on history Data");
        }


    }

/**
 * populates the client schedule table view
 *
 *Need to come up with a cleaner query for this method. Right now it pulls a bunch of stuff that isn't needed
 */
    @FXML public void populateClientSchedule(){

        scheddata = FXCollections.observableArrayList();

        try{
            String query = "SELECT schedule.availability,sessionType,therapyType,s_date,client_attendance.violation,CONCAT_WS(' ',p.fName,p.lName) AS fullCounName \n" +
"FROM schedule,Person p,sessions,counselor,client_attendance WHERE session_id = s_id AND counselor.c_id = sessions.leadCounID AND session_id=sID \n" +
"and counselor.SSN = p.SSN and client_attendance.cSSN = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(client.getSSN()));


            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                schedule = new Schedule();
                schedule.setSessionDate(rs.getDate("s_date"));
                schedule.setLeadCounselorName(rs.getString("fullCounName"));
                schedule.setAvailability(rs.getString("availability"));
                schedule.setSessionType(rs.getString("sessionType"));
                schedule.setTherapyType(rs.getString("therapyType"));
                schedule.setViolation(rs.getString("violation"));
                scheddata.add(schedule);
            }

            scheduleTable.setItems(scheddata);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on history Data");
        }




    }


    /**
     * Ideally supposed to assign a newly inserted client to a contract. Needs some work though.
     *
     * @param event ignored
     * @throws IOException
     */

    //NEED to work on this. This is suppose to be able to assign a counselor to a newly added client.
public void counselorAssignmentAlert(ActionEvent event) throws IOException{
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to assign a counselor to " +client.getFirstName() + " " + client.getLastName() + " now?",ButtonType.YES,ButtonType.NO);
    alert.setTitle("Counselor Assignment");
    alert.setHeaderText("Assign Counselor");
    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == ButtonType.YES){

            try {
               Parent root = FXMLLoader.load(getClass().getResource("../View/assignCounselor.fxml"));
                if (root != null) {
                    Stage stage = new Stage();

                    stage.setTitle("Counselor Window");
                    stage.setScene(new Scene(root, 1027, 592));
                    stage.show();
                    // Hide this current window (if this is what you want)
                    //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }



    }else{

       alert.close();
    }



    }

/*
public int getClientSSN(int clientSSN){
      return clientSSN;
}
*/


    /**
     * Refreshes data in the form
     *
     * @param actionEvent Updates the fields after all the data has been refreshed from the DB
     */
    public void refreshData(ActionEvent actionEvent){
    updateClient(actionEvent);
    populateInsurance();
    updateAssignmentInfo();
    populateClientHistory();
    populateClientSchedule();
    updateFields(actionEvent);
}

    /**
     * Clears all data on the form
     */

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
        clientHistoryTable.getItems().clear();
        scheduleTable.getItems().clear();
        dateOfBirthPicker.setValue(null);
        message.setTextFill(Color.BLACK);

        message.setText("The data has been cleared!");
    }else{
        message.setTextFill(Color.BLACK);

        message.setText("There is no data to be cleared!");

    }
}




}
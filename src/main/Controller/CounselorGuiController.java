package main.Controller;/**
 * Created by Kevin on 11/27/2016.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import main.Model.*;
import main.Resources.DBConnect;

import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class CounselorGuiController implements Initializable{

    private static final int ssnLength = 9;
    @FXML private TextField counselorAddress;
    @FXML private TextField counselorHousePhone;
    @FXML private TextField counselorCellPhone;
    @FXML private TextField counselorSSN;
    @FXML private TextArea queryResults;
    @FXML private TextField counselorFirstName;
    @FXML private TextField counselorMidInit;
    @FXML private TextField counselorLastName;
    @FXML private ChoiceBox counselorSex;
    @FXML private Label message;
    @FXML private TextField availabilityTxtField;
    private Connection connection;
    public Client client;
    public Contract contract;
    public Counselor counselor;
    public Schedule schedule;
    @FXML private DatePicker dateOfBirthPicker;
    @FXML private Label counselorIDLbl;
    @FXML private Label clientNameLbl;
    @FXML private Label contractStartLbl;
    @FXML private Label contractEndLbl;
    @FXML private Label degreeLbl;
    @FXML private Label yearsOfExpLbl;
    @FXML private Button searchBtn;
    @FXML private Button deleteBtn;
    @FXML private Button clearBtn;
    @FXML private Button updateBtn;
    @FXML private Button addBtn;
   @FXML private Label hireDateLbl;
       private LocalDate today;


    @FXML private TableColumn<Contract,Date> dateStartedCol;
    @FXML private TableColumn<Contract,Date> dateTerminatedCol;
    @FXML private TableColumn<Contract,String> clientNameCol;
    @FXML private TableColumn<Contract,String> contractIDCol;

    @FXML private TableColumn<Schedule,String> availabilityCol;
    @FXML private TableColumn<Schedule,String> clientNameSessCol;
    @FXML private TableColumn<Schedule,String> sessionDateCol;
    @FXML private TableColumn<Schedule,String> sessionTypeCol;
    @FXML private TableColumn<Schedule,String> violationCol;
    @FXML private TableColumn<Schedule,String> therapyTypeCol;

    @FXML private TableView<Contract> contractTable;
    @FXML private TableView<Schedule> scheduleTable;
    private ObservableList<Contract> contractData;
    private ObservableList<Schedule> scheddata;
    ArrayList<String> degrees = new ArrayList<String>();


    /**
     *
     * @param location ignored
     * @param resources ignored
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     connection = DBConnect.getConnection();
     counselor = new Counselor();
     client = new Client();
     counselor = new Counselor();
     contract = new Contract();
        counselorSex.setItems(FXCollections.observableArrayList("Select Sex", new Separator(), "Male", "Female")

     );
     counselorSex.getSelectionModel().selectFirst();
        contractIDCol.setCellValueFactory(new PropertyValueFactory<>("contractID"));
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        dateStartedCol.setCellValueFactory(new PropertyValueFactory<>("dateStarted"));
        dateTerminatedCol.setCellValueFactory(new PropertyValueFactory<>("dateTerminated"));


        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        clientNameSessCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        sessionDateCol.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        sessionTypeCol.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        therapyTypeCol.setCellValueFactory(new PropertyValueFactory<>("therapyType"));
        violationCol.setCellValueFactory(new PropertyValueFactory<>("violation"));

        today = LocalDate.now();

    }


    /**
     * Adds a new Counselor to the DB
     *
     * @param actionEvent ignored
     */
    @FXML public void addCounselor(ActionEvent actionEvent){
        try {

            String queryCheck = "SELECT * FROM Person WHERE type = 'counselor' AND SSN = ? ";
            PreparedStatement ps = connection.prepareStatement(queryCheck);
            ps.setString(1, counselorSSN.getText());


            ResultSet rs = ps.executeQuery();

           if(rs.next()) {
           message.setText("This record already exists!!");
           ps.close();
           }else{

               giveCounselorInfo();
               String query = "INSERT INTO Person (SSN,fName,mInit,lName,housePhoneNum,cellPhoneNum,address,sex,type,dateOfBirth) VALUES (?,?,?,?,?,?,?,?,?,?)";
               ps = connection.prepareStatement(query);
               ps.setInt(1, counselor.getSSN());
               ps.setString(2, counselor.getFirstName());
               ps.setString(3, counselor.getMiddleInit());
               ps.setString(4, counselor.getLastName());
               ps.setString(5, counselor.getHousePhoneNum());
               ps.setString(6, counselor.getCellPhoneNum());
               ps.setString(7, counselor.getAddress());
               ps.setString(8, counselor.getSex());
               ps.setString(9, "counselor");
               ps.setString(10, counselor.getDateOfBirth().toString());



               ps.executeUpdate();

               query = "INSERT INTO counselor (SSN,hireDate,yearsOfExp,availability) VALUES (?,?,?,?)";
               ps = connection.prepareStatement(query);
               ps.setInt(1, counselor.getSSN());
               ps.setDate(2,java.sql.Date.valueOf(today) );
               ps.setInt(3, 0);
               ps.setString(4, counselor.getAvailability());

               populateTextBox();

               message.setText("Record added successfully!");
               ps.close();
           }



            }catch(SQLException e){
                e.printStackTrace();
            }
        }



//updates counselor information. If anything in the textfields or dropdowns are changed.
        @FXML public void updateCounselor(ActionEvent actionEvent){
            giveCounselorInfo();
            try {
                String query = "UPDATE Person set fName=?, mInit=?, lName=?,housePhoneNum=?,cellPhoneNum=?,address=?,sex=?,dateOfBirth=?, availability=? WHERE SSN ='" + counselor.getSSN() + "'";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, counselor.getFirstName());
                ps.setString(2, counselor.getMiddleInit());
                ps.setString(3, counselor.getLastName());
                ps.setString(4, counselor.getHousePhoneNum());
                ps.setString(5, counselor.getCellPhoneNum());
                ps.setString(6, counselor.getAddress());
                ps.setString(7, counselor.getSex());
                ps.setString(8, counselor.getDateOfBirth().toString());
                ps.setString(8, counselor.getAvailability());



                ps.execute();


                message.setTextFill(Color.BLACK);
                message.setText("Record Updated!");

                populateTextBox();



                ps.close();
            }catch(SQLException e){
                e.printStackTrace();
            }

        }


    /**
     * Deletes counselor,based on SSN, from database
     *
     * @param actionEvent ignored
     */

    public void removeCounselor(ActionEvent actionEvent) {
        // We will only allow them to remove a full record by ssn

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this record for " + counselor.getFirstName() + " " + counselor.getLastName() + "?",ButtonType.NO,ButtonType.YES);
        alert.setTitle("Delete Record");
        alert.setHeaderText("Record Deletion");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.YES) {

            try {
                String query = "DELETE FROM Person WHERE SSN = ? ";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1,counselor.getSSN());

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
     * Search by SSN or ID to populate the form
     *
     * @param actionEvent updates fields in the form
     */
   @FXML public void searchBySSNorID(ActionEvent actionEvent) {
if(counselorSSN.getText().length() < 9){
    counselor.setCounselorID(Integer.parseInt(counselorSSN.getText()));

}else{
    counselor.setSSN(Integer.parseInt(counselorSSN.getText()));

}
       if (!counselorSSN.equals("")) {

           try {
               String query = "SELECT p.*,c.availability,c.c_id,c.hireDate FROM Person p, counselor c WHERE type = 'counselor' AND c.SSN = p.SSN AND p.SSN = ? OR c.c_id = ? ";
               PreparedStatement ps = connection.prepareStatement(query);
               ps.setString(1, String.valueOf(counselor.getSSN()));
               ps.setString(2, String.valueOf(counselor.getCounselorID()));


               ResultSet rs = ps.executeQuery();
               if (rs != null && rs.next()) {

                   do{
                       counselor.setSSN(rs.getInt("SSN"));
                       counselor.setFirstName(rs.getString("fName"));
                       counselor.setMiddleInit(rs.getString("mInit"));
                       counselor.setLastName(rs.getString("lName"));
                       counselor.setSex(rs.getString("sex"));
                       counselor.setAddress(rs.getString("address"));
                       counselor.setHousePhoneNum(rs.getString("housePhoneNum"));
                       counselor.setCellPhoneNum(rs.getString("cellPhoneNum"));
                       counselor.setAvailability(rs.getString("availability"));
                       counselor.setDateOfBirth(rs.getDate("dateOfBirth"));
                       counselor.setCounselorID(rs.getInt("c_id"));
                       counselor.setHireDate(rs.getDate("hireDate"));
                   }while (rs.next());


                   query = "SELECT * FROM degree d WHERE counselID = ?";
                   ps = connection.prepareStatement(query);
                   ps.setString(1,String.valueOf(counselor.getCounselorID()));
                   rs = ps.executeQuery();

                   if(rs.next()) {
                       do {
                           counselor.setDegreeLevel(rs.getString("educationLevel"));
                           counselor.setDegreeType(rs.getString("type"));
                           degrees.add(counselor.getDegreeLevel() + " " + counselor.getDegreeType());
                       } while (rs.next());
                   }
                   message.setTextFill(Color.BLACK);
                   message.setText("Record Retrieved!");
                   updateContractInfo();
                   populateContracts();
                   populateCounselorSchedule();
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
           message.setText("Please enter an SSN or an ID!");

       }
   }


    /**
     * Updates the fields on the form
     *
     * @param actionEvent ignored
     */
    @FXML public void updateFields(ActionEvent actionEvent){
      counselorFirstName.setText(counselor.getFirstName());
      counselorMidInit.setText(counselor.getMiddleInit());
      counselorLastName.setText(counselor.getLastName());
      counselorHousePhone.setText(counselor.getHousePhoneNum());
      counselorCellPhone.setText(counselor.getCellPhoneNum());
      counselorAddress.setText(counselor.getAddress());
      counselorIDLbl.setText(String.valueOf(counselor.getCounselorID()));
      clientNameLbl.setText(client.getFirstName() + " " + client.getMiddleInit() + " " + client.getLastName());
        if(counselor.getSex().equals("F")) {
            counselorSex.setValue("Female");
        }else{
            counselorSex.setValue("Male");
        }
      availabilityTxtField.setText(counselor.getAvailability());
      hireDateLbl.setText(counselor.getHireDate().toString());
      dateOfBirthPicker.setValue(counselor.getDateOfBirth().toLocalDate());
        for(String i : degrees) {
         degreeLbl.setText(i);
        }

        contractStartLbl.setText(contract.getDateStarted().toString());
        contractEndLbl.setText(contract.getDateTerminated().toString());





      populateTextBox();
    }


    /**
     * Populates the text box
     *
     */

    @FXML public void populateTextBox() {

        if (queryResults.getText().isEmpty()) {
            queryResults.appendText("Name: " + counselor.getFirstName() + " " + counselor.getMiddleInit() + " " + counselor.getLastName() + "\n");
            queryResults.appendText("House Phone #: " + counselor.getHousePhoneNum() + "\n" + "Cell Phone #: " + counselor.getCellPhoneNum() + "\n");
            queryResults.appendText("Address: " + counselor.getAddress() + "\n");
            queryResults.appendText("Date of Birth: " + counselor.getDateOfBirth() + "\n");
            queryResults.appendText("Availability: " + counselor.getAvailability());
            queryResults.appendText("\n \n");
            queryResults.appendText("Degree: ");
            for(String i : degrees) {
                queryResults.appendText(i + ", ");
            }

        } else {
            queryResults.setText("");
            populateTextBox();
        }
    }

    /**
     * If a new counselor is being established, it stores all the info into an instance of the Counselor class
     */

    @FXML public void giveCounselorInfo(){
        counselor.setSSN(Integer.parseInt(counselorSSN.getText()));
        counselor.setFirstName(counselorFirstName.getText());
        counselor.setMiddleInit(counselorMidInit.getText());
        counselor.setLastName(counselorLastName.getText());
        counselor.setAddress(counselorAddress.getText());
        counselor.setHousePhoneNum(counselorHousePhone.getText());
        counselor.setCellPhoneNum(counselorCellPhone.getText());
        counselor.setDateOfBirth(java.sql.Date.valueOf(dateOfBirthPicker.getValue()));
        if(counselorSex.getSelectionModel().getSelectedItem().toString() == "Female")
        counselor.setSex("F");
        else if(counselorSex.getSelectionModel().getSelectedItem().toString() == "Male"){
            counselor.setSex("M");
        }else{
            message.setText("Not a valid choice!");
        }

    }


    /**
     * Updates to the most recent Contract that a counselor has.
     *
     * Find query for most recent
     */

    @FXML public void updateContractInfo(){
        try{
            String query = "SELECT ct.contractID,p.fName,p.mInit,p.lName,ct.dateTerminated, MAX(ct.dateStarted) AS dateStarted FROM bjoyne2db.counselor c,bjoyne2db.contract ct, bjoyne2db.Person p WHERE  ct.counID = c.c_id AND ct.clientSSN = p.SSN AND ct.counID = ? ORDER BY dateStarted DESC  ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(counselor.getCounselorID()));



            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contract.setDateStarted(rs.getDate("dateStarted"));
                contract.setContractID(rs.getInt("contractID"));
                client.setFirstName(rs.getString("fName"));
                client.setMiddleInit(rs.getString("mInit"));
                client.setLastName(rs.getString("lName"));
                contract.setDateTerminated(rs.getDate("dateTerminated"));

            }


        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    /**
     * Populates a table for all the contracts that the counselor has
     *
     */

    @FXML public void populateContracts(){
   contractData = FXCollections.observableArrayList();

        try{
            String query = "SELECT c.*,CONCAT_WS(' ',p.fName,p.lName) AS fullName FROM contract c,Person p WHERE counID = ? AND c.clientSSN = p.SSN";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(counselor.getCounselorID()));


            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                contract = new Contract();
                contract.setContractID(rs.getInt("contractID"));
                contract.setClientName(rs.getString("fullName"));
                contract.setDateStarted(rs.getDate("dateStarted"));
                contract.setDateTerminated(rs.getDate("dateTerminated"));
                contractData.add(contract);
            }

            contractTable.setItems(contractData);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on history Data");
        }


    }


    /**
     * Populates a table for all of the scheduled sessions a Counselor partakes in.
     *
     */

    @FXML public void populateCounselorSchedule(){

       scheddata = FXCollections.observableArrayList();

        try{
            String query = " SELECT schedule.availability,sessionType,therapyType,s_date,violation,CONCAT_WS(' ',p.fName,p.lName) AS fullClientName \n" +
            "FROM schedule,Person p,sessions,client_attendance WHERE client_attendance.sID=sessions.s_id and session_id = s_id \n" +
            " and client_attendance.cSSN = p.SSN AND sessions.leadCounID = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(counselor.getCounselorID()));


            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                schedule = new Schedule();
                schedule.setSessionDate(rs.getDate("s_date"));
                schedule.setClientName(rs.getString("fullClientName"));
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
     * Clears all data from the form.
     */

@FXML public void clearData(){

    if(!counselorSSN.getText().equals("")) {
        counselorSSN.setText("");
        counselorFirstName.setText("");
        counselorMidInit.setText("");
        counselorLastName.setText("");
        counselorHousePhone.setText("");
        counselorCellPhone.setText("");
        counselorAddress.setText("");
        clientNameLbl.setText("");
        contractStartLbl.setText("");
        contractEndLbl.setText("");
        counselorSex.getSelectionModel().selectFirst();
        queryResults.setText("");
        dateOfBirthPicker.setValue(null);
        availabilityTxtField.setText("");
        hireDateLbl.setText("");
        counselorIDLbl.setText("");
        degreeLbl.setText("");
        contractTable.getItems().clear();
        scheduleTable.getItems().clear();
        degrees.clear();
        message.setTextFill(Color.BLACK);

        message.setText("The data has been cleared!");
    }else{
        message.setTextFill(Color.BLACK);

        message.setText("There is no data to be cleared!");

    }
}




}
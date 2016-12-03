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
import java.util.Optional;
import java.util.ResourceBundle;

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



    @FXML private TableColumn<Contract,Date> dateStartedCol;
    @FXML private TableColumn<Contract,Date> dateTerminatedCol;
    @FXML private TableColumn<Contract,String> clientNameCol;
    @FXML private TableColumn<Contract,String> contractIDCol;

    @FXML private TableColumn<Schedule,String> availabilityCol;
    @FXML private TableColumn<Schedule,String> counselNameCol;
    @FXML private TableColumn<Schedule,String> sessionDateCol;
    @FXML private TableColumn<Schedule,String> sessionTypeCol;
    @FXML private TableColumn<Schedule,String> violationCol;
    @FXML private TableColumn<Schedule,String> therapyTypeCol;

    @FXML private TableView<Contract> contractTable;
    @FXML private TableView<Schedule> scheduleTable;
    private ObservableList<ClientHistory> contractData;
    private ObservableList<Schedule> scheddata;


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

        dateStartedCol.setCellValueFactory(new PropertyValueFactory<>("dateDiagnosed"));
        dateTerminatedCol.setCellValueFactory(new PropertyValueFactory<>("details"));
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("previousTreatment"));
        contractIDCol.setCellValueFactory(new PropertyValueFactory<>("treatmentType"));


        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        counselNameCol.setCellValueFactory(new PropertyValueFactory<>("Counselor Name"));
        sessionDateCol.setCellValueFactory(new PropertyValueFactory<>("Session Date"));
        sessionTypeCol.setCellValueFactory(new PropertyValueFactory<>("Session Type"));
        therapyTypeCol.setCellValueFactory(new PropertyValueFactory<>("Therapy Type"));
        violationCol.setCellValueFactory(new PropertyValueFactory<>("Violation"));

    }




//Adds new client to database
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
               String query = "INSERT INTO Person (SSN,fName,mInit,lName,housePhoneNum,cellPhoneNum,address,sex,type) VALUES (?,?,?,?,?,?,?,?,?)";
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


               ps.executeUpdate();

               query = "INSERT INTO counselor (SSN,hireDate,yearsOfExp,availability) VALUES (?,?,?,?)";
               ps = connection.prepareStatement(query);
               ps.setInt(1, counselor.getSSN());
               ps.setDate(2, counselor.getHireDate());
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




        @FXML public void updateCounselor(ActionEvent actionEvent){
            giveCounselorInfo();
            try {
                String query = "UPDATE Person set fName=?, mInit=?, lName=?,housePhoneNum=?,cellPhoneNum=?,address=?,sex=? WHERE SSN ='" + counselor.getSSN() + "'";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, counselor.getFirstName());
                ps.setString(2, counselor.getMiddleInit());
                ps.setString(3, counselor.getLastName());
                ps.setString(4, counselor.getHousePhoneNum());
                ps.setString(5, counselor.getCellPhoneNum());
                ps.setString(6, counselor.getAddress());
                ps.setString(7, counselor.getSex());

                ps.execute();


                message.setTextFill(Color.BLACK);
                message.setText("Record Updated!");

                populateTextBox();



                ps.close();
            }catch(SQLException e){
                e.printStackTrace();
            }

        }


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


   @FXML public void searchBySSNorID(ActionEvent actionEvent) {

            counselor.setSSN(Integer.parseInt(counselorSSN.getText()));
       if (!counselorSSN.equals("")) {

           try {
               String query = "SELECT p.*,c.availability,c.c_id,c.hireDate FROM Person p, counselor c WHERE type = 'counselor' AND c.SSN = p.SSN AND p.SSN = ? ";
               PreparedStatement ps = connection.prepareStatement(query);
               ps.setString(1, String.valueOf(counselor.getSSN()));


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
                       counselor.setCounselorID(rs.getInt("c_id"));
                       counselor.setHireDate(rs.getDate("hireDate"));

                       message.setTextFill(Color.BLACK);
                       message.setText("Record Retrieved!");
                   }while (rs.next());

                   updateContractInfo();
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



    @FXML public void updateFields(ActionEvent actionEvent){
      counselorFirstName.setText(counselor.getFirstName());
      counselorMidInit.setText(counselor.getMiddleInit());
      counselorLastName.setText(counselor.getLastName());
      counselorHousePhone.setText(counselor.getHousePhoneNum());
      counselorCellPhone.setText(counselor.getCellPhoneNum());
      counselorAddress.setText(counselor.getAddress());
      counselorIDLbl.setText(String.valueOf(counselor.getCounselorID()));
      clientNameLbl.setText(client.getFirstName() + " " + client.getMiddleInit() + " " + client.getLastName());
      contractStartLbl.setText(contract.getDateStarted().toString());
      contractEndLbl.setText(contract.getDateTerminated().toString());
      availabilityTxtField.setText(counselor.getAvailability());
      hireDateLbl.setText(counselor.getHireDate().toString());

        if(counselor.getSex().equals("F")) {
          counselorSex.setValue("Female");
      }else{
          counselorSex.setValue("Male");
      }

      populateTextBox();
    }

    @FXML public void populateTextBox() {

        if (queryResults.getText().isEmpty()) {
            queryResults.appendText("Name: " + counselor.getFirstName() + " " + counselor.getMiddleInit() + " " + counselor.getLastName() + "\n");
            queryResults.appendText("House Phone #: " + counselor.getHousePhoneNum() + "\n" + "Cell Phone #: " + counselor.getCellPhoneNum() + "\n");
            queryResults.appendText("Address: " + counselor.getAddress());
            queryResults.appendText("\n \n");
            queryResults.appendText("Degree: " + counselor.getDegreeLevel() + "of" + counselor.getDegreeType());


        } else {
            queryResults.setText("");
            populateTextBox();
        }
    }


    @FXML public void giveCounselorInfo(){
        counselor.setSSN(Integer.parseInt(counselorSSN.getText()));
        counselor.setFirstName(counselorFirstName.getText());
        counselor.setMiddleInit(counselorMidInit.getText());
        counselor.setLastName(counselorLastName.getText());
        counselor.setAddress(counselorAddress.getText());
        counselor.setHousePhoneNum(counselorHousePhone.getText());
        counselor.setCellPhoneNum(counselorCellPhone.getText());

        if(counselorSex.getSelectionModel().getSelectedItem().toString() == "Female")
        counselor.setSex("F");
        else if(counselorSex.getSelectionModel().getSelectedItem().toString() == "Male"){
            counselor.setSex("M");
        }else{
            message.setText("Not a valid choice!");
        }

    }


// Find query for most recent
    @FXML public void updateContractInfo(){
        try{
            String query = "SELECT ct.contractID,p.fName,p.mInit,p.lName,ct.dateTerminated, MAX(ct.dateStarted) AS dateStarted FROM bjoyne2db.counselor c,bjoyne2db.contract ct, bjoyne2db.Person p WHERE  ct.counID = c.c_id AND ct.clientSSN = p.SSN AND ct.counID = ?  ";
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


    /*@FXML public void populateClientHistory(){
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


    @FXML public void populateClientSchedule(){

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




    }*/



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
       // clientHistoryTable.getItems().clear();
        scheduleTable.getItems().clear();

        message.setTextFill(Color.BLACK);

        message.setText("The data has been cleared!");
    }else{
        message.setTextFill(Color.BLACK);

        message.setText("There is no data to be cleared!");

    }
}




}
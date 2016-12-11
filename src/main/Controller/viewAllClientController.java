package main.Controller;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Model.Client;
import main.Model.ClientHistory;
import main.Model.Person;
import main.Resources.DBConnect;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by blakejoynes on 11/28/16.
 */

public class viewAllClientController implements Initializable {

    @FXML private TableColumn ssnCol;
    @FXML private TableColumn fNameCol;
    @FXML private TableColumn mInitCol;
    @FXML private TableColumn lNameCol;
    @FXML private TableColumn dobCol;
    @FXML private TableColumn sexCol;
    @FXML private TableColumn addressCol;
    @FXML private TableColumn houseNumCol;
    @FXML private TableColumn cellNumCol;
    @FXML private TableColumn courtOrderedCol;
    @FXML private TableView <Client> allClientsTable;
    private Connection connection;
    private Client client;
    private ObservableList<Client> clientData;

    @FXML private Label avgMale;
    @FXML private Label youngMale;
    @FXML private Label oldMale;
    @FXML private Label numMale;
    @FXML private Label percentMale;
    @FXML private Label avgAll;
    @FXML private Label youngAll;
    @FXML private Label oldAll;
    @FXML private Label numAll;
    @FXML private Label percentAll;
    @FXML private Label avgFemale;
    @FXML private Label youngFemale;
    @FXML private Label oldFemale;
    @FXML private Label numFemale;
    @FXML private Label percentFemale;
    @FXML private Label percentFW;
    @FXML private Label avgFW;
    @FXML private Label youngFW;
    @FXML private Label oldFW;
    @FXML private Label numFW;
    @FXML private Label percentMand;
    @FXML private Label avgMand;
    @FXML private Label youngMand;
    @FXML private Label oldMand;
    @FXML private Label numMand;

    
    /**
     *Initializes the table that lists all of the clients in the DB
     *
     * @param location not used
     * @param resources not used
     */
    @Override
     public void initialize(URL location, ResourceBundle resources) {
        connection = DBConnect.getConnection();

        ssnCol.setCellValueFactory(new PropertyValueFactory<>("SSN"));
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        mInitCol.setCellValueFactory(new PropertyValueFactory<>("middleInit"));
        lNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        houseNumCol.setCellValueFactory(new PropertyValueFactory<>("housePhoneNum"));
        cellNumCol.setCellValueFactory(new PropertyValueFactory<>("cellPhoneNum"));
        courtOrderedCol.setCellValueFactory(new PropertyValueFactory<>("courtOrdered"));


        clientData = FXCollections.observableArrayList();

        try{
            String query = "SELECT p.*, courtOrdered FROM Person as p, contract as c WHERE type = 'client' and SSN = clientSSN";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                client = new Client();
                client.setSSN(rs.getInt("SSN"));
                client.setDateOfBirth(rs.getDate("dateOfBirth"));
                client.setFirstName(rs.getString("fName"));
                client.setMiddleInit(rs.getString("mInit"));
                client.setLastName(rs.getString("lName"));
                client.setSex(rs.getString("sex"));
                client.setAddress(rs.getString("address"));
                client.setHousePhoneNum(rs.getString("housePhoneNum"));
                client.setCellPhoneNum(rs.getString("cellPhoneNum"));
                client.setCourtOrdered(rs.getString("courtOrdered"));
                clientData.add(client);
            }

            allClientsTable.setItems(clientData);

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on client Data");
        }





    }


public void display(ActionEvent actionEvent) throws SQLException{
        
        String queryCheck = "Select AVG(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where sex = \"M\" and type = \"client\";";
        PreparedStatement ps = connection.prepareStatement(queryCheck);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgMale.setText(String.valueOf(output));
                }
                
        else
            avgMale.setText("N/A");
        
        queryCheck = "Select AVG(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgAll.setText(String.valueOf(output));
                }
                
        else
            avgAll.setText("N/A");
        
        queryCheck = "Select MIN(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngAll.setText(String.valueOf(output));
                }
                
        else
            youngAll.setText("N/A");
        
        queryCheck = "Select MAX(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldAll.setText(String.valueOf(output));
                }
                
        else
            oldAll.setText("N/A");
        int total = 0;
        queryCheck = "Select count(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   total = output;
                   numAll.setText(String.valueOf(output));
                }
                
        else
            numAll.setText("N/A");
        
        percentAll.setText("100%");
        
        queryCheck = "Select MIN(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where sex = \"M\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngMale.setText(String.valueOf(output));
                }
                
        else
            youngMale.setText("N/A");
        
        queryCheck = "Select Max(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where sex = \"M\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldMale.setText(String.valueOf(output));
                }
                
        else
            oldMale.setText("N/A");
 
        queryCheck = "Select Count(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where sex = \"M\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   numMale.setText(String.valueOf(output));
                   if (total!=0)
                    percentMale.setText(String.valueOf(output*100/total)+"%");
                   else
                    percentMale.setText("N/A");
                }
                
        else
            numMale.setText("N/A");
        
       
        
        queryCheck = "Select AVG(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where sex = \"F\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgFemale.setText(String.valueOf(output));
                }
                
        else
            avgFemale.setText("N/A");
        
        queryCheck = "Select MIN(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where sex = \"F\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngFemale.setText(String.valueOf(output));
                }
                
        else
            youngFemale.setText("N/A");
        
        queryCheck = "Select MAX(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where sex = \"F\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldFemale.setText(String.valueOf(output));
                }
                
        else
            oldFemale.setText("N/A");
        
        queryCheck = "Select Count(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where sex = \"F\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   numFemale.setText(String.valueOf(output));
                   if (total!=0)
                       percentFemale.setText(String.valueOf(output*100/total)+"%");
                   else
                       percentFemale.setText("N/A");
                }
                
        else
            numFemale.setText("N/A");

        queryCheck = "Select AVG(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where SSN in (Select distinct clientSSN From contract Where courtOrdered <> \"Y\" and courtOrdered <> \"y\")and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgFW.setText(String.valueOf(output));
                }
                
        else
            avgFW.setText("N/A");
        
        queryCheck = "Select min(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where SSN in (Select distinct clientSSN From contract Where courtOrdered <> \"Y\" and courtOrdered <> \"y\")and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngFW.setText(String.valueOf(output));
                }
                
        else
            youngFW.setText("N/A");
        
        queryCheck = "Select MAX(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where SSN in (Select distinct clientSSN From contract Where courtOrdered <> \"Y\" and courtOrdered <> \"y\")and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldFW.setText(String.valueOf(output));
                }
                
        else
            oldFW.setText("N/A");
        
        queryCheck = "Select count(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where SSN in (Select clientSSN From contract Where courtOrdered <> \"Y\" and courtOrdered <> \"y\")and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   numFW.setText(String.valueOf(output));
                   if (total!=0)
                       percentFW.setText(String.valueOf(output*100/total)+"%");
                   else
                       percentFW.setText("N/A");
                }
                
        else
            numFW.setText("N/A");
        
        queryCheck = "Select AVG(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where SSN in (Select distinct clientSSN From contract Where courtOrdered = \"Y\" or courtOrdered = \"y\") and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgMand.setText(String.valueOf(output));
                }
                
        else
            avgMand.setText("N/A");
        
        queryCheck = "Select Min(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where SSN in (Select distinct clientSSN From contract Where courtOrdered = \"Y\" or courtOrdered = \"y\") and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngMand.setText(String.valueOf(output));
                }
                
        else
            youngMand.setText("N/A");
        
        queryCheck = "Select max(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where SSN in (Select distinct clientSSN From contract Where courtOrdered = \"Y\" or courtOrdered = \"y\") and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldMand.setText(String.valueOf(output));
                }
                
        else
            oldMand.setText("N/A");
        
        queryCheck = "Select count(TIMESTAMPDIFF(YEAR, dateOfBirth, CURDATE())) From Person Where SSN in (Select distinct clientSSN From contract Where courtOrdered = \"Y\" or courtOrdered = \"y\") and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   numMand.setText(String.valueOf(output));
                   if (total!=0)
                        percentMand.setText(String.valueOf(output*100/total)+"%");
                   else
                       percentMand.setText(String.valueOf("N/A"));
                }
                
        else
            numMand.setText("N/A");
        
        
        rs.close();
            
    }
public void mainMenu() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/main/View/mainView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
                stage.show();
    }

}

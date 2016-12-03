package main.Controller;

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
import java.util.ResourceBundle;

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
    @FXML private TableView <Client> allClientsTable;
    private Connection connection;
    private Client client;
    private ObservableList<Client> clientData;


    /**
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


        clientData = FXCollections.observableArrayList();

        try{
            String query = "SELECT * FROM Person WHERE type = 'client'";
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
                clientData.add(client);
            }

            allClientsTable.setItems(clientData);

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on client Data");
        }





    }





}

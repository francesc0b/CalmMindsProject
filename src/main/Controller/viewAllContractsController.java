package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Model.Contract;
import main.Resources.DBConnect;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by blakejoynes on 12/2/16.
 */
public class viewAllContractsController implements Initializable {

    @FXML
    private TableColumn ssnCol;
    @FXML private TableColumn contractIDCol;
    @FXML private TableColumn counselorNameCol;
    @FXML private TableColumn clientNameCol;
    @FXML private TableColumn dateStartedCol;
    @FXML private TableColumn dateTerminatedCol;
    @FXML private TableView<Contract> allContractsTable;
    private Contract contract;
    private Connection connection;
    private ObservableList<Contract> contractData;


    /**
     *
     * @param location not used
     * @param resources not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = DBConnect.getConnection();

        contractIDCol.setCellValueFactory(new PropertyValueFactory<>("contractID"));
        counselorNameCol.setCellValueFactory(new PropertyValueFactory<>("counselorName"));
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        dateStartedCol.setCellValueFactory(new PropertyValueFactory<>("dateStarted"));
        dateTerminatedCol.setCellValueFactory(new PropertyValueFactory<>("dateTerminated"));



        contractData = FXCollections.observableArrayList();

        try{
            String query = "SELECT c.contract_id,p.fName FROM contract c, Person p  WHERE p.type = 'client'";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                contract = new Contract();

                contractData.add(contract);
            }

            allContractsTable.setItems(contractData);

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on client Data");
        }





    }




}

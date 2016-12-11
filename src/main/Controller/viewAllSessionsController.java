package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Model.Contract;
import main.Model.Session;
import main.Resources.DBConnect;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by blakejoynes on 12/6/16.
 */
public class viewAllSessionsController implements Initializable {
    @FXML
    TableColumn sessionIDCol;
    @FXML
    TableColumn sessionTypeCol;
    @FXML
    TableColumn therapyTypeCol;
    @FXML
    TableColumn leadCounselorNameCol;
    
    @FXML
    TableColumn sessionDateCol;
    
    private Session session;
    private Connection connection;
    private ObservableList<Session> sessionData;
    @FXML
    TableView allSessionsTable;


    /**
     * Initializes a table for all of the Sessions stored in the DB.
     *
     * @param location ignored
     * @param resources ignored
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connection = DBConnect.getConnection();
        sessionIDCol.setCellValueFactory(new PropertyValueFactory<>("sessionID"));
        sessionTypeCol.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
        therapyTypeCol.setCellValueFactory(new PropertyValueFactory<>("therapyType"));
        leadCounselorNameCol.setCellValueFactory(new PropertyValueFactory<>("leadCounselorName"));
        
        sessionDateCol.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        


        sessionData = FXCollections.observableArrayList();




        try{
            String query = "SELECT sessions.*,CONCAT_WS(' ',cp.fName,cp.lName) AS fullCounName FROM sessions,counselor ,Person cp WHERE sessions.leadCounID=counselor.c_id AND counselor.SSN = cp.SSN ";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                session = new Session();
                session.setSessionID(rs.getInt("s_id"));

                session.setLeadCounselorName(rs.getString("fullCounName"));
                session.setSessionType(rs.getString("sessionType"));
                session.setTherapyType(rs.getString("therapyType"));
                session.setSessionDate(rs.getDate("s_date"));



                sessionData.add(session);
            }

            allSessionsTable.setItems(sessionData);

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on client Data");
        }




    }











}

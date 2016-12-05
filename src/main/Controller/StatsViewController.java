/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalmMindsProject.src.main.Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.Resources.DBConnect;


/**
 * FXML Controller class
 *
 * @author Francesco
 */
public class StatsViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
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
    
    private Connection connection;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = DBConnect.getConnection();
    }    
    
    public void display(ActionEvent actionEvent) throws SQLException{
        
        String queryCheck = "Select AVG(age) From PERSON Where sex = \"M\" and type = \"client\";";
        PreparedStatement ps = connection.prepareStatement(queryCheck);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgMale.setText(String.valueOf(output));
                }
                
        else
            avgMale.setText("N/A");
        
        queryCheck = "Select AVG(age) From PERSON Where type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgAll.setText(String.valueOf(output));
                }
                
        else
            avgAll.setText("N/A");
        
        queryCheck = "Select MIN(age) From PERSON Where type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngAll.setText(String.valueOf(output));
                }
                
        else
            youngAll.setText("N/A");
        
        queryCheck = "Select MAX(age) From PERSON Where type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldAll.setText(String.valueOf(output));
                }
                
        else
            oldAll.setText("N/A");
        int total = 0;
        queryCheck = "Select count(age) From PERSON Where type = \"client\";";
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
        
        queryCheck = "Select MIN(age) From PERSON Where sex = \"M\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngMale.setText(String.valueOf(output));
                }
                
        else
            youngMale.setText("N/A");
        
        queryCheck = "Select Max(age) From PERSON Where sex = \"M\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldMale.setText(String.valueOf(output));
                }
                
        else
            oldMale.setText("N/A");
 
        queryCheck = "Select Count(age) From PERSON Where sex = \"M\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   numMale.setText(String.valueOf(output));
                   if (total!=0)
                    percentMale.setText(String.valueOf(output/total*100));
                   else
                    percentMale.setText("N/A");
                }
                
        else
            numMale.setText("N/A");
        
       
        
        queryCheck = "Select AVG(age) From PERSON Where sex = \"F\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgFemale.setText(String.valueOf(output));
                }
                
        else
            avgFemale.setText("N/A");
        
        queryCheck = "Select MIN(age) From PERSON Where sex = \"F\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngFemale.setText(String.valueOf(output));
                }
                
        else
            youngFemale.setText("N/A");
        
        queryCheck = "Select MAX(age) From PERSON Where sex = \"F\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldFemale.setText(String.valueOf(output));
                }
                
        else
            oldFemale.setText("N/A");
        
        queryCheck = "Select Count(age) From PERSON Where sex = \"F\" and type = \"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   numFemale.setText(String.valueOf(output));
                   if (total!=0)
                       percentFemale.setText(String.valueOf(output/total*100));
                   else
                       percentFemale.setText("N/A");
                }
                
        else
            numFemale.setText("N/A");

        queryCheck = "Select AVG(age) From PERSON Where SSN in (Select distinct SSN From CLIENT_ATTENDANCE Where FreeWill = 1)and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgFW.setText(String.valueOf(output));
                }
                
        else
            avgFW.setText("N/A");
        
        queryCheck = "Select min(age) From PERSON Where SSN in (Select distinct SSN From CLIENT_ATTENDANCE Where FreeWill = 1)and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngFW.setText(String.valueOf(output));
                }
                
        else
            youngFW.setText("N/A");
        
        queryCheck = "Select MAX(age) From PERSON Where SSN in (Select distinct SSN From CLIENT_ATTENDANCE Where FreeWill = 1)and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldFW.setText(String.valueOf(output));
                }
                
        else
            oldFW.setText("N/A");
        
        queryCheck = "Select count(age) From PERSON Where SSN in (Select SSN From CLIENT_ATTENDANCE Where FreeWill = 1)and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   numFW.setText(String.valueOf(output));
                   if (total!=0)
                       percentFW.setText(String.valueOf(output/total*100));
                   else
                       percentFW.setText("N/A");
                }
                
        else
            numFW.setText("N/A");
        
        queryCheck = "Select AVG(age) From PERSON Where SSN in (Select distinct SSN From CLIENT_ATTENDANCE Where FreeWill <> 1) and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   avgMand.setText(String.valueOf(output));
                }
                
        else
            avgMand.setText("N/A");
        
        queryCheck = "Select Min(age) From PERSON Where SSN in (Select distinct SSN From CLIENT_ATTENDANCE Where FreeWill <> 1) and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   youngMand.setText(String.valueOf(output));
                }
                
        else
            youngMand.setText("N/A");
        
        queryCheck = "Select max(age) From PERSON Where SSN in (Select distinct SSN From CLIENT_ATTENDANCE Where FreeWill <> 1) and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   oldMand.setText(String.valueOf(output));
                }
                
        else
            oldMand.setText("N/A");
        
        queryCheck = "Select count(age) From PERSON Where SSN in (Select distinct SSN From CLIENT_ATTENDANCE Where FreeWill <> 1) and type =\"client\";";
        ps = connection.prepareStatement(queryCheck);
        rs = ps.executeQuery();
        if (rs.next()) {
                   int output = (int) rs.getFloat(1);
                   numMand.setText(String.valueOf(output));
                   if (total!=0)
                        percentMand.setText(String.valueOf(output/total*100));
                   else
                       percentMand.setText(String.valueOf("N/A"));
                }
                
        else
            numMand.setText("N/A");
        
        
        rs.close();
            
    }
}

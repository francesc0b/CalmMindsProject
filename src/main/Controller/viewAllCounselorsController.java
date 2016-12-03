package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Model.Counselor;
import main.Resources.DBConnect;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Created by blakejoynes on 12/2/16.
 */
public class viewAllCounselorsController implements Initializable {
    @FXML
    private TableColumn ssnCol;
    @FXML private TableColumn fNameCol;
    @FXML private TableColumn mInitCol;
    @FXML private TableColumn lNameCol;
    @FXML private TableColumn dobCol;
    @FXML private TableColumn sexCol;
    @FXML private TableColumn addressCol;
    @FXML private TableColumn houseNumCol;
    @FXML private TableColumn cellNumCol;
    @FXML private TableColumn counselorIDCol;
    @FXML private TableColumn hireDateCol;
    @FXML private TableColumn degreeTypeCol;
    @FXML private TableColumn degreeLevelCol;
    @FXML private TableColumn yearsOfExpCol;
    @FXML private Label message;

    @FXML private TableView<Counselor> allCounselorsTable;
    private Connection connection;
    private Counselor counselor;
    private ObservableList<Counselor> counselorData;


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
        counselorIDCol.setCellValueFactory(new PropertyValueFactory<>("counselorID"));
        hireDateCol.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        degreeTypeCol.setCellValueFactory(new PropertyValueFactory<>("degreeType"));
        degreeLevelCol.setCellValueFactory(new PropertyValueFactory<>("degreeLevel"));
        yearsOfExpCol.setCellValueFactory(new PropertyValueFactory<>("yearsOfExp"));


        counselorData = FXCollections.observableArrayList();

        try{
            String query = "SELECT p.*,c.c_id,yearsOfExp,hireDate,d.educationLevel,d.type FROM bjoyne2db.Person p \n" +
                    "JOIN bjoyne2db.counselor c ON c.SSN = p.SSN \n" +
                    "JOIN bjoyne2db.degree d ON d.counselID = c.c_id\n" +
                    "WHERE p.type='counselor';";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                counselor = new Counselor();
                counselor.setCounselorID(rs.getInt("c_ID"));
                counselor.setSSN(rs.getInt("SSN"));
                counselor.setDateOfBirth(rs.getDate("dateOfBirth"));
                counselor.setFirstName(rs.getString("fName"));
                counselor.setMiddleInit(rs.getString("mInit"));
                counselor.setLastName(rs.getString("lName"));
                counselor.setSex(rs.getString("sex"));
                counselor.setAddress(rs.getString("address"));
                counselor.setHousePhoneNum(rs.getString("housePhoneNum"));
                counselor.setCellPhoneNum(rs.getString("cellPhoneNum"));
                counselor.setHireDate(rs.getDate("hireDate"));
                counselor.setYearsOfExp(rs.getInt("yearsOfExp"));
                counselor.setDegreeType(rs.getString("d.type"));
                counselor.setDegreeLevel(rs.getString("d.educationLevel"));
                counselorData.add(counselor);
            }

            allCounselorsTable.setItems(counselorData);

        }
        catch(Exception e){
            e.printStackTrace();
            message.setText("Error on counselor Data");
        }





    }




}

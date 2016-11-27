package main.Controller;/**
 * Created by Kevin on 11/27/2016.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CounselorGuiController extends Application {
    private static final int ssnLength = 9;
    private static  final int sidLength = 9;
    public TextField counselorDegLevel;
    public TextField counselorAddress;
    public TextField counselorPhone;
    public TextField counselorSsn;
    public TextField counselorContractNumbers;
    public TextField counselorDegreeType;
    public TextField sID;
    public TextField counselorHireDate;
    public TextField counselorSpecialization;
    public Button searchButton;
    public TextField counselorFName;
    public TextField counselorMiddleInt;
    public TextField counselorLName;
    public TextField counselorSex;
    public TextField counselorClientContractNums;
    public TextArea errorMessages;
    public TextArea queryResults;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }

    public void submitQuery(ActionEvent actionEvent) {
        // check to see if record exists in the database if it does do update
        // else do insert for fields that are not empty. Btw if the field is empty a get return ""
    }

    public void submitSearch(ActionEvent actionEvent) {
        // will search based on what  and display the first result in the textfields and rest of info in query box
        // example display below:
        queryResults.setText("Name: John A. Jacob" + "\n" +
                "Education: Masters Comp Sci" +  "\n" +
                "Sex: M"); // etc.. for each result the database returns, but we'll put the first one in the fields.
    }

    public void removeSpecified(ActionEvent actionEvent) {
        // We will only allow them to remove a full record by ssn or sid

        String removeId = sID.getText();
        String removeSsn = counselorSsn.getText();

        if (!removeId.equals("") && removeId.length() == sidLength) {
            errorMessages.setText("");
            queryResults.setText("Record removed");
        } else if (!removeSsn.equals("") && removeSsn.length() == ssnLength) {
            errorMessages.setText("");
            queryResults.setText("Record removed");
        } else {
            errorMessages.setText("Only able to remove record using Ssn or Sid");
            errorMessages.setVisible(true);

        }
    }
}


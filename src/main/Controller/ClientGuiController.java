package main.Controller;/**
 * Created by Kevin on 11/27/2016.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientGuiController extends Application {
    private static final int ssnLength = 9;
    public TextField historyDate;
    public TextField clientAddress;
    public TextField clientPhone;
    public TextField clientSsn;
    public TextArea queryResults;
    public Button searchButton;
    public TextField clientFirstName;
    public TextField clientMidInit;
    public TextField historyTreatment;
    public TextField clientLastName;
    public TextField clientSex;
    public TextField therapistId;
    public TextField therapyType;
    public TextField policyNumber;
    public TextField sessionType;
    public TextArea errorMessages;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    }


    public void removeQuery(ActionEvent actionEvent) {
        // We will only allow them to remove a full record by ssn

        String removeSsn = clientSsn.getText();

        if (!removeSsn.equals("") && removeSsn.length() == ssnLength) {
            queryResults.setText("Record removed");
            errorMessages.setText("");
        } else {
            errorMessages.setText("Only able to delete record using Ssn");
            errorMessages.setVisible(true);
        }
    }

    public void searchQuery(ActionEvent actionEvent) {
        // same as the counselor buttons
    }

    public void submitQuery(ActionEvent actionEvent) {
        // same as the counselor buttons
    }
}


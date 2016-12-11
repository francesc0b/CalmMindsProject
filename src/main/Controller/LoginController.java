package main.Controller;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.Resources.DBConnect;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Created by bjoynes on 11/4/2016.
 */
public class LoginController{
    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;


    /**
     *
     * @param event returns object that the event returns
     * @throws IOException
     */


    @FXML private void login(ActionEvent event) throws IOException{

        String userName = userField.getText();
        String password = passwordField.getText();

        try {
            String sql = "SELECT userName,password FROM admin_users where userName = ? and password = ?";

            PreparedStatement ps = DBConnect.getConnection().prepareStatement(sql);
            ps.setString(1, userName.trim());
            ps.setString(2,password.trim());
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Login");
                alert.setHeaderText("Login Status");
                alert.setContentText("Login Successful!");
                alert.showAndWait();

                ((Node)(event.getSource())).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/main/View/mainView.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Login");
                stage.setScene(scene);
                stage.show();

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login");
                alert.setHeaderText("Login Status");
                alert.setContentText("Login Failed!");
                alert.showAndWait();
            }




            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }



}

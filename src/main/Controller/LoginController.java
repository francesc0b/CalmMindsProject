package main.Controller;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
/**
 * Created by bjoynes on 11/4/2016.
 */
public class LoginController {
    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;


    @FXML private void login() {
        String userName = userField.getText();
        String password = passwordField.getText();

      System.out.println("Success!");

    }



}

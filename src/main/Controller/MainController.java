package main.Controller;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;
import java.text.*;
/**
 * Created by bjoynes on 11/4/2016.
 */
public class MainController {
   private Date date = new Date();
   private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    @FXML Label timeLabel;

    @FXML public void initialize(){
        timeLabel.setText(dateFormat.format(date));
    }



}

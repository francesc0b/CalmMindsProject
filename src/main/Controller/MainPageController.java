package main.Controller;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

        import java.io.IOException;

public class MainPageController {
    public void activateCounselorGui(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/counselorConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("Counselor Window");
                stage.setScene(new Scene(root, 800, 600));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void acttivateClientGui(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/clientConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("Client Window");
                stage.setScene(new Scene(root, 952, 592));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void allClientActivation(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/allClientConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("All Clients");
                stage.setScene(new Scene(root, 800, 600));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
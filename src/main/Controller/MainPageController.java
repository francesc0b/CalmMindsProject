package main.Controller;

        //import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

        import java.io.IOException;
import javafx.fxml.FXML;
/*
Implements the start of all the UI elements on the main Page
 */
public class MainPageController {

    /**
     * Start the UI module for a Counselor
     *
     * @param actionEvent ignored
     * @throws IOException
     */
    public void activateCounselorGui(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/counselorConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("Counselor Window");
                stage.setScene(new Scene(root, 1027, 592));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Start the UI module for a Client
     *
     * @param actionEvent ignored
     * @throws IOException
     */
    public void acttivateClientGui(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/clientConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("Client Window");
                stage.setScene(new Scene(root, 1027, 592));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Start the UI module for a list of all Clients
     *
     * @param actionEvent ignored
     * @throws IOException
     */

    public void allClientActivation(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/allClientConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("All Clients");
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Start the UI module for a list of all Counselors
     *
     * @param actionEvent ignored
     * @throws IOException
     */
    public void allCounselorActivation(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/allCounselorConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("All Counselors");
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Start the UI module for a list of all Contracts
     *
     * @param actionEvent ignored
     * @throws IOException
     */
    @FXML
    public void allContractActivation(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/allContracts.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("All Contracts");
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

    /**
     * Start the UI module for a list of all Sessions
     *
     * @param actionEvent ignored
     * @throws IOException
     */
    @FXML
    public void allSessionsActivation(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/allSessions.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("All Sessions");
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





    
    @FXML
        public void statsActivation(ActionEvent actionEvent) throws IOException {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/main/View/statsView.fxml"));
                if (root != null) {
                    Stage stage = new Stage();
                    stage.setTitle("Client Stats");
                    stage.setScene(new Scene(root, 600, 400));

                    stage.show();
                    // Hide this current window (if this is what you want)
                    //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        @FXML
        public void faqActivation(ActionEvent actionEvent) throws IOException {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/main/View/faqView.fxml"));
                if (root != null) {
                    Stage stage = new Stage();
                    stage.setTitle("Other Stats");
                    stage.setScene(new Scene(root, 600, 400));

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
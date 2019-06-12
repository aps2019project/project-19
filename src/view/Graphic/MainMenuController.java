package view.Graphic;

import controller.Controller;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    private Stage stage;
    private Controller mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainController(Controller controller) {
        this.mainController = controller;
    }
}

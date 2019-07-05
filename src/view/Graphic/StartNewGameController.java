package view.Graphic;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StartNewGameController extends MenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void goToSinglePlayerMode() {
        getMainController().enterMenu("single player");
        changeMenu("singleGameMenu.fxml");
    }

    public void goToMultiPlayerMode() {
        getMainController().enterMenu("multi player");
        changeMenu("gameModeMenu.fxml");
    }

    public void exit() {
        getMainController().exitMenu();
        changeMenu("MainMenu.fxml");
    }
}

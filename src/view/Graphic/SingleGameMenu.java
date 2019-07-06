package view.Graphic;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleGameMenu extends MenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToStoryMode() {
        getMainController().enterMenu("story mode");
        changeMenu("singleGameStoryMode.fxml");
    }

    public void goToCustomMode() {
        getMainController().enterMenu("custom game");
        changeMenu("singleGameCustomMode.fxml");
    }

    public void exit() {
        getMainController().exitMenu();
        changeMenu("startNewGame.fxml");
    }
}

package view.Graphic;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleGameStoryMode extends MenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void startBattle(int level) {
        //todo
    }

    public void startLevel1() {
        startBattle(1);
    }

    public void startLevel2() {
        startBattle(2);
    }

    public void startLevel3() {
        startBattle(3);
    }

    public void exit() {
        changeMenu("singleGameMenu.fxml");
    }
}

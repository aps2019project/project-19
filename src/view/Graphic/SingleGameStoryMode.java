package view.Graphic;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import view.ErrorType;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleGameStoryMode extends MenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void startBattle(int level) {
        getMainController().selectStoryLevel(level);
        changeMenu("BattleView.fxml");
    }

    public void startLevel1() {
        if (checkDecks())
            startBattle(1);
    }

    public void startLevel2() {
        if (checkDecks())
            startBattle(2);
    }

    public void startLevel3() {
        if (checkDecks())
            startBattle(3);
    }

    public void exit() {
        getMainController().exitMenu();
        changeMenu("singleGameMenu.fxml");
    }

    public boolean checkDecks() {
        if (!getMainController().getLoggedInAccount().getCollection().isAnyDeckAvailable()) {
            AlertBox.display(Alert.AlertType.ERROR, "Story Mode", ErrorType.NO_VALID_DECK.getMessage());
            return false;
        }
        return true;

    }
}

package view.Graphic;

import javafx.fxml.Initializable;
import model.Game.GameMode;

import java.net.URL;
import java.util.ResourceBundle;

public class GameModeMenu extends MenuController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void startDeathMatch() {
        startGame(GameMode.DEATH_MATCH);
    }

    public void startKeepTheFalg() {
        startGame(GameMode.KEEP_THE_FLAG);
    }

    public void startCaptureTheFlag() {
        startGame(GameMode.CAPTURE_THE_FLAGS);
    }

    public void exit() {
        if (isCustomGame()) {
            setCustomGame(false);
            changeMenu("singleGameCustomMode.fxml");
        } else {
            changeMenu("startNewGame.fxml");
        }
    }

    public void startGame(GameMode gameMode) {
        if (isCustomGame()) {
            setCustomGame(false);
            getMainController().createCustomGame(gameMode, getDeckName());
        } else {
            //todo multiplayer
        }
    }
}

package view.Graphic;

import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import model.Game.GameMode;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameModeMenu extends MenuController implements Initializable {
    private int numberOfFlags;

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
        ChoiceDialog<Integer> falgs = new ChoiceDialog<>(4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20);
        falgs.setTitle("Flags");
        falgs.setHeaderText("Choose number of flags");
        falgs.setContentText("flags");
        Optional<Integer> result = falgs.showAndWait();
        if (result.isPresent())
            numberOfFlags = result.get();
        else
            return;
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
            getMainController().createCustomGame(gameMode, getDeckName(), numberOfFlags);
            System.out.println("game is started");
        } else {
            System.out.println("xxxxxxxxxxxxx");
            //todo multiplayer
        }
    }
}

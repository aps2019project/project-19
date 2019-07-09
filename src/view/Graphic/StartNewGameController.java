package view.Graphic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Game.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class StartNewGameController extends MenuController implements Initializable {
    @FXML
    private VBox gamesVBox;
    @FXML
    private AnchorPane gamesAnchor;
    @FXML
    private AnchorPane root;
    private boolean chatanchoreshown = true;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showBattles();
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
    public void showBattles(){
        if(chatanchoreshown) {
            root.getChildren().remove(gamesAnchor);
        }
        else {
            root.getChildren().add(gamesAnchor);
            refresh();
        }
        chatanchoreshown = !chatanchoreshown;
    }

    public void refresh() {
        gamesVBox.getChildren().clear();
        for (Game runnigBattle : getMainController().getRunnigBattles()) {
            Button button = new Button(runnigBattle.getPlayer1().getAccount().getUserName()+ " vs "+runnigBattle.getPlayer2().getAccount().getUserName());
            button.setOnMouseClicked(event -> {
                getMainController().observeBattle(runnigBattle.getPlayer1().getAccount().getUserName());
                changeMenu("observeBattle.fxml");
            });
            gamesVBox.getChildren().add(button);
        }
    }

}

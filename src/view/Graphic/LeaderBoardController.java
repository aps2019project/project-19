package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Account;

import java.net.URL;
import java.util.ResourceBundle;

public class LeaderBoardController extends MenuController implements Initializable {
    @FXML
    private JFXMasonryPane leaderBoardPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void getLeaderBoard() {
        putLeaderBoard(getMainController().showLeaderBoard());
    }
    public void exit() {
        changeMenu("MainMenu.fxml");
    }

    private void putLeaderBoard(String string) {
        String[] strings = string.split("\n");
        for (String s : strings) {
            if (isOnline(s))
                s += "\t\tonline";
            Label label = new Label(s);
            label.getStyleClass().add("leaderBoard");
            leaderBoardPane.getChildren().add(label);
        }
    }

    private boolean isOnline(String string){
        String name = string.split("\t")[3];
        System.out.println(getMainController().getOnlines().size());
        for (Account onlineAccount : getMainController().getOnlines()) {
            if (onlineAccount.getUserName().equals(name))
                return true;
        }
        return false;
    }
}

package view.Graphic;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleGameCustomMode extends MenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void exit() {
        changeMenu("singleGameMenu.fxml");
    }
}

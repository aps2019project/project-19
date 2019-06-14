package view.Graphic;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleGameCustomModeController extends MenuController implements Initializable {
    @FXML
    private JFXMasonryPane heroPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void exit() {
        changeMenu("singleGameMenu.fxml");
    }
    public void putCardsInPane(){
        createCards(heroPane, getMainController().getShop().getHeros());
    }
}

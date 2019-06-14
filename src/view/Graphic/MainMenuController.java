package view.Graphic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends MenuController implements Initializable {

    @FXML
    private ImageView shop;
    @FXML
    private ImageView collection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToShop() {
        changeMenu("shopView.fxml");
    }

    public void exitGame() {
        System.exit(0);
    }

    public void logOut() {
        getMainController().logOut();
        changeMenu("loginView.fxml");
    }

    public void goToCollection() {
        changeMenu("collectionView.fxml");
    }

    public void goToStartNewGame() {
        changeMenu("startNewGame.fxml");
    }

    public void saveAccount() {
        getMainController().save();
        AlertBox.display(Alert.AlertType.INFORMATION, "Save", "Account has been saved successfully");
    }
}

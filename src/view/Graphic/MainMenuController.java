package view.Graphic;

import controller.AccountManagement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Account;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends MenuController implements Initializable {

    @FXML
    private ImageView shop;
    @FXML
    private ImageView collection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Music.stopAllMusics();
        Music.getMainMenuMusicPlayer().play();
    }

    public void goToShop() {
        getMainController().enterMenu("shop");
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

    public void deleteAccount() {
        if (AccountManagement.deleteAccount(getMainController().getLoggedInAccount())) {
            AlertBox.display(Alert.AlertType.INFORMATION, "Account", "Account deleted successfully");
            getMainController().logOut();
            changeMenu("loginView.fxml");
        } else {
            AlertBox.display(Alert.AlertType.ERROR, "Account", "Account has not been saved yet");
        }
    }
}

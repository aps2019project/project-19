package view.Graphic;

import controller.AccountManagement;
import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        AlertBox.display("title", "Account has been saved successfully");
    }
}

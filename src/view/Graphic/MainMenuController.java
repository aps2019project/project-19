package view.Graphic;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuController extends MenuController implements Initializable{

    @FXML
    private ImageView shop;
    @FXML
    private ImageView collection;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void goToShop(){
        changeMenu("shopView.fxml");
    }

    public void goToCollection() {
        changeMenu("collectionView.fxml");
    }
}

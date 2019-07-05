package view.Graphic;

import controller.AccountManagement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Account;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends MenuController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView shop;
    @FXML
    private ImageView collection;
    @FXML
    private AnchorPane chatAnchor;
    @FXML
    private Button showChatRoomBtn;
    @FXML
    private VBox messageBox;
    @FXML
    private TextField messageTextField;
    @FXML
    private Button sendMessageBtn;
    private boolean chatAnchoreShown = true;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Music.stopAllMusics();
        Music.getMainMenuMusicPlayer().play();
        showChatroom();
    }
    public void sendMessage(){
        if(messageTextField.getText().trim().equals(""))
            return;
        System.out.println("seeending");
        getMainController().sendChat(messageTextField.getText().trim());

    }
    public void showMessages(){
        //todo: must add messages to vbox and creat a mechanism for auto updating
        for (String chat : getMainController().getChats()) {
            System.err.println("said "+chat);
        }
    }
    public void showChatroom(){
        if(chatAnchoreShown)
            root.getChildren().remove(chatAnchor);
        else
            root.getChildren().add(chatAnchor);
        chatAnchoreShown = !chatAnchoreShown;
    }
    public void goToShop() {
        getMainController().enterMenu("shop");
        changeMenu("shopView.fxml");
    }

    public void exitGame() {
        getMainController().logOut();
        System.exit(0);
    }

    public void logOut() {
        getMainController().logOut();
        changeMenu("loginView.fxml");
    }

    public void goToCollection() {
        getMainController().enterMenu("collection");
        changeMenu("collectionView.fxml");
    }

    public void goToStartNewGame() {
        getMainController().enterMenu("battle");
        changeMenu("startNewGame.fxml");
    }

    public void saveAccount() {
        getMainController().save();
        AlertBox.display(Alert.AlertType.INFORMATION, "Save", "Account has been saved successfully");
    }

    public void deleteAccount() {
        if (getMainController().removeAccount()) {
            AlertBox.display(Alert.AlertType.INFORMATION, "Account", "Account deleted successfully");
            changeMenu("loginView.fxml");
        } else {
            AlertBox.display(Alert.AlertType.ERROR, "Account", getMainController().getErrorType().getMessage());
        }
    }

}

package view.Graphic;

import controller.AccountManagement;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        getMainController().sendChat(messageTextField.getText().trim());
        messageTextField.setText("");
        showMessages();
    }
    public void showMessages(){
        messageBox.getChildren().clear();
        for (String chat : getMainController().getChats()) {
            Label label = new Label(chat);
            label.getStyleClass().add("chat");
            messageBox.getChildren().add(label);
        }
    }
    public void showChatroom(){
        AnimationTimer requestShowMessage = new AnimationTimer() {
            //every 200 milisecond sends a request to server to show messages
            private long lastTime = 0;
            private double time = 0;
            private long second = 1000000000;
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                }
                if (now > lastTime + second / 5) {
                    lastTime = now;
                    showMessages();
                }
            }
        };
        if(chatAnchoreShown) {
            root.getChildren().remove(chatAnchor);
                requestShowMessage.stop();
        }
        else {
            root.getChildren().add(chatAnchor);
            requestShowMessage.start();
        }
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

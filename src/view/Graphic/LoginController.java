package view.Graphic;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.ErrorType;
import view.Request;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends MenuController implements Initializable {
    @FXML
    private Label signInMessage;
    @FXML
    private Label logInMessage;
    @FXML
    private JFXTextField signInUserName;
    @FXML
    private JFXPasswordField signInPassword;
    @FXML
    private JFXTextField logInUserName;
    @FXML
    private JFXPasswordField logInPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void signUp() {
        if (signInUserName.getText().equals("") || signInPassword.getText().equals(""))
            return;
        if (getMainController().createNewAccount(signInUserName.getText(), signInPassword.getText()))
            signInMessage.setText("Account Created!");
        else
            signInMessage.setText(getMainController().getErrorType().getMessage());
    }

    public void logIn() {
        if (logInUserName.getText().equals("") || logInPassword.getText().equals(""))
            return;
        if (getMainController().login(logInUserName.getText(), logInPassword.getText())) {
            changeMenu("MainMenu.fxml");

        } else
            logInMessage.setText(getMainController().getErrorType().getMessage());


    }
    public void exitGame(){
        changeMenu("BattleView.fxml");

        //        System.exit(0);
    }

}

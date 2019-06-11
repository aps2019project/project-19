package view.Graphic;

import com.jfoenix.controls.JFXTextField;
import com.sun.tracing.dtrace.StabilityLevel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.ErrorType;
import view.Request;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Stage stage;
    private Request request;
    @FXML
    private Label signInMessage;
    @FXML
    private Label logInMessage;
    @FXML
    private JFXTextField signInUserName;
    @FXML
    private JFXTextField signInPassword;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void signUp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                request.setCommand("create account "+signInUserName.getText()+"\n");
                try {
                    Thread.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(request.getErrorType()!=null && request.getErrorType() == ErrorType.USERNAME_TAKEN){
                    Platform.runLater(() -> signInMessage.setText(ErrorType.USERNAME_TAKEN.getMessage()+""));
                    request.setErrorType(null);
                    return;
                }
                request.setCommand(signInPassword.getText()+"\n");
                Platform.runLater(() -> signInMessage.setText("account Creates!"));
            }
        }).start();

    }
    public void logIn(){

    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}

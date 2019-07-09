package view.Graphic;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MultiGameMenu extends MenuController implements Initializable {
    @FXML
    private Label status;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("waiting menu");
        AnimationTimer waitForAbort = new AnimationTimer() {
            private long lastTime = 0;
            private long second = 1000000000;
            private double time = 5;
            @Override
            public void handle(long now) {
                status.setText("game is about to start in "+time+"...");
                if (lastTime == 0) {
                    lastTime = now;
                }
                if(getMainController()==null)
                    return;
                if (now > lastTime + second ) {
                    lastTime = now;
                        time--;
                }
                if(time == 0){
                    if(getMainController().getGame() == null){
                        this.stop();
                        abort();
                        return;
                    }
                    changeMenu("BattleView.fxml");
                    time=5;
                    this.stop();
                }
            }
        };
        AnimationTimer requestShowMessage = new AnimationTimer() {
            //            every 200 milisecond sends a request to server to see if game is started or not
            private long lastTime = 0;
            private double time = 0;
            private long second = 1000000000;

            @Override
            public void handle(long now) {
                if(getMainController()==null)
                    return;
                if (lastTime == 0) {
                    lastTime = now;
                }
                if (now > lastTime + second/2 ) {
                    lastTime = now;
                    if (getMainController().isGameStarted()) {
                        System.out.println("isStarteeeed");
                        waitForAbort.start();
                        this.stop();
                    }
                }
            }
        };

        requestShowMessage.start();

    }
    public void abort(){
        getMainController().abortGame();
        exit();
    }
    public void exit() {
        changeMenu("startNewGame.fxml");
    }
}

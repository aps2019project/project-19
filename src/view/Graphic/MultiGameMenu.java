package view.Graphic;

import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MultiGameMenu extends MenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("waiting menu");
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
                if (now > lastTime + 5*second ) {
                    lastTime = now;
                    if (getMainController().isGameStarted()) {
                        System.out.println("isStarteeeed");
                        changeMenu("BattleView.fxml");
                    }
                }
            }
        };
        requestShowMessage.start();

    }

    public void exit() {
        changeMenu("startNewGame.fxml");
    }
}

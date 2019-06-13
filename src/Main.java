import controller.AccountManagement;
import controller.CardInitializer;
import controller.Controller;
import view.Graphic.Graphic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private Controller controller;

    public static void main(String[] args) throws IOException {
        CardInitializer cardInitializer = CardInitializer.getInstance();
        cardInitializer.createCards();
        Controller controller = Controller.getInstance();
        Graphic.main(args,controller);

    }

    public Controller getController() {
        return controller;
    }
}
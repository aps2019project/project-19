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
        AccountManagement.readAccounts();
//        ServerSocket serverSocket = new ServerSocket(8888);
//        Socket client = serverSocket.accept();

        Controller controller = Controller.getInstance();
        new Thread(() -> Graphic.main(args, controller.getRequest())).start();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        controller.run();
    }

    public Controller getController() {
        return controller;
    }
}
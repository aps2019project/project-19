import controller.AccountManagement;
import controller.CardInitializer;
import controller.Controller;
import netWork.Server;
import view.Graphic.Graphic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private Controller controller;

    public static void main(String[] args) throws IOException {
        CardInitializer cardInitializer = CardInitializer.getInstance();
        cardInitializer.createCards();
//        Controller controller = Controller.getInstance();
        AccountManagement.readAccounts();
//        Graphic.main(args,controller);
        Server.main(args);
    }

}
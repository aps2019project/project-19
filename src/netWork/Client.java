package netWork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AbstractClassAdapters;
import controller.Controller;
import model.Account;
import model.Buff.Buff;
import model.Cards.Card;
import model.Cards.SoldierCard;
import model.Shop;
import view.Graphic.Graphic;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
    private static Socket socket;


    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connectToServer();
        //todo:must recive controller from server
        ClientController clientController = new ClientController(socket.getInputStream(), socket.getOutputStream());
        Shop shop = clientController.getShop();
        System.out.println(shop.toString());
        Graphic.main(args, clientController);
    }

    private void connectToServer() throws IOException {
        socket = new Socket("localhost", Server.SERVER_PORT);
//        scanner = new Scanner(System.in);
//        serverFormatter = new PrintStream(socket.getOutputStream(), true);
//        serverScanner = new Scanner(socket.getInputStream());
    }

}

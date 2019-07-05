package netWork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AbstractClassAdapters;
import controller.Controller;
import javafx.application.Platform;
import model.Buff.Buff;
import model.Cards.Card;
import model.Cards.SoldierCard;
import model.Shop;
import view.Graphic.Graphic;
import view.Graphic.MainMenuController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    public static int SERVER_PORT = 8550;
    private static PrintStream outPut;
    private static Scanner input;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.err.println("Server is running in port " + SERVER_PORT + " :");
        while (true) {
            Socket client = serverSocket.accept();
            System.err.println("a client connected");
            Controller controller = new Controller(client.getInputStream(), client.getOutputStream());
            ManageClient manageClient = new ManageClient(client, controller);
            manageClient.start();
        }
    }

}

class ManageClient extends Thread {
    Socket clientSocket;
    Controller controller;

    @Override
    public void run() {
        controller.run();
    }

    public ManageClient(Socket clientSocket, Controller controller) {
        this.clientSocket = clientSocket;
        this.controller = controller;
    }
}
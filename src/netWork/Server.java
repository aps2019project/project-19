package netWork;

import controller.Controller;
import view.Graphic.ServerGraphic;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static int SERVER_PORT = 8551;
    private static PrintStream outPut;
    private static Scanner input;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.err.println("Server is running in port " + SERVER_PORT + " :");
        Thread t = new Thread(() -> ServerGraphic.main(args));
        t.start();
//        ServerGraphic.main(args);
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
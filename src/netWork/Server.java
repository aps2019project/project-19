package netWork;

import controller.Controller;
import view.Graphic.ServerGraphic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static int SERVER_PORT = 8000;
    private static PrintStream outPut;
    private static Scanner input;
    public static void main(String[] args) throws IOException {
        Scanner config = new Scanner(new FileInputStream("src/netWork/config.txt"));
        SERVER_PORT = Integer.parseInt(config.nextLine());
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
package netWork;

import controller.Controller;
import view.Graphic.Graphic;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintStream serverFormatter;
    private Scanner scanner;
    private Scanner serverScanner;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connectToServer();
        new Graphic().main(args,new Controller(System.in));
        while (true){}
        }

    private void connectToServer() throws IOException {
        socket = new Socket("localhost", Server.SERVER_PORT);
        scanner = new Scanner(System.in);
        serverFormatter = new PrintStream(socket.getOutputStream(), true);
        serverScanner = new Scanner(socket.getInputStream());
    }

}

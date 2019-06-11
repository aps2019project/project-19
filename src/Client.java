
import view.Graphic.Graphic;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Formatter serverFormatter;
    private Scanner scanner;
    private Scanner serverScanner;
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Graphic.main(args,);
    }

    private void connectToServer() throws IOException {
        socket = new Socket("localhost", 8888);
        scanner = new Scanner(System.in);
        serverFormatter = new Formatter(socket.getOutputStream());
        serverScanner = new Scanner(socket.getInputStream());

    }

}

package netWork;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static int SERVER_PORT = 8550;
    private static PrintStream outPut;
    private static Scanner inPut;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        while(true){
            Socket client = serverSocket.accept();
            //todo
        }
    }
}

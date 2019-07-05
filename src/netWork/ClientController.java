package netWork;

import com.sun.corba.se.spi.ior.ObjectKey;
import controller.Controller;
import view.ErrorType;

import java.io.*;
import java.util.Scanner;

public class ClientController extends Controller {
    InputStream inputStream;
    OutputStream outputStream;
    PrintStream serverPrinter;
    Scanner serverScanner;
    ObjectInputStream objectInputStream;
    public ClientController(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        serverPrinter = new PrintStream(outputStream);
        serverScanner = new Scanner(inputStream);
        try {
        objectInputStream = new ObjectInputStream(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean createNewAccount(String userName, String password) {
        sendCommandToServer("create account "+userName);
        sendCommandToServer(password);
        return readErrors();
    }
    public boolean login(String userName, String password) {
        sendCommandToServer("login "+userName);
        sendCommandToServer(password);
        return readErrors();
    }

    private void sendCommandToServer(String command){
        serverPrinter.println(command);
        serverPrinter.flush();
    }
    public boolean readErrors() {
        ErrorType errorType = null;
        try {
            errorType = (ErrorType) objectInputStream.readObject();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(errorType.equals(ErrorType.NO_ERROR)) {
            setErrorType(null);
            return true;
        }else {
            setErrorType(errorType);
            return false;
        }

    }

    public void buyFromShop(String name) {
        sendCommandToServer("buy " + name);
    }

    public void sellToShop(int id) {
        sendCommandToServer("sell " + id);
    }

    public void createDeck(String name) {
        sendCommandToServer("create deck " + name);
    }

    public void deleteDeck(String deckName) {
        sendCommandToServer("delete deck " + deckName);
    }

    public void addToDeck(String deckName, int id) {
        sendCommandToServer("add " + id + " to deck " + deckName);
    }

    public void removeFromDeck(String deckName, int id) {
        sendCommandToServer("remove " + id + " from deck " + deckName);
    }

    public void selectMainDeck(String deckName) {
        sendCommandToServer("select deck " + deckName);
    }
}

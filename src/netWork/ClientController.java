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
        if(!readErrors())
            return false;
        sendCommandToServer(password);
        return readErrors();
    }
    public boolean login(String userName, String password) {
        sendCommandToServer("login "+userName);
        if(!readErrors())
            return false;
        sendCommandToServer(password);
        return readErrors();
    }

    private void sendCommandToServer(String command){
        serverPrinter.println(command);
        serverPrinter.flush();
    }
    private boolean readErrors() {
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
}

package netWork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.corba.se.spi.ior.ObjectKey;
import controller.AbstractClassAdapters;
import controller.Controller;
import controller.MenuType;
import model.Account;
import model.Buff.Buff;
import model.Cards.Card;
import model.Cards.SoldierCard;
import model.Item;
import model.Shop;
import view.ErrorType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientController extends Controller {
    private InputStream inputStream;
    private OutputStream outputStream;
    private PrintStream serverPrinter;
    private Scanner serverScanner;
    private Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new AbstractClassAdapters<Buff>())
            .registerTypeAdapter(Card.class, new AbstractClassAdapters<Card>())
            .registerTypeAdapter(SoldierCard.class, new AbstractClassAdapters<SoldierCard>())
            .create();

    public ClientController(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        serverPrinter = new PrintStream(outputStream);
        serverScanner = new Scanner(inputStream);
    }

    public boolean createNewAccount(String userName, String password) {
        sendCommandToServer("create account " + userName);
        if (!readErrors())
            return false;
        sendCommandToServer(password);
        return readErrors();
    }

    public boolean login(String userName, String password) {
        sendCommandToServer("login " + userName);
        if (!readErrors())
            return false;
        sendCommandToServer(password);
        return readErrors();
    }

    public boolean enterMenu(String menuName) {
        sendCommandToServer("enter " + menuName);
        return readErrors();
    }

    @Override
    public Shop getShop() {
        sendCommandToServer("getShop");
        Shop shop = gson.fromJson(serverScanner.nextLine(), Shop.class);
        readErrors();
        return shop;
    }

    @Override
    public Account getLoggedInAccount() {
        sendCommandToServer("getAccount");
        Account account = gson.fromJson(serverScanner.nextLine(), Account.class);
        readErrors();
        return account;
    }

    private void sendCommandToServer(String command) {
        serverPrinter.println(command);
        serverPrinter.flush();
    }


    public boolean readErrors() {
        ErrorType errorType = null;
        errorType = gson.fromJson(serverScanner.nextLine(), ErrorType.class);
        if (errorType.equals(ErrorType.NO_ERROR)) {
            setErrorType(null);
            return true;
        } else {
            setErrorType(errorType);
            return false;
        }

    }

    public boolean buyFromShop(String name) {
        sendCommandToServer("buy " + name);
        return readErrors();
    }

    public boolean sellToShop(int id) {
        sendCommandToServer("sell " + id);
        return readErrors();
    }

    public boolean createDeck(String name) {
        sendCommandToServer("create deck " + name);
        return readErrors();
    }

    public boolean deleteDeck(String deckName) {
        sendCommandToServer("delete deck " + deckName);
        return readErrors();
    }

    public boolean addToDeck(String deckName, int id) {
        sendCommandToServer("add " + id + " to deck " + deckName);
        return readErrors();
    }

    public boolean removeFromDeck(String deckName, int id) {
        sendCommandToServer("remove " + id + " from deck " + deckName);
        return readErrors();
    }

    public boolean selectMainDeck(String deckName) {
        sendCommandToServer("select deck " + deckName);
        return readErrors();
    }

    public ArrayList<Object> searchInCollection(String name) {
        Account loggedInAccount =getLoggedInAccount();
        ArrayList<Object> results = new ArrayList<>();
        for (Item item : loggedInAccount.getCollection().getItems()) {
            if (item.getName().matches(name + "[\\w ]*")) results.add(item);
        }
        for (Card card : loggedInAccount.getCollection().getCards()) {
            if (card.getName().matches(name + "[\\w ]*")) results.add(card);
        }
        return results;
    }

    public ArrayList<Object> searchInShop(String name) {
        Shop shop = getShop();
        ArrayList<Object> results = new ArrayList<>();
        for (Card card : shop.getCards()) {
            if (card.getName().matches(name + "[\\w ]*"))
                results.add(card);
        }
        for (Item item : shop.getItems()) {
            if (item.getName().matches(name+ "[\\w ]*"))
                results.add(item);
        }
//        sendCommandToServer("search " + name);
//        return gson.fromJson(serverScanner.nextLine(), new TypeToken<List<Object>>() {}.getType());
        return results;
    }
}

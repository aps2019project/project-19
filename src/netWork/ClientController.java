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
import model.Cards.*;
import model.Game.Game;
import model.Game.GameMode;
import model.Player;
import model.Item;
import model.Shop;
import view.ErrorType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
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

    public void enterMenu(String menuName) {
        sendCommandToServer("enter " + menuName);
        readErrors();
    }

    @Override
    public ArrayList<String> getChats() {
        sendCommandToServer("getchats");
        ArrayList<String> chats = gson.fromJson(serverScanner.nextLine(), new TypeToken<ArrayList<String>>() {
        }.getType());
        readErrors();
        return chats;
    }

    public void sendChat(String message) {
        Account account = getLoggedInAccount();
        sendCommandToServer("recivechat");
        sendCommandToServer( account.getUserName()+" : "+message);
        readErrors();
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

    @Override
    public void save() {
        sendCommandToServer("save");
        readErrors();
    }

    @Override
    public void exitMenu() {
        sendCommandToServer("exit");
        readErrors();
    }

    @Override
    public void logOut() {
        sendCommandToServer("logout");
        readErrors();
    }

    @Override
    public Game getGame() {
        sendCommandToServer("getGame");
        String x = serverScanner.nextLine();
        System.out.println("x ix : " + x);
        Game game = gson.fromJson(x, Game.class);
        readErrors();
        return game;
    }

    @Override
    public Player getActivePlayer() {
        sendCommandToServer("getActivePlayer");
        Player player = gson.fromJson(serverScanner.nextLine(), Player.class);
        readErrors();
        return player;
    }

    @Override
    public Player getDeactivePlayer() {
        sendCommandToServer("getDeActivePlayer");
        Player player = gson.fromJson(serverScanner.nextLine(), Player.class);
        readErrors();
        return player;
    }

    public void createCustomCard(Card card) {
        if (card instanceof Minion)
            sendCommandToServer("create custom card minion " + gson.toJson(card));
        else if (card instanceof Hero)
            sendCommandToServer("create custom card hero " + gson.toJson(card));
        else if (card instanceof SpellCard)
            sendCommandToServer("create custom card spell" + gson.toJson(card));
        readErrors();
    }

    public void selectStoryLevel(int storyLevel) {
        sendCommandToServer("enter level " + storyLevel);
        readErrors();
    }

    public void chooseHero(String heroName) {
        sendCommandToServer("take " + heroName);
        readErrors();
    }

    public void createCustomGame(GameMode gameMode, String deckName, int numOfFlags) {
        sendCommandToServer("start game " + deckName + " " + gameMode.toString() + " " + numOfFlags);
        readErrors();
    }

    public boolean removeAccount() {
        sendCommandToServer("delete account");
        return readErrors();
    }

    private void sendCommandToServer(String command) {
        serverPrinter.println(command);
        serverPrinter.flush();
    }


    private boolean readErrors() {
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
        Account loggedInAccount = getLoggedInAccount();
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
            if (item.getName().matches(name + "[\\w ]*"))
                results.add(item);
        }
//        sendCommandToServer("search " + name);
//        return gson.fromJson(serverScanner.nextLine(), new TypeToken<List<Object>>() {}.getType());
        return results;
    }

}

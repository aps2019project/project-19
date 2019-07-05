package netWork;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.corba.se.spi.ior.ObjectKey;
import controller.AbstractClassAdapters;
import controller.Controller;
import model.Account;
import model.Buff.Buff;
import model.Cards.Card;
import model.Cards.SoldierCard;
import model.Game.Game;
import model.Game.GameMode;
import model.Player;
import model.Shop;
import view.ErrorType;

import java.io.*;
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
        //todo: must get account and send userName with message
        sendCommandToServer("recivechat");
        sendCommandToServer(message);
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
        Game game = gson.fromJson(serverScanner.nextLine(), Game.class);
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

    public void selectStoryLevel(int storyLevel) {
        sendCommandToServer("enter level " + storyLevel);
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

}

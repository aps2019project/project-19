package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import model.Buff.Buff;
import model.Buff.DispellBuff;
import model.Buff.StunBuff;
import model.Cards.*;
import model.Target.Type;
import netWork.Server;
import view.*;
import model.Game.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class Controller {


    public Controller() {
    }

    public Controller(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        request = new Request(inputStream);
        view = new View(outputStream);
        printStream = new PrintStream(outputStream, true);
    }

    private static ArrayList<Account> onlineAccounts = new ArrayList<>();
    private static ArrayList<String> chats = new ArrayList<>();
    private static ArrayList<Account> deathMatchWaiters = new ArrayList<>();
    private static ArrayList<Account> keepTheFlagWaiters = new ArrayList<>();
    private static ArrayList<Account> captureTheFlagWaiters = new ArrayList<>();

    private PrintStream printStream;
    //private Scanner scanner;
    private Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new AbstractClassAdapters<Buff>())
            .registerTypeAdapter(Card.class, new AbstractClassAdapters<Card>())
            .registerTypeAdapter(SoldierCard.class, new AbstractClassAdapters<SoldierCard>())
            .enableComplexMapKeySerialization().create();
    private OutputStream outputStream;
    private InputStream inputStream;
    private Shop shop = Shop.getInstance();
    private MenuType menuType = MenuType.ACCOUNT;
    private Request request;
    private Account loggedInAccount;
    private Account opponentAccount;
    private Game game;
    private Player activePlayer;
    private Player deactivePlayer;
    private ErrorType errorType = null;
    private View view;
    private Deck aiDeck;
    private Ai ai;

    public static ArrayList<Account> getOnlineAccounts() {
        return onlineAccounts;
    }

    public static void addOnlineAccount(Account account) {
        onlineAccounts.add(account);
    }

    public static void removeOnlineAccount(Account account) {
        onlineAccounts.remove(account);
    }

    public ArrayList<String> getChats() {
        printStream.println(gson.toJson(chats));
        printStream.flush();
        return chats;
    }

    public void reciveChat() {
        request.getNewCommand();
        chats.add(request.getCommand());
    }

    public static boolean userIsOnline(String userName) {
        for (Account onlineAccount : onlineAccounts) {
            if (onlineAccount.getUserName().equals(userName))
                return true;
        }
        return false;
    }

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public Shop getShop() {
        printStream.println(gson.toJson(shop));
        printStream.flush();
        return shop;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }


    public void run() {
        do {
            System.out.println("Menu: " + menuType + " ");
//                request.resetProperties();
            if (ai != null && !game.isTurnOfPlayerOne()) {
                request.setCommand(ai.sendRandomRequest(game.getPlayer1()));
                System.err.println("ai requested: " + request.getCommand());
            } else request.getNewCommand();
            request.setRequestType(menuType);
            request.parseCommand();
            setActivePlayer();
            switch (request.getRequestType()) {
//                    case ERROR:
//                        errorType = ErrorType.INVALID_COMMAND;
//                        break;
                /////////////////////////////default//////////////////////////////
                case GET_SHOP:
                    getShop();
                    break;
                case GET_ACCOUNT:
                    getLoggedInAccount();
                    break;
                case GET_OPPONENT_ACCOUNT:
                    getOpponentAccount();
                    break;
                case GET_GAME:
                    getGame();
                    break;
                case GET_ACTIVE_PLAYER:
                    getActivePlayer();
                    break;
                case GET_DEACTIVE_PLAYER:
                    getDeactivePlayer();
                    break;
                case GET_ONLINE_ACCOUNTS:
                    getOnlines();
                    break;

                ///////////////////////////// MAIN_MENU  && ACCOUNT ///////////////////////
                case RECIVE_CHAT:
                    reciveChat();
                    break;
                case GET_CHATS:
                    getChats();
                    break;
                case SHOW_LEADER_BOARD:
                    showLeaderBoard();
                    break;
                case CREATE_ACCOUNT:
                    createNewAccount();
                    break;
                case LOGIN:
                    login();
                    break;
                case LOGOUT:
                    logOut();
                    break;
                case SAVE:
                    save();
                    break;
                case DELETE_ACCOUNT:
                    deleteAccount();
                    break;
                ///////////////////////////// SHOP  ///////////////////////////
                case CUSTOM_CARD:
                    createCustomCard();
                    break;
                case SEARCH_IN_SHOP:
//                        searchInShop();
                    break;
                case SEARCH_IN_COLLECTION:
//                        searchInCollection();
                    break;
                case BUY_FROM_SHOP:
                    buyFromShop();
                    break;
                case SELL_TO_SHOP:
                    sellToShop();
                    break;
                case SHOW_SHOP:
                    showShop();
                    break;
                //////////////////////////// COLLECTION /////////////////////////
                case SHOW_COLLECTION_ITEMS:
                    showCollectionItems();
                    break;
                case SAVE_COLLECTION:
                    saveCollection();
                    // todo: saveCollection
                    break;
                case CREATE_DECK:
                    createDeck();
                    break;
                case DELETE_DECK:
                    deleteDeck();
                    break;

                case SHOW_DECK:
                    showDeck();
                    break;
                case SHOW_ALL_DECKS:
                    showAllDecks();
                    break;
                case ADD_TO_DECK:
                    addToDeck();
                    // todo: test for items
                    break;
                        /*
                    case VALIDATE_DECK:
                        validateDeck();
                        break;*/
                case SELECT_MAIN_DECK:
                    selectMainDeck();
                    break;
                case REMOVE_FROM_DECK:
                    removeFromDeck();
                    //todo: test for items
                    break;
                case EXPORT_DECK:
                    exportDeck();
                    break;
                case IMPORT_DECK:
                    importDeck();
                    break;
                ///////////////////////////////// CREATING GAME ///////////////////////
                case SHOW_ALL_PLAYERS:
                    showAllPlayer();
                    break;
                case SELECT_OPPONENT_USER:
//                    selectOpponent();
                    break;
                case IS_GAME_STARTED:
                    isGameStarted();
                    break;
                case SELECT_MULTI_PLAYER_MODE:
                    selectMultiPlayerMode();
                    // TODO: 2019-04- test
                    break;
                case SELECT_STORY_LEVEL:
                    selectStoryLevel();
                    break;
                case CHOOSE_HERO:
                    chooseHero();
                    break;
                case SELECT_CUSTOM_GAME_GAMEMODE:
                    createCustomGame();
                    break;
                ///////////////////////////////// BATTLE  ////////////////////////
                    case INSERT_CARD:
                        insertCard();
                        break;
                    case SHOW_GAME_INFO:
                        showGameInfo();
                        break;
                    case SHOW_MY_MINIONS:
                        showMinions();
                        break;
                    case SHOW_OPPONENT_MINIONS:
                        if (game.isTurnOfPlayerOne())
                            activePlayer = game.getPlayer2();
                        else activePlayer = game.getPlayer1();
                        showMinions();
                        break;
                    case SHOW_CARD_INFO_IN_BATTLE:
                        showCardInfoInBattle();
                        break;
                    case SELECT_CARD_OR_COLLECTABLE:
//                        selectCardOrItem(activePlayer);
                        break;
                    case MOVE_CARD:
//                        moveCard();
                        break;
                    case ATTACK:
//                        attack();
                        break;
                    case COMBO_ATTACK:
//                        comboAttack();
                        break;
                    case SHOW_HAND:
                        showHand();
                        break;
                    case END_TURN:
                        endTurn();
                        break;
                    case SHOW_NEXT_CARD:
                        showNextCard();
                        break;
                    case SHOW_COLLECATBLE_INFO:
                        showCollectableInfo();
                        break;
                    case USE_COLLECTABLE:
                        useCollectable();
                        break;
                    case SHOW_GATHERED_COLLECTABLES:
                        //todo what to do????
                        showGatheredCollectables();
                        break;
                    case SHOW_CARD_INFO_IN_GRAVEYARD:
                        showCardInfoInGraveYard();
                        break;
                    case SHOW_All_CARDS_IN_GRAVEYARD:
                        showAllCardsInGraveYard();
                        break;
                    case USE_SPECIAL_POWER:
//                        useSpecialPower();
                        break;
                    case END_GAME:
                        endGame();
                        break;

                /////////////////////////////////            //////////////////////
                case EXIT_MENU:
                    exitMenu();
                    break;
                case ENTER_MENU:
                    enterMenu();
                    break;
                case HELP:
                    view.showHelp(menuType);
                    break;
            }
            view.printError(errorType);
//                request.setErrorType(errorType);
            errorType = null;
        } while (true);
    }



    private void isGameStarted() {
        if (game == null)
            errorType = ErrorType.GAME_IS_NOT_STARTED;
    }

    public void createCustomCard() {
        Card card = request.getCustomCard();
        shop.getCards().add(card);
        CardInitializer.addCustomCardToFile(card);

    }

    public void deleteAccount() {
        if (AccountManagement.deleteAccount(loggedInAccount)) {
            logOut();
        } else {
            errorType = ErrorType.ACCOUNT_NOT_SAVED;
        }
    }

    public boolean exportDeck() {
        Deck deck = loggedInAccount.getCollection().getDecks().get(request.getDeckName());
        if (!deck.deckIsValid()) {
            errorType = ErrorType.INVALID_DECK;
            return false;
        }
        boolean isDone = DeckManagement.exportDeck(deck, request.getDeckFileName());
        if (isDone) {
            return true;
        } else {
            errorType = ErrorType.DUPLICATE_FILE_DECK_NAME;
            return false;
        }
    }

    public Account getOpponentAccount() {
        printStream.println(gson.toJson(opponentAccount));
        printStream.flush();
        return opponentAccount;
    }

    public boolean importDeck() {
        Deck deck = DeckManagement.importDeck(request.getDeckFileName());
        if (deck == null) {
            errorType = ErrorType.INVALID_DECK_FILE_NAME;
            return false;
        } else {
            if (loggedInAccount.getCollection().getDecks().containsKey(deck.getName())) {
                errorType = ErrorType.SAME_DECK;
                return false;
            } else {
                for (Card card : deck.getCards().values()) {
                    if (!loggedInAccount.getCollection().existsInCollectionCards(card.getName())) {
                        errorType = ErrorType.YOU_DONT_HAVE_THE_CARD;
                        return false;
                    }
                }
                loggedInAccount.getCollection().getDecks().put(deck.getName(), deck);
                return true;
            }
        }
    }

    public Account getLoggedInAccount() {
        return getLoggedInAccount(true);
    }

    public Account getLoggedInAccount(boolean send) {
        if (!send)
            return loggedInAccount;
        printStream.println(gson.toJson(loggedInAccount));
        printStream.flush();
        return loggedInAccount;
    }

    public void createCustomGame() {
        if (aiDeck == null) {
            System.out.println("ai deck is null");
            errorType = ErrorType.OPPONENT_HERO_NOT_SELECTED;
        } else {
            if (loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName())) {
                Player player1 = new Player(loggedInAccount,
                        loggedInAccount.getCollection().getDecks().get(request.getDeckName()));
                ai = new Ai(new Player(new Account("ai", "ai"), aiDeck));
                game = new Game(player1, ai.getPlayer());
                game.setGameMode(request.getGameMode());
                if (request.getGameMode().equals(GameMode.CAPTURE_THE_FLAGS)) {
                    game.setNumOfFlags(request.getNumOfFlags());
                }
                initNewGame(request.getGameMode());
                game.setPrize(1000);
            } else {
                errorType = ErrorType.DECK_NOT_EXISTS;
            }
        }
    }

    public void chooseHero() {
        Card newHero = shop.findCard(request.getCardName());
        if (newHero == null || !(newHero instanceof Hero))
            errorType = ErrorType.WRONG_HERO_NAME;
        else {
            aiDeck = new Deck(loggedInAccount.getCollection().getMainDeck());
            Card postHero = aiDeck.getHero();
            aiDeck.getCards().remove(postHero.getCardId());
            aiDeck.getCards().put(postHero.getCardId(), newHero);
        }
    }

    public void selectStoryLevel() {
        Player player1 = new Player(loggedInAccount, loggedInAccount.getCollection().getMainDeck());
        if (request.getStoryLevel() > 4 || request.getStoryLevel() < 1) {
            errorType = ErrorType.INVALID_LEVEL;
            return;
        }
        game = new Game();
        game.initStoryDecks(shop);
        aiDeck = game.getStoryLevelDecks().get(request.getStoryLevel() - 1);
        ai = new Ai(new Player(new Account("ai", "ai"), aiDeck));
        game = new Game(player1, ai.getPlayer());
        opponentAccount = ai.getPlayer().getAccount();
        if (request.getStoryLevel() == 1)
            game.setGameMode(GameMode.DEATH_MATCH);
        if (request.getStoryLevel() == 2)
            game.setGameMode(GameMode.CAPTURE_THE_FLAGS);
        if (request.getStoryLevel() == 3)
            game.setGameMode(GameMode.KEEP_THE_FLAG);
        initNewGame(game.getGameMode());
        game.setStoryPrize(request.getStoryLevel());
    }

    private void selectOpponent(String userName) {
//        if (!Account.userNameIsValid(userName) ||
//                loggedInAccount.getUserName().equals(request.getUserName())) {
//            errorType = ErrorType.INVALID_OPPONENT;
//            return;
//        }
        opponentAccount = Account.getAccounts().get(userName);
//        if (opponentAccount.getCollection().getMainDeck() == null ||
//                !opponentAccount.getCollection().getMainDeck().deckIsValid()) {
//            errorType = ErrorType.INVALID_OPPONENT_DECK;
//            return;
//        }
        Player player1 = new Player(loggedInAccount, new Deck(loggedInAccount.getCollection().getMainDeck()));
        Player player2 = new Player(opponentAccount, new Deck(opponentAccount.getCollection().getMainDeck()));
        game = new Game(player1, player2);
        Controller opponentController = Server.getClientController(opponentAccount.getUserName());
        opponentController.setGame(game);
        opponentController.setOpponentAccount(loggedInAccount);
        opponentController.setMenuType(MenuType.BATTLE);
        view.show(opponentAccount.getUserName() + "selected as your opponent");
    }

    private void selectMultiPlayerMode() {
        switch (request.getGameMode()) {
            case DEATH_MATCH:
                if (!deathMatchWaiters.isEmpty()) {
                    selectOpponent(deathMatchWaiters.get(0).getUserName());
                } else {
                    deathMatchWaiters.add(loggedInAccount);
                    errorType = ErrorType.GAME_IS_NOT_STARTED;
                    return;
                }
                break;
            case KEEP_THE_FLAG:
                if (!keepTheFlagWaiters.isEmpty())
                    selectOpponent(keepTheFlagWaiters.get(0).getUserName());
                else {
                    errorType = ErrorType.GAME_IS_NOT_STARTED;
                    keepTheFlagWaiters.add(loggedInAccount);
                    return;
                }
                break;
            case CAPTURE_THE_FLAGS:
                if (!captureTheFlagWaiters.isEmpty()) {
                    selectOpponent(captureTheFlagWaiters.get(0).getUserName());
                } else {
                    errorType = ErrorType.GAME_IS_NOT_STARTED;
                    captureTheFlagWaiters.add(loggedInAccount);
                    return;
                }
                break;

        }
        game.setGameMode(request.getGameMode());
        if (request.getGameMode() == GameMode.CAPTURE_THE_FLAGS)
            game.setNumOfFlags(request.getNumOfFlags());
        System.err.println("game mode seted");
        game.setPrize(1000);
        initNewGame(request.getGameMode());
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }

    public void initNewGame(GameMode gameMode) {
        switch (gameMode) {
            case DEATH_MATCH:
                break;
            case KEEP_THE_FLAG:
                game.getCell(5, 3).setFlagNumber(1);
                break;
            case CAPTURE_THE_FLAGS:
                game.initFlags();
                break;
        }
        game.setDate(new Date());
        game.setHeroes(game.getPlayer1(),
                game.getCell(1, 3)).setInBattleCardId(game.getPlayer1().getAccount().getUserName());
        game.setHeroes(game.getPlayer2(),
                game.getCell(9, 3)).setInBattleCardId(game.getPlayer2().getAccount().getUserName());
        game.getPlayer1().setFirstHand().resetCardsAttackAndMoveAbility();
        game.getPlayer2().setFirstHand().resetCardsAttackAndMoveAbility();
        useUsable(game.getPlayer1(), WhenToUse.ON_GAME_START);
        useUsable(game.getPlayer2(), WhenToUse.ON_GAME_START);
        menuType = MenuType.BATTLE;
        System.err.println("game started");
    }

    public boolean createNewAccount() {
        if (Account.userNameIsValid(request.getUserName())) {
            errorType = ErrorType.USERNAME_TAKEN;
            return false;
        }
        view.printError(errorType);
        request.getNewCommand();
        Account newAccount = new Account(request.getUserName(), request.getCommand());
        Account.addAccount(newAccount);
        System.out.println("account created");
        return true;
    }


    public boolean login() {
        if (!Account.userNameIsValid(request.getUserName())) {
            errorType = ErrorType.INVALID_USERNAME;
            return false;
        }
        if (userIsOnline(request.getUserName())) {
            errorType = ErrorType.USER_IS_ONLINE;
            return false;
        }
        view.printError(errorType);
        request.getNewCommand();
        if (!Account.passwordIsValid(request.getCommand(), request.getUserName())) {
            errorType = ErrorType.INVALID_PASSWORD;
            return false;
        }
        loggedInAccount = Account.getAccounts().get(request.getUserName());
        addOnlineAccount(loggedInAccount);
        System.out.println(onlineAccounts.size());
        menuType = MenuType.MAINMENU;
        System.out.println("logged into " + request.getUserName());
        return true;
    }

    public String showLeaderBoard() {
        printStream.println(gson.toJson(Account.printAccounts()));
        printStream.flush();
//        view.show(Account.printAccounts());
        return Account.printAccounts();
    }

    public void save() {
        AccountManagement.saveAccount(loggedInAccount);
    }

    public void logOut() {
        menuType = MenuType.ACCOUNT;
        removeOnlineAccount(loggedInAccount);
        System.out.println("logged out from " + loggedInAccount.getUserName());
        loggedInAccount = null;
    }

    public void help() {
    }

    public void exitMenu() {
        switch (menuType) {
            case MAINMENU:
                System.exit(0);
                break;
            case ACCOUNT:
                System.exit(0);
                break;
            case SINGLE_GAME_CUSTOM_MODE:
                menuType = MenuType.SINGLE_GAME_MENU;
                break;
            case SINGLE_GAME_STORY_MODE:
                menuType = MenuType.SINGLE_GAME_MENU;
                break;
            case SINGLE_GAME_MENU:
                menuType = MenuType.START_NEW_GAME;
                break;
            case MULTI_GAME_MENU:
                menuType = MenuType.START_NEW_GAME;
                break;
            case GRAVEYARD:
                menuType = MenuType.BATTLE;
                break;
            case BATTLE:
                break;
            case CUSTOM_CARD_MENU:
                menuType = MenuType.SHOP;
                break;
            default:
                menuType = MenuType.MAINMENU;
                break;
        }
    }

    public void enterMenu() {
//        switch (menuType) {
//            case MAINMENU:
//                if (request.getEnteringMenu() == MenuType.SHOP ||
//                        request.getEnteringMenu() == MenuType.COLLECTION) {
//                    menuType = request.getEnteringMenu();
//                    return;
//                }
        if (request.getEnteringMenu() == MenuType.START_NEW_GAME) {
            if (loggedInAccount.getCollection().getMainDeck() == null ||
                    !loggedInAccount.getCollection().getMainDeck().deckIsValid()) {
                errorType = ErrorType.INVALID_DECK;
                return;
            }
            menuType = request.getEnteringMenu();
            return;
        }
        menuType = request.getEnteringMenu();

//
//                break;
//            case START_NEW_GAME:
//                if (request.getEnteringMenu() == MenuType.SINGLE_GAME_MENU ||
//                        request.getEnteringMenu() == MenuType.MULTI_GAME_MENU) {
//                    menuType = request.getEnteringMenu();
//                    return;
//                }
//                break;
//            case SINGLE_GAME_MENU:
//                if (request.getEnteringMenu() == MenuType.SINGLE_GAME_CUSTOM_MODE ||
//                        request.getEnteringMenu() == MenuType.SINGLE_GAME_STORY_MODE) {
//                    menuType = request.getEnteringMenu();
//                    if (menuType.equals(MenuType.SINGLE_GAME_STORY_MODE))
//                        view.showStoryModes();
//                    else if (menuType.equals(MenuType.SINGLE_GAME_CUSTOM_MODE)) {
//                        view.showHeros();
//                    }
//                    return;
//                }
//                break;
//            case BATTLE:
//                if (request.getEnteringMenu() == MenuType.GRAVEYARD)
//                    menuType = MenuType.GRAVEYARD;
//                break;
//        }
//        errorType = ErrorType.INVALID_COMMAND;
    }

    public void showCollectionItems() {
        int number = 1;
        view.show("Heroes :");
        for (Card card : loggedInAccount.getCollection().getCards()) {
            if (card instanceof Hero) {
                view.show("\t" + number + " : " + card.toString() + " - Sell cost : " + card.getPrice());
                number++;
            }
        }
        number = 1;
        view.show("Items :");
        for (Item item : loggedInAccount.getCollection().getItems()) {
            view.show("\t" + number + " : " + item.toString() + " - Sell cost : " + item.getPrice());
            number++;
        }
        number = 1;
        view.show("Cards :");
        for (Card card : loggedInAccount.getCollection().getCards()) {
            if (!(card instanceof Hero)) {
                view.show("\t" + number + " : " + card.toString() + " - Sell cost : " + card.getPrice());
                number++;
            }
        }
    }

    public ArrayList<Object> searchInCollection() {
        ArrayList<Object> results = new ArrayList<>();
        for (Item item : loggedInAccount.getCollection().getItems()) {
            if (item.getName().matches(request.getSearchingName() + "[\\w ]*")) results.add(item);
        }
        for (Card card : loggedInAccount.getCollection().getCards()) {
            if (card.getName().matches(request.getSearchingName() + "[\\w ]*")) results.add(card);
        }
        printStream.println(gson.toJson(results));
        printStream.flush();
//        if (results.size() == 0) {
//            errorType = ErrorType.NOT_FOUND;
//        }
        return results;
    }

    public void saveCollection() {
        view.show("saved!");
    }

    public void createDeck() {
        if (loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName())) {
            errorType = ErrorType.DECK_EXISTS;
            return;
        }
        Deck deck = new Deck(request.getDeckName());
        loggedInAccount.getCollection().getDecks().put(request.getDeckName(), deck);
        view.show(request.getDeckName() + " created");
    }

    public void deleteDeck() {
        if (loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName())) {
            loggedInAccount.getCollection().getDecks().remove(request.getDeckName());
            view.show(request.getDeckName() + " deleted");
            return;
        }
        errorType = ErrorType.DECK_NOT_EXISTS;
    }

    public void addToDeck() {
        if (!loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName()))
            errorType = ErrorType.DECK_NOT_EXISTS;
        else if (!loggedInAccount.getCollection().existsInCollection(request.getCardOrItemID()))
            errorType = ErrorType.NOT_FOUND;
        else if (loggedInAccount.getCollection().existsInDeck(request.getDeckName(), request.getCardOrItemID()))
            errorType = ErrorType.EXISTS_IN_DECK;
        else if (loggedInAccount.getCollection().deckIsFull(request.getDeckName(), request.getCardOrItemID()))
            errorType = ErrorType.DECK_IS_FULL;
        else if (loggedInAccount.getCollection().isHero(request.getCardOrItemID()) &&
                loggedInAccount.getCollection().getDecks().get(request.getDeckName()).deckHasHero())
            errorType = ErrorType.DECK_HAS_HERO;
        else {
            loggedInAccount.getCollection().addToDeck(request.getCardOrItemID(), request.getDeckName());
            view.show("card " + request.getCardOrItemID() + " added to deck " + request.getDeckName());
        }
    }

    public void removeFromDeck() {
        if (!loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName()))
            errorType = ErrorType.DECK_NOT_EXISTS;
        else if (!loggedInAccount.getCollection().existsInDeck(request.getDeckName(), request.getCardOrItemID()))
            errorType = ErrorType.NOT_FOUND;
        else {
            loggedInAccount.getCollection().removeFromDeck(request.getDeckName(), request.getCardOrItemID());
            view.show("card " + request.getCardOrItemID() + " removed from deck " + request.getDeckName());
        }
    }

    public void validateDeck() {
        if (!loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName()))
            errorType = ErrorType.DECK_NOT_EXISTS;
        else if (loggedInAccount.getCollection().getDecks().get(request.getDeckName()).deckIsValid())
            view.show("deck is valid");
        else errorType = ErrorType.INVALID_DECK;
    }

    public void selectMainDeck() {
        if (!loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName()))
            errorType = ErrorType.DECK_NOT_EXISTS;
        else if (loggedInAccount.getCollection().getDecks().get(request.getDeckName()).deckIsValid()) {
            loggedInAccount.getCollection().setMainDeck(request.getDeckName());
            view.show("deck has selected");
        } else errorType = ErrorType.INVALID_DECK;
    }

    public void showAllDecks() {
        int deckNumber = 1;
        for (Deck deck : loggedInAccount.getCollection().getDecks().values()) {
            view.show(deckNumber + " : " + deck.toString());
            deckNumber++;
        }
    }

    public void showDeck() {
        if (!loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName()))
            errorType = ErrorType.DECK_NOT_EXISTS;
        else {
            Deck deck = loggedInAccount.getCollection().getDecks().get(request.getDeckName());
            view.show(deck.toString());
        }
    }

    public ArrayList searchInShop() {
        ArrayList<Object> results = new ArrayList<>();
        for (Card card : shop.getCards()) {
            if (card.getName().matches(request.getSearchingName() + "[\\w ]*"))
                results.add(card);
        }
        for (Item item : shop.getItems()) {
            if (item.getName().matches(request.getSearchingName() + "[\\w ]*"))
                results.add(item);
        }
        printStream.println(gson.toJson(results));
        printStream.flush();
        return results;
        //todo:all arraylists must set to hashmap
        //duplicated code with existsInShop in Shop class
//        errorType = ErrorType.NOT_FOUND;
    }

    public void buyFromShop() {
        if (!shop.existsInShop(request.getProductName()))
            errorType = ErrorType.NOT_FOUND;
        else if (!shop.priceIsEnough(request.getProductName(), loggedInAccount))
            errorType = ErrorType.NOT_ENOUGH_MONEY;
        else if (!shop.validateNumberOfItems(request.getProductName(), loggedInAccount))
            errorType = ErrorType.FULL_ITEMS;
        else if (shop.isFinished(request.getProductName()))
            errorType = ErrorType.FINISH;
        else {
            shop.buy(request.getProductName(), loggedInAccount);
            view.show("Successful purchase");
        }
    }

    public void sellToShop() {
        if (!loggedInAccount.getCollection().existsInCollection(request.getProductId()))
            errorType = ErrorType.NOT_FOUND;
        else {
            shop.sell(request.getProductId(), loggedInAccount);
            view.show("Successful sale");
        }
    }

    public void showShop() {
        int number = 1;
        view.show("Heroes :");
        for (Card card : shop.getCards()) {
            if (card instanceof Hero) {
                view.show("\t" + number + " : " + card.toString() + " - Buy cost : " + card.getPrice());
                number++;
            }
        }
        number = 1;
        view.show("Items :");
        for (Item item : shop.getItems()) {
            view.show("\t" + number + " : " + item.toString() + " - Buy cost : " + item.getPrice());
            number++;
        }
        number = 1;
        view.show("Cards :");
        for (Card card : shop.getCards()) {
            if (!(card instanceof Hero)) {
                view.show("\t" + number + " : " + card.toString() + "- Buy cost : " + card.getPrice());
                number++;
            }
        }
    }

    public void showAllPlayer() {
        for (String account : Account.getAccounts().keySet()) {
            view.show(account);
        }
    }

    public void showGameInfo() {
        if (game.getGameMode().equals(GameMode.DEATH_MATCH))
            view.show(game.toStringDeathMatchMode());
        else if (game.getGameMode().equals(GameMode.KEEP_THE_FLAG))
            view.show(game.toStringKeepFlag());
        else view.show(game.toStringCaptureFlags());

    }

    public void showMinions() {
        for (Card card : activePlayer.getInBattleCards().keySet()) {
            if (card instanceof SoldierCard) {
                Cell cell = activePlayer.getInBattleCards().get(card);
                view.show(((SoldierCard) card).toBattleFormat(cell.getXCoordinate(), cell.getYCoordinate()));
            }
        }
    }

    public void showOpponentMinions() {
    }

    public void showCardInfoInBattle() {
        for (Card card : activePlayer.getInBattleCards().keySet()) {
            if (card.getInBattleCardId().equals(request.getInBattleCardId())) {
                view.show(card.toInfoString());
                return;
            }
        }
        errorType = ErrorType.INVALID_CARD_ID;
    }

    public boolean selectCardOrItem(Player player, String inBattleCardId, int itemId) {
        if (player.containsCardInBattle(inBattleCardId)) {
            Card card = activePlayer.getInBattleCard(inBattleCardId);
            activePlayer.setSelectedCard(card);
            System.err.println(card.getName() + " " + card.getCardId() + " selected");
            return true;
        }
        if (player.getItems().containsKey(itemId)) {
            Item item = activePlayer.getItems().get(itemId);
            activePlayer.setSelectedItem(item);
            System.err.println(item.getName() + " selected");
            return true;
        }
        errorType = ErrorType.INVALID_CARD_ID;
        return false;
    }

    public boolean moveCard(int x, int y) {
        if (!activePlayer.isAnyCardSelected()) {
            errorType = ErrorType.CARD_NOT_SELECTED;
            return false;
        }
        SoldierCard card = (SoldierCard) activePlayer.getSelectedCard();
        for (Buff buff : card.getBuffs()) {
            if (buff instanceof StunBuff) {
                errorType = ErrorType.CARD_IS_STUNNED;
                return false;
            }
        }
        if (card.isMovedThisTurn()) {
            errorType = ErrorType.CAN_NOT_MOVE_AGAIN;
            return false;
        }
        if (!game.coordinateIsValid(x, y)) {
            errorType = ErrorType.INVALID_TARGET;
            return false;
        }
        Cell currentCell = activePlayer.getInBattleCards().get(card);
        Cell targetCell = game.getCell(x, y);
        if (targetCell.getCard() != null) {
            errorType = ErrorType.INVALID_TARGET;
            return false;
        } else if (currentCell.getManhattanDistance(targetCell) > 2 || game.pathIsBlocked(currentCell, targetCell)) {
            errorType = ErrorType.INVALID_TARGET;
            return false;
        } else {
            //removing old cell buffs
            card.removeCellBuffs();
            currentCell.setCard(null);
            targetCell.setCard(card);
            activePlayer.getInBattleCards().replace(card, targetCell);
            if (!game.getGameMode().equals(GameMode.DEATH_MATCH))
                card.pickUpFlags(targetCell);
            System.err.println("card" + card.getName() + " moved to " + x + "," + y);
            //adding cell buff to card
            if (targetCell.getBuff() != null) {
                targetCell.getBuff().setForCell(true);
                card.addBuffToTarget(targetCell.getBuff(), card);
                card.castOnMomentBUffs(card);
                card.checkBUffTiming(card, false);
            }
            card.setMovedThisTurn(true);
            return true;
        }
    }

    public void comboAttack(ArrayList<String> comboAttackers, String defenderInBattleId) {
        for (String comboAttackerId : comboAttackers) {
            if (!activePlayer.containsCardInBattle(comboAttackerId)) {
                errorType = ErrorType.INVALID_CARD_ID;
                return;
            }
            SoldierCard attacker = (SoldierCard) activePlayer.getInBattleCard(comboAttackerId);
            if (!attacker.getAbilityCastTime().equals(AbilityCastTime.COMBO)) {
                continue;
            }
            activePlayer.setSelectedCard(attacker);
            attack(defenderInBattleId);
        }
    }

    public int attack(String defenderInBattleId) {
        // return -1 if error occurred
        // return 0 if attack had no counter Attack
        // return 1 if attack had counter attack
        int result = -1;
        if (!activePlayer.isAnyCardSelected()) {
            errorType = ErrorType.CARD_NOT_SELECTED;
            return -1;
        }
        SoldierCard attacker = (SoldierCard) activePlayer.getSelectedCard();
        if (attacker.isAttackedThisTurn()) {
            errorType = ErrorType.CAN_NOT_ATTACK_AGAIN;
            return -1;
        }
        if (!deactivePlayer.containsCardInBattle(defenderInBattleId)) {
            errorType = ErrorType.INVALID_CARD_ID;
            return -1;
        }
        SoldierCard defender = (SoldierCard) deactivePlayer.getInBattleCard(defenderInBattleId);
        Cell attackerCell = activePlayer.getInBattleCards().get(attacker);
        Cell defenderCell = deactivePlayer.getInBattleCards().get(defender);
        if (!attacker.targetIsInRange(attackerCell, defenderCell)) {
            errorType = ErrorType.TARGET_NOT_IN_RANGE;
            return -1;
        }
        attacker.attack(defender);
        useUsable(activePlayer, WhenToUse.ON_ATTACK);
        attacker.setAttackedThisTurn(true);
        System.err.println("attacked");
        result = 0;
        if (defender.targetIsInRange(defenderCell, attackerCell)) {
            defender.counterAttack(attacker);
            System.err.println("counter attacked");
            checkDeadCard(activePlayer, attacker);
            result = 1;
        }
        checkDeadCard(deactivePlayer, defender);
        if (game.gameIsOver())
            endTurn();
        return result;
    }

    public void useSpecialPower(int x, int y) {
        if (!activePlayer.isAnyCardSelected()) {
            errorType = ErrorType.CARD_NOT_SELECTED;
            return;
        }
        SoldierCard card = (SoldierCard) activePlayer.getSelectedCard();
        if (card instanceof Hero) {
            if (((Hero) card).getSpecialPower() == null || ((Hero) card).getCoolDown() == 0) {
                errorType = ErrorType.NO_SPECIAL_POWER;
            } else if (!card.getTarget().checkTargetValidation(game, activePlayer, deactivePlayer,
                    x, y)) {
                errorType = ErrorType.INVALID_TARGET;
            } else if (activePlayer.getMana() < card.getMana()) {
                errorType = ErrorType.NOT_ENOUGH_MANA;
            } else if (((Hero) card).getCoolDownWaiting() < ((Hero) card).getCoolDown()) {
                errorType = ErrorType.NOT_ENOUGH_COOLDOWN;
            } else {
                castHeroSpecialPower((Hero) card, x, y);
                ((Hero) card).setCoolDownWaiting(0);
            }
        }
        if (card instanceof Minion) {
            if (((Minion) card).getAbilities() == null) {
                errorType = ErrorType.NO_SPECIAL_POWER;
                return;
            }
            if (!card.getTarget().checkTargetValidation(game, activePlayer,
                    deactivePlayer, x, y)) {
                errorType = ErrorType.INVALID_TARGET;
                return;
            }
            if (card.getAbilityCastTime() != null && (
                    card.getAbilityCastTime().equals(AbilityCastTime.ON_SPAWN) ||
                            card.getAbilityCastTime().equals(AbilityCastTime.ON_TURN))
                    && !card.isAttackedThisTurn()) {
                castMinionSpecialPower((Minion) card);
            }
        }
    }

    private void castMinionSpecialPower(Minion card) {
        Cell cell = game.FindCardCellInGame(card);
        switch (card.getTarget().getSoldierTargetType()) {
            case ITSELF:
                addMinionSpecialPowersToTarget(card, card);
                break;
            case ALL_MINIONS_TO_2_CELLS_FURTHER:
                for (int i = cell.getXCoordinate() - 2; i < cell.getXCoordinate() + 2; i++) {
                    if (game.getCell(i, cell.getYCoordinate()) != null && i != cell.getXCoordinate() &&
                            game.getCell(i, cell.getYCoordinate()).getCard() instanceof Minion) {
                        addMinionSpecialPowersToTarget(card,
                                (SoldierCard) game.getCell(i, cell.getYCoordinate()).getCard());
                    }
                }
                for (int i = cell.getYCoordinate() - 2; i < cell.getYCoordinate() + 2; i++) {
                    if (game.getCell(cell.getXCoordinate(), i) != null && i != cell.getYCoordinate() &&
                            game.getCell(cell.getXCoordinate(), i).getCard() instanceof Minion) {
                        addMinionSpecialPowersToTarget(card,
                                (SoldierCard) game.getCell(cell.getXCoordinate(), i).getCard());
                    }
                }
                break;
            case ALL_FRIENDLY_MINIONS:
                for (Card target : activePlayer.getInBattleCards().keySet()) {
                    if (target instanceof Minion) {
                        addMinionSpecialPowersToTarget(card, ((SoldierCard) target));
                    }
                }
                break;
            case ALL_ENEMY_MINIONS_AROUND:
                findAroundMinons(card, cell, deactivePlayer);
                break;
            case ALL_FRIENDLY_MINIONS_AROUND_AND_ITSELF:
                findAroundMinons(card, cell, activePlayer);
                break;
            case ALL_MINIONS_AROUND:
                for (int i = cell.getXCoordinate() - 1; i <= cell.getXCoordinate() + 1; i++) {
                    for (int j = cell.getYCoordinate() - 1; j <= cell.getYCoordinate() + 1; j++) {
                        if (game.coordinateIsValid(i, j) && game.getCell(i, j).getCard() != null
                                && game.getCell(i, j).getCard() instanceof Minion &&
                                game.getCell(i, j).getCard() != card) {
                            addMinionSpecialPowersToTarget(card, (SoldierCard) game.getCell(i, j).getCard());
                        }
                    }
                }
                break;
        }
    }

    private void findAroundMinons(Minion card, Cell cell, Player player) {
        for (int i = cell.getXCoordinate() - 1; i <= cell.getXCoordinate() + 1; i++) {
            for (int j = cell.getYCoordinate() - 1; j <= cell.getYCoordinate() + 1; j++) {
                if (game.coordinateIsValid(i, j) && game.getCell(i, j).getCard() != null
                        && game.getCell(i, j).getCard() instanceof Minion &&
                        player.getInBattleCards().containsKey(game.getCell(i, j).getCard())) {
                    addMinionSpecialPowersToTarget(card, (SoldierCard) game.getCell(i, j).getCard());
                }
            }
        }
    }

    private void castHeroSpecialPower(Hero card, int x, int y) {
        if (card.getTarget().getType().equals(Type.AREA)) {
            int areaSize = card.getTarget().getAreaSize();
            for (int i = x; i < y + areaSize; i++) {
                for (int j = y; j < y + areaSize; j++) {
                    if (game.coordinateIsValid(i, j)) {
                        if (card.getTarget().isIsaffectingCell()) {
                            game.getCell(i, j).setBuff(card.getSpecialPower());
                        }
                    }
                }
            }

        } else {
            switch (card.getTarget().getSoldierTargetType()) {
                case FRIENDLY_HERO:
                case ITSELF:
                    addHeroSpecialPowerToTarget(card, card);
                    break;
                case ALL_ENEMIES:
                    for (Card soldier : deactivePlayer.getInBattleCards().keySet()) {
                        addHeroSpecialPowerToTarget(card, (SoldierCard) soldier);
                    }
                    break;
                case ONE_ENEMY:
                case ONE_SOLDIER:
                    Cell cell = game.getCell(x, y);
                    addHeroSpecialPowerToTarget(card, (SoldierCard) cell.getCard());
                    break;
                case ALL_SOLDIERS_IN_THE_ROW_OF_FRIENDLY_HERO:
                    for (int i = 1; i <= 9; i++) {
                        int j = activePlayer.getInBattleCards().get(activePlayer.getHero()).getYCoordinate();
                        int x1 = activePlayer.getInBattleCards().get(activePlayer.getHero()).getXCoordinate();
                        if (game.getCell(i, j) != null && i != x1) {
                            addHeroSpecialPowerToTarget(card, (SoldierCard) game.getCell(i, j).getCard());
                        }
                    }
                    break;
            }
        }
    }

    private void addMinionSpecialPowersToTarget(Minion minion, SoldierCard target) {
        for (Buff buff : minion.getAbilities()) {
            minion.addBuffToTarget(buff, target);
        }
        target.castOnMomentBUffs(target);
        target.checkBUffTiming(target, false);
    }

    private void addHeroSpecialPowerToTarget(Hero hero, SoldierCard target) {
        hero.addBuffToTarget(hero.getSpecialPower(), target);
        target.castOnMomentBUffs(target);
        target.checkBUffTiming(target, false);
    }

    public void showHand() {
        view.show(activePlayer.handInfo());
    }

    public boolean insertCard() {
        Player player = activePlayer;
        ArrayList<Card> cards = new ArrayList<>(player.getHandCards().values());
        Card card = findCardInHandByName(cards, request.getCardName());
        if (card == null) {
            errorType = ErrorType.INVALID_CARDNAME;
            return false;
        }
        if (!game.coordinateIsValid(request.getX(), request.getY())) {
            errorType = ErrorType.INVALID_TARGET;
            return false;
        }
        if (player.getMana() < card.getMana()) {
            errorType = ErrorType.NOT_ENOUGH_MANA;
            return false;
        }
        Cell insertionCell = game.getCell(request.getX(), request.getY());
        if (card instanceof SpellCard) {
            if (!((SpellCard) card).getTargetArea().checkTargetValidation(game,
                    activePlayer, deactivePlayer, request.getX(), request.getY())) {
                errorType = ErrorType.INVALID_TARGET;
                return false;
            }
            castSpell((SpellCard) card, request.getX(), request.getY());
            card.setCardStatus(CardStatus.IN_GRAVEYARD);
            activePlayer.getGraveYard().put(card.getInBattleCardId(), card);
        } else {
            if (!isInsertionPossible(player, insertionCell)) {
                errorType = ErrorType.INVALID_TARGET;
            } else {
                card.setCardStatus(CardStatus.PLACED);
                ((SoldierCard) card).setAttackedThisTurn(false).setMovedThisTurn(false);
                player.getInBattleCards().put(card, insertionCell);
                insertionCell.setCard(card);
                card.setInBattleCardId(generateInBattleCardId(card));
                view.show(card.getName() + " with " + card.getInBattleCardId() +
                        " inserted to (" + request.getX() + ", " + request.getY() + ")");
            }
            useUsable(activePlayer, WhenToUse.ON_SPAWN);
            player.getHandCards().remove(card.getCardId(), card);
            player.decreaseMana(card.getMana());
            checkDeadCards(activePlayer);
            checkDeadCards(deactivePlayer);
        }
        return errorType == null;
    }

    private void castSpell(SpellCard card, int x, int y) {
        if (card.getTargetArea().getType().equals(Type.AREA)) {
            int areaSize = card.getTargetArea().getAreaSize();
            for (int i = x; i < x + areaSize; i++) {
                for (int j = y; j < y + areaSize; j++) {
                    if (game.coordinateIsValid(i, j)) {
                        if (card.getTargetArea().isIsaffectingCell()) {
                            game.getCell(i, j).setBuff(card.getBuff().get(0));
                        } else if (game.getCell(i, j).getCard() != null) {
                            if (card.getName().equals("Area Dispel")) {
                                DispellBuff buff = (DispellBuff) card.getBuff().get(0);
                                Card soldier = game.getCell(i, j).getCard();
                                ((SoldierCard) soldier).getBuffs().add(buff);
                                if (activePlayer.getInBattleCards().containsKey(soldier))
                                    buff.cancelNegativeBuffs((SoldierCard) soldier);
                                else
                                    buff.cancelPositiveBuffs((SoldierCard) soldier);
                            } else {
                                Cell cell = game.getCell(x, y);
                                castSpellBuffsOnTarget(card, (SoldierCard) cell.getCard());
                            }
                        }
                    }
                }
            }
        } else {
            switch (card.getTargetArea().getSoldierTargetType()) {
                //cases are handled with break points
                case ONE_ENEMY_MINION:
                case ONE_FRIENDLY_MINION:
                case ONE_SOLDIER:
                case ONE_FRIENDLY_SOLDIER:
                case ONE_ENEMY:
                    Cell cell = game.getCell(x, y);
                    castSpellBuffsOnTarget(card, (SoldierCard) cell.getCard());
                    break;
                case ENEMY_HERO:
                    castSpellBuffsOnTarget(card, (SoldierCard) deactivePlayer.getHero());
                    break;
                case ALL_ENEMIES:
                    for (Card soldier : deactivePlayer.getInBattleCards().keySet()) {
                        castSpellBuffsOnTarget(card, (SoldierCard) soldier);
                    }
                    break;
                case FRIENDLY_HERO:
                    castSpellBuffsOnTarget(card, (SoldierCard) activePlayer.getHero());
                    break;
                case ALL_FRIENDLY_SOLDIERS:
                    castSpellBuffsOnTarget(card, (SoldierCard) activePlayer.getHero());
                    //don't add break
                case ALL_FRIENDLY_MINIONS:
                    for (Card minion : activePlayer.getInBattleCards().keySet()) {
                        if (!(minion instanceof Hero)) {
                            castSpellBuffsOnTarget(card, (SoldierCard) minion);
                        }
                    }
                    break;
                case ONE_RANDOM_MINION_AROUND_FRIENDLY_HERO:
                    Cell heroCell = activePlayer.getInBattleCards().get(activePlayer.getHero());
                    for (int i = heroCell.getXCoordinate() - 1; i <= heroCell.getXCoordinate() + 1; i++) {
                        for (int j = heroCell.getYCoordinate() - 1; j <= heroCell.getYCoordinate() + 1; j++) {
                            if (game.coordinateIsValid(i, j) && i != heroCell.getXCoordinate() &&
                                    j != heroCell.getYCoordinate() &&
                                    activePlayer.getInBattleCards().containsKey(game.getCell(i, j).getCard())) {
                                castSpellBuffsOnTarget(card, (SoldierCard) game.getCell(i, j).getCard());
                            }
                        }
                    }
                    break;
                case ALL_ENEMIES_IN_A_COLUMN:
                    for (int i = 1; i <= 5; i++) {
                        Card soldier = game.getCell(x, i).getCard();
                        if (soldier != null && deactivePlayer.getInBattleCards().containsKey(soldier)) {
                            castSpellBuffsOnTarget(card, (SoldierCard) soldier);
                        }
                    }
                    break;
            }
        }
    }

    private void castSpellBuffsOnTarget(SpellCard card, SoldierCard target) {
        for (Buff buff : card.getBuff()) {
            target.addBuffToTarget(buff, target);
        }
        target.castOnMomentBUffs(target);
        target.checkBUffTiming(target, false);
    }

    private String generateInBattleCardId(Card card) {
        StringBuilder string = new StringBuilder();
        string.append(activePlayer.getAccount().getUserName());
        string.append("_");
        string.append(card.getName());
        string.append("_");
        HashMap<String, Integer> ids = activePlayer.getCardNameIdHashMap();
        if (!ids.containsKey(card.getName())) {
            ids.put(card.getName(), 0);
        }
        int id = ids.get(card.getName());
        id++;
        string.append(id);
        ids.replace(card.getName(), id);
        return string.toString().replaceAll(" ", "_");
    }

    private Card findCardInHandByName(ArrayList<Card> cards, String cardName) {
        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    private boolean isInsertionPossible(Player player, Cell cell) {
        boolean flag = false;
        for (Cell filledCell : player.getInBattleCards().values()) {
            if (Math.abs(cell.getXCoordinate() - filledCell.getXCoordinate()) <= 1 &&
                    Math.abs(cell.getYCoordinate() - filledCell.getYCoordinate()) <= 1) {
                flag = true;
            }
            if (cell.getCard() != null) {
                return false;
            }
        }
        return flag;
    }


    public void endTurn() {
        activePlayer.setSelectedItem(null);
        activePlayer.setSelectedCard(null);
        Player player = activePlayer;
        checkBuffsAtTheEndOfTurn(activePlayer);
        checkBuffsAtTheEndOfTurn(deactivePlayer);
        player.resetCardsAttackAndMoveAbility();
        player.moveARandomCardToHand();
        checkDeadCards(activePlayer);
        checkDeadCards(deactivePlayer);
        if (game.playerWithFlag() != null)
            game.playerWithFlag().increaseNumberOfTurnWithFlag();
        if (!game.gameIsOver()) {
            game.changeTurn();
            castPassiveBuffs();
        } else
            endGame();
        setActivePlayer();
    }

    private void castPassiveBuffs() {
        for (Card card : activePlayer.getInBattleCards().keySet()) {
            if (((SoldierCard) card).getAbilityCastTime() != null &&
                    ((SoldierCard) card).getAbilityCastTime().equals(AbilityCastTime.PASSIVE)) {
                castMinionSpecialPower((Minion) card);
            }
        }
        for (Card card : deactivePlayer.getInBattleCards().keySet()) {
            if (((SoldierCard) card).getAbilityCastTime() != null &&
                    ((SoldierCard) card).getAbilityCastTime().equals(AbilityCastTime.PASSIVE)) {
                castMinionSpecialPower((Minion) card);
            }
        }
    }

    private void checkBuffsAtTheEndOfTurn(Player player) {
        ArrayList<Card> cards = new ArrayList<>(player.getInBattleCards().keySet());
        for (Card card : cards) {
            if (card instanceof SoldierCard) {
                ((SoldierCard) card).checkBUffTiming((SoldierCard) card, true);
            }
        }
    }

    public void showGatheredCollectables() {
        for (Item item : activePlayer.getDeckCards().getItems().values()) {
            if (item.getType().equals(ItemTypes.COLLECTABLE)) {
                view.show(item.toString());
            }
        }
    }

    public void selectCollectables() {
        Item item = activePlayer.getItems().get(request.getItemID());
        activePlayer.setSelectedItem(item);
    }

    public void showCollectableInfo() {
        if (activePlayer.getSelectedItem() != null)
            view.show(activePlayer.getSelectedItem().toString());
        else errorType = ErrorType.NO_ITEM_SELECTED;
    }

    public void useCollectable() {
        if (!activePlayer.isAnyItemSelected()) {
            errorType = ErrorType.NO_ITEM_SELECTED;
            return;
        }
        if (findTarget(activePlayer.getSelectedItem()) == null)
            errorType = ErrorType.INVALID_TARGET;
        else activePlayer.getSelectedItem().castItemsSpell(findTarget(activePlayer.getSelectedItem()));
    }

    public void showNextCard() {
        Player currentPlayer = activePlayer;
        if (currentPlayer.getNextCardId() == 0) {
            view.show("No More Card In Your Deck!!!");
        } else {
            int cardId = currentPlayer.getNextCardId();
            view.show(currentPlayer.getDeckCards().getCards().get(cardId).toInfoString());
        }
    }

    public void showCardInfoInGraveYard() {
        view.show(activePlayer.getGraveYard().get(request.getInBattleCardId()).toString());
    }

    public void showAllCardsInGraveYard() {
        for (Card card : activePlayer.getGraveYard().values()) {
            view.show(card.toString());
        }
    }

    public void endGame() {
        if (game.getWinnerPlayer() == null) {
            view.show("draw!");
        } else {
            view.show(game.getWinnerPlayer().getAccount().getUserName()
                    + " won the game and his prize is: " + game.getPrize());
            game.getWinnerPlayer().getAccount().increaseMoney(game.getPrize());

        }
        game.getPlayer1().getAccount().getMatchHistory().add(game);
        game.getPlayer2().getAccount().getMatchHistory().add(game);
        menuType = MenuType.MAINMENU;
        game = null;
        ai = null;
        aiDeck = null;
    }

    public void showMenuOptions() {
    }

    public Game getGame() {
        printStream.println(gson.toJson(game));
        printStream.flush();
        return game;
    }

    public void checkDeadCard(Player player, SoldierCard card) {
        if ((card.getName().equals("oghab") && card.getHp() < 0) ||
                (card.getHp() <= 0 && !card.getName().equals("oghab"))) {
            if (card.getName().equals("oghab") && card.getHp() < 0)
                if (card.getAbilityCastTime() != null && card.getAbilityCastTime().equals(AbilityCastTime.ON_DEATH)) {
                    castMinionSpecialPower((Minion) card);
                }
            if (card.getHp() <= 0) {
                if (card.getAbilityCastTime() != null && card.getAbilityCastTime().equals(AbilityCastTime.ON_DEATH)) {
                    castMinionSpecialPower((Minion) card);
                }
                useUsable(activePlayer, WhenToUse.ON_DEATH);
                Cell cell = player.getInBattleCards().get(card);
                if (game.getGameMode().equals(GameMode.KEEP_THE_FLAG) && card.getFlagNumber() != 0)
                    player.setNumberOfTurnsWithFlag(0);
                card.dropFlags(cell);
                cell.setCard(null);
                player.getInBattleCards().remove(card);
                player.getGraveYard().put(card.getInBattleCardId(), card);
                card.setCardStatus(CardStatus.IN_GRAVEYARD);
                System.out.println(card.getInBattleCardId() + " died in battle!");
            }
        }
    }

    public void checkDeadCards(Player player) {
        ArrayList<Card> cards = new ArrayList<>(player.getInBattleCards().keySet());
        for (int i = cards.size() - 1; i >= 0; i--) {
            checkDeadCard(player, (SoldierCard) cards.get(i));
        }
    }

    public SoldierCard findTarget(Item item) {
        ArrayList<Card> myInBattleCards = new ArrayList<>(activePlayer.getInBattleCards().keySet());
        ArrayList<Card> opponentInBattleCards = new ArrayList<>(deactivePlayer.getInBattleCards().keySet());
        switch (item.getTarget().getSoldierTargetType()) {
            case ONE_FRIENDLY_SOLDIER:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof SoldierCard) {
                        return ((SoldierCard) card);
                    }
                }
                break;
            case FRIENDLY_RANGED_AND_HYBRID:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof SoldierCard && (((SoldierCard) card).getType().equals(SoldierTypes.HYBRID) ||
                            ((SoldierCard) card).getType().equals(SoldierTypes.RANGED))) {
                        return ((SoldierCard) card);
                    }
                }
                break;
            case ENEMY_HERO_RANGED_AND_HYBRID:
                Collections.shuffle(opponentInBattleCards);
                for (Card card : opponentInBattleCards) {
                    if (card instanceof Hero && (((Hero) card).getType().equals(SoldierTypes.RANGED) ||
                            ((Hero) card).getType().equals(SoldierTypes.HYBRID))) {
                        return ((Hero) card);
                    }
                }
                break;
            case ONE_FRIENDLY_MINION:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof Minion) {
                        return ((Minion) card);
                    }
                }
                break;
            case FRIENDLY_MELEE:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof SoldierCard && ((SoldierCard) card).getType().equals(SoldierTypes.MELEE)) {
                        return ((SoldierCard) card);
                    }
                }
                break;
            case FRIENDLY_HERO:
                for (Card card : myInBattleCards) {
                    if (card instanceof Hero)
                        return ((Hero) card);
                }
                break;
            case FRIENDLY_HERO_RANGED_AND_HYBRID:
                for (Card card : myInBattleCards) {
                    if (card instanceof Hero && (((Hero) card).getType().equals(SoldierTypes.HYBRID) ||
                            ((Hero) card).getType().equals(SoldierTypes.RANGED)))
                        return ((Hero) card);
                }
                break;
            case ONE_ENEMY:
                Collections.shuffle(opponentInBattleCards);
                for (Card card : opponentInBattleCards) {
                    if (card instanceof SoldierCard)
                        return ((SoldierCard) card);
                }
                break;
            case ENEMY_HERO:
                for (Card card : opponentInBattleCards) {
                    if (card instanceof Hero)
                        return ((Hero) card);
                }
                break;
            case ONE_SOLDIER:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof SoldierCard)
                        return ((SoldierCard) card);
                }
                break;
            case ITSELF:
                return ((SoldierCard) activePlayer.getSelectedCard());
        }
        return null;
    }

    public void useUsable(Player player, WhenToUse whenToUse) {
        for (Item item : player.getItems().values()) {
            if (item.getWhenToUse().equals(whenToUse)) {
                item.castItemsSpell(findTarget(item));
            }
        }

    }

    public Request getRequest() {
        return request;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setActivePlayer() {
        if (game != null) {
            if (game.isTurnOfPlayerOne()) {
                activePlayer = game.getPlayer1();
                deactivePlayer = game.getPlayer2();
            } else {
                activePlayer = game.getPlayer2();
                deactivePlayer = game.getPlayer1();
            }
        }
    }

    public Player getActivePlayer() {
        printStream.println(gson.toJson(activePlayer));
        printStream.flush();
        return activePlayer;
    }

    public Player getDeactivePlayer() {
        printStream.println(gson.toJson(deactivePlayer));
        printStream.flush();
        return deactivePlayer;
    }

    public ArrayList<Account> getOnlines() {
        printStream.println(gson.toJson(getOnlineAccounts()));
        printStream.flush();
        return getOnlineAccounts();
    }

    public void setOpponentAccount(Account opponentAccount) {
        this.opponentAccount = opponentAccount;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
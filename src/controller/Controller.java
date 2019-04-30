package controller;

import model.*;
import model.Cards.Card;
import model.Cards.Hero;
import model.Cards.SoldierCard;
import view.*;
import model.Game.*;

public class Controller {
    private final static Controller CONTROLLER = new Controller();

    public static Controller getInstance() {
        return CONTROLLER;
    }

    private Controller() {
    }

    private Shop shop = Shop.getInstance();
    private MenuType menuType = MenuType.ACCOUNT;
    private Request request;
    private Account loggedInAccount;
    private Game game;
    private boolean playerOneMustShow;
    private ErrorType errorType = null;
    private View view = View.getInstance();

    public void run() {
        //mainLoop:
        do {
            System.out.println("Menu: " + menuType);
            request = new Request();
            request.getNewCommand();
            request.setRequestType(menuType);
            request.parseCommand();
            switch (request.getRequestType()) {
                case ERROR:
                    errorType = ErrorType.INVALID_COMMAND;
                    break;
                ///////////////////////////// MAIN_MENU  && ACCOUNT ///////////////////////
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
                ///////////////////////////// SHOP  ///////////////////////////
                case SEARCH_IN_SHOP:
                    searchInShop();
                    break;
                case SEARCH_IN_COLLECTION:
                    searchInCollection();
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
                    // todo: showCollection
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
                case VALIDATE_DECK:
                    validateDeck();
                    break;
                case SELECT_MAIN_DECK:
                    selectMainDeck();
                    break;
                case REMOVE_FROM_DECK:
                    removeFromDeck();
                    //todo: test for items
                    break;
                ///////////////////////////////// CREATING GAME ///////////////////////
                case SHOW_ALL_PLAYERS:
                    showAllPlayer();
                    break;
                case SELECT_OPPONENT_USER:
                    createGame();
                    break;
                case SELECT_MODE:
                    // TODO: 2019-04-29 check user must be selected before
                    break;
                ///////////////////////////////// BATTLE  ////////////////////////
                case INSERT_CARD:
                    break;
                case SHOW_GAME_INFO:
                    break;
                case SHOW_MY_MINIONS:
                    playerOneMustShow = game.isTurnOfPlayerOne();
                    showMinions();
                    break;
                case SHOW_OPPONENT_MINIONS:
                    playerOneMustShow = !game.isTurnOfPlayerOne();
                    showMinions();
                    break;
                case SHOW_CARD_INFO_IN_BATTLE:
                    showCardInfoInBattle();
                    break;
                case SHOW_HAND:
                    showHand();
                    break;
                case END_TURN:
                    endTurn();
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
            if (errorType != null) {
                view.printError(errorType);
                errorType = null;
            }
        } while (true);
    }

    private void createGame() {
        if (!Account.userNameIsValid(request.getUserName()) || loggedInAccount.getUserName().equals(request.getUserName())) {
            errorType = ErrorType.INVALID_OPPONENT;
            return;
        }
        Account opponentAccount = Account.getAccounts().get(request.getUserName());
        if (opponentAccount.getCollection().getMainDeck() == null ||
                !opponentAccount.getCollection().getMainDeck().deckIsValid()) {
            errorType = ErrorType.INVALID_OPPONENT_DECK;
            return;
        }

        Player player1 = new Player(loggedInAccount, loggedInAccount.getCollection().getMainDeck());
        Player player2 = new Player(opponentAccount, opponentAccount.getCollection().getMainDeck());
        game = new Game(player1, player2);
        view.show(opponentAccount.getUserName() + "selected as your opponent");
    }


    public void createNewAccount() {
        if (Account.userNameIsValid(request.getUserName())) {
            errorType = ErrorType.USERNAME_TAKEN;
            return;
        }
        view.printEnterPassword();
        request.getNewCommand();
        Account newAccount = new Account(request.getUserName(), request.getCommand());
        Account.addAccount(newAccount);
        System.out.println("account created");
    }

    public void login() {
        if (!Account.userNameIsValid(request.getUserName())) {
            errorType = ErrorType.INVALID_USERNAME;
            return;
        }
        view.printEnterPassword();
        request.getNewCommand();
        if (!Account.passwordIsValid(request.getCommand(), request.getUserName())) {
            errorType = ErrorType.INVALID_PASSWORD;
            return;
        }
        loggedInAccount = Account.getAccounts().get(request.getUserName());
        menuType = MenuType.MAINMENU;
        System.out.println("logged into " + request.getUserName());
    }

    public void showLeaderBoard() {
        view.show(Account.sortAccounts());
    }

    public void save() {
    }

    public void logOut() {
        menuType = MenuType.ACCOUNT;
        System.out.println("logged out from " + loggedInAccount.getUserName());
        loggedInAccount = null;
    }

    public void help() {
    }

    public void exitMenu() {
        //todo: maybe it need some changes
        switch (menuType) {
            case MAINMENU:
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
            default:
                menuType = MenuType.MAINMENU;
                break;
        }
    }

    public void enterMenu() {
        switch (menuType) {
            case MAINMENU:
                if (request.getEnteringMenu() == MenuType.SHOP ||
                        request.getEnteringMenu() == MenuType.COLLECTION) {
                    menuType = request.getEnteringMenu();
                    return;
                }
                if (request.getEnteringMenu() == MenuType.START_NEW_GAME) {
                    if (loggedInAccount.getCollection().getMainDeck() == null ||
                            !loggedInAccount.getCollection().getMainDeck().deckIsValid()) {
                        errorType = ErrorType.INVALID_DECK;
                        return;
                    }
                    menuType = request.getEnteringMenu();
                    return;
                }

                break;
            case START_NEW_GAME:
                if (request.getEnteringMenu() == MenuType.SINGLE_GAME_MENU ||
                        request.getEnteringMenu() == MenuType.MULTI_GAME_MENU) {
                    menuType = request.getEnteringMenu();
                    return;
                }
                break;
            case SINGLE_GAME_MENU:
                if (request.getEnteringMenu() == MenuType.SINGLE_GAME_CUSTOM_MODE ||
                        request.getEnteringMenu() == MenuType.SINGLE_GAME_STORY_MODE) {
                    menuType = request.getEnteringMenu();
                    return;
                }
                break;
        }
        errorType = ErrorType.INVALID_COMMAND;
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

    public void searchInCollection() {
        boolean find = false;
        for (Item item : loggedInAccount.getCollection().getItems()) {
            if (item.getName().equals(request.getSearchingName())) {
                view.show("" + item.getItemId());
                find = true;
            }
        }
        for (Card card : loggedInAccount.getCollection().getCards()) {
            if (card.getName().equals(request.getSearchingName())) {
                view.show("" + card.getCardId());
                find = true;
            }
        }
        if (!find) errorType = ErrorType.NOT_FOUND;
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

    public void searchInShop() {
        for (Item item : shop.getItems())
            if (item.getName().equals(request.getSearchingName())) {
                view.show("" + item.getItemId());
                return;
            }
        for (Card card : shop.getCards())
            if (card.getName().equals(request.getSearchingName())) {
                view.show("" + card.getCardId());
                return;
            }
        //todo:all arraylists must set to hashmap
        //duplicated code with existsInShop in Shop class
        errorType = ErrorType.NOT_FOUND;
    }

    public void buyFromShop() {
        if (!shop.existsInShop(request.getProductName()))
            errorType = ErrorType.NOT_FOUND;
        else if (!shop.priceIsEnough(request.getProductName(), loggedInAccount))
            errorType = ErrorType.NOT_ENOUGH_MONEY;
        else if (!shop.validateNumberOfItems(request.getProductName(), loggedInAccount))
            errorType = ErrorType.FULL_ITEMS;
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
        // TODO: 2019-04-27 must remove card from decks
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
    }

    public void showMinions() {
        if (playerOneMustShow) {
            for (Card card : game.getPlayer1().getIntBattleCards()) {
                if (card instanceof SoldierCard)
                    view.show(((SoldierCard) card).toBattleFormat());
            }
        } else {
            for (Card card : game.getPlayer2().getIntBattleCards()) {
                if (card instanceof SoldierCard)
                    view.show(((SoldierCard) card).toBattleFormat());
            }
        }
    }

    public void showOpponentMinions() {
    }

    public void showCardInfoInBattle() {
        if (game.isTurnOfPlayerOne()) {
            for (Card card : game.getPlayer1().getIntBattleCards()) {
                if (card.getInBattleCardId().equals(request.getInBattleCardId())) {
                    view.show(card.toInfoString());
                    return;
                }
            }
        } else for (Card card : game.getPlayer2().getIntBattleCards()) {
            if (card.getInBattleCardId().equals(request.getInBattleCardId())) {
                view.show(card.toInfoString());
                return;
            }
        }
        errorType = ErrorType.INVLID_CARD_ID;
    }

    public void selectCard() {
    }

    public void moveCard() {
    }

    public void attack() {
    }

    public void comboAttack() {
    }

    public void useSpecialPower() {
    }

    public void showHand() {
        if (game.isTurnOfPlayerOne()) {
            view.show(game.getPlayer1().handInfo());
        } else {
            view.show(game.getPlayer2().handInfo());
        }
    }

    public void insertCard() {
    }

    public void endTurn() {
        //todo 1.cast buff, 2.check winner
        //1
        //2
        if (game.isTurnOfPlayerOne()) {
            game.getPlayer1().moveARandomCardToHand();
        } else {
            game.getPlayer2().moveARandomCardToHand();
        }
        game.changeTurn();
    }

    public void showGatheredCollectables() {
    }

    public void selectCollectables() {
    }

    public void showCollectableInfo() {
    }

    public void useCollectable() {
    }

    public void showNextCard() {
    }

    public void showCardInfoInGraveYard() {
    }

    public void endGame() {
    }

    public void showMenuOptions() {
    }

    public Game getGame() {
        return game;
    }
}
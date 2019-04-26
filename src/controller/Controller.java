package controller;

import model.*;
import model.Cards.Card;
import view.*;

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
    private ErrorType errorType = null;
    private View view = View.getInstance();

    public void run() {
        mainLoop:
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
                case CREATE_ACCOUNT:
                    createNewAccount();
                    break;
                case LOGIN:
                    login();
                    break;
                case LOGOUT:
                    logOut();
                case SEARCH_IN_SHOP:
                    searchInShop();
                    // TODO: test
                    break;
                case SEARCH_IN_COLLECTION:
                    searchInCollection();
                    // TODO: test
                    break;
                case BUY_FROM_SHOP:
                    buyFromShop();
                    // TODO: test
                    break;
                case SELL_TO_SHOP:
                    sellToShop();
                    // TODO: test
                    break;
                case SHOW_SHOP:
                    showShop();
                    break;
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
                case ADD_TO_DECK:
                    addToDeck();
                    // todo: test
                    break;
                case REMOVE_FROM_DECK:
                    removeFromDeck();
                    //todo: test
                    break;
                case EXIT_MENU:
                    exitMenu();
                    break;
                case ENTER_MENU:
                    enterMenu();
                    break;
                case SHOW_LEADER_BOARD:
                    showLeaderBoard();
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
            case BATTLE:
                menuType = MenuType.MAINMENU;
                break;
            case COLLECTION:
                menuType = MenuType.MAINMENU;
                break;
            case SHOP:
                menuType = MenuType.MAINMENU;
                break;
        }
    }

    public void enterMenu() {
        menuType = request.getEnteringMenu();
    }

    public void showCollectionItems() {
        for (Card card : loggedInAccount.getCollection().getCards()) {
            view.show(card.toString());
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
    }

    public void createDeck() {
        if (loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName())) {
            errorType = ErrorType.DECK_EXISTS;
            return;
        }
        Deck deck = new Deck(request.getDeckName());
        loggedInAccount.getCollection().getDecks().put(request.getDeckName(), deck);
    }

    public void deleteDeck() {
        if (loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName())) {
            loggedInAccount.getCollection().getDecks().remove(request.getDeckName());
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
        else if (loggedInAccount.getCollection().isHero(request.getCardOrItemID())) {
            if (loggedInAccount.getCollection().getDecks().get(request.getDeckName()).deckHasHero())
                errorType = ErrorType.DECK_HAS_HERO;
        } else loggedInAccount.getCollection().addToDeck(request.getCardOrItemID(), request.getDeckName());
    }

    public void removeFromDeck() {
        if (!loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName()))
            errorType = ErrorType.DECK_NOT_EXISTS;
        else if (!loggedInAccount.getCollection().existsInDeck(request.getDeckName(), request.getCardOrItemID()))
            errorType = ErrorType.NOT_FOUND;
        else loggedInAccount.getCollection().removeFromDeck(request.getDeckName(), request.getCardOrItemID());

    }

    public void validateDeck() {
    }

    public void selectMainDeck() {
    }

    public void showAllDecks() {
    }

    public void showDeck() {
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
        else if (!shop.validateNumberOfItems(request.getProductName()))
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
            view.show("successful sales");
        }
    }

    public void showShop() {
        for (Card card : shop.getCards()) {
            view.show(card.toString());
            //todo:to string must be written
        }
    }

    public void showGameInfo() {
    }

    public void showMyMinions() {
    }

    public void showOpponentMinions() {
    }

    public void showCardInfoInBattle() {
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
    }

    public void insertCard() {
    }

    public void endTurn() {
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
}
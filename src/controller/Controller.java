package controller;

import model.Account;
import model.Card;
import model.Deck;
import model.Item;
import view.*;

public class Controller {
    private final static Controller CONTROLLER = new Controller();

    public static Controller getInstance() {
        return CONTROLLER;
    }

    private Controller() {
    }

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
                    // todo: showShop
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
        if (loggedInAccount.getCollection().deckExist(request.getDeckName())) {
            errorType = ErrorType.DECK_EXISTS;
            return;
        }
        Deck deck = new Deck(request.getDeckName());
        loggedInAccount.getCollection().getDecks().add(deck);
    }

    public void deleteDeck() {
        if (loggedInAccount.getCollection().deckExist(request.getDeckName())) {
            loggedInAccount.getCollection().getDecks().removeIf(deck -> deck.getName().equals(request.getDeckName()));
            return;
        }
        errorType = ErrorType.DECK_NOT_EXISTS;
    }

    public void addToDeck() {
    }

    public void removeCardFromDeck() {
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
        for (Item item : loggedInAccount.getShop().getItems())
            if (item.getName().equals(request.getSearchingName())) {
                view.show("" + item.getItemId());
                return;
            }
        for (Card card : loggedInAccount.getShop().getCards())
            if (card.getName().equals(request.getSearchingName())) {
                view.show("" + card.getCardId());
                return;
            }
        errorType = ErrorType.NOT_FOUND;
    }

    public void buyFromShop() {
        if (!loggedInAccount.getShop().existsInShop(request.getProductName()))
            errorType = ErrorType.NOT_FOUND;
        else if (loggedInAccount.getShop().priceIsEnough(request.getProductName(), loggedInAccount))
            errorType = ErrorType.NOT_ENOUGH_MONEY;
        else if (!loggedInAccount.getShop().validateNumberOfItems(request.getProductName()))
            errorType = ErrorType.FULL_ITEMS;
        else {
            loggedInAccount.getShop().buy(request.getProductName(), loggedInAccount);
            view.show("Successful purchase");
        }
    }

    public void sellToShop() {
        if (!loggedInAccount.getCollection().existsInCollection(request.getProductId()))
            errorType = ErrorType.INVALID_SELL;
        else {
            loggedInAccount.getShop().sell(request.getProductId(), loggedInAccount);
            view.show("successful sales");
        }
    }

    public void showShop() {
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
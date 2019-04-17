package controller;

import model.Account;
import model.Card;
import model.Item;
import view.*;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    private final static Controller CONTROLLER = new Controller();

    public static Controller getInstance() {
        return CONTROLLER;
    }

    private Controller() {
    }

    private MenuType menuType = MenuType.ACCOUNT;
    //todo:check starting menu
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
                    // TODO: buy functions
                case EXIT_MENU:
                    break mainLoop;
                case SHOW_LEADER_BOARD:
                    showLeaderBoard();
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
        ArrayList<Account> accounts = new ArrayList<>(Account.getAccounts().values());
        Collections.sort(accounts);
        int counter = 1;
        for (Account account : accounts) {
            System.out.println(counter + " -  UserName : " + account.getUserName() +
                    " - Wins : " + account.getNumberOfWins());
            counter++;
        }
    }

    public void save() {
    }

    public void logOut() {
    }

    public void help() {
    }

    public void exitMenu() {
    }

    public void enterBattleMenu() {
    }

    public void enterShop() {
    }

    public void enterCollection() {
    }

    public void enterCollectionItems() {
    }

    public void searchInCollection() {
        boolean find = false;
        for (Item item : loggedInAccount.getCollection().getItems()) {
            if (item.getName().equals(request.getSearchingName())) {
                System.out.println(item.getItemId());
                find = true;
            }
        }
        for (Card card : loggedInAccount.getCollection().getCards()) {
            if (card.getName().equals(request.getSearchingName())){
                System.out.println(card.getCardId());
                find = true;
            }
        }
        if (!find) request.setErrorType(ErrorType.NOT_FOUND);
    }

    public void saveCollection() {
    }

    public void createDeck() {
    }

    public void deleteDeck() {
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
                System.out.println(item.getItemId());
                return;
            }
        for (Card card : loggedInAccount.getShop().getCards())
            if (card.getName().equals(request.getSearchingName())) {
                System.out.println(card.getCardId());
                return;
            }
        request.setErrorType(ErrorType.NOT_FOUND);
    }

    public void buyFromShop() {
        if (!loggedInAccount.getShop().itemExistsInShop(request.getProductName()) &&
        !loggedInAccount.getShop().cardExistsInShop(request.getSearchingName()))
            request.setErrorType(ErrorType.NOT_FOUND);
        else if (loggedInAccount.getShop().priceIsEnough(request.getProductName(), loggedInAccount))
            request.setErrorType(ErrorType.NOT_ENOUGH_MONEY);
        else if (loggedInAccount.getCollection().getItems().size() < 3) request.setErrorType(ErrorType.FULL_ITEMS);
        else{
            loggedInAccount.getShop().buyCard(request.getProductName());
            loggedInAccount.getShop().buyItem(request.getProductName());
        }
    }

    public void sellToShop() {
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
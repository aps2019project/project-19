package controller;

import model.Account;
import view.*;

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
        mainloop:
        do {
            System.out.println("Menu: " + menuType);
            request = new Request();
            request.getNewCommand();
            request.setRequestType(menuType);
            switch (request.getRequestType()) {
                case ERROR:
                    errorType = ErrorType.INVALID_COMMAND;
                    break ;
                case CREATE_ACCOUNT:
                    createNewAccount();
                    break;
                case LOGIN:
                    login();
                    break;
                case EXIT_MENU:
                    break mainloop;
            }
            if(errorType != null){
                view.printError(errorType);
                errorType=null;
            }
        } while (true);
    }


    public void createNewAccount() {
        if (Account.userNameIsValid(request.getUserName())) {
            errorType=ErrorType.INVALID_USERNAME;
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
            errorType=ErrorType.INVALID_USERNAME;
            return;
        }
        view.printEnterPassword();
        request.getNewCommand();
        if (!Account.passwordIsValid(request.getCommand(), request.getUserName())) {
            errorType=ErrorType.INVALID_PASSWORD;
            return;
        }
        loggedInAccount = Account.getAccounts().get(request.getUserName());
        menuType = MenuType.MAINMENU;
        System.out.println("logged into " + request.getUserName());
    }

    public void showLeaderBoard() {
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
    }

    public void buyFromShop() {
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
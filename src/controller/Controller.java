package controller;

import model.Account;
import sun.applet.Main;
import view.*;

public class Controller {
    private final static Controller CONTROLLER = new Controller();
    public static Controller getInstance(){
        return CONTROLLER;
    }

    private Controller() {
    }

    private MenuType menuType = MenuType.ACCOUNT;
    //todo:check starting menu
    private Request request;
    private View view = View.getInstance();
    private Account logedInAccount;
    public void main() {
        mainloop:
        do {
            System.out.println("Menu: "+menuType);
            request = new Request();
            request.getNewCommand();
            request.setRequestType(menuType);
            if(!request.commandIsValid()){
                view.printError(request.getErrorType());
                continue ;
            }
            switch (request.getRequestType()){
                case CREATE_ACCOUNT:
                    createNewAccount();
                    System.out.println("account created");
                    break;
                case LOGIN:
                    login();
                    System.out.println("loged into "+ request.getUserName());
                    break;
                case EXIT_MENU:
                    break mainloop;
            }
        } while (true);
    }

    public static void managelputs() {
    }

    public void createNewAccount() {
        if(Account.userNameIsValid(request.getUserName())){
            view.printError(ErrorType.USERNAME_TAKEN);
            return;
        }
        view.printEnterPassword();
        request.getNewCommand();
        Account newAccount = new Account(request.getUserName(),request.getCommand());
        Account.addAccount(newAccount);
    }

    public void login() {
        if(!Account.userNameIsValid(request.getUserName())){
            view.printError(ErrorType.INVALID_USERNAME);
            return;
        }
        view.printEnterPassword();
        request.getNewCommand();
        if (!Account.passwordIsValid(request.getCommand(),request.getUserName())){
            view.printError(ErrorType.INVALID_PASSWORD);
            return;
        }
        logedInAccount = Account.getAccounts().get(request.getUserName());
        menuType = MenuType.MAINMENU;
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

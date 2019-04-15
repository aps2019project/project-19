package controller;

import view.*;

public class Controller {
    private static MenuType menuType = MenuType.ACCOUNT;
    //todo:check starting menu
    private static ErrorType errorType;

    public static void main() {
        mainloop:
        do {
            System.out.println("Menu: "+menuType);
            Request request = new Request();
            request.getNewCommand();
            request.setRequestType(menuType);
            if(!request.commandIsValid()){
                View.printError(request.getErrorType());
                continue ;
            }
            switch (request.getRequestType()){
                case CREATE_ACCOUNT:
                    System.out.println("account created");
                    menuType = MenuType.MAINMENU;
                    break;
                case EXIT_MENU:
                    break mainloop;
            }
        } while (true);
    }

    public void managelputs() {
    }

    public void createNewAccount() {
    }

    public void login() {
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

package view;

import controller.MenuType;

import java.util.Scanner;

public class Request {
    private static Scanner scanner = new Scanner(System.in);
   private String command;
   private RequestType requestType;
   private ErrorType errorType;
   private String deckName;
   private String cardName;
   private String collectableName;
   private int cardID;
   private int itemID;

    public void getNewCommand() {
        command=scanner.nextLine().trim();
    }


    public RequestType findTypeOfRequest(MenuType menuType) {
        switch (menuType){
            case ACCOUNT:
                if (command.matches("create account \\w+"))
                    return RequestType.CREATE_ACCOUNT;
                if(command.matches("exit"))
                    return RequestType.EXIT_MENU;
                break;
        }
        return RequestType.ERROR;
    }


    public boolean commandIsValid() {
        switch (requestType){
            case CREATE_ACCOUNT:
                return checkSyntaxOfCreateAccount();
            case ERROR:
                errorType = ErrorType.INVALID_COMMAND;
                return false;
            case EXIT_MENU:
                return true;
        }
        return false;
    }


    public boolean checkSyntaxOfCreateAccount() {
        return true;
    }

    public void checkSyntaxOfLogin() {
    }


    public void checkSyntaxOfShow() {
    }


    public void checkSyntaxOfSearchInCollection() {
    }


    public void checkSyntaxOfCreateDeck() {
    }


    public void checkSyntaxOfDeleteDeck() {
    }

    public void checkSyntaxOfAddToDeck() {
    }


    public void checkSyntaxOfRemoveCardFromDeck() {
    }


    public void checkSyntaxOfValidateDeck() {
    }


    public void checkSyntaxOfSelectMainDeck() {
    }


    public void checkSyntaxOfShowDeck() {
    }


    public void checkSyntaxOfSearchInShop() {
    }


    public void checkSyntaxOfBuyFromShop() {
    }


    public void checkSyntaxOfSellToShop() {
    }


    public void checkSyntaxOfShowCardInfoInBattle() {
    }


    public void checkSyntaxOfSelectCard() {
    }


    public void checkSyntaxOfMoveCard() {
    }


    public void checkSyntaxOfAttack() {
    }


    public void checkSyntaxOfComboAttack() {
    }


    public void checkSyntaxOfInsertCard() {
    }


    public void checkSyntaxOfSelectCollectable() {
    }


    public void checkSyntaxOfUseCollectable() {
    }


    public void checkSyntaxOfCardInfoInGraveYard() {
    }

    public void setRequestType(MenuType menuType) {
        this.requestType = findTypeOfRequest(menuType);
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCollectableName(String collectableName) {
        this.collectableName = collectableName;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getCommand() {
        return command;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getDeckName() {
        return deckName;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCollectableName() {
        return collectableName;
    }

    public int getCardID() {
        return cardID;
    }

    public int getItemID() {
        return itemID;
    }
}

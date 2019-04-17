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
   private String searchingName;
   private String userName;
   private String password;
   private String collectableName;
   private int cardID;
   private int itemID;

    public void getNewCommand() {
        command=scanner.nextLine().trim().toLowerCase();
    }


    public RequestType findTypeOfRequest(MenuType menuType) {
        switch (menuType){
            case ACCOUNT:
                if (command.matches("create account \\w+"))
                    return RequestType.CREATE_ACCOUNT;
                if (command.matches("login \\w+"))
                    return RequestType.LOGIN;
                if(command.matches("show leaderboard"))
                    return RequestType.SHOW_LEADER_BOARD;
                if(command.matches("save"))
                    return RequestType.SAVE;
                if(command.matches("logout"))
                    return RequestType.LOGOUT;
                break;
            case MAINMENU:
                if(command.matches("enter battle"))
                    return RequestType.ENTER_BATTLE_MENU;
                if(command.matches("enter shop"))
                    return RequestType.ENTER_SHOP;
                if(command.matches("enter collection"))
                    return RequestType.ENTER_COLLECTION;
                break;
            case COLLECTION:
                break;
            case BATTLE:
                break;
            case SHOP:
                if (command.matches("show collection"))
                    return RequestType.SHOW_COLLECTION_ITEMS;
                if (command.matches("search \\w+"))
                    return RequestType.SEARCH_IN_SHOP;
                if (command.matches("search collection \\w+"))
                    return RequestType.SEARCH_IN_COLLECTION;
                if (command.matches("buy \\w+"))
                    return RequestType.BUY_FROM_SHOP;
                if (command.matches("sell \\d+"))
                    return RequestType.SELL_TO_SHOP;
                if (command.matches("show"))
                    return RequestType.SHOW_SHOP;
        }
        if(command.matches("help"))
            return RequestType.HELP;
        else if(command.matches("exit"))
            return RequestType.EXIT_MENU;
        return RequestType.ERROR;
    }


    public void parseCommand() {
        switch (requestType){
            case CREATE_ACCOUNT:
                 parseCreateAccount();
            case LOGIN:
                 parseLogin();
            case SEARCH_IN_SHOP:
                parseSearchInShop();
                break;
        }
    }


    public void parseCreateAccount() {
        userName = command.split(" ")[2];
    }

    public void parseLogin() {
        userName = command.split(" ")[1];
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

    public void parseSearchInShop() {
        searchingName = command.split(" ")[1];
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

    public String getSearchingName() {
        return searchingName;
    }

    public void setSearchingName(String searchingName) {
        this.searchingName = searchingName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword(){
        return password;
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

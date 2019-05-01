package controller;

import model.*;
import model.Cards.*;
import view.*;
import model.Game.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    private Player activePlayer;
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
            if (game != null) {
                if (game.isTurnOfPlayerOne())
                    activePlayer = game.getPlayer1();
                else
                    activePlayer = game.getPlayer2();
            }
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
                    selectOpponent();
                    break;
                case SELECT_MODE:
                    selectMode();
                    // TODO: 2019-04- test
                    break;
                case SELECT_STORY_LEVEL:
                    selectStoryLevel();
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
                    selectCardOrItem(activePlayer);
                    // TODO: 2019-04-30 test
                    break;
                case MOVE_CARD:
                    moveCard();
                    // TODO: 2019-04-30 test
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
                    // TODO: 2019-04-30 check if there is any item selected or not (from activeplayer)
                    break;
                case SHOW_GATHERED_COLLECTABLES:
                    //todo what to do????
                    showGatheredCollectables();
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
            if (errorType != null) {
                view.printError(errorType);
                errorType = null;
            }
        } while (true);
    }

    private void selectStoryLevel() {
        //todo how to create plalyers
        if (request.getStoryLevel() == 1) {

        } else if (request.getStoryLevel() == 2) {

        } else if (request.getStoryLevel() == 3) {

        } else {
            errorType = ErrorType.INVALID_LEVEL;
        }
    }

    private void selectOpponent() {
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
        Player player1 = new Player(loggedInAccount, new Deck(loggedInAccount.getCollection().getMainDeck()));
        Player player2 = new Player(opponentAccount, new Deck(opponentAccount.getCollection().getMainDeck()));
        game = new Game(player1, player2);
        view.show(opponentAccount.getUserName() + "selected as your opponent");
    }

    private void selectMode() {
        if (game == null) {
            errorType = ErrorType.INVALID_COMMAND;
            return;
        }
        game.setGameMode(request.getGameMode());
        game.setPrize();
        if (request.getGameMode() == GameMode.KEEP_THE_FLAG)
            game.setNumOfFlags(request.getNumOfFlags());
        System.err.println("game mode seted");
        initNewGame(request.getGameMode());
    }

    public void initNewGame(GameMode gameMode) {
        switch (gameMode) {
            case DEATH_MATCH:
                game.setDate(new Date());
                break;
            case KEEP_THE_FLAG:
                break;
            case CAPTURE_THE_FLAGS:
                break;
        }
        game.setHeroes(game.getPlayer1(), game.getCell(1, 3)).setInBattleCardId(game.getPlayer1().getAccount().getUserName());
        game.setHeroes(game.getPlayer2(), game.getCell(9, 3)).setInBattleCardId(game.getPlayer2().getAccount().getUserName());
        game.getPlayer1().setFirstHand();
        game.getPlayer2().setFirstHand();
        menuType = MenuType.BATTLE;
        System.err.println("game started");
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
                    if (menuType.equals(MenuType.SINGLE_GAME_STORY_MODE))
                        view.showStoryMode();
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

    public void selectCardOrItem(Player player) {
        String id = request.getInBattleCardId();
        if (player.containsCardInBattle(id)) {
            Card card = activePlayer.getInBattleCard(id);
            activePlayer.setSelectedCard(card);
            System.err.println(card.getName() + " " + card.getCardId() + " selected");
            return;
        }
        int itemId = request.getItemID();
        if (player.getItems().containsKey(itemId)) {
            Item item = activePlayer.getItems().get(itemId);
            activePlayer.setSelectedItem(item);
            System.err.println(item.getName() + " selected");
            return;
        }
        errorType = ErrorType.INVALID_CARD_ID;
    }

    public void moveCard() {
        if (!activePlayer.isAnyCardSelected()) {
            errorType = ErrorType.CARD_NOT_SELECTED;
            return;
        }
        Card card = activePlayer.getSelectedCard();
        Cell targetCell = game.getCell(request.getX(), request.getY());
        Cell currentCell = activePlayer.getInBattleCards().get(card);
        if (targetCell.getCard() != null) {
            errorType = ErrorType.INVALID_TARGET;
        } else if (currentCell.getManhattanDistance(targetCell) > 2 || game.pathIsBlocked(currentCell, targetCell)) {
            errorType = ErrorType.INVALID_TARGET;
        } else {
            currentCell.setCard(null);
            targetCell.setCard(card);
            activePlayer.getInBattleCards().replace(card, targetCell);
            // TODO: 2019-04-30 check replace function in hashmap
            System.err.println("card" + card.getName() + " moved to " + request.getX() + "," + request.getY());
        }
    }

    public void attack() {
    }

    public void comboAttack() {
    }

    public void useSpecialPower() {
    }

    public void showHand() {
        view.show(activePlayer.handInfo());
    }

    public void insertCard() {
        Player player = activePlayer;
        ArrayList<Card> cards = new ArrayList<>(player.getHandCards().values());
        Card card = findCardInHandByName(cards, request.getCardName());
        if (card == null) {
            errorType = ErrorType.INVALID_CARDNAME;
            return;
        }
        if (!game.coordinateIsValid(request.getX(), request.getY())) {
            errorType = ErrorType.INVALID_TARGET;
            return;
        }
        Cell insertionCell = game.getCell(request.getX(), request.getY());
        if (card instanceof SpellCard) {
            errorType = ErrorType.SPELL_NOT_IMPLEMENTABLE;
        } else if (!isInsertionPossible(player, insertionCell)) {
            errorType = ErrorType.INVALID_TARGET;
        } else if (player.getMana() < card.getMana()) {
            errorType = ErrorType.NOT_ENOUGH_MANA;
        } else {
            card.setCardStatus(CardStatus.PLACED);
            player.getInBattleCards().put(card, insertionCell);
            insertionCell.setCard(card);
            player.getHandCards().remove(card.getCardId(), card);
            card.setInBattleCardId(generateInBattleCardId(card));
            player.decreaseMana(card.getMana());
            view.show(card.getName() + " with " + card.getInBattleCardId() +
                    " inserted to (" + request.getX() + ", " + request.getY() + ")");
        }
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
        return string.toString();
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
        Player player = activePlayer;
        //todo 1.cast buff, 2.check winner
        //1
        //2
        player.moveARandomCardToHand();
        game.changeTurn();
    }

    public void showGatheredCollectables() {
    }

    public void selectCollectables() {
    }

    public void showCollectableInfo() {
        if (activePlayer.getSelectedItem() != null)
            view.show(activePlayer.getSelectedItem().toString());
        else errorType = ErrorType.NO_ITEM_SELECTED;
    }

    public void useCollectable() {
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
    }

    public void endGame() {
        if (game.getWinnerPlayer() != null)
            menuType = MenuType.MAINMENU;
        errorType = ErrorType.GAME_IS_NOT_OVER;
    }

    public void showMenuOptions() {
    }

    public Game getGame() {
        return game;
    }
}
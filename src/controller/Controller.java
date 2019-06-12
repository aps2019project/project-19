package controller;

import model.*;
import model.Buff.Buff;
import model.Buff.DispellBuff;
import model.Buff.StunBuff;
import model.Cards.*;
import model.Target.Type;
import view.*;
import model.Game.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class Controller {
    private final static Controller CONTROLLER = new Controller(System.in);

    public static Controller getInstance() {
        return CONTROLLER;
    }

    public Controller(InputStream inputStream) {
        this.inputStream = inputStream;
        request = new Request(inputStream);
    }

    private InputStream inputStream;
    private Shop shop = Shop.getInstance();
    private MenuType menuType = MenuType.ACCOUNT;
    private Request request;
    private Account loggedInAccount;
    private Game game;
    private Player activePlayer;
    private Player deactivePlayer;
    private ErrorType errorType = null;
    private View view = View.getInstance();
    private Deck aiDeck;
    private Ai ai;

    public Shop getShop() {
        return shop;
    }
/*
    public void run() {
        //mainLoop:
        do {
            System.out.println("Menu: " + menuType + " ");
            request.resetProperties();
            if (ai != null && !game.isTurnOfPlayerOne()) {
                request.setCommand(ai.sendRandomRequest(game.getPlayer1()));
                System.err.println("ai requested: " + request.getCommand());
            } else request.getNewCommand();
            request.setRequestType(menuType);
            request.parseCommand();
            if (game != null) {
                if (game.isTurnOfPlayerOne()) {
                    activePlayer = game.getPlayer1();
                    deactivePlayer = game.getPlayer2();
                } else {
                    activePlayer = game.getPlayer2();
                    deactivePlayer = game.getPlayer1();
                }
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
                case SAVE:
                    save();
                    break;
                ///////////////////////////// SHOP  ///////////////////////////
                case SEARCH_IN_SHOP:
//                    searchInShop();
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
                case SELECT_MULTI_PLAYER_MODE:
                    selectMultiPlayerMode();
                    // TODO: 2019-04- test
                    break;
                case SELECT_STORY_LEVEL:
                    selectStoryLevel();
                    break;
                case CHOOSE_HERO:
                    chooseHero();
                    break;
                case SELECT_CUSTOM_GAME_GAMEMODE:
                    createCustomGame();
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
                    break;
                case MOVE_CARD:
                    moveCard();
                    break;
                case ATTACK:
                    attack();
                    break;
                case COMBO_ATTACK:
                    comboAttack();
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
                    useCollectable();
                    break;
                case SHOW_GATHERED_COLLECTABLES:
                    //todo what to do????
                    showGatheredCollectables();
                    break;
                case SHOW_CARD_INFO_IN_GRAVEYARD:
                    showCardInfoInGraveYard();
                    break;
                case SHOW_All_CARDS_IN_GRAVEYARD:
                    showAllCardsInGraveYard();
                    break;
                case USE_SPECIAL_POWER:
                    useSpecialPower();
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
                request.setErrorType(errorType);
                errorType = null;
            }
        } while (true);
    }
*/
    private void createCustomGame() {
        if (aiDeck == null) {
            errorType = ErrorType.OPPONENT_HERO_NOT_SELECTED;
        } else {
            if (loggedInAccount.getCollection().getDecks().containsKey(request.getDeckName())) {
                Player player1 = new Player(loggedInAccount,
                        loggedInAccount.getCollection().getDecks().get(request.getDeckName()));
                ai = new Ai(new Player(new Account("ai", "ai"), aiDeck));
                game = new Game(player1, ai.getPlayer());
                game.setGameMode(request.getGameMode());
                initNewGame(game.getGameMode());
                game.setPrize(1000);
            } else {
                errorType = ErrorType.DECK_NOT_EXISTS;
            }
        }
    }

    private void chooseHero() {
        Card newHero = shop.findCard(request.getCardName());
        if (newHero == null || !(newHero instanceof Hero))
            errorType = ErrorType.WRONG_HERO_NAME;
        else {
            aiDeck = new Deck(loggedInAccount.getCollection().getMainDeck());
            Card postHero = aiDeck.getHero();
            aiDeck.getCards().remove(postHero.getCardId());
            aiDeck.getCards().put(postHero.getCardId(), newHero);
        }
    }

    private void selectStoryLevel() {
        Player player1 = new Player(loggedInAccount, loggedInAccount.getCollection().getMainDeck());
        if (request.getStoryLevel() > 4 || request.getStoryLevel() < 1) {
            errorType = ErrorType.INVALID_LEVEL;
            return;
        }
        game = new Game();
        game.initStoryDecks(shop);
        aiDeck = game.getStoyLevelDecks().get(request.getStoryLevel());
        ai = new Ai(new Player(new Account("ai", "ai"), aiDeck));
        game = new Game(player1, ai.getPlayer());
        game.setGameMode(GameMode.DEATH_MATCH);
        initNewGame(game.getGameMode());
        game.setStoryPrize(request.getStoryLevel());
    }

    private void selectOpponent() {
        if (!Account.userNameIsValid(request.getUserName()) ||
                loggedInAccount.getUserName().equals(request.getUserName())) {
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

    private void selectMultiPlayerMode() {
        if (game == null) {
            errorType = ErrorType.INVALID_COMMAND;
            return;
        }
        game.setGameMode(request.getGameMode());
        if (request.getGameMode() == GameMode.CAPTURE_THE_FLAGS)
            game.setNumOfFlags(request.getNumOfFlags());
        System.err.println("game mode seted");
        game.setPrize(1000);
        initNewGame(request.getGameMode());
    }

    public void initNewGame(GameMode gameMode) {
        switch (gameMode) {
            case DEATH_MATCH:
                break;
            case KEEP_THE_FLAG:
                game.getCell(5, 3).setFlagNumber(1);
                break;
            case CAPTURE_THE_FLAGS:
                game.initFlags();
                break;
        }
        game.setDate(new Date());
        game.setHeroes(game.getPlayer1(),
                game.getCell(1, 3)).setInBattleCardId(game.getPlayer1().getAccount().getUserName());
        game.setHeroes(game.getPlayer2(),
                game.getCell(9, 3)).setInBattleCardId(game.getPlayer2().getAccount().getUserName());
        game.getPlayer1().setFirstHand().resetCardsAttackAndMoveAbility();
        game.getPlayer2().setFirstHand().resetCardsAttackAndMoveAbility();
        useUsable(game.getPlayer1(), WhenToUse.ON_GAME_START);
        useUsable(game.getPlayer2(), WhenToUse.ON_GAME_START);
        menuType = MenuType.BATTLE;
        System.err.println("game started");
    }

    public boolean createNewAccount(String userName,String password) {
        System.out.println("is creating");
        if (Account.userNameIsValid(userName)) {
            errorType = ErrorType.USERNAME_TAKEN;
            return false;
        }
        Account newAccount = new Account(userName, password);
        Account.addAccount(newAccount);
        System.out.println("account created");
        return true;
    }

    public boolean login(String userName,String password) {
        if (!Account.userNameIsValid(userName)) {
            errorType = ErrorType.INVALID_USERNAME;
            return false;
        }
        if (!Account.passwordIsValid(password, userName)) {
            errorType = ErrorType.INVALID_PASSWORD;
            return false;
        }
        loggedInAccount = Account.getAccounts().get(userName);
        menuType = MenuType.MAINMENU;
        System.out.println("logged into " + userName);
        return true;
    }

    public void showLeaderBoard() {
        view.show(Account.printAccounts());
    }

    public void save() {
        AccountManagement.saveAccount(loggedInAccount);
    }

    public void logOut() {
        menuType = MenuType.ACCOUNT;
        System.out.println("logged out from " + loggedInAccount.getUserName());
        loggedInAccount = null;
    }

    public void help() {
    }

    public void exitMenu() {
        switch (menuType) {
            case MAINMENU:
                System.exit(0);
                break;
            case ACCOUNT:
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
            case GRAVEYARD:
                menuType = MenuType.BATTLE;
                break;
            case BATTLE:
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
                        view.showStoryModes();
                    else if (menuType.equals(MenuType.SINGLE_GAME_CUSTOM_MODE)) {
                        view.showHeros();
                    }
                    return;
                }
                break;
            case BATTLE:
                if (request.getEnteringMenu() == MenuType.GRAVEYARD)
                    menuType = MenuType.GRAVEYARD;
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

    public ArrayList searchInShop(String name) {
        ArrayList<Object> results = new ArrayList<>();
        for (Card card : shop.getCards()) {
            if (card.getName().matches(name +"[\\w ]*"))
                results.add(card);
        }
        for (Item item : shop.getItems()) {
            if (item.getName().matches(name + "[\\w ]*"))
                results.add(item);
        }
        return results;
        //todo:all arraylists must set to hashmap
        //duplicated code with existsInShop in Shop class
//        errorType = ErrorType.NOT_FOUND;
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
        SoldierCard card = (SoldierCard) activePlayer.getSelectedCard();
        for (Buff buff : card.getBuffs()) {
            if (buff instanceof StunBuff) {
                errorType = ErrorType.CARD_IS_STUNNED;
                return;
            }
        }
        if (card.isMovedThisTurn()) {
            errorType = ErrorType.CAN_NOT_MOVE_AGAIN;
            return;
        }
        if (!game.coordinateIsValid(request.getX(), request.getY())) {
            errorType = ErrorType.INVALID_TARGET;
            return;
        }
        Cell currentCell = activePlayer.getInBattleCards().get(card);
        Cell targetCell = game.getCell(request.getX(), request.getY());
        if (targetCell.getCard() != null) {
            errorType = ErrorType.INVALID_TARGET;
        } else if (currentCell.getManhattanDistance(targetCell) > 2 || game.pathIsBlocked(currentCell, targetCell)) {
            errorType = ErrorType.INVALID_TARGET;
        } else {
            //removing old cell buffs
            card.removeCellBuffs();
            currentCell.setCard(null);
            targetCell.setCard(card);
            activePlayer.getInBattleCards().replace(card, targetCell);
            if (!game.getGameMode().equals(GameMode.DEATH_MATCH))
                card.pickUpFlags(targetCell);
            System.err.println("card" + card.getName() + " moved to " + request.getX() + "," + request.getY());
            //adding cell buff to card
            if (targetCell.getBuff() != null) {
                targetCell.getBuff().setForCell(true);
                card.addBuffToTarget(targetCell.getBuff(), card);
                card.castOnMomentBUffs(card);
                card.checkBUffTiming(card, false);
            }
            card.setMovedThisTurn(true);
        }
    }

    public void comboAttack() {
        for (String comboAttackerId : request.getComboAttackers()) {
            if (!activePlayer.containsCardInBattle(comboAttackerId)) {
                errorType = ErrorType.INVALID_CARD_ID;
                return;
            }
            SoldierCard attacker = (SoldierCard) activePlayer.getInBattleCard(comboAttackerId);
            if (!attacker.getAbilityCastTime().equals(AbilityCastTime.COMBO)) {
                continue;
            }
            activePlayer.setSelectedCard(attacker);
            attack();
        }
    }

    public void attack() {
        if (!activePlayer.isAnyCardSelected()) {
            errorType = ErrorType.CARD_NOT_SELECTED;
            return;
        }
        SoldierCard attacker = (SoldierCard) activePlayer.getSelectedCard();
        if (attacker.isAttackedThisTurn()) {
            errorType = ErrorType.CAN_NOT_ATTACK_AGAIN;
            return;
        }
        String defenderId = request.getInBattleCardId();
        if (!deactivePlayer.containsCardInBattle(defenderId)) {
            errorType = ErrorType.INVALID_CARD_ID;
            return;
        }
        SoldierCard defender = (SoldierCard) deactivePlayer.getInBattleCard(defenderId);
        Cell attackerCell = activePlayer.getInBattleCards().get(attacker);
        Cell defenderCell = deactivePlayer.getInBattleCards().get(defender);
        if (!attacker.targetIsInRange(attackerCell, defenderCell)) {
            errorType = ErrorType.TARGET_NOT_IN_RANGE;
            return;
        }
        attacker.attack(defender);
        useUsable(activePlayer, WhenToUse.ON_ATTACK);
        attacker.setAttackedThisTurn(true);
        System.err.println("attacked");
        if (defender.targetIsInRange(defenderCell, attackerCell)) {
            defender.counterAttack(attacker);
            System.err.println("counter attaacked");
            checkDeadCard(activePlayer, attacker);
        }
        checkDeadCard(deactivePlayer, defender);
        if (game.gameIsOver())
            endTurn();
    }

    public void useSpecialPower() {
        if (!activePlayer.isAnyCardSelected()) {
            errorType = ErrorType.CARD_NOT_SELECTED;
            return;
        }
        SoldierCard card = (SoldierCard) activePlayer.getSelectedCard();
        if (card instanceof Hero) {
            if (((Hero) card).getSpecialPower() == null || ((Hero) card).getCoolDown() == 0) {
                errorType = ErrorType.NO_SPECIAL_POWER;
            } else if (!card.getTarget().checkTargetValidation(game, activePlayer, deactivePlayer,
                    request.getX(), request.getY())) {
                errorType = ErrorType.INVALID_TARGET;
            } else if (activePlayer.getMana() < card.getMana()) {
                errorType = ErrorType.NOT_ENOUGH_MANA;
            } else if (((Hero) card).getCoolDownWaiting() < ((Hero) card).getCoolDown()) {
                errorType = ErrorType.NOT_ENOUGH_COOLDOWN;
            } else {
                castHeroSpecialPower((Hero) card);
                ((Hero) card).setCoolDownWaiting(0);
            }
        }
        if (card instanceof Minion) {
            if (((Minion) card).getAbilities() == null) {
                errorType = ErrorType.NO_SPECIAL_POWER;
                return;
            }
            if (!card.getTarget().checkTargetValidation(game, activePlayer,
                    deactivePlayer, request.getX(), request.getY())) {
                errorType = ErrorType.INVALID_TARGET;
                return;
            }
            if (card.getAbilityCastTime() != null && (
                    card.getAbilityCastTime().equals(AbilityCastTime.ON_SPAWN) ||
                            card.getAbilityCastTime().equals(AbilityCastTime.ON_TURN))
                    && !card.isAttackedThisTurn()) {
                castMinionSpecialPower((Minion) card);
            }
        }
    }

    private void castMinionSpecialPower(Minion card) {
        Cell cell = game.FindCardCellInGame(card);
        switch (card.getTarget().getSoldierTargetType()) {
            case ITSELF:
                addMinionSpecialPowersToTarget(card, card);
                break;
            case ALL_MINIONS_TO_2_CELLS_FURTHER:
                for (int i = cell.getXCoordinate() - 2; i < cell.getXCoordinate() + 2; i++) {
                    if (game.getCell(i, cell.getYCoordinate()) != null && i != cell.getXCoordinate() &&
                            game.getCell(i, cell.getYCoordinate()).getCard() instanceof Minion) {
                        addMinionSpecialPowersToTarget(card,
                                (SoldierCard) game.getCell(i, cell.getYCoordinate()).getCard());
                    }
                }
                for (int i = cell.getYCoordinate() - 2; i < cell.getYCoordinate() + 2; i++) {
                    if (game.getCell(cell.getXCoordinate(), i) != null && i != cell.getYCoordinate() &&
                            game.getCell(cell.getXCoordinate(), i).getCard() instanceof Minion) {
                        addMinionSpecialPowersToTarget(card,
                                (SoldierCard) game.getCell(cell.getXCoordinate(), i).getCard());
                    }
                }
                break;
            case ALL_FRIENDLY_MINIONS:
                for (Card target : activePlayer.getInBattleCards().keySet()) {
                    if (target instanceof Minion) {
                        addMinionSpecialPowersToTarget(card, ((SoldierCard) target));
                    }
                }
                break;
            case ALL_ENEMY_MINIONS_AROUND:
                findAroundMinons(card, cell, deactivePlayer);
                break;
            case ALL_FRIENDLY_MINIONS_AROUND_AND_ITSELF:
                findAroundMinons(card, cell, activePlayer);
                break;
            case ALL_MINIONS_AROUND:
                for (int i = cell.getXCoordinate() - 1; i <= cell.getXCoordinate() + 1; i++) {
                    for (int j = cell.getYCoordinate() - 1; j <= cell.getYCoordinate() + 1; j++) {
                        if (game.coordinateIsValid(i, j) && game.getCell(i, j).getCard() != null
                                && game.getCell(i, j).getCard() instanceof Minion &&
                                game.getCell(i, j).getCard() != card) {
                            addMinionSpecialPowersToTarget(card, (SoldierCard) game.getCell(i, j).getCard());
                        }
                    }
                }
                break;
        }
    }

    private void findAroundMinons(Minion card, Cell cell, Player player) {
        for (int i = cell.getXCoordinate() - 1; i <= cell.getXCoordinate() + 1; i++) {
            for (int j = cell.getYCoordinate() - 1; j <= cell.getYCoordinate() + 1; j++) {
                if (game.coordinateIsValid(i, j) && game.getCell(i, j).getCard() != null
                        && game.getCell(i, j).getCard() instanceof Minion &&
                        player.getInBattleCards().containsKey(game.getCell(i, j).getCard())) {
                    addMinionSpecialPowersToTarget(card, (SoldierCard) game.getCell(i, j).getCard());
                }
            }
        }
    }

    private void castHeroSpecialPower(Hero card) {
        if (card.getTarget().getType().equals(Type.AREA)) {
            int areaSize = card.getTarget().getAreaSize();
            for (int i = request.getX(); i < request.getX() + areaSize; i++) {
                for (int j = request.getY(); j < request.getY() + areaSize; j++) {
                    if (game.coordinateIsValid(i, j)) {
                        if (card.getTarget().isIsaffectingCell()) {
                            game.getCell(i, j).setBuff(card.getSpecialPower());
                        }
                    }
                }
            }

        } else {
            switch (card.getTarget().getSoldierTargetType()) {
                case FRIENDLY_HERO:
                case ITSELF:
                    addHeroSpecialPowerToTarget(card, card);
                    break;
                case ALL_ENEMIES:
                    for (Card soldier : deactivePlayer.getInBattleCards().keySet()) {
                        addHeroSpecialPowerToTarget(card, (SoldierCard) soldier);
                    }
                    break;
                case ONE_ENEMY:
                case ONE_SOLDIER:
                    Cell cell = game.getCell(request.getX(), request.getY());
                    addHeroSpecialPowerToTarget(card, (SoldierCard) cell.getCard());
                    break;
                case ALL_SOLDIERS_IN_THE_ROW_OF_FRIENDLY_HERO:
                    for (int i = 1; i <= 9; i++) {
                        int j = activePlayer.getInBattleCards().get(activePlayer.getHero()).getYCoordinate();
                        int x = activePlayer.getInBattleCards().get(activePlayer.getHero()).getXCoordinate();
                        if (game.getCell(i, j) != null && i != x) {
                            addHeroSpecialPowerToTarget(card, (SoldierCard) game.getCell(i, j).getCard());
                        }
                    }
                    break;
            }
        }
    }

    private void addMinionSpecialPowersToTarget(Minion minion, SoldierCard target) {
        for (Buff buff : minion.getAbilities()) {
            minion.addBuffToTarget(buff, target);
        }
        target.castOnMomentBUffs(target);
        target.checkBUffTiming(target, false);
    }

    private void addHeroSpecialPowerToTarget(Hero hero, SoldierCard target) {
        hero.addBuffToTarget(hero.getSpecialPower(), target);
        target.castOnMomentBUffs(target);
        target.checkBUffTiming(target, false);
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
        if (request.isHasXY() && !game.coordinateIsValid(request.getX(), request.getY())) {
            errorType = ErrorType.INVALID_TARGET;
            return;
        }
        if (player.getMana() < card.getMana()) {
            errorType = ErrorType.NOT_ENOUGH_MANA;
            return;
        }
        Cell insertionCell = game.getCell(request.getX(), request.getY());
        if (card instanceof SpellCard) {
            if (!((SpellCard) card).getTargetArea().checkTargetValidation(game,
                    activePlayer, deactivePlayer, request.getX(), request.getY())) {
                errorType = ErrorType.INVALID_TARGET;
                return;
            }
            castSpell((SpellCard) card);
            card.setCardStatus(CardStatus.IN_GRAVEYARD);
            activePlayer.getGraveYard().put(card.getInBattleCardId(), card);
        } else {
            if (!isInsertionPossible(player, insertionCell)) {
                errorType = ErrorType.INVALID_TARGET;
            } else {
                card.setCardStatus(CardStatus.PLACED);
                ((SoldierCard) card).setAttackedThisTurn(false).setMovedThisTurn(false);
                player.getInBattleCards().put(card, insertionCell);
                insertionCell.setCard(card);
                card.setInBattleCardId(generateInBattleCardId(card));
                view.show(card.getName() + " with " + card.getInBattleCardId() +
                        " inserted to (" + request.getX() + ", " + request.getY() + ")");
            }
            useUsable(activePlayer, WhenToUse.ON_SPAWN);
            player.getHandCards().remove(card.getCardId(), card);
            player.decreaseMana(card.getMana());
            checkDeadCards(activePlayer);
            checkDeadCards(deactivePlayer);
        }
    }

    private void castSpell(SpellCard card) {
        if (card.getTargetArea().getType().equals(Type.AREA)) {
            int areaSize = card.getTargetArea().getAreaSize();
            for (int i = request.getX(); i < request.getX() + areaSize; i++) {
                for (int j = request.getY(); j < request.getY() + areaSize; j++) {
                    if (game.coordinateIsValid(i, j)) {
                        if (card.getTargetArea().isIsaffectingCell()) {
                            game.getCell(i, j).setBuff(card.getBuff().get(0));
                        } else if (game.getCell(i, j).getCard() != null) {
                            if (card.getName().equals("Area Dispel")) {
                                DispellBuff buff = (DispellBuff) card.getBuff().get(0);
                                Card soldier = game.getCell(i, j).getCard();
                                ((SoldierCard) soldier).getBuffs().add(buff);
                                if (activePlayer.getInBattleCards().containsKey(soldier))
                                    buff.cancelNegativeBuffs((SoldierCard) soldier);
                                else
                                    buff.cancelPositiveBuffs((SoldierCard) soldier);
                            } else {
                                Cell cell = game.getCell(request.getX(), request.getY());
                                castSpellBuffsOnTarget(card, (SoldierCard) cell.getCard());
                            }
                        }
                    }
                }
            }
        } else {
            switch (card.getTargetArea().getSoldierTargetType()) {
                //cases are handled with break points
                case ONE_ENEMY_MINION:
                case ONE_FRIENDLY_MINION:
                case ONE_SOLDIER:
                case ONE_FRIENDLY_SOLDIER:
                case ONE_ENEMY:
                    Cell cell = game.getCell(request.getX(), request.getY());
                    castSpellBuffsOnTarget(card, (SoldierCard) cell.getCard());
                    break;
                case ENEMY_HERO:
                    castSpellBuffsOnTarget(card, (SoldierCard) deactivePlayer.getHero());
                    break;
                case ALL_ENEMIES:
                    for (Card soldier : deactivePlayer.getInBattleCards().keySet()) {
                        castSpellBuffsOnTarget(card, (SoldierCard) soldier);
                    }
                    break;
                case FRIENDLY_HERO:
                    castSpellBuffsOnTarget(card, (SoldierCard) activePlayer.getHero());
                    break;
                case ALL_FRIENDLY_SOLDIERS:
                    castSpellBuffsOnTarget(card, (SoldierCard) activePlayer.getHero());
                    //don't add break
                case ALL_FRIENDLY_MINIONS:
                    for (Card minion : activePlayer.getInBattleCards().keySet()) {
                        if (!(minion instanceof Hero)) {
                            castSpellBuffsOnTarget(card, (SoldierCard) minion);
                        }
                    }
                    break;
                case ONE_RANDOM_MINION_AROUND_FRIENDLY_HERO:
                    Cell heroCell = activePlayer.getInBattleCards().get(activePlayer.getHero());
                    for (int i = heroCell.getXCoordinate() - 1; i <= heroCell.getXCoordinate() + 1; i++) {
                        for (int j = heroCell.getYCoordinate() - 1; j <= heroCell.getYCoordinate() + 1; j++) {
                            if (game.coordinateIsValid(i, j) && i != heroCell.getXCoordinate() &&
                                    j != heroCell.getYCoordinate() &&
                                    activePlayer.getInBattleCards().containsKey(game.getCell(i, j).getCard())) {
                                castSpellBuffsOnTarget(card, (SoldierCard) game.getCell(i, j).getCard());
                            }
                        }
                    }
                    break;
                case ALL_ENEMIES_IN_A_COLUMN:
                    for (int i = 1; i <= 5; i++) {
                        Card soldier = game.getCell(request.getX(), i).getCard();
                        if (soldier != null && deactivePlayer.getInBattleCards().containsKey(soldier)) {
                            castSpellBuffsOnTarget(card, (SoldierCard) soldier);
                        }
                    }
                    break;
            }
        }
    }

    private void castSpellBuffsOnTarget(SpellCard card, SoldierCard target) {
        for (Buff buff : card.getBuff()) {
            target.addBuffToTarget(buff, target);
        }
        target.castOnMomentBUffs(target);
        target.checkBUffTiming(target, false);
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
        return string.toString().replaceAll(" ", "_");
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
        activePlayer.setSelectedItem(null);
        activePlayer.setSelectedCard(null);
        Player player = activePlayer;
        checkBuffsAtTheEndOfTurn(activePlayer);
        checkBuffsAtTheEndOfTurn(deactivePlayer);
        player.resetCardsAttackAndMoveAbility();
        player.moveARandomCardToHand();
        checkDeadCards(activePlayer);
        checkDeadCards(deactivePlayer);
        if (game.playerWithFlag() != null)
            game.playerWithFlag().increaseNumberOfTurnWithFlag();
        if (!game.gameIsOver()) {
            game.changeTurn();
            castPassiveBuffs();
        } else
            endGame();
    }

    private void castPassiveBuffs() {
        for (Card card : activePlayer.getInBattleCards().keySet()) {
            if (((SoldierCard) card).getAbilityCastTime() != null &&
                    ((SoldierCard) card).getAbilityCastTime().equals(AbilityCastTime.PASSIVE)) {
                castMinionSpecialPower((Minion) card);
            }
        }
        for (Card card : deactivePlayer.getInBattleCards().keySet()) {
            if (((SoldierCard) card).getAbilityCastTime() != null &&
                    ((SoldierCard) card).getAbilityCastTime().equals(AbilityCastTime.PASSIVE)) {
                castMinionSpecialPower((Minion) card);
            }
        }
    }

    private void checkBuffsAtTheEndOfTurn(Player player) {
        ArrayList<Card> cards = new ArrayList<>(player.getInBattleCards().keySet());
        for (Card card : cards) {
            if (card instanceof SoldierCard) {
                ((SoldierCard) card).checkBUffTiming((SoldierCard) card, true);
            }
        }
    }

    public void showGatheredCollectables() {
        for (Item item : activePlayer.getDeckCards().getItems().values()) {
            if (item.getType().equals(ItemTypes.COLLECTABLE)) {
                view.show(item.toString());
            }
        }
    }

    public void selectCollectables() {
        Item item = activePlayer.getItems().get(request.getItemID());
        activePlayer.setSelectedItem(item);
    }

    public void showCollectableInfo() {
        if (activePlayer.getSelectedItem() != null)
            view.show(activePlayer.getSelectedItem().toString());
        else errorType = ErrorType.NO_ITEM_SELECTED;
    }

    public void useCollectable() {
        if (!activePlayer.isAnyItemSelected()) {
            errorType = ErrorType.NO_ITEM_SELECTED;
            return;
        }
        if (findTarget(activePlayer.getSelectedItem()) == null)
            errorType = ErrorType.INVALID_TARGET;
        else activePlayer.getSelectedItem().castItemsSpell(findTarget(activePlayer.getSelectedItem()));
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
        view.show(activePlayer.getGraveYard().get(request.getInBattleCardId()).toString());
    }

    public void showAllCardsInGraveYard() {
        for (Card card : activePlayer.getGraveYard().values()) {
            view.show(card.toString());
        }
    }

    public void endGame() {
        if (game.getWinnerPlayer() == null) {
            view.show("draw!");
        } else {
            view.show(game.getWinnerPlayer().getAccount().getUserName()
                    + " won the game and his prize is: " + game.getPrize());
            game.getWinnerPlayer().getAccount().increaseMoney(game.getPrize());

            do {
                request.resetProperties();
                request.getNewCommand();
            } while (!request.getCommand().equals("end game"));
        }
        game.getPlayer1().getAccount().getMatchHistory().add(game);
        game.getPlayer2().getAccount().getMatchHistory().add(game);
        menuType = MenuType.MAINMENU;
        game = null;
        ai = null;
        aiDeck = null;
    }

    public void showMenuOptions() {
    }

    public Game getGame() {
        return game;
    }

    public void checkDeadCard(Player player, SoldierCard card) {
        if ((card.getName().equals("oghab") && card.getHp() < 0) ||
                (card.getHp() <= 0 && !card.getName().equals("oghab"))) {
            if (card.getName().equals("oghab") && card.getHp() < 0)
                if (card.getAbilityCastTime() != null && card.getAbilityCastTime().equals(AbilityCastTime.ON_DEATH)) {
                    castMinionSpecialPower((Minion) card);
                }
            if (card.getHp() <= 0) {
                if (card.getAbilityCastTime() != null && card.getAbilityCastTime().equals(AbilityCastTime.ON_DEATH)) {
                    castMinionSpecialPower((Minion) card);
                }
                useUsable(activePlayer, WhenToUse.ON_DEATH);
                Cell cell = player.getInBattleCards().get(card);
                if (game.getGameMode().equals(GameMode.KEEP_THE_FLAG) && card.getFlagNumber() != 0)
                    player.setNumberOfTurnsWithFlag(0);
                card.dropFlags(cell);
                cell.setCard(null);
                player.getInBattleCards().remove(card);
                player.getGraveYard().put(card.getInBattleCardId(), card);
                card.setCardStatus(CardStatus.IN_GRAVEYARD);
                System.out.println(card.getInBattleCardId() + " died in battle!");
            }
        }
    }

    public void checkDeadCards(Player player) {
        ArrayList<Card> cards = new ArrayList<>(player.getInBattleCards().keySet());
        for (int i = cards.size() - 1; i >= 0; i--) {
            checkDeadCard(player, (SoldierCard) cards.get(i));
        }
    }

    public SoldierCard findTarget(Item item) {
        ArrayList<Card> myInBattleCards = new ArrayList<>(activePlayer.getInBattleCards().keySet());
        ArrayList<Card> opponentInBattleCards = new ArrayList<>(deactivePlayer.getInBattleCards().keySet());
        switch (item.getTarget().getSoldierTargetType()) {
            case ONE_FRIENDLY_SOLDIER:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof SoldierCard) {
                        return ((SoldierCard) card);
                    }
                }
                break;
            case FRIENDLY_RANGED_AND_HYBRID:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof SoldierCard && (((SoldierCard) card).getType().equals(SoldierTypes.HYBRID) ||
                            ((SoldierCard) card).getType().equals(SoldierTypes.RANGED))) {
                        return ((SoldierCard) card);
                    }
                }
                break;
            case ENEMY_HERO_RANGED_AND_HYBRID:
                Collections.shuffle(opponentInBattleCards);
                for (Card card : opponentInBattleCards) {
                    if (card instanceof Hero && (((Hero) card).getType().equals(SoldierTypes.RANGED) ||
                            ((Hero) card).getType().equals(SoldierTypes.HYBRID))) {
                        return ((Hero) card);
                    }
                }
                break;
            case ONE_FRIENDLY_MINION:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof Minion) {
                        return ((Minion) card);
                    }
                }
                break;
            case FRIENDLY_MELEE:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof SoldierCard && ((SoldierCard) card).getType().equals(SoldierTypes.MELEE)) {
                        return ((SoldierCard) card);
                    }
                }
                break;
            case FRIENDLY_HERO:
                for (Card card : myInBattleCards) {
                    if (card instanceof Hero)
                        return ((Hero) card);
                }
                break;
            case FRIENDLY_HERO_RANGED_AND_HYBRID:
                for (Card card : myInBattleCards) {
                    if (card instanceof Hero && (((Hero) card).getType().equals(SoldierTypes.HYBRID) ||
                            ((Hero) card).getType().equals(SoldierTypes.RANGED)))
                        return ((Hero) card);
                }
                break;
            case ONE_ENEMY:
                Collections.shuffle(opponentInBattleCards);
                for (Card card : opponentInBattleCards) {
                    if (card instanceof SoldierCard)
                        return ((SoldierCard) card);
                }
                break;
            case ENEMY_HERO:
                for (Card card : opponentInBattleCards) {
                    if (card instanceof Hero)
                        return ((Hero) card);
                }
                break;
            case ONE_SOLDIER:
                Collections.shuffle(myInBattleCards);
                for (Card card : myInBattleCards) {
                    if (card instanceof SoldierCard)
                        return ((SoldierCard) card);
                }
                break;
            case ITSELF:
                return ((SoldierCard) activePlayer.getSelectedCard());
        }
        return null;
    }

    public void useUsable(Player player, WhenToUse whenToUse) {
        for (Item item : player.getItems().values()) {
            if (item.getWhenToUse().equals(whenToUse)) {
                item.castItemsSpell(findTarget(item));
            }
        }

    }

    public Request getRequest() {
        return request;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
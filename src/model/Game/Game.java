package model.Game;

import model.*;
import model.Cards.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Game {
    private final int length = 9;
    private final int width = 5;
    private Cell[][] cells = new Cell[width][length];

    {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(j + 1, i + 1);
            }
        }
    }

    private ArrayList<Item> items = new ArrayList<>();
    private int numOfFlags;
    private Player player1;
    private Player player2;
    private boolean isTurnOfPlayerOne;
    private int turnNumber;
    private Date date;
    private Player winnerPlayer;
    private GameMode gameMode;
    private int prize;
    private ArrayList<Deck> stoyLevelDecks = new ArrayList<>(
    );

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        // TODO: 2019-04-29 set date
        this.isTurnOfPlayerOne = true;
        this.turnNumber = 1;
    }

    public Game() {
    }

    //todo set prize
    public void setStoryPrize(int level) {
        switch (level) {
            case 1:
                this.prize = 500;
                break;
            case 2:
                this.prize = 1000;
                break;
            case 3:
                this.prize = 1500;
        }
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public boolean isTurnOfPlayerOne() {
        return isTurnOfPlayerOne;
    }

    public void setTurnOfPlayerOne(boolean turnOfPlayerOne) {
        isTurnOfPlayerOne = turnOfPlayerOne;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public Player getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(Player winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell getCell(int x, int y) {
        if (y - 1 >= width || x - 1 >= length)
            return null;
        return cells[y - 1][x - 1];
    }

    public boolean coordinateIsValid(int x, int y) {
        return getCell(x, y) != null;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getNumOfFlags() {
        return numOfFlags;
    }

    public void setNumOfFlags(int numOfFlags) {
        this.numOfFlags = numOfFlags;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public int getPrize() {
        return prize;
    }

    public void changeTurn() {
        Player currentPlayer;
        if (isTurnOfPlayerOne) {
            isTurnOfPlayerOne = false;
            currentPlayer = player2;
        } else {
            isTurnOfPlayerOne = true;
            currentPlayer = player1;
        }
        turnNumber++;
        if (turnNumber <= 14) {
            currentPlayer.increaseMaxMana();
            currentPlayer.setMana(currentPlayer.getMaxMana());
            System.err.println(currentPlayer.getMana());
        }
    }

    public int totalGameFlagsNmber() {
        int result = 0;
        for (Cell[] inRowCell : cells) {
            for (Cell cell : inRowCell) {
                result += cell.getFlagNumber();
            }
        }
        return result;
    }

    public boolean pathIsBlocked(Cell srcCell, Cell targetCell) {
        int xDeffrence = targetCell.getXCoordinate() - srcCell.getXCoordinate();
        int yDeffrence = targetCell.getYCoordinate() - srcCell.getYCoordinate();
        if (xDeffrence != 0 && yDeffrence != 0)
            return false;
        while (xDeffrence != 0) {
            if (cells[srcCell.getYCoordinate() - 1][srcCell.getXCoordinate() + xDeffrence - 1].getCard() != null)
                return true;
            if (xDeffrence < 0)
                xDeffrence++;
            if (xDeffrence > 0)
                xDeffrence--;
        }
        while (yDeffrence != 0) {
            if (cells[srcCell.getYCoordinate() + yDeffrence - 1][srcCell.getXCoordinate() - 1].getCard() != null)
                return true;
            if (yDeffrence < 0)
                yDeffrence++;
            if (yDeffrence > 0)
                yDeffrence--;
        }
        return false;
    }

    public Hero setHeroes(Player player, Cell cell) {
        Card hero = player.getDeckCards().getHero();
        player.getDeckCards().getCards().remove(hero.getCardId());
        cell.setCard(hero);
        player.getInBattleCards().put(hero, cell);
        return (Hero) hero;
    }

    public String toStringDeathMatchMode() {
        StringBuilder toString = new StringBuilder();
        toString.append("player one hero's health: ");
        for (Card card : player1.getInBattleCards().keySet()) {
            if (card instanceof Hero) {
                toString.append(((Hero) card).getHp()).append("\n");
                break;
            }
        }
        toString.append("player two hero's health: ");
        for (Card card : player2.getInBattleCards().keySet()) {
            if (card instanceof Hero) {
                toString.append(((Hero) card).getHp());
                break;
            }
        }
        return toString.toString();
    }

    public String toStringKeepFlag() {
        StringBuilder toString = new StringBuilder();
        if (player1.getMinionsWithFlag().size() != 0)
            toString.append("player ").append(player1.getAccount().getUserName()).append(" has flag in coordinate: ")
                    .append(player1.getInBattleCards().get(player1.getMinionsWithFlag().get(0)).toString());
        else if (player2.getMinionsWithFlag().size() != 0)
            toString.append("player ").append(player2.getAccount().getUserName()).append(" has flag in coordinate: ")
                    .append(player2.getInBattleCards().get(player2.getMinionsWithFlag().get(0)).toString());
        else {
            toString.append("no one has flag. flag is in coordinate : ");
            toString.append(toStringFlagsGame());
        }
        return toString.toString();
    }

    public String toStringCaptureFlags() {
        return player1.toStringFlags() + player2.toStringFlags() +
                "free flags are in coordinates: " + toStringFlagsGame();
    }

    private String toStringFlagsGame() {
        StringBuilder toString = new StringBuilder();
        for (Cell[] cellInRow : cells) {
            for (Cell cell : cellInRow) {
                if (cell.getFlagNumber() != 0) {
                    toString.append("\t").append(cell.toString());
                }
            }
        }
        return toString.toString();
    }

    public boolean gameIsOver() {
        switch (gameMode) {
            case CAPTURE_THE_FLAGS:
                winnerPlayer = captureTheFlagsWinner();
                break;
            case KEEP_THE_FLAG:
                winnerPlayer = keepTheFlagWinner();
                break;
            case DEATH_MATCH:
                if (draw()) {
                    return true;
                }
                winnerPlayer = deathMatchWinner();
                break;
        }
        return !(winnerPlayer == null);
    }

    private Player deathMatchWinner() {
        if (player1.heroIsDead())
            return player2;
        if (player2.heroIsDead())
            return player1;
        return null;
    }

    private Player keepTheFlagWinner() {
        if (player1.getNumberOFTurnsWithFlag() == 6)
            return player1;
        if (player2.getNumberOFTurnsWithFlag() == 6)
            return player2;
        return null;
    }

    private Player captureTheFlagsWinner() {
        if (2 * player1.totalPlayersFlagsNumber() > totalGameFlagsNmber())
            return player1;
        if (2 * player2.totalPlayersFlagsNumber() > totalGameFlagsNmber())
            return player2;
        return null;
    }

    private boolean draw() {
        return player1.heroIsDead() && player2.heroIsDead();
    }

    public void initFlags() {
        Random random = new Random();
        for (int i = 0; i < numOfFlags; i++) {
            int x = random.nextInt(length);
            int y = random.nextInt(width);
            cells[y][x].setFlagNumber(cells[y][x].getFlagNumber() + 1);
        }
    }

    public Player playerWithFlag() {
        if (player1.getMinionsWithFlag().size() != 0)
            return player1;
        else if (player2.getMinionsWithFlag().size() != 0)
            return player2;
        else return null;
    }

    public void initStoryDecks(Shop shop) {
        Deck level1 = new Deck("level1");
        level1.getCards().put(1, new Minion((Minion) shop.findCard(101)));
        level1.getCards().put(2, new Minion((Minion) shop.findCard(109)));
        level1.getCards().put(3, new Minion((Minion) shop.findCard(111)));
        level1.getCards().put(4, new Minion((Minion) shop.findCard(111)));
        level1.getCards().put(5, new Minion((Minion) shop.findCard(113)));
        level1.getCards().put(6, new Minion((Minion) shop.findCard(117)));
        level1.getCards().put(7, new Minion((Minion) shop.findCard(118)));
        level1.getCards().put(8, new Minion((Minion) shop.findCard(121)));
        level1.getCards().put(9, new Minion((Minion) shop.findCard(122)));
        level1.getCards().put(10, new Minion((Minion) shop.findCard(126)));
        level1.getCards().put(11, new Minion((Minion) shop.findCard(138)));
        level1.getCards().put(12, new Minion((Minion) shop.findCard(136)));
        level1.getCards().put(13, new Minion((Minion) shop.findCard(140)));
        level1.getCards().put(14, new SpellCard((SpellCard) shop.findCard(201)));
        level1.getCards().put(15, new SpellCard((SpellCard) shop.findCard(207)));
        level1.getCards().put(16, new SpellCard((SpellCard) shop.findCard(210)));
        level1.getCards().put(17, new SpellCard((SpellCard) shop.findCard(211)));
        level1.getCards().put(18, new SpellCard((SpellCard) shop.findCard(212)));
        level1.getCards().put(19, new SpellCard((SpellCard) shop.findCard(218)));
        level1.getCards().put(20, new SpellCard((SpellCard) shop.findCard(220)));
        level1.getCards().put(21, new Hero((Hero) shop.findCard(301)));
        level1.getItems().put(22, new Item(shop.findItem(4)));

        Deck level2 = new Deck("level2");
        level2.getCards().put(1, new Minion((Minion) shop.findCard(102)));
        level2.getCards().put(2, new Minion((Minion) shop.findCard(103)));
        level2.getCards().put(3, new Minion((Minion) shop.findCard(105)));
        level2.getCards().put(4, new Minion((Minion) shop.findCard(108)));
        level2.getCards().put(5, new Minion((Minion) shop.findCard(112)));
        level2.getCards().put(6, new Minion((Minion) shop.findCard(115)));
        level2.getCards().put(7, new Minion((Minion) shop.findCard(115)));
        level2.getCards().put(8, new Minion((Minion) shop.findCard(119)));
        level2.getCards().put(9, new Minion((Minion) shop.findCard(123)));
        level2.getCards().put(10, new Minion((Minion) shop.findCard(127)));
        level2.getCards().put(11, new Minion((Minion) shop.findCard(130)));
        level2.getCards().put(12, new Minion((Minion) shop.findCard(133)));
        level2.getCards().put(13, new Minion((Minion) shop.findCard(139)));
        level2.getCards().put(14, new SpellCard((SpellCard) shop.findCard(202)));
        level2.getCards().put(15, new SpellCard((SpellCard) shop.findCard(203)));
        level2.getCards().put(16, new SpellCard((SpellCard) shop.findCard(205)));
        level2.getCards().put(17, new SpellCard((SpellCard) shop.findCard(209)));
        level2.getCards().put(18, new SpellCard((SpellCard) shop.findCard(208)));
        level2.getCards().put(19, new SpellCard((SpellCard) shop.findCard(213)));
        level2.getCards().put(20, new SpellCard((SpellCard) shop.findCard(219)));
        level2.getCards().put(21, new Hero((Hero) shop.findCard(305)));
        level2.getItems().put(22, new Item(shop.findItem(2)));

        Deck level3 = new Deck("level3");
        level3.getCards().put(1, new Minion((Minion) shop.findCard(106)));
        level3.getCards().put(2, new Minion((Minion) shop.findCard(107)));
        level3.getCards().put(3, new Minion((Minion) shop.findCard(110)));
        level3.getCards().put(4, new Minion((Minion) shop.findCard(114)));
        level3.getCards().put(5, new Minion((Minion) shop.findCard(116)));
        level3.getCards().put(6, new Minion((Minion) shop.findCard(116)));
        level3.getCards().put(7, new Minion((Minion) shop.findCard(120)));
        level3.getCards().put(8, new Minion((Minion) shop.findCard(124)));
        level3.getCards().put(9, new Minion((Minion) shop.findCard(125)));
        level3.getCards().put(10, new Minion((Minion) shop.findCard(128)));
        level3.getCards().put(11, new Minion((Minion) shop.findCard(129)));
        level3.getCards().put(12, new Minion((Minion) shop.findCard(131)));
        level3.getCards().put(13, new Minion((Minion) shop.findCard(134)));
        level3.getCards().put(14, new SpellCard((SpellCard) shop.findCard(206)));
        level3.getCards().put(15, new SpellCard((SpellCard) shop.findCard(210)));
        level3.getCards().put(16, new SpellCard((SpellCard) shop.findCard(212)));
        level3.getCards().put(17, new SpellCard((SpellCard) shop.findCard(214)));
        level3.getCards().put(18, new SpellCard((SpellCard) shop.findCard(215)));
        level3.getCards().put(19, new SpellCard((SpellCard) shop.findCard(216)));
        level3.getCards().put(20, new SpellCard((SpellCard) shop.findCard(217)));
        level3.getCards().put(21, new Hero((Hero) shop.findCard(307)));
        level3.getItems().put(22, new Item(shop.findItem(3)));

        stoyLevelDecks.add(level1);
        stoyLevelDecks.add(level2);
        stoyLevelDecks.add(level3);
    }

    public ArrayList<Deck> getStoyLevelDecks() {
        return stoyLevelDecks;
    }
}


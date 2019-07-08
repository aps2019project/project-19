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
    private ArrayList<Deck> storyLevelDecks = new ArrayList<>(
    );

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.isTurnOfPlayerOne = true;
        this.turnNumber = 1;
    }

    public Game() {
    }

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
    public Player getPlayer(String userName){
        if(player2.getAccount().getUserName().equals(userName))
            return player2;
        if(player1.getAccount().getUserName().equals(userName))
            return player1;
        return null;
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
        if (y - 1 >= width || y - 1 < 0 || x - 1 >= length || x - 1 < 0)
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
        checkCoolDownWaiting(player1);
        checkCoolDownWaiting(player2);
        checkBuffsAtTheStartOfTurn(player1);
        checkBuffsAtTheStartOfTurn(player2);
    }

    private void checkCoolDownWaiting(Player player) {
        Hero hero = (Hero) player.getHero();
        if (hero.getCoolDownWaiting() < hero.getCoolDown()) {
            hero.increaseCoolDownWating();
        }
    }

    private void checkBuffsAtTheStartOfTurn(Player player1) {
        for (Card card : player1.getInBattleCards().keySet()) {
            if (card instanceof SoldierCard) {
                ((SoldierCard) card).castFirstTurnBuffs();
            }
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
        int xDifference = targetCell.getXCoordinate() - srcCell.getXCoordinate();
        int yDifference = targetCell.getYCoordinate() - srcCell.getYCoordinate();
        if (xDifference != 0 && yDifference != 0) {
            return cells[targetCell.getYCoordinate() - 1][targetCell.getXCoordinate() - 1].getCard() != null;
        }
        while (xDifference != 0) {
            if (cells[srcCell.getYCoordinate() - 1][srcCell.getXCoordinate() + xDifference - 1].getCard() != null)
                return true;
            if (xDifference < 0)
                xDifference++;
            if (xDifference > 0)
                xDifference--;
        }
        while (yDifference != 0) {
            if (cells[srcCell.getYCoordinate() + yDifference - 1][srcCell.getXCoordinate() - 1].getCard() != null)
                return true;
            if (yDifference < 0)
                yDifference++;
            if (yDifference > 0)
                yDifference--;
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
        level1.getCards().put(1, new Minion((Minion) shop.findCard(101), 1));
        level1.getCards().put(2, new Minion((Minion) shop.findCard(109), 2));
        level1.getCards().put(3, new Minion((Minion) shop.findCard(111), 3));
        level1.getCards().put(4, new Minion((Minion) shop.findCard(111), 4));
        level1.getCards().put(5, new Minion((Minion) shop.findCard(113), 5));
        level1.getCards().put(6, new Minion((Minion) shop.findCard(117), 6));
        level1.getCards().put(7, new Minion((Minion) shop.findCard(118), 7));
        level1.getCards().put(8, new Minion((Minion) shop.findCard(121), 8));
        level1.getCards().put(9, new Minion((Minion) shop.findCard(122), 9));
        level1.getCards().put(10, new Minion((Minion) shop.findCard(126), 10));
        level1.getCards().put(11, new Minion((Minion) shop.findCard(138), 11));
        level1.getCards().put(12, new Minion((Minion) shop.findCard(136), 12));
        level1.getCards().put(13, new Minion((Minion) shop.findCard(140), 13));
        level1.getCards().put(14, new SpellCard((SpellCard) shop.findCard(201), 14));
        level1.getCards().put(15, new SpellCard((SpellCard) shop.findCard(207), 15));
        level1.getCards().put(16, new SpellCard((SpellCard) shop.findCard(210), 16));
        level1.getCards().put(17, new SpellCard((SpellCard) shop.findCard(211), 17));
        level1.getCards().put(18, new SpellCard((SpellCard) shop.findCard(212), 18));
        level1.getCards().put(19, new SpellCard((SpellCard) shop.findCard(218), 19));
        level1.getCards().put(20, new SpellCard((SpellCard) shop.findCard(220), 20));
        level1.getCards().put(21, new Hero((Hero) shop.findCard(301), 21));
        level1.getItems().put(22, new Item(shop.findItem(4), 22));

        Deck level2 = new Deck("level2");
        level2.getCards().put(1, new Minion((Minion) shop.findCard(102), 1));
        level2.getCards().put(2, new Minion((Minion) shop.findCard(103), 2));
        level2.getCards().put(3, new Minion((Minion) shop.findCard(105), 3));
        level2.getCards().put(4, new Minion((Minion) shop.findCard(108), 4));
        level2.getCards().put(5, new Minion((Minion) shop.findCard(112), 5));
        level2.getCards().put(6, new Minion((Minion) shop.findCard(115), 6));
        level2.getCards().put(7, new Minion((Minion) shop.findCard(115), 7));
        level2.getCards().put(8, new Minion((Minion) shop.findCard(119), 8));
        level2.getCards().put(9, new Minion((Minion) shop.findCard(123), 9));
        level2.getCards().put(10, new Minion((Minion) shop.findCard(127), 10));
        level2.getCards().put(11, new Minion((Minion) shop.findCard(130), 11));
        level2.getCards().put(12, new Minion((Minion) shop.findCard(133), 12));
        level2.getCards().put(13, new Minion((Minion) shop.findCard(139), 13));
        level2.getCards().put(14, new SpellCard((SpellCard) shop.findCard(202), 14));
        level2.getCards().put(15, new SpellCard((SpellCard) shop.findCard(203), 15));
        level2.getCards().put(16, new SpellCard((SpellCard) shop.findCard(205), 16));
        level2.getCards().put(17, new SpellCard((SpellCard) shop.findCard(209), 17));
        level2.getCards().put(18, new SpellCard((SpellCard) shop.findCard(208), 18));
        level2.getCards().put(19, new SpellCard((SpellCard) shop.findCard(213), 19));
        level2.getCards().put(20, new SpellCard((SpellCard) shop.findCard(219), 20));
        level2.getCards().put(21, new Hero((Hero) shop.findCard(305), 21));
        level2.getItems().put(22, new Item(shop.findItem(2), 22));

        Deck level3 = new Deck("level3");
        level3.getCards().put(1, new Minion((Minion) shop.findCard(106), 1));
        level3.getCards().put(2, new Minion((Minion) shop.findCard(107), 2));
        level3.getCards().put(3, new Minion((Minion) shop.findCard(110), 3));
        level3.getCards().put(4, new Minion((Minion) shop.findCard(114), 4));
        level3.getCards().put(5, new Minion((Minion) shop.findCard(116), 5));
        level3.getCards().put(6, new Minion((Minion) shop.findCard(116), 6));
        level3.getCards().put(7, new Minion((Minion) shop.findCard(120), 7));
        level3.getCards().put(8, new Minion((Minion) shop.findCard(124), 8));
        level3.getCards().put(9, new Minion((Minion) shop.findCard(125), 9));
        level3.getCards().put(10, new Minion((Minion) shop.findCard(128), 10));
        level3.getCards().put(11, new Minion((Minion) shop.findCard(129), 11));
        level3.getCards().put(12, new Minion((Minion) shop.findCard(131), 12));
        level3.getCards().put(13, new Minion((Minion) shop.findCard(134), 13));
        level3.getCards().put(14, new SpellCard((SpellCard) shop.findCard(206), 14));
        level3.getCards().put(15, new SpellCard((SpellCard) shop.findCard(210), 15));
        level3.getCards().put(16, new SpellCard((SpellCard) shop.findCard(212), 16));
        level3.getCards().put(17, new SpellCard((SpellCard) shop.findCard(214), 17));
        level3.getCards().put(18, new SpellCard((SpellCard) shop.findCard(215), 18));
        level3.getCards().put(19, new SpellCard((SpellCard) shop.findCard(216), 19));
        level3.getCards().put(20, new SpellCard((SpellCard) shop.findCard(217), 20));
        level3.getCards().put(21, new Hero((Hero) shop.findCard(307), 21));
        level3.getItems().put(22, new Item(shop.findItem(3), 22));

        storyLevelDecks.add(level1);
        storyLevelDecks.add(level2);
        storyLevelDecks.add(level3);
    }

    public ArrayList<Deck> getStoryLevelDecks() {
        return storyLevelDecks;
    }

    public Cell FindCardCellInGame(Minion card) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getCard() == card)
                    return cells[i][j];
            }
        }
        return null;
    }
}



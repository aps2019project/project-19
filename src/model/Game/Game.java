package model.Game;

import model.Cards.*;
import model.Cell;
import model.Item;
import model.Player;

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

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        // TODO: 2019-04-29 set date
        this.isTurnOfPlayerOne = true;
        this.turnNumber = 1;
    }
    //todo set prize
    public void setPrize() {
        if (gameMode.equals(GameMode.DEATH_MATCH)) {
            this.prize = 500;
        } else if (gameMode.equals(GameMode.KEEP_THE_FLAG)) {
            this.prize = 1000;
        } else if (gameMode.equals(GameMode.CAPTURE_THE_FLAGS)) {
            this.prize = 1500;
        }
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
        if(y-1>=width || x-1 >= length)
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
        while (xDeffrence != 0){
            if (cells[srcCell.getYCoordinate() - 1][srcCell.getXCoordinate() + xDeffrence - 1].getCard() != null)
                return true;
            if (xDeffrence < 0)
                xDeffrence ++;
            if (xDeffrence > 0)
                xDeffrence --;
        }
        while (yDeffrence != 0){
            if (cells[srcCell.getYCoordinate() + yDeffrence - 1][srcCell.getXCoordinate() - 1].getCard() != null)
                return true;
            if (yDeffrence < 0)
                yDeffrence ++;
            if (yDeffrence > 0)
                yDeffrence --;
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
        if (player1.getMinionsWithFlag() != null)
            toString.append("player ").append(player1.getAccount().getUserName()).append(" has flag in coordinate: ")
                    .append(player1.getInBattleCards().get(player1.getMinionsWithFlag().get(0)).toString());
        else if (player2.getMinionsWithFlag() != null)
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
        switch (gameMode){
            case CAPTURE_THE_FLAGS:
                winnerPlayer = captureTheFlagsWinner();
                break;
            case KEEP_THE_FLAG:
                winnerPlayer = keepTheFlagWinner();
                break;
            case DEATH_MATCH:
                if (draw()){
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
         if (2 * player1.totalPlyerFlagsNumber() > totalGameFlagsNmber())
             return player1;
         if (2 * player2.totalPlyerFlagsNumber() > totalGameFlagsNmber())
             return player2;
         return null;
    }

    private boolean draw() {
        return player1.heroIsDead()&& player2.heroIsDead();
    }

    public void initFlags() {
        Random random = new Random();
        for (int i = 0; i < numOfFlags; i++) {
            int x = random.nextInt(length);
            int y = random.nextInt(width);
            cells[y][x].setFlagNumber(cells[y][x].getFlagNumber() + 1);
        }
    }
}


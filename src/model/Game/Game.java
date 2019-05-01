package model.Game;

import model.Cell;
import model.Item;
import model.Player;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private final int length = 9;
    private final int width = 5;
    private Cell[][] cells = new Cell[width][length];

    {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i + 1, j + 1);
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

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        // TODO: 2019-04-29 set date
        this.isTurnOfPlayerOne = true;
        this.turnNumber = 1;
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
            currentPlayer.increaseMana();
            currentPlayer.setMana(currentPlayer.getMaxMana());
        }
    }
    public boolean pathIsBlocked(Cell destCell,Cell targetCell){
        // TODO: 2019-04-30 must be implemented
        return false;
    }
}

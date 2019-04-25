package model.Game;

import model.Cell;
import model.Item;
import model.Player;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private int length;
    private int width;
    private Cell[][] cells = new Cell[width][length];
    private ArrayList<Item> items = new ArrayList<>();
    private Player player1;
    private Player player2;
    private boolean isTurnOfPlayerOne;
    private int turnNumber;
    private Date date;
    private Player winnerPlayer;
    private GameMode gameMode;
    private GameKind gameKind;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public GameKind getGameKind() {
        return gameKind;
    }

    public void selectGameKind(String mode) {
        // TODO: 4/22/19 get mode in view
        if (mode.equals("single player")) {
            this.gameKind = GameKind.SINGLEPLAYER;
        } else if (mode.equals("multi palyer")) {
            this.gameKind = GameKind.MULTIPLAYER;
        }
    }

    public void selectSinglePlayerGameMode(String mode) {
        // TODO: 4/22/19 ??????? 
        if (mode.equals("story")) {

        } else if (mode.equals("custom game")) {

        }
    }
}

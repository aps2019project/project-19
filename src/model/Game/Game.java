package model.Game;

import model.Cards.*;
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
            currentPlayer.increaseMaxMana();
            currentPlayer.setMana(currentPlayer.getMaxMana());
            System.out.println(currentPlayer.getMana());
        }
    }

    public boolean pathIsBlocked(Cell destCell, Cell targetCell) {
        // TODO: 2019-04-30 must be implemented
        return false;
    }
    public Hero setHeroes(Player player, Cell cell){
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
                toString.append(((Hero) card).getHp());
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
            toString.append("no one has flag. flag is in coordinate : (");
            for (Cell[] cellInRow : cells) {
                for (Cell cell : cellInRow) {
                    if (cell.getFlagNumber() != 0) {
                        toString.append(cell.toString());
                    }
                }
            }
        }
        return toString.toString();
    }

    public String toStringCaptureFlags(){
        return player1.toStringFlags() + player2.toStringFlags();
    }
}

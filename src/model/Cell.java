package model;

import model.Buff.Buff;
import model.Cards.Card;

public class Cell {
    private int xCoordinate;
    private int yCoordinate;
    private Item item;
    private Card card;
    private Buff buff;
    private int flagNumber;

    public Cell(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    public int getFlagNumber() {
        return flagNumber;
    }

    public void setFlagNumber(int flagNumber) {
        this.flagNumber = flagNumber;
    }
    public  int getManhattanDistance(Cell cell){
        return Math.abs(this.xCoordinate - cell.xCoordinate)+ Math.abs(this.yCoordinate- cell.yCoordinate);
    }

    @Override
    public String toString() {
        return "(" + xCoordinate + ", " + yCoordinate + ")";
    }
}

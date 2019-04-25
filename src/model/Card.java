package model;

import java.util.concurrent.CancellationException;

public class Card {
    private int cardId;
    private String inBattleCardId;
    private String name;
    private CardStatus cardStatus;
    private int price;
    private int mana;

    public Card() {
    }

    public Card(int cardId, String name, int price, int mana) {
        this.cardId = cardId;
        this.name = name;
        this.price = price;
        this.mana = mana;
    }

    public Card(Card card) {
        this.name = card.name;
        this.price = card.price;
        this.mana = card.mana;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getInBattleCardId() {
        return inBattleCardId;
    }

    public void setInBattleCardId(String inBattleCardId) {
        this.inBattleCardId = inBattleCardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void castSpell(Cell targetCell) {
    }

    public void castSpell(Card targetCard) {
    }
}

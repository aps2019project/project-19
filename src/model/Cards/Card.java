package model.Cards;

import model.Cell;

public abstract class Card {
    private int cardId;
    private String inBattleCardId;
    private String name;
    private CardStatus cardStatus;
    private int price;
    private int mana;
    private String description;

    public Card() {
    }

    public Card(int cardId, String name, int price, int mana, String description) {
        this.cardId = cardId;
        this.name = name.toLowerCase();
        this.price = price;
        this.mana = mana;
        this.description = description;
    }

    public Card(Card card) {
        this.name = card.name;
        this.price = card.price;
        this.mana = card.mana;
        this.description = card.description;
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

    public String getDescription() {
        return description;
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

    public abstract String toInfoString();
}

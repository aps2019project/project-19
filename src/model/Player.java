package model;

import java.util.ArrayList;

public class Player {
    private int mana;
    private Account account;
    private Deck deckCards;
    private ArrayList<Card> handCards = new ArrayList<>();
    private ArrayList<Card> intBattleCards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private int selectedCardId;

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Deck getDeckCards() {
        return deckCards;
    }

    public void setDeckCards(Deck deckCards) {
        this.deckCards = deckCards;
    }

    public int getSelectedCardId() {
        return selectedCardId;
    }

    public void setSelectedCardId(int selectedCardId) {
        this.selectedCardId = selectedCardId;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public ArrayList<Card> getIntBattleCards() {
        return intBattleCards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean cardExistsInHand(int cardId){}

    public boolean myCardIsOnGround(String inBattleCardId){}

    public void select(String inBattleCardId){}
}

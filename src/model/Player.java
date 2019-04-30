package model;

import model.Cards.*;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private final int handCapacity = 5;
    private int mana;
    private Account account;
    private Deck deckCards;
    private ArrayList<Card> handCards = new ArrayList<>();
    private ArrayList<Card> intBattleCards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private int selectedCardId;
    private int nextCardId;

    public Player(Account account, Deck deckCards) {
        this.account = account;
        this.deckCards = deckCards;
        this.mana = 2;
    }

    public int getHandCapacity() {
        return handCapacity;
    }

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

    public void setNextCardId(int nextCardId) {
        this.nextCardId = nextCardId;
    }

    public int getNextCardId() {
        return nextCardId;
    }

    public void increaseMana() {
        this.mana++;
    }
    //    public boolean cardExistsInHand(int cardId){}
//
//    public boolean myCardIsOnGround(String inBattleCardId){}

    public void select(String inBattleCardId) {
    }

    public String handInfo() {
        StringBuilder result = new StringBuilder();
        for (Card card : handCards) {
            result.append(card.toInfoString());
            result.append("\n");
        }
        result.append("next card is :\n");
        result.append(deckCards.getCards().get(nextCardId));
        result.append("\n");
        return result.toString();
    }

    public void moveARandomCardToHand() {
        if (handCards.size() < handCapacity) {
            Random random = new Random();
            ArrayList<Card> cards = new ArrayList<>(deckCards.getCards().values());
            int rand = random.nextInt(cards.size());
            Card card = cards.get(rand);
            card.setCardStatus(CardStatus.IN_HAND);
            handCards.add(card);
        }
    }
}

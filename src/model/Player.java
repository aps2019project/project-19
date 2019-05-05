package model;

import model.Cards.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Player {
    private final int handCapacity = 5;
    private int mana;
    private int maxMana;
    private Account account;
    private Deck deckCards;
    private HashMap<Integer, Card> handCards = new HashMap<>();
    private HashMap<Card, Cell> inBattleCards = new HashMap<>();
    private HashMap<Integer, Item> items = new HashMap<>();
    private HashMap<String, Card> graveYard = new HashMap<>();
    private Card selectedCard;
    private Item selectedItem;
    private int nextCardId;
    private HashMap<String, Integer> cardNameIdHashMap = new HashMap<>();

    public Player(Account account, Deck deckCards) {
        this.account = account;
        this.deckCards = deckCards;
        this.mana = 2;
        this.maxMana = 2;
    }

    public void decreaseMana(int number) {
        this.mana -= number;
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

    public Card getSelectedCard() {
        return selectedCard;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public HashMap<Integer, Card> getHandCards() {
        return handCards;
    }

    public HashMap<Card, Cell> getInBattleCards() {
        return inBattleCards;
    }

    public HashMap<Integer, Item> getItems() {
        return items;
    }

    public HashMap<String, Integer> getCardNameIdHashMap() {
        return cardNameIdHashMap;
    }

    public void setNextCardId(int nextCardId) {
        this.nextCardId = nextCardId;
    }

    public int getNextCardId() {
        return nextCardId;
    }

    public void increaseMaxMana() {
        this.maxMana++;
    }

    public boolean isAnyCardSelected() {
        return getSelectedCard() != null;
    }

    public boolean isAnyItemSelected() {
        return getSelectedItem() != null;
    }

//
//    public boolean myCardIsOnGround(String inBattleCardId){}


    public void setHandCards(HashMap<Integer, Card> handCards) {
        this.handCards = handCards;
    }

    public void setInBattleCards(HashMap<Card, Cell> inBattleCards) {
        this.inBattleCards = inBattleCards;
    }

    public void setItems(HashMap<Integer, Item> items) {
        this.items = items;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public HashMap<String, Card> getGraveYard() {
        return graveYard;
    }

    public String handInfo() {
        StringBuilder result = new StringBuilder();
        ArrayList<Card> cards = new ArrayList<>(handCards.values());
        for (Card card : cards) {
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
            if (nextCardId == 0) {
                ArrayList<Card> cards = new ArrayList<>(deckCards.getCards().values());
                int rand = random.nextInt(cards.size());
                Card card = cards.get(rand);
                deckCards.getCards().remove(card.getCardId());
                card.setCardStatus(CardStatus.IN_HAND);
                handCards.put(card.getCardId(), card);

            } else {
                Card card = deckCards.getCards().get(nextCardId);
                deckCards.getCards().remove(card.getCardId());
                card.setCardStatus(CardStatus.IN_HAND);
                handCards.put(card.getCardId(), card);
            }
            ArrayList<Card> cards = new ArrayList<>(deckCards.getCards().values());
            int rand = random.nextInt(cards.size());
            Card card = cards.get(rand);
            nextCardId = card.getCardId();
        }
    }

    public Player setFirstHand() {
        for (int i = 0; i < 5; i++) {
            this.moveARandomCardToHand();
        }
        return this;
    }

    public boolean containsCardInBattle(String cardId) {
        return getInBattleCard(cardId) != null;
    }

    public Card getInBattleCard(String cardId) {
        for (Card card : getInBattleCards().keySet()) {
            if (card.getInBattleCardId().equals(cardId))
                return card;
        }
        return null;
    }

    public ArrayList<SoldierCard> getMinionsWithFlag(){
        ArrayList<SoldierCard> soldierCards = new ArrayList<>();
        for (Card card : inBattleCards.keySet()) {
            if (card instanceof SoldierCard && ((SoldierCard) card).getFlagNumber() != 0)
                soldierCards.add(((SoldierCard) card));
        }
        return soldierCards;
    }

    public String toStringFlags(){
        StringBuilder toString = new StringBuilder();
        toString.append("player : ").append(this.getAccount().getUserName()).append( " flags :\n");
        for (SoldierCard card : this.getMinionsWithFlag()) {
            toString.append(card.getInBattleCardId()).append("\n");
        }
        return toString.toString();
    }
    public Player resetCardsAttackAndMoveAbility(){
        for (Card card :getInBattleCards().keySet()) {
            card = (SoldierCard) card;
            ((SoldierCard) card).setMovedThisTurn(false).setAttackedThisTurn(false);

        }
        return this;
    }
}

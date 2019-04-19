package model;

import java.util.ArrayList;

public class Collection {
    private Deck mainDeck;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void createDeck(String deckName) {
    }

    public void deleteDeck(String deckName) {
    }

//    public boolean deckExist(String deckName){}
//
//    public boolean cardIsValid(int cardId){}
//
//    public boolean itemIsValid(int itemId){}
//
//    public boolean cardIsInDeck(int cardId, String deckName){}
//
//    public boolean itemIsInDeck(int itemId, String deckName){}
//
//    public boolean deckHasHero(String deckName){}

    public boolean existsInCollection(int producId) {
        for (Card card : cards) {
            if (card.getCardId() == producId)
                return true;
        }
        for (Item item : items) {
            if (item.getItemId() == producId)
                return true;
        }
        return false;
    }

//    public boolean itemExistsInCollection(int itemId){}

    public void addCardToDeck(int cardId, String deckName) {
    }

    public void addItemToDeck(int itemId, String deckName) {
    }

    public void removeCard(int cardId, String deckName) {
    }

    public void removeItem(int itemId, String deckName) {
    }

//    public boolean validateDeck(String deckName){}
}

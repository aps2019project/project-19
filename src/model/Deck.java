package model;

import model.Cards.Card;
import model.Cards.Hero;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String name;
    private HashMap<Integer, Card> cards = new HashMap<>();
    private HashMap<Integer, Item> items = new HashMap<>();

    public String getName() {
        return name;
    }

    public Deck(String name){
        this.name = name;
    }

    public boolean deckHasHero(){
        ArrayList<Card> cards = new ArrayList<>(this.getCards().values());
        for (Card card : cards) {
            if (card instanceof Hero)
                return true;
        }
        return false;
    }

    public HashMap<Integer, Card> getCards() {
        return cards;
    }

    public HashMap<Integer, Item> getItems() {
        return items;
    }
}

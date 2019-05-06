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

    public Deck() {
    }

    public Deck(String name) {
        this.name = name;
    }

    public Deck(Deck deck) {
        this.name = deck.name;
        this.cards = new HashMap<>(deck.cards);
        this.items = new HashMap<>(deck.items);
    }

    public boolean deckHasHero() {
        ArrayList<Card> cards = new ArrayList<>(this.getCards().values());
        for (Card card : cards) {
            if (card instanceof Hero)
                return true;
        }
        return false;
    }

    public boolean deckIsValid() {
        if (cards.size() == 21 && deckHasHero())
            return true;
        return false;
    }

    public HashMap<Integer, Card> getCards() {
        return cards;
    }

    public HashMap<Integer, Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        int number = 1;
        StringBuilder toString = new StringBuilder();
        toString.append(this.getName()).append(" : ").append("\n");
        toString.append("\tHeroes :\n");
        for (Card card : this.getCards().values()) {
            if (card instanceof Hero)
                toString.append("\t\t").append(number).append(" : ").append(card.toString()).append("\n");
        }
        toString.append("\tItems :\n");
        for (Item item : this.getItems().values()) {
            toString.append("\t\t").append(number).append(" : ").append(item.toString()).append("\n");
            number++;
        }
        number = 1;
        toString.append("\tCards :\n");
        for (Card card : this.getCards().values()) {
            if (!(card instanceof Hero)){
                toString.append("\t\t").append(number).append(" : ").append(card.toString()).append("\n");
                number++;
            }
        }
        return toString.toString();
    }

    public Hero getHero(){
        for (Card card : cards.values()) {
            if (card instanceof Hero)
                return (((Hero) card));
        }
        return null;
    }
}

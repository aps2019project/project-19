package model;

import java.util.ArrayList;

public class Deck {
    private String name;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Deck(String name){
        this.name = name;
    }
}

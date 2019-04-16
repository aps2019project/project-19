package model;

import java.util.ArrayList;

public class Shop {
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

//    public boolean cardExistsInShop(String cardName){}
//
//    public boolean itemExistsInShop(String itemName){}

    public void buyCard(String cardName){}

    public void buyItem(String  itemName){}

//    public boolean cardExistsInCollection(int itemId){}
//
//    public boolean itemExistsInCollection(int cardId){}

    public void sellCard(int cardId){}

    public void sellItem(int itemId){}
}

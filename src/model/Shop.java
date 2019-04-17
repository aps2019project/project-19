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

    public boolean cardExistsInShop(String cardName){
        return true;
    }

    public boolean itemExistsInShop(String itemName){
        return true;
    }

    public void buyCard(String cardName){}

    public void buyItem(String  itemName){}

    public boolean cardExistsInCollection(int itemId){
        return true;
    }

    public boolean itemExistsInCollection(int cardId){
        return true;
    }

    public void sellCard(int cardId){}

    public void sellItem(int itemId){}
}

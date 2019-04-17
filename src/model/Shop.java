package model;

import controller.Controller;

import java.util.ArrayList;

public class Shop {
    private int id;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean cardExistsInShop(String cardName) {
        for (Card card : cards)
            if (card.getName().equals(cardName))
                return true;
        return false;
    }

    public boolean itemExistsInShop(String itemName) {
        for (Item item : items)
            if (item.getName().equals(itemName))
                return true;
        return false;
    }

    public boolean priceIsEnough(String productName, Account account){
        for (Item item : items) {
            if (item.getName().equals(productName))
                if (item.getPrice() < account.getMoney())
                    return true;
        }
        for (Card card : cards) {
            if (card.getName().equals(productName))
                if (card.getPrice() < account.getMoney())
                    return true;
        }
        return false;
    }

    public void buyCard(String cardName) {
        System.out.println("card bought successfully");
    }

    public void buyItem(String itemName) {
        System.out.println("item bought successfully");
    }

//    public boolean cardExistsInCollection(int itemId){}
//
//    public boolean itemExistsInCollection(int cardId){}

    public void sellCard(int cardId) {
    }

    public void sellItem(int itemId) {
    }
}

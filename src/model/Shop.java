package model;


import model.Cards.Card;
import model.Cards.Hero;
import model.Cards.Minion;
import model.Cards.SpellCard;

import java.util.ArrayList;

public class Shop {
    private static int id;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private final static Shop SHOP = new Shop();

    public static Shop getInstance() {
        return SHOP;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Object> getHeros() {
        ArrayList<Object> heros = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Hero)
                heros.add(card);
        }
        return heros;
    }

    public Card findCard(String cardName) {
        for (Card card : cards) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    public Card findCard(int cardId) {
        for (Card card : cards) {
            if (card.getCardId() == (cardId))
                return card;
        }
        return null;
    }

    public Item findItem(int itemid) {
        for (Item item : items) {
            if (item.getItemId() == (itemid))
                return item;
        }
        return null;
    }

    private Shop() {
    }

    public boolean existsInShop(String productName) {
        for (Item item : items)
            if (item.getName().equals(productName))
                return true;
        for (Card card : cards) {
            if (card.getName().equals(productName))
                return true;
        }
        return false;
    }

    public boolean priceIsEnough(String productName, Account account) {
        //todo: duplicate code :/
        for (Item item : items) {
            if (item.getName().equals(productName))
                if (item.getPrice() <= account.getMoney())
                    return true;
        }
        for (Card card : cards) {
            if (card.getName().equals(productName))
                if (card.getPrice() <= account.getMoney())
                    return true;
        }
        return false;
    }

    public boolean validateNumberOfItems(String productName, Account account) {
        for (Item item : items) {
            if (item.getName().equals(productName))
                if (account.getCollection().getItems().size() >= 3)
                    return false;
        }
        return true;
    }

    public void buy(String productName, Account account) {
        generateNewId();
        for (Card card : cards) {
            if (card.getName().equals(productName)) {
                if (card instanceof Hero) {
                    Hero card1 = new Hero((Hero) card);
                    card1.setCardId(id);
                    account.getCollection().getCards().add(card1);
                }

                if (card instanceof Minion) {
                    Minion card1 = new Minion((Minion) card);
                    card1.setCardId(id);
                    account.getCollection().getCards().add(card1);
                }

                if (card instanceof SpellCard) {
                    SpellCard card1 = new SpellCard((SpellCard) card);
                    card1.setCardId(id);
                    account.getCollection().getCards().add(card1);
                }
                account.increaseMoney(-card.getPrice());
                return;
            }
        }
        for (Item item : items) {
            if (item.getName().equals(productName)) {
                Item item1 = new Item(item);
                item1.setItemId(id);
                account.getCollection().getItems().add(item1);
                account.increaseMoney(-item.getPrice());
            }
        }
    }

    public void sell(int productId, Account account) {
        for (Card card : account.getCollection().getCards()) {
            if (card.getCardId() == productId)
                account.increaseMoney(card.getPrice());
        }
        for (Item item : account.getCollection().getItems()) {
            if (item.getItemId() == productId)
                account.increaseMoney(item.getPrice());
        }
        for (Deck deck : account.getCollection().getDecks().values()) {
            deck.getCards().remove(productId);
            deck.getItems().remove(productId);
        }
        account.getCollection().getCards().removeIf(card -> card.getCardId() == productId);
        account.getCollection().getItems().removeIf(item -> item.getItemId() == productId);
    }

    public void sellItem(int itemId) {
    }

    public static void generateNewId() {
        id++;
    }

    public static int getNewId() {
        id++;
        return id;
    }
}

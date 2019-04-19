package model;


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

//    public boolean cardExistsInShop(String cardName) {
//        for (Card card : cards)
//            if (card.getName().equals(cardName))
//                return true;
//        return false;
//    }

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

    public void buy(String productName, Account account) {
        // TODO: its needs some changes please dont touch it
        generateNewId();
        for (Card card : cards) {
            if (card.getName().equals(productName)){
                if (card instanceof SoldierCard){
                    SoldierCard card1 = new SoldierCard();
                    card1 = (SoldierCard)card;
                    card1.setCardId(id);
                    account.getCollection().getCards().add(card1);
                }
                if (card instanceof SpellCard){
                    SpellCard card1 = new SpellCard();
                    card1 = (SpellCard)card;
                    card1.setCardId(id);
                    account.getCollection().getCards().add(card1);
                }
                account.setMoney(account.getMoney() - card.getPrice());
                return;
            }
        }
        for (Item item : items) {
            if (item.getName().equals(productName)){
                Item item1 = new Item();
                item1 = item;
                item1.setItemId(id);
                account.getCollection().getItems().add(item1);
                account.setMoney(account.getMoney() - item.getPrice());
            }
        }
    }

//    public void buyItem(String itemName) {
//        System.out.println("item bought successfully");
//    }

//    public boolean cardExistsInCollection(int itemId){}
//
//    public boolean itemExistsInCollection(int cardId){}

    public void sell(int productId, Account account) {
        for (Card card : account.getCollection().getCards()) {
            if (card.getCardId() == productId)
                account.setMoney(account.getMoney() + card.getPrice());
        }
        for (Item item : account.getCollection().getItems()) {
            if (item.getItemId() == productId)
                account.setMoney(account.getMoney() + item.getPrice());
        }
        account.getCollection().getCards().removeIf(card -> card.getCardId() == productId);
        account.getCollection().getItems().removeIf(item -> item.getItemId() == productId);
    }

    public void sellItem(int itemId) {
    }

    public void generateNewId(){ id ++;}
}

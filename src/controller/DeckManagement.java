package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Buff.Buff;
import model.Cards.Card;
import model.Cards.SoldierCard;
import model.Deck;

import java.io.*;

public class DeckManagement {
    private static Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new AbstractClassAdapters<Buff>())
            .registerTypeAdapter(Card.class, new AbstractClassAdapters<Card>())
            .registerTypeAdapter(SoldierCard.class, new AbstractClassAdapters<SoldierCard>())
            .setPrettyPrinting().create();
    private static File file = new File("src/data/Decks");

    //returns false is the name exists
    public static boolean exportDeck(Deck deck, String name) {
        String[] decks = file.list();
        for (String fileName : decks) {
            fileName = fileName.split("\\.json")[0];
            if (fileName.equals(name))
                return false;
        }
        try {
            Writer writer = new FileWriter("src/data/Decks/" + name + ".json");
            writer.write(gson.toJson(deck));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    //returns null if deck doesn't exist
    public static Deck importDeck(String name) {
        String[] decks = file.list();
        for (String fileName : decks) {
            fileName = fileName.split("\\.json")[0];
            if (fileName.equals(name)) {
                try (Reader reader = new FileReader("src/data/Decks/" + name + ".json")) {
                    Deck deck = gson.fromJson(reader, Deck.class);
                    return deck;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
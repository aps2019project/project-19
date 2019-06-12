package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Buff.Buff;
import model.Deck;

import java.io.*;

public class DeckManagement {
    private static Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new BuffAdapter())
            .setPrettyPrinting().create();
    private static File file = new File("src/data/Decks");

    //returns false is the name exists
    //todo : before export check if the deck is valid & get another name if it returned false
    public static boolean exportDeck(Deck deck, String name) {
        String[] decks = file.list();
        for (String fileName : decks) {
            fileName = fileName.split("\\.json")[0];
            if (fileName.equals(name))
                return false;
        }
        try {
            Writer writer = new FileWriter("src/data/" + name + ".json");
            writer.write(gson.toJson(deck));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    //returns null if deck doesn't exist
    //todo : ckeck if playar has all deck cards then add deck to his decks
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
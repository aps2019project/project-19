package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;
import model.Buff.*;
import model.Cards.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CardInitializer {
    private static ArrayList<Minion> minionCards = new ArrayList<>();
    private static ArrayList<SpellCard> spellCards = new ArrayList<>();
    private static ArrayList<Hero> heroes = new ArrayList<>();
    private static ArrayList<Item> items = new ArrayList<>();
    private static Shop shop = Shop.getInstance();
    private static Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new AbstractClassAdapters<Buff>())
            .registerTypeAdapter(Card.class, new AbstractClassAdapters<Card>())
            .registerTypeAdapter(SoldierCard.class, new AbstractClassAdapters<SoldierCard>())
            .setPrettyPrinting().create();

    private final static CardInitializer CARD_INITIALIZER = new CardInitializer();

    public static CardInitializer getInstance() {
        return CARD_INITIALIZER;
    }

    private CardInitializer() {
    }

    public void createCards() throws FileNotFoundException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new AbstractClassAdapters()).create();
        URL url = CardInitializer.class.getResource("../data/minions.json");
        Reader reader = new FileReader("src/data/minions.json");
        minionCards = gson.fromJson(reader, new TypeToken<List<Minion>>() {
        }.getType());
        url = CardInitializer.class.getResource("../data/spells.json");
        reader = new FileReader("src/data/spells.json");
        spellCards = gson.fromJson(reader, new TypeToken<List<SpellCard>>() {
        }.getType());
        url = CardInitializer.class.getResource("../data/heros.json");
        reader = new FileReader("src/data/heros.json");
        heroes = gson.fromJson(reader, new TypeToken<List<Hero>>() {
        }.getType());
        url = CardInitializer.class.getResource("../data/items.json");
        reader = new FileReader("src/data/items.json");
        items = gson.fromJson(reader, new TypeToken<List<Item>>() {
        }.getType());
        for (Minion minionCard : minionCards) {
            shop.getCards().add(minionCard);
        }
        for (SpellCard spellCard : spellCards) {
            shop.getCards().add(spellCard);
        }
        for (Hero hero : heroes) {
            shop.getCards().add(hero);
        }
        for (Item item : items) {
            shop.getItems().add(item);
        }
    }

    public static void addCustomCardToFile(Card card) {
        FileWriter fileWriter;
        try {
            if (card instanceof Minion) {
                minionCards.add((Minion) card);
                fileWriter = new FileWriter("src/data/minions.json");
                gson.toJson(minionCards, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } else if (card instanceof Hero) {
                heroes.add((Hero) card);
                fileWriter = new FileWriter("src/data/heros.json");
                gson.toJson(heroes, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            } else if (card instanceof SpellCard) {
                spellCards.add((SpellCard) card);
                fileWriter = new FileWriter("src/data/spells.json");
                gson.toJson(spellCards, fileWriter);
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateJson() throws IOException {
        FileWriter fileWriter;
        fileWriter = new FileWriter("src/data/minions.json");
        gson.toJson(minionCards, fileWriter);
        fileWriter.flush();
        fileWriter = new FileWriter("src/data/heros.json");
        gson.toJson(heroes, fileWriter);
        fileWriter.flush();
        fileWriter = new FileWriter("src/data/spells.json");
        gson.toJson(spellCards, fileWriter);
        fileWriter.flush();
        fileWriter = new FileWriter("src/data/items.json");
        gson.toJson(items, fileWriter);
        fileWriter.flush();
    }
}

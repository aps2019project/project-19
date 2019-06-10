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
    private ArrayList<Minion> minionCards = new ArrayList<>();
    private ArrayList<SpellCard> spellCards = new ArrayList<>();
    private ArrayList<Hero> heroes = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Shop shop = Shop.getInstance();
    private Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new BuffAdapter())
            .setPrettyPrinting().create();

    private final static CardInitializer CARD_INITIALIZER = new CardInitializer();

    public static CardInitializer getInstance() {
        return CARD_INITIALIZER;
    }

    private CardInitializer() {
    }

    public void createCards() throws FileNotFoundException {
        URL url = CardInitializer.class.getResource("data/minions.json");
        Reader reader = new FileReader(url.getPath());
        minionCards = gson.fromJson(reader, new TypeToken<List<Minion>>() {
        }.getType());
        url = CardInitializer.class.getResource("data/spells.json");
        reader = new FileReader(url.getPath());
        spellCards = gson.fromJson(reader, new TypeToken<List<SpellCard>>() {
        }.getType());
        url = CardInitializer.class.getResource("data/heros.json");
        reader = new FileReader(url.getPath());
        heroes = gson.fromJson(reader, new TypeToken<List<Hero>>() {
        }.getType());
        url = CardInitializer.class.getResource("data/items.json");
        reader = new FileReader(url.getPath());
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

    public void addCustomCardToFile(Card card) throws IOException {
        FileWriter fileWriter;
        shop.getCards().add(card);
        if (card instanceof Minion) {
            minionCards.add((Minion) card);
            fileWriter = new FileWriter("data/minions.json");
            fileWriter.write(gson.toJson(minionCards));
        } else if (card instanceof Hero) {
            heroes.add((Hero) card);
            fileWriter = new FileWriter("data/heros.json");
            fileWriter.write(gson.toJson(heroes));
        } else if (card instanceof SpellCard) {
            spellCards.add((SpellCard) card);
            fileWriter = new FileWriter("data/spells.json");
            fileWriter.write(gson.toJson(spellCards));
        }
    }
}

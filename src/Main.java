
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.CardInitializer;
import controller.Controller;
import model.*;
import model.Buff.*;
import model.Cards.Minion;
import model.Cards.SoldierTypes;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new BuffAdapter()).create();
//        URL url = Main.class.getResource("data/minions.json");
//        Reader reader = new FileReader(url.getPath());
//        ArrayList<Minion> readedminions = gson.fromJson(reader, new TypeToken<List<Minion>>(){}.getType());
//        System.out.println(readedminions.get(0).getName());
        CardInitializer cardInitializer = CardInitializer.getInstance();
        cardInitializer.createCards();
        //todo: write description
        Controller controller = Controller.getInstance();
        controller.run();
    }
}
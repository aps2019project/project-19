import com.google.gson.Gson;
import controller.CardInitializer;
import controller.Controller;
import model.*;
import model.Buff.Buff;
import model.Buff.HolyBuff;
import model.Buff.Kind;
import model.Buff.PoisonBuff;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CardInitializer cardInitializer = CardInitializer.getInstance();
        cardInitializer.createCards();
        Controller controller = Controller.getInstance();
        controller.run();
//        ArrayList<Buff> buffs = new ArrayList<>();
//        buffs.add(new HolyBuff(Kind.POSITIVE, 1, false, 4));
//        buffs.add(new PoisonBuff(Kind.NEGATIVE, 2, false, 10));
//        Minion one = new Minion(85, "roham", 2000, 3, 12, 2, SoldierTypes.RANGED, 10, buffs);
//        Gson gson = new Gson();
//        String json = gson.toJson(one).;
//        System.out.println(json);

    }
}
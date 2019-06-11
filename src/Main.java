import controller.CardInitializer;
import controller.Controller;
import view.Graphic.Graphic;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CardInitializer cardInitializer = CardInitializer.getInstance();
        cardInitializer.createCards();
        Controller controller = Controller.getInstance();
        Graphic graphic = new Graphic();
        graphic.main(args);
        controller.run();
    }
}
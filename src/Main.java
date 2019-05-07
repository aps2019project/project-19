import controller.CardInitializer;
import controller.Controller;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CardInitializer cardInitializer = CardInitializer.getInstance();
        cardInitializer.createCards();
        Controller controller = Controller.getInstance();
        controller.run();
    }
}
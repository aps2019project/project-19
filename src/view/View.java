package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.AbstractClassAdapters;
import controller.MenuType;
import model.Buff.Buff;
import model.Cards.Card;
import model.Cards.SoldierCard;
import model.Shop;

import java.io.*;

public class View {
    private PrintStream printStream;
    private OutputStream outputStream;
    private Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new AbstractClassAdapters<Buff>())
            .registerTypeAdapter(Card.class, new AbstractClassAdapters<Card>())
            .registerTypeAdapter(SoldierCard.class, new AbstractClassAdapters<SoldierCard>())
            .create();
    public View(OutputStream outputStream) {
        this.outputStream = outputStream;
        printStream = new PrintStream(outputStream);
    }


    public void printError(ErrorType errorType) {
        if (errorType == null)
            errorType = ErrorType.NO_ERROR;
            System.err.println(errorType + " "+ this.toString());
            printStream.println(gson.toJson(errorType));
            printStream.flush();
    }

    public void printEnterPassword() {
        System.out.println("Enter your password:");
    }

    public void show(String string) {
        System.out.println(string);
    }

    public void showHelp(MenuType menuType) {
        switch (menuType) {
            case MAINMENU:
                System.out.println("1. Collection\n2. Shop\n" +
                        "3. Battle\n4. Exit\n5. Help\n");
                break;
            case ACCOUNT:
                System.out.println("1. Create Account [user name]\n2. Login [user name]\n" +
                        "3. Show Leaderboard\n4. Save\n5. Logout\n6. Help\n7. Exit");
                break;
            case SHOP:
                System.out.println("1. Show Collection\n2. Search [item name | card name]\n" +
                        "3. Search Collection [item name | card name]\n4. Buy [item name | card name]\n" +
                        "5. Sell [item id | card id]\n6. Show\n7. Help\n8. Exit");
                break;
            case BATTLE:
                System.out.println("1. Game Info\n2. Show My Minions\n3. Show Opponent Minions\n" +
                        "4. Show Card Info [card id]\n5. Select [Card id]\n\t5.1 Move to ([x], [y])\n" +
                        "\t5.2 Attack [opponent card id]\n\t5.3 Use Special Power ([x], [y])\n" +
                        "6. Attack Combo [opponent card id] [my card id] [my card id] [...]\n7. Show Hand\n" +
                        "8. Insert [card name] In ([x], [y])\n9. End Turn\n10. Show Collectables\n" +
                        "11. Select [collectable id]\n\t11.1 Show Info\n\t11.2 Use Location [x, y]\n" +
                        "12. Show Next Card\n13. Enter Graveyard\n14. Show My Choices\n" +
                        "15. End Game\n16. Help\n17. Exit");
                break;
            case GRAVEYARD:
                System.out.println("1. Show Cards\n2. Show Info [card id]\n" +
                        "3. Help\n4. Exit\n");
                break;
            case COLLECTION:
                System.out.println("1. Show\n2. Save\n3. Search [item name| card name]" +
                        "4. Create Deck\n5. Delete Deck\n" +
                        "6. Add [item id | card id | hero id] To Deck [deck name]\n" +
                        "7. Remove [item id | card id | hero id] From Deck [deck name]\n" +
                        "8. Validate Deck [deck name]\n9. Select Deck [deck name]\n9. Show Deck [deck name]\n" +
                        "7. Help\n8. Exit");
                break;
            case START_NEW_GAME:
                System.out.println("1. enter Single player\n2. enter Multi player\n3. Help\n4. Exit");
                break;
            case SINGLE_GAME_MENU:
                System.out.println("1. enter Story Mode\n2. enter Custom Game\n3. Help\n4. Exit");
                break;
            case SINGLE_GAME_STORY_MODE:
                System.out.println("1. enter level [1 | 2 | 3]\n2. Help\n3. Exit");
                break;
            case SINGLE_GAME_CUSTOM_MODE:
                System.out.println("1. take [hero name]\n2. start game [deck name]" +
                        " [Death Match|keep The Flag|capture The Flags] [number of flags In Keep The Flag Mode]\n" +
                        "3. Help\n4. Exit");
                break;
            case MULTI_GAME_MENU:
                System.out.println("1. Show Players\n2. select user [user name]\n" +
                        "2.1 start multiplayer game [Death Match|keep The Flag|capture The Flags] " +
                        "[number Of Flags In capture The Flags Mode]\n" +
                        "3.Help \n4. Exit");
                break;
        }

    }

    public void showStoryModes() {
        System.out.println("Level: 1 Hero: Dive Sepid Mode: Death match Prize: 500 dricks\n" +
                "Level: 2 Hero: Zahhack Mode: Keep the flag Prize: 1000 dricks\n" +
                "Level: 3 Hero: Arash Mode: Capture the flags Prize: 1500 dricks");
    }

    public void showHeros() {
        System.out.println("1. dive sepid\n2. simorgh\n3. ezhdeha ye haft sar\n4. rakhsh\n" +
                "5. zahack\n6. kaveh\n7.arash\n8. afsaneh\n9. esfandiar\n10. rostam");
    }
}
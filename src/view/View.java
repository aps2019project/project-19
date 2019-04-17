package view;

import controller.MenuType;

public class View {
    private static final View VIEW = new View();

    public static View getInstance() {
        return VIEW;
    }

    private View() {
    }

    public void printError(ErrorType errorType) {
        System.out.println(errorType.getMessage());
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
                System.out.println("1. Collection\n2. Shop [user name]\n" +
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
                        "4. Show Card Info [card id]\n5. Select [Card id]\n6. Move to ([x], [y])\n" +
                        "7. Attack [opponent card id]\n8. Use Special Power ([x], [y])\n" +
                        "9. Attack Combo [opponent card id] [my card id] [my card id] [...]\n9. Show Hand\n" +
                        "10. Insert [card name] In ([x], [y])\n11. End Turn\n12. Show Collectables\n" +
                        "13. Select [collectable id]\n\t13.1 Show Info\n\t13.2 Use [location x, y]\n" +
                        "14. Show Next Card\n15. Enter Graveyard\n\t15.1 Show Cards\n\t15.2 Show Info [card id]\n" +
                        "\t15.3 Exit\n16. Show Menu\n17. End Game\n18. Help\n20. Exit");
                break;
            case COLLECTION:
                System.out.println("1. Show\n2. Save\n3. Search [item name| card name]" +
                        "4. Create Deck\n5. Delete Deck\n6. Add [item id | card id | hero id] To Deck [deck name]\n" +
                        "7. Remove [item id | card id | hero id] From Deck [deck name]\n" +
                        "8. Validate Deck [deck name]\n9. Select Deck [deck name]\n9. Show Deck [deck name]\n" +
                        "7. Help\n8. Exit");
                break;
        }
    }
}

package view;

public enum ErrorType {
//    INVALID_COMMAND("invalid command."),
    USERNAME_TAKEN("this user name is already taken."),
    INVALID_USERNAME("this user name does not exists."),
    INVALID_PASSWORD("your password is wrong."),
    NOT_FOUND("card or item not found"),
    NOT_ENOUGH_MONEY("not enough money"),
    FULL_ITEMS("you have already three items"),
    //    INVALID_SELL("you dont have that."),
    DECK_EXISTS("a deck with this name already exists"),
    DECK_NOT_EXISTS("you dont have any deck with this name"),
    EXISTS_IN_DECK("exists in deck."),
    DECK_IS_FULL("deck is full"),
    INVALID_DECK("deck is not valid"),
    DECK_HAS_HERO("deck has hero."),
    WRONG_MODE("wrong mode"),
    INVALID_OPPONENT_DECK("opponent deck is not complete"),
    INVALID_OPPONENT("oponent username is invalid"),
    CARD_NOT_SELECTED("card not selected"),
    INVALID_CARD_ID("invalid card id"),
    INVALID_CARDNAME("invalid card name"),
    INVALID_TARGET("invalid target"),
    NOT_ENOUGH_MANA("you don't have enough mana"),
    GAME_IS_NOT_OVER("game is not over"),
    NO_ITEM_SELECTED("no item selected"),
    INVALID_LEVEL("invalid level"),
    CAN_NOT_MOVE_AGAIN("this card can't move again"),
    TARGET_NOT_IN_RANGE("target not in range"),
    CAN_NOT_ATTACK_AGAIN("this card can't attack again"),
    WRONG_HERO_NAME("hero doesn't exist with this name"),
    OPPONENT_HERO_NOT_SELECTED("opponent hero is not selected"),
    NO_SPECIAL_POWER("card doesn't have special power"),
    NOT_ENOUGH_COOLDOWN("hero is waiting for cooldown"),
    CARD_IS_STUNNED("this card is stunned"),
    DUPLICATE_FILE_DECK_NAME("a deck with this name has been saved"),
    INVALID_DECK_FILE_NAME("no deck with this name is saved"),
    NO_VALID_DECK("you don't have any valid deck"),
    SAME_DECK("you have a deck with this name"),
    YOU_DONT_HAVE_THE_CARD("you don't have deck cards"),
    //    LOGGED_IN("you must logout from your account before creating new account or login into another"),
//    NOT_LOGGED_IN("you must login to your account to");
    NO_ERROR("no error");
    private String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

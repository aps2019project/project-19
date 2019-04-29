package view;

public enum ErrorType {
    INVALID_COMMAND("invalid command."),
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
    INVALID_OPPONENT("oponent username is invalid");
    //    LOGGED_IN("you must logout from your account before creating new account or login into another"),
//    NOT_LOGGED_IN("you must login to your account to");
    private String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

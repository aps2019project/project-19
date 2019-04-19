package view;

public enum ErrorType {
    INVALID_COMMAND("invalid command."),
    USERNAME_TAKEN("this user name is already taken."),
    INVALID_USERNAME("this user name does not exists."),
    INVALID_PASSWORD("your password is wrong."),
    NOT_FOUND("not found"),
    NOT_ENOUGH_MONEY("not enough money"),
    FULL_ITEMS("you have already three items"),
    INVALID_SELL("you dont have that.");
//    LOGGED_IN("you must logout from your account before creating new account or loggin into another"),
//    NOT_LOGGED_IN("you must login to your account to");
    private String message;
    ErrorType(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}

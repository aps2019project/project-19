package view;

public enum ErrorType {
    INVALID_COMMAND("invalid command"),
    INVALID_ACCOUNT_NAME("account name is already taken");
    private String message;
    ErrorType(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}

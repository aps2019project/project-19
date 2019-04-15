package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Account {

    private static HashMap<String, Account> nameAccountHashMap = new HashMap<String, Account>();
    private String userName;
    private String password;
    private long money;
    private ArrayList<Game> matchHistory = new ArrayList<>();
    private Collection collection;

    public static void addAccount(Account account){}

    public void addGame(Game game){}

    public static HashMap<String, Account> getNameAccountHashMap() {
        return nameAccountHashMap;
    }

    public ArrayList<Game> getMatchHistory() {
        return matchHistory;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public static boolean userNameIsValid(String userName){}

    public static boolean passwordIsValid(String password, Account account){}
}

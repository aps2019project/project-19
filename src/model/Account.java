package model;

import model.Game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Account implements Comparable<Account> {

    private static HashMap<String, Account> accounts = new HashMap<String, Account>();
    private String userName;
    private String password;
    private int numberOfWins;
    private long money;
    private ArrayList<Game> matchHistory = new ArrayList<>();
    private Collection collection = new Collection();

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.money = 15000;
    }

    public static String printAccounts() {
        ArrayList<Account> accounts = new ArrayList<>(Account.getAccounts().values());
        Collections.sort(accounts);
        String output = new String("");
        int counter = 1;
        for (Account account : accounts) {
            output += counter + " -  UserName : " + account.getUserName() +
                    " - Wins : " + account.getNumberOfWins() + "\n";
            counter++;
        }
        return output;
    }

    public static void addAccount(Account account) {
        accounts.put(account.getUserName(), account);
    }

    public void addGame(Game game) {
    }

    public static HashMap<String, Account> getAccounts() {
        return accounts;
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

    public void increaseMoney(long money) {
        this.money += money;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public static boolean userNameIsValid(String userName) {
        return accounts.containsKey(userName);
    }

    public static boolean passwordIsValid(String password, String userName) {
        return accounts.get(userName).password.equals(password);
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void increaseWinNumbers() {
        this.numberOfWins++;
    }

    @Override
    public int compareTo(Account account) {
        return this.numberOfWins != account.numberOfWins ? -Integer.compare(this.numberOfWins, account.numberOfWins) :
                this.userName.compareTo(account.userName);
    }

    public void setMatchHistory(ArrayList<Game> matchHistory) {
        this.matchHistory = matchHistory;
    }

}

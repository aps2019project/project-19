package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Account;
import model.Buff.Buff;

import java.io.*;

public class AccountManagement {
    private static Gson gson = new GsonBuilder().registerTypeAdapter(Buff.class, new BuffAdapter())
            .setPrettyPrinting().create();

    public static void readAccounts() {
        File file = new File("src/data/Accounts");
        String[] accounts = file.list();
        Reader reader;
        for (int i = 0; i < accounts.length; i++) {
            try {
                reader = new FileReader("src/data/Accounts/"+accounts[i]);
                Account account = gson.fromJson(reader, Account.class);
                Account.getAccounts().put(account.getUserName(), account);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveAccount(Account account) {
        try (Writer writer = new FileWriter("src/data/Accounts/" + account.getUserName() + ".json")) {
            writer.write(gson.toJson(account));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
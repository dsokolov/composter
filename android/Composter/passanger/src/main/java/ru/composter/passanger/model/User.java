package ru.composter.passanger.model;

import android.content.Context;
import android.content.SharedPreferences;

import ru.composter.passanger.http.response.ProfileResponse;

public class User {

    private static final String USER = "user";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BALANCE = "balance";

    private String id;
    private String name;
    private int balance;

    public User(ProfileResponse info) {
        if (info != null) {
            id = info.getId();
            name = info.getName();
            balance = info.getBalance();
        }
    }

    public User(String id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void save(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        preferences.edit().putString(ID, id).putString(NAME, name).putInt(BALANCE, balance).apply();
    }

    public static User getUserInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        return new User(preferences.getString(ID, null), preferences.getString(NAME, null), preferences.getInt(BALANCE, 0));
    }
}

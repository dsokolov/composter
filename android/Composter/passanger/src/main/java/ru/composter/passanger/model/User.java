package ru.composter.passanger.model;

import android.content.Context;
import android.content.SharedPreferences;

import ru.composter.passanger.http.response.ProfileResponse;

public class User {

    private static final String USER = "user";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BALANCE = "balance";
    private static final String TIMESTAMP = "timestamp";

    private String id;
    private String name;
    private String balance;
    private long timestamp;

    public User(ProfileResponse info) {
        if (info != null) {
            id = info.getId();
            name = info.getName();
            balance = info.getBalance();
        }
    }

    public User(String id, String name, String balance, long timestamp) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.timestamp = timestamp;
    }

    public void setBalance(String balance) {
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

    public String getBalance() {
        return balance;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void save(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        preferences.edit().putString(ID, id).putString(NAME, name).
                putString(BALANCE, balance).putLong(TIMESTAMP, System.currentTimeMillis()).apply();
    }

    public static User getUserInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        return new User(preferences.getString(ID, null), preferences.getString(NAME, null),
                preferences.getString(BALANCE, "0"), preferences.getLong(TIMESTAMP, System.currentTimeMillis()));
    }
}

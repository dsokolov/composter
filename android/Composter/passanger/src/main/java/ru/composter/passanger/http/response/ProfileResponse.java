package ru.composter.passanger.http.response;

import java.io.Serializable;

public class ProfileResponse implements Serializable {

    String id;
    String name;
    String balance;
    int role;

    public int getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }
}

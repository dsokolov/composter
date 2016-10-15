package ru.composter.passanger.http.request;

/**
 * Created by Admin on 15.10.2016.
 */

public class RegistrationRequest {

    String name;
    String password;
    String public_key;
    int role;

    public RegistrationRequest(String name, String password, String public_key, int role) {
        this.name = name;
        this.password = password;
        this.public_key = public_key;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getRole() {
        return role;
    }

    public String getPublic_key() {
        return public_key;
    }
}

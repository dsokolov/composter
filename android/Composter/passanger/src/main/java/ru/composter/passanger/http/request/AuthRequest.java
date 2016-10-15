package ru.composter.passanger.http.request;

public class AuthRequest {

    String name;
    String password;

    public AuthRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}

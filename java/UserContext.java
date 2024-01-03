package com.example.java;

public class UserContext {
    private static UserContext instance;
    private String loggedInUsername;


    UserContext() {
        // Private constructor to prevent instantiation
    }

    public static UserContext getInstance() {
        if (instance == null) {
            instance = new UserContext();
        }
        return instance;
    }




    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setLoggedInUsername(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
    }
}

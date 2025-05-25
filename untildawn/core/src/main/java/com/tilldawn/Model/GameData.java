package com.tilldawn.Model;

import java.util.ArrayList;

public class GameData {
    private static GameData instance;
    private final ArrayList<User> users;
    private User currentUser;

    public GameData() {
        users = new ArrayList<>();
    }

    public static GameData getInstance() {
        if (instance == null) instance = new GameData();
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

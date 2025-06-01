package com.tilldawn.Model;

import java.util.ArrayList;

public class GameData {
    private static GameData instance;
    private final ArrayList<Player> players;
    private Player currentPlayer;

    public GameData() {
        players = new ArrayList<>();
    }

    public static GameData getInstance() {
        if (instance == null) instance = new GameData();
        return instance;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player findPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) return player;
        }
        return null;
    }

    public void addUser(Player player) {
        players.add(player);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentUser(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}

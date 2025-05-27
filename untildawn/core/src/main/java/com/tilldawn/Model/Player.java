package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Model.enums.InputKey;

import java.util.HashMap;
import java.util.Random;

public class Player {
    private String username;
    private String password;
    private String securityQuestion;
    private String answer;
    private int points = 0;
    private boolean isGuest = false;
    private int avatarIndex;
    private TillDawnGame game = null;
    private final HashMap<InputKey, Integer> keys = new HashMap<>();

    public Player(String username, String password, String securityQuestion, String answer) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.avatarIndex = (new Random()).nextInt(7);
        for (InputKey value : InputKey.values()) {
            keys.put(value,value.getMainKey());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoint(int amount) {
        this.points += amount;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public Texture getAvatar() {
        return GameAssetManager.getInstance().getAvatars().get(this.avatarIndex);
    }

    public int getAvatarIndex() {
        return avatarIndex;
    }

    public void setAvatarIndex(int avatarIndex) {
        this.avatarIndex = avatarIndex;
    }

    public void drawAvatar(SpriteBatch batch, float x, float y) {
        batch.draw(getAvatar(), x, y);
    }

    public TillDawnGame getGame() {
        return game;
    }

    public void setGame(TillDawnGame game) {
        this.game = game;
    }

    public HashMap<InputKey, Integer> getKeys() {
        return keys;
    }
}

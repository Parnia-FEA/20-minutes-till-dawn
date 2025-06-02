package com.tilldawn.Model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Main;
import com.tilldawn.Model.enums.InputKey;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.Model.enums.Language;
import com.tilldawn.Model.enums.MusicEnum;
import com.tilldawn.View.GameView;

import java.awt.image.TileObserver;
import java.util.HashMap;
import java.util.Random;

public class Player {
    private String username;
    private String password;
    private LangKey securityQuestion;
    private String answer;
    private boolean isGuest = false;
    private int avatarIndex;
    private TillDawnGame game = null;
    private final HashMap<InputKey, Integer> keys = new HashMap<>();
    private boolean autoReload = false;
    private int kill = 0;
    private int score = 0;
    private int maxSurvivalTime = 0;
    private float musicVolume = 0.5f;
    private MusicEnum music = MusicEnum.Chase;
    private boolean sfx = true;
    private Language language;


    public Player(String username, String password, LangKey securityQuestion, String answer, Language language) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.avatarIndex = (new Random()).nextInt(7);
        for (InputKey value : InputKey.values()) {
            keys.put(value,value.getMainKey());
        }
        this.language = language;
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

    public LangKey getSecurityQuestion() {
        return securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public int getAvatarIndex() {
        return avatarIndex;
    }

    public void setAvatarIndex(int avatarIndex) {
        this.avatarIndex = avatarIndex;
    }

    public void drawAvatar(float x, float y) {
        Sprite sprite = new Sprite(GameAssetManager.getInstance().getAvatars().get(this.avatarIndex));
        sprite.setPosition(x, y);
        sprite.draw(Main.getBatch());
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

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxSurvivalTime() {
        return maxSurvivalTime;
    }

    public void setMaxSurvivalTime(int maxSurvivalTime) {
        this.maxSurvivalTime = maxSurvivalTime;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public MusicEnum getMusic() {
        return music;
    }

    public void setMusic(MusicEnum music) {
        this.music = music;
    }

    public boolean isSfx() {
        return sfx;
    }

    public void setSfx(boolean sfx) {
        this.sfx = sfx;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}

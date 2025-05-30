package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.Ability;
import com.tilldawn.Model.enums.Hero;
import com.tilldawn.Model.enums.InputKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class TillDawnGame {
    private Hero hero;
    private Weapon weapon;
    private float gameTimer;
    private Texture playerTexture;
    private Sprite playerSprite;
    private float playerPosX = (float) Gdx.graphics.getWidth() / 2;
    private float playerPosY = (float) Gdx.graphics.getHeight() / 2;
    private float playerHealth = 100;
    private CollisionRect playerRect ;
    private float playerSpeed;
    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;
    private boolean isPlayerWalking = false;
    private float playerTime = 0f;
    private int maxHP;
    private int HP;
    private int level = 1;
    private int XP = 0;
    private boolean isChoosingRandomAbility = false;
    private final ArrayList<Ability> randomAbilities = new ArrayList<>();


    public TillDawnGame(Hero hero, Weapon weapon, int time) {
        this.hero = hero;
        this.weapon = weapon;
        this.gameTimer = time * 60;
        this.playerSpeed = hero.getSpeed();
        this.playerTexture = GameAssetManager.getInstance().getIdleTexture().get(hero.toString()).get(0);
        this.playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        playerRect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight(), playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.maxHP = hero.getSpeed();
        this.HP = this.maxHP;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public float getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(float gameTimer) {
        this.gameTimer = gameTimer;
    }

    public Player getPlayer() {
        return GameData.getInstance().getCurrentPlayer();
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public float getPlayerPosX() {
        return playerPosX;
    }

    public void setPlayerPosX(float playerPosX) {
        this.playerPosX = playerPosX;
    }

    public float getPlayerPosY() {
        return playerPosY;
    }

    public void setPlayerPosY(float playerPosY) {
        this.playerPosY = playerPosY;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public CollisionRect getPlayerRect() {
        return playerRect;
    }

    public void setPlayerRect(CollisionRect playerRect) {
        this.playerRect = playerRect;
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public boolean isPlayerWalking() {
        return isPlayerWalking;
    }

    public void setPlayerWalking(boolean playerWalking) {
        isPlayerWalking = playerWalking;
    }

    public HashMap<InputKey, Integer> getKeys() {
        return getPlayer().getKeys();
    }

    public float getPlayerTime() {
        return playerTime;
    }

    public void setPlayerTime(float playerTime) {
        this.playerTime = playerTime;
    }

    public void changePlayerPosX(float amount) {
        this.playerPosX += amount;
    }

    public void changePlayerPosY(float amount) {
        this.playerPosY += amount;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int xp) {
        this.XP = xp;
    }

    public void increaseXP(int amount) {
        XP += amount;
        if (XP >= (level * (level + 1)) * 10) {
            level++;
            isChoosingRandomAbility = true;
            randomAbilities.clear();
            ArrayList<Ability> abilities = new ArrayList<>(Arrays.asList(Ability.values()));
            for (int i = 0; i < 3; i++) {
                int random = (new Random()).nextInt(abilities.size());
                randomAbilities.add(abilities.get(random));
                abilities.remove(random);
            }
        }
    }

    public boolean isChoosingRandomAbility() {
        return isChoosingRandomAbility;
    }

    public void setChoosingRandomAbility(boolean choosingRandomAbility) {
        isChoosingRandomAbility = choosingRandomAbility;
    }

    public ArrayList<Ability> getRandomAbilities() {
        return randomAbilities;
    }
}

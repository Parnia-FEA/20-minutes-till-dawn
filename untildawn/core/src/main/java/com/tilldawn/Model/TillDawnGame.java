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
    private final float time;
    private float gameTimer;
    private Texture playerTexture;
    private Sprite playerSprite;
    private float playerPosX = (float) Gdx.graphics.getWidth() / 2;
    private float playerPosY = (float) Gdx.graphics.getHeight() / 2;
    private float playerHealth = 100;
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
    private boolean isPlayerInvincible = false;
    private float invincibleTime = 0f;
    private boolean isSpeedyAbilityOn = false;
    private float speedyAbilityTimer = 0f;
    private boolean isDamagerAbilityOn = false;
    private float DamagerAbilityTimer = 0f;
    private boolean isEyebatCheatCodeUsed = false;
    private int kill = 0;
    private final HashMap<Ability, Integer> abilities = new HashMap<>();
    private boolean isGamePaused = false;


    public TillDawnGame(Hero hero, Weapon weapon, int time) {
        this.hero = hero;
        this.weapon = weapon;
        this.time = time * 60;
        this.gameTimer = time * 60;
        this.playerSpeed = hero.getSpeed();
        this.playerTexture = GameAssetManager.getInstance().getIdleTexture().get(hero.toString()).get(0);
        this.playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.maxHP = hero.getHP();
        this.HP = this.maxHP;
        for (Ability value : Ability.values()) {
            abilities.put(value, 0);
        }
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

    public float getTime() {
        return time;
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

    public float getPlayerSpeed() {
        if (isSpeedyAbilityOn) return playerSpeed * 2;
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

    public void decrementHP() {
        if (this.HP > 0) this.HP--;
    }

    public int getLevel() {
        return level;
    }

    public void increaseLevel() {
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

    public int getXP() {
        return XP;
    }

    public void increaseXP(int amount) {
        XP += amount;
        if (XP >= (level * (level + 1)) * 10) {
            increaseLevel();
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

    public boolean isPlayerInvincible() {
        return isPlayerInvincible;
    }

    public void setPlayerInvincible(boolean playerInvincible) {
        isPlayerInvincible = playerInvincible;
    }

    public float getInvincibleTime() {
        return invincibleTime;
    }

    public void setInvincibleTime(float invincibleTime) {
        this.invincibleTime = invincibleTime;
    }

    public boolean isSpeedyAbilityOn() {
        return isSpeedyAbilityOn;
    }

    public void setSpeedyAbilityOn(boolean speedyAbilityOn) {
        isSpeedyAbilityOn = speedyAbilityOn;
    }

    public float getSpeedyAbilityTimer() {
        return speedyAbilityTimer;
    }

    public void setSpeedyAbilityTimer(float speedyAbilityTimer) {
        this.speedyAbilityTimer = speedyAbilityTimer;
    }

    public boolean isDamagerAbilityOn() {
        return isDamagerAbilityOn;
    }

    public void setDamagerAbilityOn(boolean damagerAbilityOn) {
        isDamagerAbilityOn = damagerAbilityOn;
    }

    public float getDamagerAbilityTimer() {
        return DamagerAbilityTimer;
    }

    public void setDamagerAbilityTimer(float damagerAbilityTimer) {
        DamagerAbilityTimer = damagerAbilityTimer;
    }

    public int getWeaponDamage() {
        if (isDamagerAbilityOn) return weapon.getType().getDamage() * 125 / 100;
        return weapon.getType().getDamage();
    }

    public boolean isEyebatCheatCodeUsed() {
        return isEyebatCheatCodeUsed;
    }

    public void setEyebatCheatCodeUsed(boolean eyebatCheatCodeUsed) {
        isEyebatCheatCodeUsed = eyebatCheatCodeUsed;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public HashMap<Ability, Integer> getAbilities() {
        return abilities;
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }

    public void setGamePaused(boolean gamePaused) {
        isGamePaused = gamePaused;
    }
}

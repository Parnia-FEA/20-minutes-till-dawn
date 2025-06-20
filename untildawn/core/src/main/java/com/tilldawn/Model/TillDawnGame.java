package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.Ability;
import com.tilldawn.Model.enums.Hero;
import com.tilldawn.Model.enums.InputKey;
import com.tilldawn.Model.enums.MonsterType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class TillDawnGame {
    private final Hero hero;
    private final Weapon weapon;
    private final float time;
    private float gameTimer;
    private final Sprite playerSprite;
    private float playerPosX = (float) Gdx.graphics.getWidth() / 2;
    private float playerPosY = (float) Gdx.graphics.getHeight() / 2;
    private float playerHealth;
    private final float playerSpeed;
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
    private float backgroundX = 0;
    private float backgroundY = 0;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Monster> monsters = new ArrayList<>();
    private final ArrayList<Monster> explodedMonsters = new ArrayList<>();
    private final ArrayList<Drop> drops = new ArrayList<>();
    private float tentacleSpawnTimer = 0f;
    private final float tentacleSpawnInterval = 3f;
    private float eyebatSpawnTimer = 0f;
    private final float eyebatSpawnInterval = 10f;
    private final ArrayList<Bullet> eyebatBullets = new ArrayList<>();
    private final int healthToHP = 5;
    private final Sprite hitImpactSprite;
    private float hitImpactTime = 0f;
    private boolean isHitImpactOn = false;
    private Boss boss = null;
    private boolean isBossSpawned = false;
    private boolean isBossKilled = false;


    public TillDawnGame(Hero hero, Weapon weapon, int time) {
        this.hero = hero;
        this.weapon = weapon;
        this.time = time * 60;
        this.gameTimer = time * 60;
        this.playerSpeed = hero.getSpeed();
        Texture playerTexture = GameAssetManager.getInstance().getIdleTexture().get(hero.toString()).get(0);
        this.playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        this.maxHP = hero.getHP();
        this.HP = this.maxHP;
        for (Ability value : Ability.values()) {
            abilities.put(value, 0);
        }
        this.playerHealth = this.HP * 5;

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50 ; j++) {
                Monster monster = new Monster(MonsterType.Tree);
                float x, y;
                Random random = new Random();
                boolean flag = true;
                do {
                    x = random.nextFloat() * ((float) Gdx.graphics.getWidth() / 2);
                    y = random.nextFloat() * ((float) Gdx.graphics.getHeight() / 2);
                    monster.getSprite().setPosition(x + (i - 25) * ((float) Gdx.graphics.getWidth() / 2), y + (j - 25) * ((float) Gdx.graphics.getHeight() / 2));
                    if (playerSprite.getBoundingRectangle().overlaps(monster.getSprite().getBoundingRectangle())) flag = false;
                } while(!flag);
                monsters.add(monster);
            }
        }
        this.hitImpactSprite = new Sprite(GameAssetManager.getInstance().getHitImpactTexture().get(0));
        this.hitImpactSprite.setScale(3);
    }

    public Hero getHero() {
        return hero;
    }

    public Weapon getWeapon() {
        return weapon;
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

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public float getPlayerPosX() {
        return playerPosX;
    }

    public float getPlayerPosY() {
        return playerPosY;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
        if (this.playerHealth <= (this.HP - 1) * this.healthToHP) decrementHP();
    }

    public float getPlayerSpeed() {
        if (isSpeedyAbilityOn) return playerSpeed * 2;
        return playerSpeed;
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

    public boolean increaseXP(int amount) {
        XP += amount;
        if (XP >= (level * (level + 1)) * 10) {
            increaseLevel();
            return true;
        }
        return false;
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

    public float getBackgroundX() {
        return backgroundX;
    }

    public void setBackgroundX(float backgroundX) {
        this.backgroundX = backgroundX;
    }

    public float getBackgroundY() {
        return backgroundY;
    }

    public void setBackgroundY(float backgroundY) {
        this.backgroundY = backgroundY;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<Monster> getExplodedMonsters() {
        return explodedMonsters;
    }

    public ArrayList<Drop> getDrops() {
        return drops;
    }

    public float getTentacleSpawnTimer() {
        return tentacleSpawnTimer;
    }

    public void setTentacleSpawnTimer(float tentacleSpawnTimer) {
        this.tentacleSpawnTimer = tentacleSpawnTimer;
    }

    public float getTentacleSpawnInterval() {
        return tentacleSpawnInterval;
    }

    public float getEyebatSpawnTimer() {
        return eyebatSpawnTimer;
    }

    public void setEyebatSpawnTimer(float eyebatSpawnTimer) {
        this.eyebatSpawnTimer = eyebatSpawnTimer;
    }

    public float getEyebatSpawnInterval() {
        return eyebatSpawnInterval;
    }

    public ArrayList<Bullet> getEyebatBullets() {
        return eyebatBullets;
    }

    public int getHealthToHP() {
        return healthToHP;
    }

    public Sprite getHitImpactSprite() {
        return hitImpactSprite;
    }

    public float getHitImpactTime() {
        return hitImpactTime;
    }

    public void setHitImpactTime(float hitImpactTime) {
        this.hitImpactTime = hitImpactTime;
    }

    public boolean isHitImpactOn() {
        return isHitImpactOn;
    }

    public void setHitImpactOn(boolean hitImpactOn) {
        isHitImpactOn = hitImpactOn;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public boolean isBossSpawned() {
        return isBossSpawned;
    }

    public void setBossSpawned(boolean bossSpawned) {
        isBossSpawned = bossSpawned;
    }

    public boolean isBossKilled() {
        return isBossKilled;
    }

    public void setBossKilled(boolean bossKilled) {
        isBossKilled = bossKilled;
    }
}

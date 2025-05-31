package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.enums.MonsterType;

import java.util.ArrayList;
import java.util.Random;

public class MonsterController {
    private final TillDawnGame game;
    private final ArrayList<Monster> monsters = new ArrayList<>();
    private final ArrayList<Monster> explodedMonsters = new ArrayList<>();
    private float tentacleSpawnTimer = 0f;
    private final float tentacleSpawnInterval = 3f;
    private float eyebatSpawnTimer = 0f;
    private final float eyebatSpawnInterval = 10f;

    public MonsterController(TillDawnGame game) {
        this.game = game;
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
                    if (game.getPlayerSprite().getBoundingRectangle().overlaps(monster.getSprite().getBoundingRectangle())) flag = false;
                } while(!flag);
                monsters.add(monster);
            }
        }
    }

    public void update(OrthographicCamera camera, float delta) {
        tentacleSpawnTimer += delta;
        eyebatSpawnTimer += delta;
        if (tentacleSpawnTimer >= tentacleSpawnInterval) {
            tentacleSpawnTimer = 0;
            for (int i = 0; i < (int) ((game.getTime() - game.getGameTimer()) / 30); i++) {
                spawnMonster(camera, MonsterType.Tentacle);
            }
        }
        if (eyebatSpawnTimer >= eyebatSpawnInterval) {
            eyebatSpawnTimer = 0;
            for (int i = 0; i < (int) ((4 * (game.getTime() - game.getGameTimer()) - game.getTime() + 30) / 30); i++) {
                spawnMonster(camera, MonsterType.Eyebat);
            }
        }
        moveMonsters();
        monsterAnimation();
        explodedMonsterAnimation();
    }

    private void explodedMonsterAnimation() {
        ArrayList<Monster> toBeDeleted = new ArrayList<>();
        for (Monster explodedMonster : explodedMonsters) {
            Animation<Texture> animation = GameAssetManager.getInstance().getExplosionAnimation();
            explodedMonster.setAnimationTime(explodedMonster.getAnimationTime() + Gdx.graphics.getDeltaTime());
            explodedMonster.getSprite().setRegion(animation.getKeyFrame(explodedMonster.getAnimationTime()));
            if (animation.isAnimationFinished(explodedMonster.getAnimationTime())) {
                toBeDeleted.add(explodedMonster);
            }
        }
        for (Monster monster : toBeDeleted) {
            explodedMonsters.remove(monster);
        }
    }

    private void moveMonsters() {
        for (int i = 2500; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            Vector2 direction = new Vector2(game.getPlayerPosX() - monster.getSprite().getX(), game.getPlayerPosY() - monster.getSprite().getY()).nor();
            monster.getSprite().translate(
                direction.x * 0.2f,
                direction.y * 0.2f
            );
            float angle = direction.angleDeg();
            monster.getSprite().setRotation(angle - 90);
        }
    }

    private void spawnMonster(OrthographicCamera camera, MonsterType type) {
        Monster monster = new Monster(type);
        Random random = new Random();
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;
        Vector2 cameraCenter = new Vector2(camera.position.x, camera.position.y);
        int side = random.nextInt(4);
        float spawnX = 0, spawnY = 0;
        switch (side) {
            case 0:
                spawnX = cameraCenter.x - screenWidth / 2 + random.nextFloat() * screenWidth;
                spawnY = cameraCenter.y + screenHeight / 2 + 100;
                break;
            case 1:
                spawnX = cameraCenter.x - screenWidth / 2 + random.nextFloat() * screenWidth;
                spawnY = cameraCenter.y - screenHeight / 2 - 100;
                break;
            case 2:
                spawnX = cameraCenter.x - screenWidth / 2 - 100;
                spawnY = cameraCenter.y - screenHeight / 2 + random.nextFloat() * screenHeight;
                break;
            case 3:
                spawnX = cameraCenter.x + screenWidth / 2 + 100;
                spawnY = cameraCenter.y - screenHeight / 2 + random.nextFloat() * screenHeight;
                break;
        }
        monster.getSprite().setPosition(spawnX, spawnY);
        monsters.add(monster);
    }

    private void monsterAnimation() {
        for (Monster monster : monsters) {
            Animation<Texture> animation = getTextureAnimation(monster);
            monster.getSprite().setRegion(animation.getKeyFrame(monster.getAnimationTime()));
            if (!animation.isAnimationFinished(monster.getAnimationTime())) {
                monster.setAnimationTime(monster.getAnimationTime() + Gdx.graphics.getDeltaTime());
            }
            else {
                monster.setAnimationTime(0);
            }
            animation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }

    private static Animation<Texture> getTextureAnimation(Monster monster) {
        Animation<Texture> animation = null;
        if (monster.getType().equals(MonsterType.Tree)) {
            animation = GameAssetManager.getInstance().getTreeAnimation();
        }
        else if (monster.getType().equals(MonsterType.Tentacle)) {
            animation = GameAssetManager.getInstance().getTentacleAnimation();
        }
        else if (monster.getType().equals(MonsterType.Eyebat)) {
            animation = GameAssetManager.getInstance().getEyebatAnimation();
        }
        return animation;
    }

    public void draw() {
        for (Monster monster : monsters) {
            monster.getSprite().draw(Main.getBatch());
        }
        for (Monster monster : explodedMonsters) {
            monster.getSprite().draw(Main.getBatch());
        }
    }

    public void handleCollisionOfPlayerWithMonster() {
        if (!game.isPlayerInvincible()) {
            for (Monster monster : monsters) {
                if (monster.getSprite().getBoundingRectangle().overlaps(game.getPlayerSprite().getBoundingRectangle())) {
                    game.decrementHP();
                    game.setPlayerInvincible(true);
                    game.setInvincibleTime(0);
                    return;
                }
            }
        }
    }

    public void killEyebats() {
        ArrayList<Monster> killed = new ArrayList<>();
        for (int i = 2500; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            if (monster.getType().equals(MonsterType.Eyebat)) {
                killed.add(monster);
            }
        }
        game.setKill(game.getKill() + killed.size());
        for (Monster monster : killed) {
            monsters.remove(monster);
            monsterExploded(monster);
        }
    }

    public void handleCollisionOfBulletsAndMonsters(ArrayList<Bullet> bullets) {
        int damage = game.getWeaponDamage();
        ArrayList<Monster> killed = new ArrayList<>();
        for (int i = 2500; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            Bullet collidedBullet = null;
            for (Bullet bullet : bullets) {
                if (monster.getSprite().getBoundingRectangle().overlaps(bullet.getSprite().getBoundingRectangle())) {
                    monster.setHP(monster.getHP() - damage);
                    collidedBullet = bullet;
                    break;
                }
            }
            if (monster.getHP() <= 0) killed.add(monster);
            if (collidedBullet != null) bullets.remove(collidedBullet);
        }
        game.setKill(game.getKill() + killed.size());
        for (Monster monster : killed) {
            monsters.remove(monster);
            monsterExploded(monster);
        }
    }

    private void monsterExploded(Monster monster) {
        Monster explodedMonster = new Monster(MonsterType.Exploded);
        explodedMonster.getSprite().setPosition(monster.getSprite().getX(), monster.getSprite().getY());
        explodedMonsters.add(explodedMonster);
    }
}

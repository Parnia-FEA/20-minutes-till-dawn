package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.Model.enums.MonsterType;

import java.util.ArrayList;
import java.util.Random;

public class MonsterController {
    private final TillDawnGame game;

    public MonsterController(TillDawnGame game) {
        this.game = game;
    }

    public void update(OrthographicCamera camera, float delta) {
        game.setTentacleSpawnTimer(game.getTentacleSpawnTimer() + delta);
        game.setEyebatSpawnTimer(game.getEyebatSpawnTimer() + delta);
        updateEyebatBullets();
        if (game.getTentacleSpawnTimer() >= game.getTentacleSpawnInterval()) {
            game.setTentacleSpawnTimer(0);
            for (int i = 0; i < (int) ((game.getTime() - game.getGameTimer()) / 30); i++) {
                spawnMonster(camera, MonsterType.Tentacle);
            }
        }
        if (game.getEyebatSpawnTimer() >= game.getEyebatSpawnInterval()) {
            game.setEyebatSpawnTimer(0);
            for (int i = 0; i < (int) ((4 * (game.getTime() - game.getGameTimer()) - game.getTime() + 30) / 30); i++) {
                spawnMonster(camera, MonsterType.Eyebat);
            }
        }
        eyebatShot(delta);
        moveMonsters();
        monsterAnimation();
        explodedMonsterAnimation();
    }

    private void updateEyebatBullets() {
        for(Bullet bullet : game.getEyebatBullets()) {
            bullet.getSprite().translate(
                bullet.getDirection().x * 5f,
                bullet.getDirection().y * 5f
            );
        }
    }

    private void eyebatShot(float delta) {
        for (int i = 2500; i < game.getMonsters().size(); i++) {
            Monster monster = game.getMonsters().get(i);
            if (monster.getType().equals(MonsterType.Eyebat)) {
                monster.setShotTime(monster.getShotTime() + delta);
                if (monster.getShotTime() >= monster.getShotInterval()) {
                    monster.setShotTime(0);
                    Vector2 baseDirection = new Vector2(game.getPlayerPosX() - monster.getSprite().getX(), game.getPlayerPosY() - monster.getSprite().getY()).nor();
                    Vector2 perpendicular = new Vector2(-baseDirection.y, baseDirection.x).nor().scl(0);
                    game.getEyebatBullets().add(new Bullet(monster.getSprite().getX() + perpendicular.x, monster.getSprite().getY() + perpendicular.y, baseDirection, GameAssetManager.getInstance().getEyebatBulletTexture()));
                }
            }
        }
    }

    private void explodedMonsterAnimation() {
        ArrayList<Monster> toBeDeleted = new ArrayList<>();
        for (Monster explodedMonster : game.getExplodedMonsters()) {
            Animation<Texture> animation = GameAssetManager.getInstance().getExplosionAnimation();
            explodedMonster.setAnimationTime(explodedMonster.getAnimationTime() + Gdx.graphics.getDeltaTime());
            explodedMonster.getSprite().setRegion(animation.getKeyFrame(explodedMonster.getAnimationTime()));
            if (animation.isAnimationFinished(explodedMonster.getAnimationTime())) {
                toBeDeleted.add(explodedMonster);
            }
        }
        for (Monster monster : toBeDeleted) {
            game.getExplodedMonsters().remove(monster);
            Drop drop = new Drop();
            drop.getSprite().setPosition(monster.getSprite().getX(), monster.getSprite().getY());
            drop.getSprite().setSize(drop.getSprite().getWidth() * 0.1f, drop.getSprite().getHeight() * 0.1f);
            game.getDrops().add(drop);
        }
    }

    private void moveMonsters() {
        for (int i = 2500; i < game.getMonsters().size(); i++) {
            Monster monster = game.getMonsters().get(i);
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
        game.getMonsters().add(monster);
    }

    private void monsterAnimation() {
        for (Monster monster : game.getMonsters()) {
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
        for (Drop drop : game.getDrops()) {
            drop.getSprite().draw(Main.getBatch());
        }
        for (Monster monster : game.getMonsters()) {
            monster.getSprite().draw(Main.getBatch());
        }
        for (Monster monster : game.getExplodedMonsters()) {
            monster.getSprite().draw(Main.getBatch());
        }
        for (Bullet bullet : game.getEyebatBullets()) {
            bullet.getSprite().draw(Main.getBatch());
        }
    }

    public void handleCollisionOfPlayerWithMonster() {
        if (!game.isPlayerInvincible()) {
            for (Monster monster : game.getMonsters()) {
                if (monster.getSprite().getBoundingRectangle().overlaps(game.getPlayerSprite().getBoundingRectangle())) {
                    game.setPlayerHealth(game.getPlayerHealth() - game.getHealthToHP());
                    game.setPlayerInvincible(true);
                    game.setInvincibleTime(0);
                    return;
                }
            }
        }
    }

    public void killEyebats() {
        ArrayList<Monster> killed = new ArrayList<>();
        for (int i = 2500; i < game.getMonsters().size(); i++) {
            Monster monster = game.getMonsters().get(i);
            if (monster.getType().equals(MonsterType.Eyebat)) {
                killed.add(monster);
            }
        }
        game.setKill(game.getKill() + killed.size());
        for (Monster monster : killed) {
            game.getMonsters().remove(monster);
            monsterExploded(monster);
        }
    }

    public void handleCollisionOfBulletsAndMonsters(ArrayList<Bullet> bullets) {
        int damage = game.getWeaponDamage();
        ArrayList<Monster> killed = new ArrayList<>();
        for (int i = 2500; i < game.getMonsters().size(); i++) {
            Monster monster = game.getMonsters().get(i);
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
            game.getMonsters().remove(monster);
            monsterExploded(monster);
        }
    }

    private void monsterExploded(Monster monster) {
        if (GameData.getInstance().getCurrentPlayer().isSfx())
            GameAssetManager.getInstance().getExplosionSound().play();
        Monster explodedMonster = new Monster(MonsterType.Exploded);
        explodedMonster.getSprite().setPosition(monster.getSprite().getX(), monster.getSprite().getY());
        game.getExplodedMonsters().add(explodedMonster);
    }

    public void handleCollisionOfPlayerAndDrops() {
        Sprite player = game.getPlayerSprite();
        ArrayList<Drop> toBeDeleted = new ArrayList<>();
        for (Drop drop : game.getDrops()) {
            if (drop.getSprite().getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
                if (GameData.getInstance().getCurrentPlayer().isSfx())
                    GameAssetManager.getInstance().getCrystalRewardSound().play();
                toBeDeleted.add(drop);
                boolean flag = game.increaseXP(3);
                if (flag && GameData.getInstance().getCurrentPlayer().isSfx())
                    GameAssetManager.getInstance().getLevelUpSound().play();
            }
        }
        for (Drop drop : toBeDeleted) {
            game.getDrops().remove(drop);
        }
    }

    public void handleCollisionOfBulletsAndPlayer() {
        ArrayList<Bullet> toBeDeleted = new ArrayList<>();
        for (Bullet bullet : game.getEyebatBullets()) {
            if (bullet.getSprite().getBoundingRectangle().overlaps(game.getPlayerSprite().getBoundingRectangle())) {
                toBeDeleted.add(bullet);
                game.setPlayerHealth(game.getPlayerHealth() - 1);
            }
        }
        for (Bullet bullet : toBeDeleted) {
            game.getEyebatBullets().remove(bullet);
        }
    }
}

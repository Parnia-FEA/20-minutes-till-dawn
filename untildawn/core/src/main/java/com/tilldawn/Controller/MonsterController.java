package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.enums.MonsterType;

import java.util.ArrayList;
import java.util.Random;

public class MonsterController {
    private final TillDawnGame game;
    private final ArrayList<Monster> monsters = new ArrayList<>();

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

    public void update(OrthographicCamera camera) {
        monsterAnimation();
    }

    private void monsterAnimation() {
        for (Monster monster : monsters) {
            if (monster.getType().equals(MonsterType.Tree)) {
                Animation<Texture> animation = GameAssetManager.getInstance().getTreeAnimation();
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
    }

    public void draw() {
        for (Monster monster : monsters) {
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
}

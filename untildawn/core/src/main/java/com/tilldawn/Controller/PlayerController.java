package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.enums.InputKey;

public class PlayerController {
    private TillDawnGame game;

    public PlayerController(TillDawnGame game) {
        this.game = game;
    }

    public void update() {
        if(game.isPlayerIdle()){
            idleAnimation();
        }
        Vector2 direction = new Vector2(0, 0);
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.Up))) direction.y += 1;
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.Down))) direction.y -= 1;
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.Left))) {
            direction.x -= 1;
            game.getPlayerSprite().flip(true, false);
        }
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.Right))) direction.x += 1;
        if (!direction.isZero()) {
            direction.nor();
            float speed = game.getPlayerSpeed();
            game.changePlayerPosX(direction.x * speed);
            game.changePlayerPosY(direction.y * speed);
        }

    }

    public void draw() {
        float spriteWidth = game.getPlayerSprite().getWidth();
        float spriteHeight = game.getPlayerSprite().getHeight();
        float playerX = game.getPlayerPosX();
        float playerY = game.getPlayerPosY();

        game.getPlayerSprite().setPosition(playerX - spriteWidth / 2f, playerY - spriteHeight / 2f);
        game.getPlayerSprite().draw(Main.getBatch());
    }

    private void idleAnimation() {
        Animation<Texture> animation = GameAssetManager.getInstance().getIdleAnimation().get(game.getHero().toString());

        game.getPlayerSprite().setRegion(animation.getKeyFrame(game.getPlayerTime()));

        if (!animation.isAnimationFinished(game.getPlayerTime())) {
            game.setPlayerTime(game.getPlayerTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            game.setPlayerTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }
}

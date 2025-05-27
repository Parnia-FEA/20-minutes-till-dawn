package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
        float move = game.getPlayerSpeed();
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.Up))) game.changePlayerPosY(move);
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.Down))) game.changePlayerPosY(-move);
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.Left))) {
            game.changePlayerPosX(-move);
            game.getPlayerSprite().flip(true, false);
        }
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.Right))) game.changePlayerPosX(move);
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

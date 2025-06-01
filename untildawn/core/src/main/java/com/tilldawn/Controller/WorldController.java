package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Main;
import com.tilldawn.Model.TillDawnGame;

public class WorldController {
    private TillDawnGame game;
    private PlayerController playerController;
    private Texture backgroundTexture;

    public WorldController(TillDawnGame game, PlayerController playerController) {
        this.game = game;
        this.backgroundTexture = new Texture("background.png");
        this.playerController = playerController;
    }

    public void update(OrthographicCamera camera) {
        game.setBackgroundX(camera.position.x - camera.viewportWidth / 2);
        game.setBackgroundY(camera.position.y - camera.viewportHeight / 2);
    }

    public void draw() {
        Main.getBatch().draw(backgroundTexture, game.getBackgroundX(), game.getBackgroundY());
    }
}

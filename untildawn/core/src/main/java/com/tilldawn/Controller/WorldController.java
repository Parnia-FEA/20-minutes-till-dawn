package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Main;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private float backgroundX = 0;
    private float backgroundY = 0;

    public WorldController(PlayerController playerController) {
        this.backgroundTexture = new Texture("background.png");
        this.playerController = playerController;
    }

    public void update(OrthographicCamera camera) {
        backgroundX = camera.position.x - camera.viewportWidth / 2;
        backgroundY = camera.position.y - camera.viewportHeight / 2;
    }

    public void draw() {
        Main.getBatch().draw(backgroundTexture, backgroundX, backgroundY);
    }
}

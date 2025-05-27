package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tilldawn.Main;
import com.tilldawn.Model.Player;
import com.tilldawn.View.GameView;

public class GameController {
    private GameView view;
    private OrthographicCamera camera;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;

    public void setView(GameView view) {
        this.view = view;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playerController = new PlayerController(view.getGame());
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(view.getGame().getWeapon());
    }

    public void updateGame() {
        if (view != null) {
            playerController.update();
            camera.position.set(view.getGame().getPlayerPosX(), view.getGame().getPlayerPosY(), 0);
            camera.update();
            Main.getBatch().setProjectionMatrix(camera.combined);
            worldController.update(camera);
            weaponController.update();
        }
    }

    public void draw() {
        if (view != null) {
            worldController.draw();
            playerController.draw();
        }
    }
}

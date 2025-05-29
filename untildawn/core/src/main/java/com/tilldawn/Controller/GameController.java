package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.enums.InitialPositions;
import com.tilldawn.View.GameView;

import java.util.ArrayList;

public class GameController {
    private GameView view;
    private OrthographicCamera camera;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private ArrayList<Animation<Texture>> hearts = new ArrayList<>();
    private ArrayList<Float> heartTime = new ArrayList<>();

    public void setView(GameView view) {
        this.view = view;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playerController = new PlayerController(view.getGame());
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(view.getGame(), view.getGame().getWeapon());
        for (Sprite heart : this.view.getHearts()) {
            this.hearts.add(GameAssetManager.getInstance().getHeartAnimation());
            this.heartTime.add(0f);
        }
    }

    public void updateGame() {
        if (view != null) {
            playerController.update();
            camera.position.set(view.getGame().getPlayerPosX(), view.getGame().getPlayerPosY(), 0);
            camera.update();
            float cameraXOffset = camera.position.x - camera.viewportWidth / 2;
            float cameraYOffset = camera.position.y - camera.viewportHeight / 2;
            view.getAmmoIcon().setPosition(cameraXOffset + InitialPositions.AmmoIcon.getX(), cameraYOffset + InitialPositions.AmmoIcon.getY());
            heartsAnimation(view.getHearts());
            for (int i = 0; i < view.getHearts().size(); i++) {
                view.getHearts().get(i).setPosition(cameraXOffset + InitialPositions.Hearts.getX() + 30 * i, cameraYOffset + InitialPositions.Hearts.getY());
            }
            for (int i = 0; i < view.getEmptyHearts().size(); i++) {
                view.getEmptyHearts().get(i).setPosition(cameraXOffset + InitialPositions.Hearts.getX() + 30 * i, cameraYOffset + InitialPositions.Hearts.getY());
            }
            Main.getBatch().setProjectionMatrix(camera.combined);
            worldController.update(camera);
            weaponController.update(camera);
        }
    }

    private void heartsAnimation(ArrayList<Sprite> heartSprites) {
        for (int i = 0; i < heartSprites.size(); i++) {
            Sprite sprite = heartSprites.get(i);
            Animation<Texture> animation = hearts.get(i);
            sprite.setRegion(animation.getKeyFrame(heartTime.get(i)));

            if (!animation.isAnimationFinished(heartTime.get(i))) {
                heartTime.set(i, heartTime.get(i) + Gdx.graphics.getDeltaTime());
            }
            else {
                heartTime.set(i, 0f);
            }
            animation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }

    public void draw() {
        if (view != null) {
            worldController.draw();
            playerController.draw();
            weaponController.draw();
            view.getAmmoIcon().draw(Main.getBatch());
            for (int i = 0; i < view.getHearts().size(); i++) {
                if (view.getGame().getHP() <= i) {
                    view.getEmptyHearts().get(i).draw(Main.getBatch());
                }
                else {
                    view.getHearts().get(i).draw(Main.getBatch());
                }
            }
            //TODO if maxHP changes then one element must be added to heart and empty heart arrays
        }
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }
}

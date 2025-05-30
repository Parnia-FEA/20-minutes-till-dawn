package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.enums.Ability;
import com.tilldawn.Model.enums.InitialPositions;
import com.tilldawn.Model.enums.InputKey;
import com.tilldawn.View.GameView;

import java.util.ArrayList;

public class GameController {
    private GameView view;
    private OrthographicCamera camera;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private ArrayList<Animation<Texture>> hearts = new ArrayList<>();
    private ArrayList<Float> heartTime = new ArrayList<>();

    public void setView(GameView view) {
        this.view = view;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playerController = new PlayerController(view.getGame());
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(view.getGame(), view.getGame().getWeapon());
        monsterController = new MonsterController(view.getGame());
        for (Sprite heart : this.view.getHearts()) {
            this.hearts.add(GameAssetManager.getInstance().getHeartAnimation());
            this.heartTime.add(0f);
        }
    }
    private void endGame() {

    }

    public String getTimeRemainingFormatted() {
        float remaining = view.getGame().getGameTimer();
        int minutes = (int)(remaining / 60);
        int seconds = (int)(remaining % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void handleTimer(float delta) {
        view.getGame().setGameTimer(view.getGame().getGameTimer() - delta);

        if (view.getGame().getGameTimer() <= 0) {
            endGame();
        }
        if (view.getGame().getGameTimer() < 60) {
            view.getTimer().setColor(Color.RED);
        }
        view.getTimer().setText(getTimeRemainingFormatted());
    }

    public void updateGame(float delta) {
        if (view != null) {
            handleTimer(delta);
            if (view.getGame().isChoosingRandomAbility()) {
                handleChooseAbilityMenuInputs();
                return;
            }
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
            monsterController.update(camera);
        }
    }

    private void handleChooseAbilityMenuInputs() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            int index = view.getAbilitiesGroup().getCheckedIndex();
            //if (index != -1) {
                CheckBox checkedBox = view.getAbilitiesCheckBox().get(index);
                Ability ability = Ability.valueOf(checkedBox.getText().toString());
                //TODO ability
                view.getAbilitySelectTable().setVisible(false);
                view.getGame().setChoosingRandomAbility(false);
            //}
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            int index = view.getAbilitiesGroup().getCheckedIndex();
            if (index > 0) {
                view.getAbilitiesCheckBox().get(index - 1).setChecked(true);
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            int index = view.getAbilitiesGroup().getCheckedIndex();
            if (index < 2) {
                view.getAbilitiesCheckBox().get(index + 1).setChecked(true);
            }
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
            monsterController.draw();
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

    public OrthographicCamera getCamera() {
        return camera;
    }
}

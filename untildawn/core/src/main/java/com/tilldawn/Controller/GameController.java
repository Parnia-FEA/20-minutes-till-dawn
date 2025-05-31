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
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.enums.Ability;
import com.tilldawn.Model.enums.CheatCode;
import com.tilldawn.Model.enums.InitialPositions;
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
        weaponController = new WeaponController(view.getGame());
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

    private void handleInvincibleTimer(float delta) {
        if (view.getGame().isPlayerInvincible()) {
            view.getGame().setInvincibleTime(view.getGame().getInvincibleTime() + delta);
            if (view.getGame().getInvincibleTime() >= 1) {
                view.getGame().setPlayerInvincible(false);
                view.getGame().setInvincibleTime(0);
            }
        }
    }

    private void handleAbilityTimers(float delta) {
        TillDawnGame game = view.getGame();
        if (game.isSpeedyAbilityOn()) {
            game.setSpeedyAbilityTimer(game.getSpeedyAbilityTimer() + delta);
            if (game.getSpeedyAbilityTimer() >= 10) {
                game.setSpeedyAbilityTimer(0);
                game.setSpeedyAbilityOn(false);
            }
        }
        if (game.isDamagerAbilityOn()) {
            game.setDamagerAbilityTimer(game.getDamagerAbilityTimer() + delta);
            if (game.getDamagerAbilityTimer() >= 10) {
                game.setDamagerAbilityTimer(0);
                game.setDamagerAbilityOn(false);
            }
        }
    }

    private void handleCheatCodes() {
        if (Gdx.input.isKeyJustPressed(CheatCode.DecrementTime.getMainKey())) {
            if (view.getGame().getGameTimer() > 60)
                view.getGame().setGameTimer(view.getGame().getGameTimer() - 60);
            return;
        }
        if (Gdx.input.isKeyJustPressed(CheatCode.IncrementLevel.getMainKey())) {
            view.getGame().increaseLevel();
            return;
        }
        if (Gdx.input.isKeyJustPressed(CheatCode.IncrementHealth.getMainKey())) {
            if (view.getGame().getHP() <= 1) {
                view.getGame().setHP(view.getGame().getHP() + 1);
            }
            return;
        }
        if (Gdx.input.isKeyJustPressed(CheatCode.KillAllEyebats.getMainKey())) {
            if (!view.getGame().isEyebatCheatCodeUsed()) {
                monsterController.killEyebats();
                view.getGame().setEyebatCheatCodeUsed(true);
            }
        }

    }

    public void updateGame(float delta) {
        if (view != null) {
            handleTimer(delta);
            handleInvincibleTimer(delta);
            handleAbilityTimers(delta);
            if (view.getGame().isChoosingRandomAbility()) {
                handleChooseAbilityMenuInputs();
                return;
            }
            handleCheatCodes();
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
            monsterController.update(camera, delta);
            handleCollisions();
        }
    }

    private void handleCollisions() {
        monsterController.handleCollisionOfPlayerWithMonster();
        monsterController.handleCollisionOfBulletsAndMonsters(weaponController.getBullets());
        monsterController.handleCollisionOfPlayerAndDrops();
    }


    private void handleAbility(Ability ability) {
        TillDawnGame game = view.getGame();
        if (ability.equals(Ability.Vitality)) {
            game.setMaxHP(game.getMaxHP() + 1);
            Sprite sprite = new Sprite(view.getFirstHeartTexture());
            sprite.setPosition(InitialPositions.Hearts.getX() + 30 * (view.getHearts().size()), InitialPositions.Hearts.getY());
            view.getHearts().add(sprite);
            sprite = new Sprite(view.getEmptyHeartTexture());
            sprite.setPosition(InitialPositions.Hearts.getX() + 30 * (view.getEmptyHearts().size()), InitialPositions.Hearts.getY());
            view.getEmptyHearts().add(sprite);
            this.hearts.add(GameAssetManager.getInstance().getHeartAnimation());
            this.heartTime.add(0f);
        }
        else if (ability.equals(Ability.Damager)) {
            game.setDamagerAbilityOn(true);
            game.setDamagerAbilityTimer(0);
        }
        else if (ability.equals(Ability.Procrease)) {
            game.getWeapon().setProjectile(game.getWeapon().getProjectile() + 1);
        }
        else if (ability.equals(Ability.Amocrease)) {
            game.getWeapon().setMaxAmmo(game.getWeapon().getMaxAmmo() + 5);
        }
        else if (ability.equals(Ability.Speedy)){
            game.setSpeedyAbilityOn(true);
            game.setSpeedyAbilityTimer(0);
        }
    }

    private void handleChooseAbilityMenuInputs() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            int index = view.getAbilitiesGroup().getCheckedIndex();
            if (index != -1) {
                CheckBox checkedBox = view.getAbilitiesCheckBox().get(index);
                Ability ability = Ability.valueOf(checkedBox.getText().toString());
                handleAbility(ability);
                view.getAbilitySelectTable().setVisible(false);
                view.getGame().setChoosingRandomAbility(false);
            }
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

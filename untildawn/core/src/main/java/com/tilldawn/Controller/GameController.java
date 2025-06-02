package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.Model.enums.*;
import com.tilldawn.View.EndGameView;
import com.tilldawn.View.GameView;
import com.tilldawn.View.MainMenuView;

import java.util.ArrayList;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private ArrayList<Animation<Texture>> hearts = new ArrayList<>();
    private ArrayList<Float> heartTime = new ArrayList<>();

    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(view.getGame());
        worldController = new WorldController(view.getGame(), playerController);
        weaponController = new WeaponController(view.getGame());
        monsterController = new MonsterController(view.getGame());
        for (Sprite heart : this.view.getHearts()) {
            this.hearts.add(GameAssetManager.getInstance().getHeartAnimation());
            this.heartTime.add(0f);
        }
    }
    private void endGame(String result) {
        Player player = GameData.getInstance().getCurrentPlayer();
        player.setGame(null);
        player.setKill(player.getKill() + view.getGame().getKill());
        float remaining = Math.min(view.getGame().getTime() - view.getGame().getGameTimer(), view.getGame().getTime());
        int minutes = (int)(remaining / 60);
        int seconds = (int)(remaining % 60);
        int survivalTime = minutes * 60 + seconds;
        player.setMaxSurvivalTime(Math.max(survivalTime, player.getMaxSurvivalTime()));
        player.setScore(player.getScore() + survivalTime * view.getGame().getKill());
        Main.getCamera().position.set((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2, 0);
        Main.getCamera().update();
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new EndGameView(new EndGameController(), GameAssetManager.getInstance().getSkin(), view.getGame(), result));
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
            endGame("YOU SURVIVED:)");
            if (GameData.getInstance().getCurrentPlayer().isSfx())
                GameAssetManager.getInstance().getWinSound().play();
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
            boolean flag = view.getGame().increaseXP(view.getGame().getLevel() * (view.getGame().getLevel() + 1) * 10 - view.getGame().getXP());
            if (flag && GameData.getInstance().getCurrentPlayer().isSfx())
                GameAssetManager.getInstance().getLevelUpSound().play();
            return;
        }
        if (Gdx.input.isKeyJustPressed(CheatCode.IncrementHealth.getMainKey())) {
            if (view.getGame().getHP() <= 1) {
                view.getGame().setHP(view.getGame().getHP() + 1);
                view.getGame().setPlayerHealth(view.getGame().getPlayerHealth() + view.getGame().getHealthToHP());
            }
            return;
        }
        if (Gdx.input.isKeyJustPressed(CheatCode.KillAllEyebats.getMainKey())) {
            if (!view.getGame().isEyebatCheatCodeUsed()) {
                monsterController.killEyebats();
                view.getGame().setEyebatCheatCodeUsed(true);
            }
        }
        if (Gdx.input.isKeyJustPressed(CheatCode.BossFight.getMainKey())) {
            if (!view.getGame().isBossSpawned()) {
                monsterController.spawnMonster(Main.getCamera(), MonsterType.Boss);
                view.getGame().setBossSpawned(true);
            }
        }

    }

    public void updateGame(float delta) {
        if (view != null) {
            if (view.getGame().isGamePaused()) return;
            if (view.getGame().getHP() <= 0) {
                if (GameData.getInstance().getCurrentPlayer().isSfx())
                    GameAssetManager.getInstance().getLoseSound().play();
                endGame("GAME OVER!");
            }
            handleTimer(delta);
            handleInvincibleTimer(delta);
            handleAbilityTimers(delta);
            if (view.getGame().isChoosingRandomAbility()) {
                handleChooseAbilityMenuInputs();
                return;
            }
            handleCheatCodes();
            updateStage();
            if (Gdx.input.isKeyJustPressed(view.getGame().getKeys().get(InputKey.Pause))) {
                view.getGame().setGamePaused(true);
            }
            playerController.update();
            Main.getCamera().position.set(view.getGame().getPlayerPosX(), view.getGame().getPlayerPosY(), 0);
            Main.getCamera().update();
            float cameraXOffset = Main.getCamera().position.x - Main.getCamera().viewportWidth / 2;
            float cameraYOffset = Main.getCamera().position.y - Main.getCamera().viewportHeight / 2;
            view.getAmmoIcon().setPosition(cameraXOffset + InitialPositions.AmmoIcon.getX(), cameraYOffset + InitialPositions.AmmoIcon.getY());
            heartsAnimation(view.getHearts());
            for (int i = 0; i < view.getHearts().size(); i++) {
                view.getHearts().get(i).setPosition(cameraXOffset + InitialPositions.Hearts.getX() + 30 * i, cameraYOffset + InitialPositions.Hearts.getY());
            }
            for (int i = 0; i < view.getEmptyHearts().size(); i++) {
                view.getEmptyHearts().get(i).setPosition(cameraXOffset + InitialPositions.Hearts.getX() + 30 * i, cameraYOffset + InitialPositions.Hearts.getY());
            }
            Main.getBatch().setProjectionMatrix(Main.getCamera().combined);
            worldController.update(Main.getCamera());
            weaponController.update(Main.getCamera());
            monsterController.update(Main.getCamera(), delta);
            handleCollisions();
            updateActors();
        }
    }

    public float getXPPercent() {
        int XPInThisLevel = view.getGame().getXP() - (view.getGame().getLevel() * (view.getGame().getLevel() - 1) * 10);
        return (float) XPInThisLevel / (20 * view.getGame().getLevel());
    }

    private void updateStage() {
        view.getKill().setText("Kill " + view.getGame().getKill());
        view.getLevel().setText("Level " + view.getGame().getLevel());
    }

    private void updateActors() {
        view.getAmmo().setText(String.format(String.format("%02d", view.getGame().getWeapon().getAmmo()) + " / " + String.format("%02d", view.getGame().getWeapon().getMaxAmmo())));
    }

    public void handleOtherTables() {
        if (view.getGame().isChoosingRandomAbility()) {
            view.getAbilitySelectTable().setVisible(true);
            for (int i = 0; i < 3; i++) {
                Ability ability = view.getGame().getRandomAbilities().get(i);
                view.getAbilities().get(i).setDrawable(new TextureRegionDrawable(new TextureRegion(GameAssetManager.getInstance().getAbilityTexture().get(ability.toString()))));
                view.getAbilitiesDescription().get(i).setText(ability.getDescription());
                view.getAbilitiesCheckBox().get(i).setText(ability.toString());
            }
        }

        if (view.getGame().isGamePaused()) {
            view.getPauseTable().setVisible(true);
            Gdx.input.setInputProcessor(view.getStage());
            for (int i = 0; i < view.getGainedAbilities().size(); i++) {
                view.getNumOfAbility().get(i).setText(view.getGame().getAbilities().get(Ability.valueOf(view.getGainedAbilities().get(i).getText().toString())).toString());
            }
        }
    }

    private void handleCollisions() {
        monsterController.handleCollisionOfPlayerWithMonster();
        monsterController.handleCollisionOfBulletsAndMonsters(weaponController.getBullets());
        monsterController.handleCollisionOfPlayerAndDrops();
        monsterController.handleCollisionOfBulletsAndPlayer();
    }


    private void handleAbility(Ability ability) {
        TillDawnGame game = view.getGame();
        game.getAbilities().put(ability, game.getAbilities().get(ability) + 1);
        if (ability.equals(Ability.Vitality)) {
            game.setMaxHP(game.getMaxHP() + 1);
            game.setHP(game.getHP() + 1);
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

    public void handleResumeButton() {
        view.getGame().setGamePaused(false);
        view.getPauseTable().setVisible(false);
        Gdx.input.setInputProcessor(view);
    }

    public void handleGiveUpButton() {
        endGame("GAME OVER!");
        if (GameData.getInstance().getCurrentPlayer().isSfx())
            GameAssetManager.getInstance().getLoseSound().play();
    }

    public void handleSaveButton() {
        view.getPauseConditionMessage().setText("Game Saved Successfully");
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
            }
        }, 2);
    }
}

package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.enums.InputKey;

public class GameView implements Screen, InputProcessor {
    private final TillDawnGame game;
    private Stage stage;
    private final Sprite ammoIcon;
    private final Label ammos;
    private GameController controller;

    public GameView(TillDawnGame game, GameController controller, Skin skin) {
        this.game = game;
        this.ammoIcon = new Sprite(GameAssetManager.getInstance().getAmmoIconTexture());
        this.ammoIcon.setPosition(5, (float) Gdx.graphics.getHeight() - 70);
        this.ammos = new Label(String.format(String.format("%02d", game.getWeapon().getAmmo()) + " / " + String.format("%02d", game.getWeapon().getMaxAmmo())) ,skin);
        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        ammos.setPosition(30, (float) Gdx.graphics.getHeight() - 73);
        stage.addActor(ammos);
    }

    @Override
    public void render(float delta) {
        controller.updateGame();
        updateActors();
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        controller.draw();
        ammoIcon.draw(Main.getBatch());
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void updateActors() {
        ammos.setText(String.format(String.format("%02d", game.getWeapon().getAmmo()) + " / " + String.format("%02d", game.getWeapon().getMaxAmmo())));
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(screenX, screenY, button);
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public TillDawnGame getGame() {
        return game;
    }

    public Sprite getAmmoIcon() {
        return ammoIcon;
    }
}

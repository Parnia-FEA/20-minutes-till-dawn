package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.enums.InitialPositions;

import java.util.ArrayList;

public class GameView implements Screen, InputProcessor {
    private final TillDawnGame game;
    private Stage stage;
    private final Sprite ammoIcon;
    private final Label ammo;
    private final Texture firstHeartTexture;
    private final Texture emptyHeartTexture;
    private final ArrayList<Sprite> hearts = new ArrayList<>();
    private final ArrayList<Sprite> emptyHearts = new ArrayList<>();
    private GameController controller;

    public GameView(TillDawnGame game, GameController controller, Skin skin) {
        this.game = game;
        this.ammoIcon = new Sprite(GameAssetManager.getInstance().getAmmoIconTexture());
        this.ammoIcon.setPosition(InitialPositions.AmmoIcon.getX(), InitialPositions.AmmoIcon.getY());
        this.ammo = new Label(String.format(String.format("%02d", game.getWeapon().getAmmo()) + " / " + String.format("%02d", game.getWeapon().getMaxAmmo())) ,skin);
        this.controller = controller;
        this.firstHeartTexture = GameAssetManager.getInstance().getHeartTexture().get(0);
        this.emptyHeartTexture = GameAssetManager.getInstance().getEmptyHeartTexture();
        buildHeartsArray();
        controller.setView(this);
    }

    private void buildHeartsArray() {
        for (int i = 0; i < this.game.getMaxHP(); i++) {
            Sprite sprite = new Sprite(this.firstHeartTexture);
            sprite.setPosition(InitialPositions.Hearts.getX() + 30 * i, InitialPositions.Hearts.getY());
            hearts.add(sprite);
            sprite = new Sprite(this.emptyHeartTexture);
            sprite.setPosition(InitialPositions.Hearts.getX() + 30 * i, InitialPositions.Hearts.getY());
            emptyHearts.add(sprite);
        }
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        ammo.setPosition(InitialPositions.Ammo.getX(), InitialPositions.AmmoIcon.getY());
        stage.addActor(ammo);
    }

    @Override
    public void render(float delta) {
        controller.updateGame();
        updateActors();
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        controller.draw();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void updateActors() {
        ammo.setText(String.format(String.format("%02d", game.getWeapon().getAmmo()) + " / " + String.format("%02d", game.getWeapon().getMaxAmmo())));
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

    public Texture getFirstHeartTexture() {
        return firstHeartTexture;
    }

    public ArrayList<Sprite> getHearts() {
        return hearts;
    }

    public Texture getEmptyHeartTexture() {
        return emptyHeartTexture;
    }

    public ArrayList<Sprite> getEmptyHearts() {
        return emptyHearts;
    }
}

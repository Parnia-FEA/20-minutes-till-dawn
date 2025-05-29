package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.enums.Ability;
import com.tilldawn.Model.enums.InitialPositions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameView implements Screen, InputProcessor {
    private final TillDawnGame game;
    private Stage stage;
    private final Sprite ammoIcon;
    private final Label ammo;
    private final Texture firstHeartTexture;
    private final Texture emptyHeartTexture;
    private final ArrayList<Sprite> hearts = new ArrayList<>();
    private final ArrayList<Sprite> emptyHearts = new ArrayList<>();

    private final Label chooseAbilityLabel;
    private final ArrayList<CheckBox> abilitiesCheckBox = new ArrayList<>();
    private final ButtonGroup<CheckBox> abilitiesGroup = new ButtonGroup<>();
    private final ArrayList<Image> abilities = new ArrayList<>();
    private final ArrayList<Label> abilitiesDescription = new ArrayList<>();
    private final TextButton chooseAbilityButton;
    private Table abilitySelectTable;

    private final Label message;

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
        this.abilitySelectTable = new Table();
        abilitySelectTable.setSize((float) Gdx.graphics.getWidth() * 4 / 5, (float) Gdx.graphics.getHeight() * 4 / 5);
        abilitySelectTable.setPosition(
            (Gdx.graphics.getWidth() - abilitySelectTable.getWidth()) / 2,
            (Gdx.graphics.getHeight() - abilitySelectTable.getHeight()) / 2
        );
        abilitySelectTable.setBackground(skin.getDrawable("shadow"));

        this.chooseAbilityLabel = new Label("Choose an Ability", skin, "subtitle");
        this.chooseAbilityLabel.setColor(Color.MAROON);
        for (int i = 0 ; i < 3; i++) {
            CheckBox checkBox = new CheckBox("", skin);
            this.abilitiesCheckBox.add(checkBox);
            this.abilitiesGroup.add(checkBox);
            Image image = new Image();
            this.abilities.add(image);
            Label label = new Label("", skin);
            label.setColor(Color.CORAL);
            this.abilitiesDescription.add(label);
        }
        this.abilitiesGroup.setMinCheckCount(1);
        this.abilitiesGroup.setMaxCheckCount(1);
        this.chooseAbilityButton = new TextButton("Choose", skin);

        this.message = new Label("bbb", skin);
        this.message.setColor(Color.GOLD);
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
        message.setPosition(InitialPositions.Message.getX(), InitialPositions.Message.getY());
        stage.addActor(ammo);
        stage.addActor(message);
        abilitySelectTable.setVisible(false);
        abilitySelectTable.add(chooseAbilityLabel).colspan(7).center();
        abilitySelectTable.add().height(150).colspan(2);
        abilitySelectTable.row();
        abilitySelectTable.row().pad(10, 0, 10, 0);
        for (int i = 0; i < 3; i++) {
            abilitySelectTable.add(this.abilitiesCheckBox.get(i)).pad(5);
            abilitySelectTable.add(this.abilities.get(i)).pad(5);
            abilitySelectTable.add(this.abilitiesDescription.get(i)).pad(5);
            abilitySelectTable.row().pad(10, 0, 10, 0);
        }
        abilitySelectTable.row().pad(10, 0, 10, 0);
        abilitySelectTable.add(chooseAbilityButton).colspan(7).center();
        stage.addActor(abilitySelectTable);
    }

    @Override
    public void render(float delta) {
        controller.updateGame();
        updateActors();
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        controller.draw();
        Main.getBatch().end();
        if (game.isChoosingRandomAbility()) {
            abilitySelectTable.setVisible(true);
            for (int i = 0; i < 3; i++) {
                Ability ability = game.getRandomAbilities().get(i);
                abilities.get(i).setDrawable(new TextureRegionDrawable(new TextureRegion(GameAssetManager.getInstance().getAbilityTexture().get(ability.toString()))));
                abilitiesDescription.get(i).setText(ability.getDescription());
                abilitiesCheckBox.get(i).setText(ability.toString());
            }
        }
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

    public ArrayList<CheckBox> getAbilitiesCheckBox() {
        return abilitiesCheckBox;
    }

    public ButtonGroup<CheckBox> getAbilitiesGroup() {
        return abilitiesGroup;
    }

    public Label getMessage() {
        return message;
    }

    public Table getAbilitySelectTable() {
        return abilitySelectTable;
    }
}

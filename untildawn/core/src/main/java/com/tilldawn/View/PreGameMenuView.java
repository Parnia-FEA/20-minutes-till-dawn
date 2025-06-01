package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.PreGameMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.enums.WeaponEnum;

import java.util.ArrayList;

public class PreGameMenuView implements Screen {
    private Stage stage;
    private final Label title;

    private final Label heroLabel;
    private final ArrayList<CheckBox> heroesCheckBox = new ArrayList<>();
    private final ButtonGroup<CheckBox> heroesGroup = new ButtonGroup<>();
    private final ArrayList<Image> heroes = new ArrayList<>();

    private final Label weaponLabel;
    private final ArrayList<CheckBox> weaponsCheckBox = new ArrayList<>();
    private final ButtonGroup<CheckBox> weaponsGroup = new ButtonGroup<>();

    private final Label timeLabel;
    private final ArrayList<Integer> timeAmount = new ArrayList<>();
    private final ArrayList<CheckBox> times = new ArrayList<>();
    private final ButtonGroup<CheckBox> timesGroup = new ButtonGroup<>();

    private final TextButton startGameButton;
    private final TextButton backButton;

    private final Label conditionMessage;
    public Table table;
    private final PreGameMenuController controller;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.controller = controller;
        this.title = new Label("Pre Game Menu", skin, "title");

        this.heroLabel = new Label("Hero", skin, "subtitle");
        for (String hero : GameAssetManager.getInstance().getHeroes().keySet()) {
            CheckBox checkBox = new CheckBox(hero, skin);
            this.heroesCheckBox.add(checkBox);
            this.heroesGroup.add(checkBox);
            Image heroImage = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager.getInstance().getHeroes().get(hero))));
            this.heroes.add(heroImage);
        }
        this.heroesGroup.setMaxCheckCount(1);
        this.heroesGroup.setMinCheckCount(1);

        this.weaponLabel = new Label("Weapon", skin, "subtitle");
        for (WeaponEnum value : WeaponEnum.values()) {
            CheckBox checkBox = new CheckBox(value.toString(), skin);
            this.weaponsCheckBox.add(checkBox);
            this.weaponsGroup.add(checkBox);
        }
        this.weaponsGroup.setMaxCheckCount(1);
        this.weaponsGroup.setMinCheckCount(1);

        this.timeLabel = new Label("Time", skin, "subtitle");
        timeAmount.add(2);
        timeAmount.add(5);
        timeAmount.add(10);
        timeAmount.add(20);
        for (Integer integer : timeAmount) {
            CheckBox checkBox = new CheckBox(integer + " min", skin);
            this.times.add(checkBox);
            this.timesGroup.add(checkBox);
        }

        this.timesGroup.setMaxCheckCount(1);
        this.timesGroup.setMinCheckCount(1);
        this.conditionMessage = new Label("", skin);

        this.startGameButton = new TextButton("Start Game", skin);
        this.backButton = new TextButton("Back", skin);
        this.table = new Table();
        controller.setView(this);
    }

    @Override
    public void show() {
        GameAssetManager.getInstance().getMenuMusic().play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();

        table.add(title).colspan(5).center();
        table.row().pad(10, 0, 10, 0);

        Table heroTable = new Table();
        heroTable.add(heroLabel).colspan(2).center();
        heroTable.row().pad(10, 0, 10, 0);
        for (int i = 0; i < this.heroes.size(); i++) {
            heroTable.add(this.heroesCheckBox.get(i)).pad(5);
            heroTable.add(this.heroes.get(i)).pad(5);
            heroTable.row().pad(10, 0, 10, 0);
        }
        ScrollPane scrollPane = new ScrollPane(heroTable);
        scrollPane.setScrollingDisabled(true, false);
        table.add(scrollPane).height(400).colspan(2).left().padBottom(20);


        Table weaponTable = new Table();
        weaponTable.add(weaponLabel).colspan(2).center();
        weaponTable.row().pad(10, 0, 10, 0);
        for (CheckBox checkBox : this.weaponsCheckBox) {
            weaponTable.add(checkBox).pad(5);
            weaponTable.row().pad(10, 0, 10, 0);
        }
        table.add(weaponTable).colspan(2).right().padBottom(20);
        table.row().pad(10, 0, 10, 0);

        table.add(timeLabel).colspan(5).center();
        table.row().pad(10, 0, 10, 0);
        for (CheckBox checkBox : this.times) {
            table.add(checkBox).pad(5);
        }
        table.row().pad(10, 0, 10, 0);
        table.add(startGameButton).colspan(5).center();
        table.row().pad(10, 0, 10, 0);
        table.add(conditionMessage).colspan(5).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).colspan(5).center();

        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleStartGameButton();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackButton();
            }
        });

        scrollPane = new ScrollPane(table);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        GameAssetManager.getInstance().getMenuMusic().pause();
    }

    public ArrayList<CheckBox> getHeroesCheckBox() {
        return heroesCheckBox;
    }

    public ButtonGroup<CheckBox> getHeroesGroup() {
        return heroesGroup;
    }

    public ArrayList<CheckBox> getWeaponsCheckBox() {
        return weaponsCheckBox;
    }

    public ButtonGroup<CheckBox> getWeaponsGroup() {
        return weaponsGroup;
    }

    public ArrayList<CheckBox> getTimes() {
        return times;
    }

    public ButtonGroup<CheckBox> getTimesGroup() {
        return timesGroup;
    }

    public ArrayList<Integer> getTimeAmount() {
        return timeAmount;
    }

    public void setConditionMessage(String message, Color color) {
        conditionMessage.setText(message);
        conditionMessage.setColor(color);
    }
}

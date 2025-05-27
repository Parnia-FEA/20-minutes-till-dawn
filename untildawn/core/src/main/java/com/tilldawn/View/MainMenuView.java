package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameData;

public class MainMenuView implements Screen {
    private Stage stage;
    private final Label welcomeTitle;
    private final Label points;
    private final TextButton settingsMenuButton;
    private final TextButton profileMenuButton;
    private final TextButton preGameMenuButton;
    private final TextButton scoreboardMenuButton;
    private final TextButton talentMenuButton;
    private final TextButton logoutButton;
    public Table table;
    public ScrollPane scrollPane;
    private final MainMenuController controller;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.welcomeTitle = new Label("WELCOME " + GameData.getInstance().getCurrentPlayer().getUsername(), skin, "subtitle");
        this.points = new Label("points : " + GameData.getInstance().getCurrentPlayer().getPoints(), skin, "subtitle");
        this.settingsMenuButton = new TextButton("Settings", skin);
        this.profileMenuButton = new TextButton("Profile Menu", skin);
        this.preGameMenuButton = new TextButton("Pre Game Menu", skin);
        this.scoreboardMenuButton = new TextButton("Scoreboard", skin);
        this.talentMenuButton = new TextButton("Talent Menu", skin);
        this.logoutButton = new TextButton("Logout", skin);

        this.table = new Table();
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        table.center();
        table.add(welcomeTitle).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(points).colspan(2).center();
        table.add().height(150).colspan(2);
        table.row();
        table.row().pad(10, 0, 10, 0);
        table.add(settingsMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(profileMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(preGameMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(scoreboardMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(talentMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(logoutButton).colspan(2).center();


        settingsMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSettingsMenuButton();
            }
        });

        profileMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleProfileMenuButton();
            }
        });

        preGameMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handlePreGameMenuButton();
            }
        });

        scoreboardMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleScoreboardMenuButton();
            }
        });

        talentMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleTalentMenuButton();
            }
        });

        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLogoutButton();
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
        GameData.getInstance().getCurrentPlayer().drawAvatar(Main.getBatch(), 100, 450);
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
    }
}

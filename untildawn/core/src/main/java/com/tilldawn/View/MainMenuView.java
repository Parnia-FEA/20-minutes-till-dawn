package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;

public class MainMenuView implements Screen {
    private Stage stage;
    private final Label welcomeTitle;
    private final Label points;
    private final TextButton settingsMenuButton;
    private final TextButton profileMenuButton;
    private final TextButton preGameMenuButton;
    private final TextButton continueSavedGameButton;
    private final TextButton scoreboardMenuButton;
    private final TextButton talentMenuButton;
    private final TextButton logoutButton;
    private final Label conditionMessage;
    private final Image avatarImage;
    private final SelectBox<String> language;
    public Table table;
    public ScrollPane scrollPane;
    private final MainMenuController controller;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.welcomeTitle = new Label("WELCOME " + GameData.getInstance().getCurrentPlayer().getUsername(), skin, "subtitle");
        this.points = new Label("Score : " + GameData.getInstance().getCurrentPlayer().getScore(), skin, "subtitle");
        this.settingsMenuButton = new TextButton("Settings", skin);
        this.profileMenuButton = new TextButton("Profile Menu", skin);
        this.preGameMenuButton = new TextButton("Pre Game Menu", skin);
        this.continueSavedGameButton = new TextButton("Continue Game", skin);
        this.scoreboardMenuButton = new TextButton("Scoreboard", skin);
        this.talentMenuButton = new TextButton("Talent Menu", skin);
        this.logoutButton = new TextButton("Logout", skin);
        this.conditionMessage = new Label("", skin);
        this.conditionMessage.setColor(Color.RED);
        this.language = new SelectBox<>(skin);
        String[] languages = {"English", "French"};
        this.language.setItems(languages);
        this.avatarImage = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager.getInstance().getAvatars().get(GameData.getInstance().getCurrentPlayer().getAvatarIndex()))));
        Gdx.input.setCursorCatched(true);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
        this.table = new Table();
        controller.setView(this);
    }

    @Override
    public void show() {
        GameAssetManager.getInstance().getMenuMusic().play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(false);
        table.top();
        table.padBottom(100);

        table.center();
        table.add(welcomeTitle).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(points).colspan(2).center();
        table.add().height(150).colspan(2);
        table.row();
        table.row().pad(10, 0, 10, 0);
        table.add(conditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(settingsMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(profileMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(preGameMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(continueSavedGameButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(scoreboardMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(talentMenuButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(logoutButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(language).colspan(2).center();
        avatarImage.setPosition(100, 400);

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

        continueSavedGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleContinueSavedGameButton();
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

        language.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = language.getSelected();
                controller.handleLanguageSelectBox(selected);
            }
        });

        scrollPane = new ScrollPane(table);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);
        stage.addActor(avatarImage);

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        stage.getCamera().unproject(mouse);
        Main.getBatch().begin();
        Main.getCursor().setPosition(mouse.x, mouse.y);
        Main.getCursor().draw(Main.getBatch());
        Main.getBatch().end();
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

    public Label getConditionMessage() {
        return conditionMessage;
    }
}

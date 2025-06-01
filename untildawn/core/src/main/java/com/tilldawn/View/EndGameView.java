package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.EndGameController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.TillDawnGame;

public class EndGameView implements Screen {
    private Stage stage;
    private TillDawnGame game;
    private final Label username;
    private final Label timeOfSurvival;
    private final Label kill;
    private final Label score;
    private final Label result;
    private final TextButton quitButton;
    private Table table;
    private final EndGameController controller;

    public EndGameView(EndGameController controller, Skin skin, TillDawnGame game, String condition) {
        this.game = game;
        this.controller = controller;
        controller.setView(this);
        this.username = new Label("Username : " + GameData.getInstance().getCurrentPlayer().getUsername(), skin);
        this.timeOfSurvival = new Label("Survival Time : " + controller.getTimeRemainingFormatted(), skin);
        this.kill = new Label("Kill : " + game.getKill(), skin);
        this.score = new Label("Score : " + controller.getPoints(), skin);
        this.result = new Label(condition, skin, "title");
        this.quitButton = new TextButton("Quit to Menu", skin);
        this.table = new Table();
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        table.center();
        table.add(result).colspan(2).center();
        table.add().height(150).colspan(2);
        table.row();
        table.row().pad(10, 0, 10, 0);
        table.add(username).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(timeOfSurvival).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(kill).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(score).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(quitButton).colspan(2).center();

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleQuitButton();
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    public TillDawnGame getGame() {
        return game;
    }
}

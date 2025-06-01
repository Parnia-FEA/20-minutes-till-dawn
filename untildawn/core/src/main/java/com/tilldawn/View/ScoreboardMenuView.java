package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.ScoreboardMenuController;
import com.tilldawn.Main;
import sun.tools.jconsole.Tab;

import java.util.ArrayList;

public class ScoreboardMenuView implements Screen {
    private Stage stage;
    private final Label title;
    private final Label usernameLabel;
    private final Label scoreLabel;
    private final Label killLabel;
    private final Label timeLabel;
    private final ArrayList<Label> usernames = new ArrayList<>();
    private final ArrayList<Label> scores = new ArrayList<>();
    private final ArrayList<Label> kills = new ArrayList<>();
    private final ArrayList<Label> times = new ArrayList<>();
    private final TextButton backButton;
    private final int num = 10;
    private Table table;
    private ScrollPane scrollPane;
    private final ScoreboardMenuController controller;

    public ScoreboardMenuView(ScoreboardMenuController controller, Skin skin) {
        this.controller = controller;
        this.title = new Label("Scoreboard", skin, "subtitle");
        this.usernameLabel = new Label("Username", skin);
        this.usernameLabel.setColor(Color.CYAN);
        this.scoreLabel = new Label("Score", skin);
        this.scoreLabel.setColor(Color.CYAN);
        this.killLabel = new Label("Kill", skin);
        this.killLabel.setColor(Color.CYAN);
        this.timeLabel = new Label("Max Survival Time", skin);
        this.timeLabel.setColor(Color.CYAN);
        this.backButton = new TextButton("Back", skin);
        for (int i = 0; i < num; i++) {
            usernames.add(new Label("", skin));
            scores.add(new Label("", skin));
            kills.add(new Label("", skin));
            times.add(new Label("", skin));
        }
        usernames.get(0).setColor(Color.GOLD);
        scores.get(0).setColor(Color.GOLD);
        kills.get(0).setColor(Color.GOLD);
        times.get(0).setColor(Color.GOLD);

        usernames.get(1).setColor(Color.GRAY);
        scores.get(1).setColor(Color.GRAY);
        kills.get(1).setColor(Color.GRAY);
        times.get(1).setColor(Color.GRAY);

        usernames.get(2).setColor(Color.BROWN);
        scores.get(2).setColor(Color.BROWN);
        kills.get(2).setColor(Color.BROWN);
        times.get(2).setColor(Color.BROWN);
        table = new Table();
        controller.setView(this);
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(false);
        table.top();
        table.padBottom(100);

        table.center();
        table.add(title).colspan(8).center();
        table.row().pad(10, 0, 10, 0);

        controller.handleScoreLabel();

        Table usernameTable = new Table();
        usernameTable.add(usernameLabel).colspan(2).center();
        usernameTable.row().pad(10, 0, 10, 0);
        for (Label username : usernames) {
            usernameTable.add(username);
            usernameTable.row().pad(10, 0, 10, 0);
        }

        Table scoreTable = new Table();
        scoreTable.add(scoreLabel).colspan(2).center();
        scoreTable.row().pad(10, 0, 10, 0);
        for (Label score : scores) {
            scoreTable.add(score);
            scoreTable.row().pad(10, 0, 10, 0);
        }

        Table killTable = new Table();
        killTable.add(killLabel).colspan(2).center();
        killTable.row().pad(10, 0, 10, 0);
        for (Label kill : kills) {
            killTable.add(kill);
            killTable.row().pad(10, 0, 10, 0);
        }

        Table timeTable = new Table();
        timeTable.add(timeLabel).colspan(2).center();
        timeTable.row().pad(10, 0, 10, 0);
        for (Label time : times) {
            timeTable.add(time);
            timeTable.row().pad(10, 0, 10, 0);
        }

        table.add(usernameTable).colspan(2).center();
        table.add(scoreTable).colspan(2).center();
        table.add(killTable).colspan(2).center();
        table.add(timeTable).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).colspan(8).center();

        usernameLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleUsernameLabel();
            }
        });

        scoreLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleScoreLabel();
            }
        });

        killLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleKillLabel();
            }
        });

        timeLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleTimeLabel();
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

    }

    public ArrayList<Label> getUsernames() {
        return usernames;
    }

    public ArrayList<Label> getScores() {
        return scores;
    }

    public ArrayList<Label> getKills() {
        return kills;
    }

    public ArrayList<Label> getTimes() {
        return times;
    }

    public int getNum() {
        return num;
    }
}

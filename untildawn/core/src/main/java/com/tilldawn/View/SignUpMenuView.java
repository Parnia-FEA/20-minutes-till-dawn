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
import com.tilldawn.Controller.SignUpMenuController;
import com.tilldawn.Main;

public class SignUpMenuView implements Screen {
    private Stage stage;
    private final Label title;
    private final Label usernameField;
    private final Label passwordField;
    private final TextField username;
    private final TextField password;
    private final TextButton signUpButton;
    private final Label signUpConditionMessage;
    private final TextButton guestButton;
    private final TextButton backButton;
    public Table table;
    private final SignUpMenuController controller;

    public SignUpMenuView(SignUpMenuController controller, Skin skin) {
        this.controller = controller;
        this.title = new Label("SIGN UP", skin, "subtitle");
        this.usernameField = new Label("username", skin);
        this.passwordField = new Label("password", skin);
        this.username = new TextField("", skin);
        this.password = new TextField("", skin);
        this.signUpButton = new TextButton("Sign Up", skin);
        this.signUpConditionMessage = new Label("",skin);
        this.guestButton = new TextButton("Play as a guest.", skin);
        this.backButton = new TextButton("Back", skin);
        this.table = new Table();

        controller.setView(this);
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        table.center();
        table.add(title).colspan(2).center();
        table.row();
        table.add().height(100).colspan(2);
        table.row().pad(10, 0, 10, 0);
        table.add(usernameField).width(150).right();
        table.add(username).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(passwordField).width(150).right();
        table.add(password).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(signUpButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(signUpConditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(guestButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).colspan(2).center();

        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSignUpButton();
            }
        });

        guestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleGuestButton();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackButton();
            }
        });

        stage.addActor(table);
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

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    public TextButton getSignUpButton() {
        return signUpButton;
    }

    public TextButton getGuestButton() {
        return guestButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setSignUpConditionMessage(String message, Color color) {
        signUpConditionMessage.setText(message);
        signUpConditionMessage.setColor(color);
    }
}

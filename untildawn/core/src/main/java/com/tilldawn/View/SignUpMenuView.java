package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.SignUpMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;

import java.util.MissingFormatArgumentException;

public class SignUpMenuView implements Screen {
    private Stage stage;
    private final Label title;
    private final Label usernameLabel;
    private final Label passwordLabel;
    private final TextField username;
    private final TextField password;
    private final Label pickQuestionLabel;
    private final SelectBox<String> securityQuestionsBox;
    private final Label answerLabel;
    private final TextField answer;
    private final TextButton signUpButton;
    private final Label signUpConditionMessage;
    private final TextButton guestButton;
    private final TextButton backButton;
    public Table table;
    public ScrollPane scrollPane;
    private final SignUpMenuController controller;

    public SignUpMenuView(SignUpMenuController controller, Skin skin) {
        this.controller = controller;
        this.title = new Label("SIGN UP", skin, "subtitle");
        this.usernameLabel = new Label("username", skin);
        this.passwordLabel = new Label("password", skin);
        this.username = new TextField("", skin);
        this.password = new TextField("", skin);
        this.pickQuestionLabel = new Label("Pick a security question", skin);

        String[] questions = {
            "What is your favorite color?",
            "What is your pet's name?",
            "What is your mother's maiden name?",
            "What was the name of your first school?",
            "What is your favorite movie?",
            "Where were you born?",
            "What is your best friend's name?"
        };
        this.securityQuestionsBox = new SelectBox<>(skin);
        this.securityQuestionsBox.setItems(questions);

        this.answerLabel = new Label("answer", skin);
        this.answer = new TextField("", skin);
        this.signUpButton = new TextButton("Sign Up", skin);
        this.signUpConditionMessage = new Label("",skin);
        this.guestButton = new TextButton("Play as a Guest", skin);
        this.backButton = new TextButton("Back", skin);
        this.table = new Table();
        Gdx.input.setCursorCatched(true);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);

        controller.setView(this);
    }
    @Override
    public void show() {
        GameAssetManager.getInstance().getMenuMusic().play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);

        table.center();
        table.add(title).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(usernameLabel).width(150).right();
        table.add(username).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(passwordLabel).width(150).right();
        table.add(password).width(600).left();
        table.row().pad(10, 0, 10, 0);

        table.add(pickQuestionLabel).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(securityQuestionsBox).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(answerLabel).width(150).right();
        table.add(answer).width(600).left();
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

    public SelectBox<String> getSecurityQuestionsBox() {
        return securityQuestionsBox;
    }

    public TextField getAnswer() {
        return answer;
    }

    public void setSignUpConditionMessage(String message, Color color) {
        signUpConditionMessage.setText(message);
        signUpConditionMessage.setColor(color);
    }
}

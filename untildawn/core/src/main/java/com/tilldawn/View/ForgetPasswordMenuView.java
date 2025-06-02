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
import com.tilldawn.Controller.ForgetPasswordMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.Model.enums.Language;

public class ForgetPasswordMenuView implements Screen {
    private Stage stage;
    private final Label title;
    private final Label usernameLabel;
    private final TextField username;
    private final Label questionLabel;
    private final TextField answer;
    private final Label passwordLabel;
    private final TextField password;
    private final TextButton continueButton;
    private final TextButton submitAnswerButton;
    private final TextButton changePasswordButton;
    private final TextButton backButton;

    private final Label usernameConditionMessage;
    private final Label securityQuestionConditionMessage;
    private final Label passwordConditionMessage;

    public Table table;
    private final ForgetPasswordMenuController controller;

    private Player player = null;

    public ForgetPasswordMenuView(ForgetPasswordMenuController controller, Skin skin) {
        this.controller = controller;
        Language language = GameData.getInstance().getLanguage();
        this.title = new Label(LangKey.ForgetMenuTitle.getTranslation(language), skin, "subtitle");
        this.usernameLabel = new Label(LangKey.UsernameLabel.getTranslation(language), skin);
        this.username = new TextField("", skin);
        this.passwordLabel = new Label(LangKey.ForgetMenuNewPasswordLabel.getTranslation(language), skin);
        this.password = new TextField("", skin);
        this.questionLabel = new Label("", skin);
        this.answer = new TextField("", skin);
        this.continueButton = new TextButton(LangKey.ForgetMenuContinueButton.getTranslation(language), skin);
        this.submitAnswerButton = new TextButton(LangKey.ForgetMenuSubmitAnswerButton.getTranslation(language), skin);
        this.changePasswordButton = new TextButton(LangKey.ForgetMenuTitle.getTranslation(language).toLowerCase(), skin);
        this.backButton = new TextButton(LangKey.Back.getTranslation(language), skin);

        this.usernameConditionMessage = new Label("", skin);
        this.securityQuestionConditionMessage = new Label("", skin);
        this.passwordConditionMessage = new Label("", skin);
        Gdx.input.setCursorCatched(true);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
        this.table = new Table();
        controller.setView(this);
    }

    public void setupUsernameStage() {
        table.clear();
        table.add(title).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(usernameLabel).width(150).right();
        table.add(username).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(continueButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(usernameConditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).colspan(2).center();
    }

    public void setupSecurityQuestionStage() {
        table.clear();
        table.add(title).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(questionLabel).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(answer).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(submitAnswerButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(securityQuestionConditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).colspan(2).center();
    }

    public void setupNewPasswordStage() {
        table.clear();
        table.add(title).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(passwordLabel).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(password).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(changePasswordButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(passwordConditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).colspan(2).center();
    }

    @Override
    public void show() {
        GameAssetManager.getInstance().getMenuMusic().play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();

        setupUsernameStage();


        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleContinueButton();
            }
        });

        submitAnswerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSubmitAnswerButton();
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleChangePasswordButton();
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

    public void setQuestion(String question) {
        questionLabel.setText(question);
    }

    public TextField getAnswer() {
        return answer;
    }

    public TextField getPassword() {
        return password;
    }

    public void setUsernameConditionMessage(String message, Color color) {
        usernameConditionMessage.setText(message);
        usernameConditionMessage.setColor(color);
    }

    public void setSecurityQuestionConditionMessage(String message, Color color) {
        securityQuestionConditionMessage.setText(message);
        securityQuestionConditionMessage.setColor(color);
    }

    public void setPasswordConditionMessage(String message, Color color) {
        passwordConditionMessage.setText(message);
        passwordConditionMessage.setColor(color);
    }

    public Player getUser() {
        return player;
    }

    public void setUser(Player player) {
        this.player = player;
    }
}

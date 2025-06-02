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
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.Model.enums.Language;

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
        Language language = GameData.getInstance().getLanguage();
        this.title = new Label(LangKey.SignUpMenuTitle.getTranslation(language), skin, "subtitle");
        this.usernameLabel = new Label(LangKey.UsernameLabel.getTranslation(language), skin);
        this.passwordLabel = new Label(LangKey.PasswordLabel.getTranslation(language), skin);
        this.username = new TextField("", skin);
        this.password = new TextField("", skin);
        this.pickQuestionLabel = new Label(LangKey.SignUpMenuPickQuestionLabel.getTranslation(language), skin);

        String[] questions = {
            LangKey.FavoriteColorQuestion.getTranslation(language),
            LangKey.PetNameQuestion.getTranslation(language),
            LangKey.MotherNameQuestion.getTranslation(language),
            LangKey.SchoolNameQuestion.getTranslation(language),
            LangKey.FavoriteMovieQuestion.getTranslation(language),
            LangKey.BornQuestion.getTranslation(language),
            LangKey.FriendNameQuestion.getTranslation(language)
        };
        this.securityQuestionsBox = new SelectBox<>(skin);
        this.securityQuestionsBox.setItems(questions);

        this.answerLabel = new Label(LangKey.SignUpMenuAnswerLabel.getTranslation(language), skin);
        this.answer = new TextField("", skin);
        this.signUpButton = new TextButton(LangKey.SignUpMenuSignUpButton.getTranslation(language), skin);
        this.signUpConditionMessage = new Label("",skin);
        this.guestButton = new TextButton(LangKey.SignUpMenuGuestButton.getTranslation(language), skin);
        this.backButton = new TextButton(LangKey.Back.getTranslation(language), skin);
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

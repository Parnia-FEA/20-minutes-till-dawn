package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.LoginMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.Model.enums.Language;

public class LoginMenuView implements Screen {
    private Stage stage;
    private final Label title;
    private final Label loginLabel;
    private final Label usernameLabel;
    private final Label passwordLabel;
    private final TextField username;
    private final TextField password;
    private final TextButton loginButton;
    private final Label loginConditionMessage;
    private final TextButton signUpButton;
    private final SelectBox<String> languageSelectBox;
    private final TextButton forgetPasswordButton;
    private Table table;
    private ScrollPane scrollPane;
    private final LoginMenuController controller;

    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.controller = controller;
        Language language = GameData.getInstance().getLanguage();
        this.title = new Label(LangKey.LoginMenuTitle.getTranslation(language), skin, "title");
        this.loginLabel = new Label(LangKey.LoginMenuLoginLabel.getTranslation(language), skin, "subtitle");
        this.usernameLabel = new Label(LangKey.UsernameLabel.getTranslation(language), skin);
        this.passwordLabel = new Label(LangKey.PasswordLabel.getTranslation(language), skin);
        this.username = new TextField("", skin);
        this.password = new TextField("", skin);
        this.loginButton = new TextButton(LangKey.LoginMenuLoginButton.getTranslation(language), skin);
        this.loginConditionMessage = new Label("",skin);
        this.signUpButton = new TextButton(LangKey.LoginMenuSignUpButton.getTranslation(language), skin);
        this.languageSelectBox = new SelectBox<>(skin);
        String[] languages = {LangKey.LoginMenuEnglishLanguage.getTranslation(language), LangKey.FrenchLanguage.getTranslation(language)};
        this.languageSelectBox.setItems(languages);
        if (language.equals(Language.English))
            this.languageSelectBox.setSelected(LangKey.LoginMenuEnglishLanguage.getTranslation(language));
        else if (language.equals(Language.French))
            this.languageSelectBox.setSelected(LangKey.FrenchLanguage.getTranslation(language));
        this.forgetPasswordButton = new TextButton(LangKey.LoginMenuForgetPasswordButton.getTranslation(language), skin);
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
        table.setFillParent(false);
        table.top();
        table.padBottom(100);

        table.center();
        table.add(title).colspan(2).center();
        table.add().height(150).colspan(2);
        table.row();
        table.row().pad(10, 0, 10, 0);
        table.add(loginLabel).colspan(2).center();
        table.add().height(30).colspan(2);
        table.row().pad(10, 0, 10, 0);
        table.add(usernameLabel).width(300).right();
        table.add(username).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(passwordLabel).width(300).right();
        table.add(password).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(loginButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(loginConditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(signUpButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(forgetPasswordButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(languageSelectBox).colspan(2).center();

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLoginButton();
            }
        });

        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSignUpButton();
            }
        });

        forgetPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleForgetPasswordButton();
            }
        });

        languageSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = languageSelectBox.getSelected();
                controller.handleLanguageSelectBox(selected);
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

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getSignUpButton() {
        return signUpButton;
    }

    public TextButton getForgetPasswordButton() {
        return forgetPasswordButton;
    }

    public void setLoginConditionMessage(String message, Color color) {
        loginConditionMessage.setText(message);
        loginConditionMessage.setColor(color);
    }

}

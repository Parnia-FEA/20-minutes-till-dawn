package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.ProfileMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.Model.enums.Language;

import java.util.ArrayList;

public class ProfileMenuView implements Screen {
    private Stage stage;
    private final Label title;
    private final Label guestConditionMessage;
    private final TextButton changeUsernameButton;
    private final TextButton setUsernameButton;
    private final Label usernameLabel;
    private final TextField username;
    private final Label changeUsernameConditionMessage;
    private final TextButton changePasswordButton;
    private final TextButton setPasswordButton;
    private final Label passwordLabel;
    private final TextField password;
    private final Label changePasswordConditionMessage;
    private final TextButton changeAvatarButton;
    private final ArrayList<CheckBox> avatars = new ArrayList<>();
    private final ButtonGroup<CheckBox> avatarsGroup = new ButtonGroup<>();
    private final ArrayList<Image> avatarImages = new ArrayList<>();
    private final TextButton selectAvatarButton;
    private final Label changeAvatarConditionMessage;
    private final TextButton deleteAccountButton;
    private final TextButton deleteButton;
    private final Label deleteAccountLabel;
    private final CheckBox yes;
    private final CheckBox no;
    private final ButtonGroup<CheckBox> deleteAccount;
    private final TextButton backButton;
    private final TextButton backToProfileMenuButton;
    public Table table;
    private final ProfileMenuController controller;

    public ProfileMenuView(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        Language language = GameData.getInstance().getCurrentPlayer().getLanguage();
        this.title = new Label(LangKey.ProfileMenuLabel.getTranslation(language).toUpperCase(), skin, "subtitle");
        this.guestConditionMessage = new Label("", skin);
        this.changeUsernameButton = new TextButton(LangKey.ProfileMenuChangeUsernameLabel.getTranslation(language), skin);
        this.setUsernameButton = new TextButton(LangKey.ProfileMenuChangeUsernameLabel.getTranslation(language), skin);
        this.changePasswordButton = new TextButton(LangKey.ProfileMenuChangePasswordLabel.getTranslation(language), skin);
        this.setPasswordButton = new TextButton(LangKey.ProfileMenuChangePasswordLabel.getTranslation(language), skin);
        this.usernameLabel = new Label(LangKey.ProfileMenuNewUsernameLabel.getTranslation(language), skin);
        this.passwordLabel = new Label(LangKey.ProfileMenuNewPasswordLabel.getTranslation(language), skin);
        this.username = new TextField("", skin);
        this.password = new TextField("", skin);
        this.changeUsernameConditionMessage = new Label("", skin);
        this.changePasswordConditionMessage = new Label("", skin);
        this.changeAvatarConditionMessage = new Label("", skin);
        this.changeAvatarButton = new TextButton(LangKey.ProfileMenuChangeAvatarButton.getTranslation(language), skin);
        for (Texture avatar : GameAssetManager.getInstance().getAvatars()) {
            CheckBox checkBox = new CheckBox("", skin);
            this.avatars.add(checkBox);
            this.avatarsGroup.add(checkBox);
            Image avatarImage = new Image(new TextureRegionDrawable(new TextureRegion(avatar)));
            this.avatarImages.add(avatarImage);
        }
        this.avatarsGroup.setMaxCheckCount(1);
        this.avatarsGroup.setMinCheckCount(1);
        this.selectAvatarButton = new TextButton(LangKey.ProfileMenuSelectButton.getTranslation(language), skin);
        this.deleteAccountButton = new TextButton(LangKey.ProfileMenuDeleteAccountButton.getTranslation(language), skin);
        this.deleteButton = new TextButton(LangKey.ProfileMenuOKButton.getTranslation(language), skin);
        this.deleteAccountLabel = new Label(LangKey.ProfileMenuDeleteAccountLabel.getTranslation(language), skin);
        this.yes = new CheckBox(LangKey.ProfileMenuYes.getTranslation(language), skin);
        this.no = new CheckBox(LangKey.ProfileMenuNo.getTranslation(language), skin);
        this.deleteAccount = new ButtonGroup<>(yes, no);
        this.deleteAccount.setMaxCheckCount(1);
        this.deleteAccount.setMinCheckCount(1);
        this.backButton = new TextButton(LangKey.Back.getTranslation(language), skin);
        this.backToProfileMenuButton = new TextButton(LangKey.Back.getTranslation(language), skin);
        this.table = new Table();
        Gdx.input.setCursorCatched(true);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
        controller.setView(this);
    }

    public void setupProfileMenuStage() {
        table.clear();
        table.add(title).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(guestConditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(changeUsernameButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(changePasswordButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(changeAvatarButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(deleteAccountButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).colspan(2).center();
    }

    public void setupChangeUsernameStage() {
        table.clear();
        table.add(usernameLabel).width(200).right();
        table.add(username).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(changeUsernameConditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(setUsernameButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backToProfileMenuButton).colspan(2).center();
    }

    public void setupChangePasswordStage() {
        table.clear();
        table.add(passwordLabel).width(200).right();
        table.add(password).width(600).left();
        table.row().pad(10, 0, 10, 0);
        table.add(changePasswordConditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(setPasswordButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backToProfileMenuButton).colspan(2).center();
    }

    public void setupChangeAvatarStage() {
        table.clear();
        table.row();

        Table avatarTable = new Table();

        for (int i = 0; i < this.avatars.size(); i++) {
            avatarTable.add(this.avatars.get(i)).pad(5);
            avatarTable.add(this.avatarImages.get(i)).pad(5);
            avatarTable.row().pad(10, 0, 10, 0);
        }
        ScrollPane scrollPane = new ScrollPane(avatarTable);
        scrollPane.setScrollingDisabled(true, false);
        table.add(scrollPane).height(400).colspan(2).padBottom(20);
        table.row().pad(10, 0, 10, 0);
        table.row();
        table.row().pad(10, 0, 10, 0);
        table.add(selectAvatarButton).colspan(5).center();
        table.row().pad(10, 0, 10, 0);
        table.add(changeAvatarConditionMessage).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backToProfileMenuButton).colspan(5).center();

    }

    public void setupDeleteAccountStage() {
        table.clear();
        table.add(deleteAccountLabel).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.row();
        table.add(yes).pad(5);
        table.add(no).pad(5);
        table.row();
        table.row().pad(10, 0, 10, 0);
        table.add(deleteButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backToProfileMenuButton).colspan(2).center();
    }

    @Override
    public void show() {
        GameAssetManager.getInstance().getMenuMusic().play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();

        setupProfileMenuStage();

        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleChangeUsernameButton();
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleChangePasswordButton();
            }
        });

        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleChangeAvatarButton();
            }
        });

        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleDeleteAccountButton();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackButton();
            }
        });

        backToProfileMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackToProfileMenuButtonButton();
            }
        });

        setUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSetUsernameButton();
            }
        });

        setPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSetPasswordButton();
            }
        });

        selectAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSelectAvatarButton();
            }
        });

        deleteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleDeleteButton();
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

    public TextField getPassword() {
        return password;
    }

    public CheckBox getYes() {
        return yes;
    }

    public CheckBox getNo() {
        return no;
    }

    public ButtonGroup<CheckBox> getDeleteAccount() {
        return deleteAccount;
    }

    public ArrayList<CheckBox> getAvatars() {
        return avatars;
    }

    public ButtonGroup<CheckBox> getAvatarsGroup() {
        return avatarsGroup;
    }

    public void setChangeUsernameConditionMessage(String message, Color color) {
        changeUsernameConditionMessage.setText(message);
        changeUsernameConditionMessage.setColor(color);
    }

    public void setChangePasswordConditionMessage(String message, Color color) {
        changePasswordConditionMessage.setText(message);
        changePasswordConditionMessage.setColor(color);
    }

    public void setGuestConditionMessage(String message, Color color) {
        guestConditionMessage.setText(message);
        guestConditionMessage.setColor(color);
    }

    public void setChangeAvatarConditionMessage(String message, Color color) {
        changeAvatarConditionMessage.setText(message);
        changeAvatarConditionMessage.setColor(color);
    }
}

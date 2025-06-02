package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.SettingsMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.enums.InputKey;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.Model.enums.Language;
import com.tilldawn.Model.enums.MusicEnum;

import java.util.ArrayList;

public class SettingsMenuView implements Screen {
    private Stage stage;
    private final Label title;
    private final Label musicVolumeLabel;
    private final Slider musicVolumeSlider;
    private final Label musicLabel;
    private final ArrayList<CheckBox> musicCheckBox = new ArrayList<>();
    private final ButtonGroup<CheckBox> musicGroup = new ButtonGroup<>();
    private final CheckBox sfx;
    private final CheckBox autoReload;

    private final TextButton changeInputKeysButton;
    private final ArrayList<Label> inputKeysDescription = new ArrayList<>();
    private final ArrayList<Label> inputKeys = new ArrayList<>();
    private final ArrayList<TextField> inputKeysTextFields = new ArrayList<>();
    private final ArrayList<TextButton> changeButton = new ArrayList<>();
    private final Label conditionMessage;

    private final TextButton backToSettingsButton;

    private final TextButton changeSettingsButton;
    private final TextButton backButton;
    public Table table;
    public ScrollPane scrollPane;
    private final SettingsMenuController controller;

    public SettingsMenuView(SettingsMenuController controller, Skin skin) {
        this.controller = controller;
        Language language = GameData.getInstance().getCurrentPlayer().getLanguage();
        title = new Label(LangKey.SettingsLabel.getTranslation(language), skin, "title");
        musicVolumeLabel = new Label("Music Volume", skin, "subtitle");
        musicVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicVolumeSlider.setValue(GameData.getInstance().getCurrentPlayer().getMusicVolume());
        musicLabel = new Label("Music", skin, "subtitle");
        for (MusicEnum music : MusicEnum.values()) {
            CheckBox checkBox = new CheckBox(music.toString(), skin);
            musicCheckBox.add(checkBox);
            musicGroup.add(checkBox);
        }
        musicGroup.setMaxCheckCount(1);
        musicGroup.setMinCheckCount(1);
        sfx = new CheckBox("sfx", skin);
        autoReload = new CheckBox("Auto-Reload", skin);

        changeInputKeysButton = new TextButton("Change KeyBoard Controllers", skin);
        for (InputKey key : GameData.getInstance().getCurrentPlayer().getKeys().keySet()) {
            inputKeysDescription.add(new Label(key.getDescription(), skin));
            inputKeys.add(new Label("", skin));
            inputKeysTextFields.add(new TextField("", skin));
            changeButton.add(new TextButton("Change", skin));
        }
        conditionMessage = new Label("", skin);
        backToSettingsButton = new TextButton("Back", skin);

        changeSettingsButton = new TextButton("Change", skin);
        backButton = new TextButton("Back", skin);
        table = new Table();
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
        table.padTop(100);
        table.padBottom(100);
        table.center();

        setUpSettingsStage();


        backToSettingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackToSettingsButton();
            }
        });

        for (int i = 0; i < changeButton.size(); i++) {
            int finalI = i;
            changeButton.get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleChangeButton(finalI);
                }
            });
        }

        changeSettingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleChangeSettingsButton();
            }
        });

        changeInputKeysButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleChangeInputKeysButton();
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

    public void setUpKeysStage() {
        table.clear();
        table.add(conditionMessage).colspan(30).center();
        table.row().pad(10, 0, 10, 0);
        for (int i = 0; i < inputKeysDescription.size(); i++) {
            table.add(inputKeysDescription.get(i)).left().padRight(30);
            table.add(inputKeys.get(i)).center().padRight(30);
            table.add(inputKeysTextFields.get(i)).width(300).center().padRight(30);
            table.add(changeButton.get(i)).right();
            table.row().pad(10, 0, 10, 0);
        }
        table.add(backToSettingsButton).colspan(30).center();
    }

    public void setUpSettingsStage() {
        sfx.setChecked(GameData.getInstance().getCurrentPlayer().isSfx());
        autoReload.setChecked(GameData.getInstance().getCurrentPlayer().isAutoReload());
        table.clear();
        table.add(title).colspan(6).center();
        table.row().pad(10, 0, 10, 0);
        table.add(musicVolumeLabel).colspan(4).center();
        table.add(musicVolumeSlider).width(300).pad(10);
        table.row().pad(10, 0, 10, 0);
        table.add().height(100).colspan(2);
        table.row();
        table.add(musicLabel).colspan(6).center();
        table.row().pad(10, 0, 10, 0);
        for (CheckBox checkBox : musicCheckBox) {
            table.add(checkBox).colspan(6).center();
            table.row().pad(10, 0, 10, 0);
        }
        table.add().height(100).colspan(2);
        table.row();
        table.row().pad(10, 0, 10, 0);
        table.add(sfx).colspan(3).center();
        table.add(autoReload).colspan(3).center();
        table.row().pad(10, 0, 10, 0);
        table.add(changeSettingsButton).colspan(6).center();
        table.row().pad(10, 0, 10, 0);
        table.add(changeInputKeysButton).colspan(6).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).colspan(6).center();
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

    public ArrayList<Label> getInputKeysDescription() {
        return inputKeysDescription;
    }

    public ArrayList<Label> getInputKeys() {
        return inputKeys;
    }

    public ArrayList<TextField> getInputKeysTextFields() {
        return inputKeysTextFields;
    }

    public Label getConditionMessage() {
        return conditionMessage;
    }

    public Slider getMusicVolumeSlider() {
        return musicVolumeSlider;
    }

    public ArrayList<CheckBox> getMusicCheckBox() {
        return musicCheckBox;
    }

    public ButtonGroup<CheckBox> getMusicGroup() {
        return musicGroup;
    }

    public CheckBox getSfx() {
        return sfx;
    }

    public CheckBox getAutoReload() {
        return autoReload;
    }
}

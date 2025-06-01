package com.tilldawn.Controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.enums.CheatCode;
import com.tilldawn.Model.enums.InputKey;
import com.tilldawn.Model.enums.MusicEnum;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.SettingsMenuView;

import java.util.ArrayList;

public class SettingsMenuController {
    private SettingsMenuView view;

    public void setView(SettingsMenuView view) {
        this.view = view;
    }

    public void handleChangeInputKeysButton() {
        fillKeysFields();
        view.setUpKeysStage();
    }

    private void fillKeysFields() {
        for (int i = 0; i < view.getInputKeys().size(); i++) {
            view.getInputKeysTextFields().get(i).setText("");
            Label label = view.getInputKeys().get(i);
            InputKey key = null;
            for (InputKey value : InputKey.values()) {
                if (value.getDescription().equals(view.getInputKeysDescription().get(i).getText().toString())) {
                    key = value;
                    break;
                }
            }
            if (key.equals(InputKey.ShootProjectile)) {
                if (GameData.getInstance().getCurrentPlayer().getKeys().get(key).equals(Input.Buttons.LEFT)) {
                    label.setText("Left Mouse Button");
                }
                else if (GameData.getInstance().getCurrentPlayer().getKeys().get(key).equals(Input.Buttons.RIGHT)) {
                    label.setText("Right Mouse Button");
                }
                else if (GameData.getInstance().getCurrentPlayer().getKeys().get(key).equals(Input.Buttons.MIDDLE)) {
                    label.setText("Middle Mouse Button");
                }
                else {
                    label.setText(Input.Keys.toString(GameData.getInstance().getCurrentPlayer().getKeys().get(key)));
                }
            }
            else {
                label.setText(Input.Keys.toString(GameData.getInstance().getCurrentPlayer().getKeys().get(key)));
            }
        }
    }

    public void handleBackToSettingsButton() {
        view.setUpSettingsStage();
    }

    public void handleBackButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleChangeButton(int i) {
        String newKey = view.getInputKeysTextFields().get(i).getText().toString();
        InputKey key = null;
        for (InputKey value : InputKey.values()) {
            if (value.getDescription().equals(view.getInputKeysDescription().get(i).getText().toString())) {
                key = value;
                break;
            }
        }
        boolean flag = true;
        Integer inputKey = null;
        if (newKey.equalsIgnoreCase("Left Mouse Button")) {
            if (!key.equals(InputKey.ShootProjectile)) flag = false;
            inputKey = Input.Buttons.LEFT;
            if (GameData.getInstance().getCurrentPlayer().getKeys().get(InputKey.ShootProjectile) == inputKey) flag = false;
        }
        else if (newKey.equalsIgnoreCase("Right Mouse Button")) {
            if (!key.equals(InputKey.ShootProjectile)) flag = false;
            inputKey = Input.Buttons.RIGHT;
            if (GameData.getInstance().getCurrentPlayer().getKeys().get(InputKey.ShootProjectile) == inputKey) flag = false;
        }
        else if (newKey.equalsIgnoreCase("Middle Mouse Button")) {
            if (!key.equals(InputKey.ShootProjectile)) flag = false;
            inputKey = Input.Buttons.MIDDLE;
            if (GameData.getInstance().getCurrentPlayer().getKeys().get(InputKey.ShootProjectile) == inputKey) flag = false;
        }
        else {
            inputKey = Input.Keys.valueOf(newKey.toUpperCase());
            for (InputKey inputKey1 : GameData.getInstance().getCurrentPlayer().getKeys().keySet()) {
                if (inputKey == GameData.getInstance().getCurrentPlayer().getKeys().get(inputKey1)) flag = false;
            }
            for (CheatCode value : CheatCode.values()) {
                if (inputKey == value.getMainKey()) flag = false;
            }
        }
        if (!flag) {
            view.getConditionMessage().setText("Invalid or Already Taken!");
            view.getConditionMessage().setColor(Color.RED);
            return;
        }
        GameData.getInstance().getCurrentPlayer().getKeys().put(key, inputKey);
        view.getConditionMessage().setText("Changed");
        view.getConditionMessage().setColor(Color.GREEN);
        fillKeysFields();
    }

    public void handleChangeSettingsButton() {
        GameData.getInstance().getCurrentPlayer().setMusicVolume(view.getMusicVolumeSlider().getValue());

        CheckBox selectedMusic = view.getMusicGroup().getChecked();
        if (selectedMusic != null) {
            GameData.getInstance().getCurrentPlayer().setMusic(MusicEnum.valueOf(selectedMusic.getText().toString()));
        }

        GameData.getInstance().getCurrentPlayer().setSfx(view.getSfx().isChecked());
        GameData.getInstance().getCurrentPlayer().setAutoReload(view.getAutoReload().isChecked());
    }
}

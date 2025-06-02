package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.ProfileMenuView;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ProfileMenuController {
    private ProfileMenuView view;

    public void setView(ProfileMenuView view) {
        this.view = view;
    }

    public void handleChangeUsernameButton() {
        if (GameData.getInstance().getCurrentPlayer().isGuest())
            view.setGuestConditionMessage(LangKey.ProfileMenuGuestMessage.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()), Color.RED);
        else
            view.setupChangeUsernameStage();
    }

    public void handleChangePasswordButton() {
        if (GameData.getInstance().getCurrentPlayer().isGuest())
            view.setGuestConditionMessage(LangKey.ProfileMenuGuestMessage.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()), Color.RED);
        else
            view.setupChangePasswordStage();
    }

    public void handleChangeAvatarButton() {
        view.setupChangeAvatarStage();
    }

    public void handleDeleteAccountButton() {
        if (GameData.getInstance().getCurrentPlayer().isGuest())
            view.setGuestConditionMessage(LangKey.ProfileMenuGuestMessage.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()), Color.RED);
        else
            view.setupDeleteAccountStage();
    }

    public void handleBackButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleBackToProfileMenuButtonButton() {
        view.setupProfileMenuStage();
    }

    public void handleSetUsernameButton() {
        String username = view.getUsername().getText();
        if (GameData.getInstance().findPlayerByUsername(username) == null) {
            view.setChangeUsernameConditionMessage(LangKey.ProfileMenuSuccessfulChangeUsernameMessage.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()), Color.GREEN);
            GameData.getInstance().getCurrentPlayer().setUsername(username);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new ProfileMenuView(
                        new ProfileMenuController(),
                        GameAssetManager.getInstance().getSkin()
                    ));
                }
            }, 2);
        }
        else {
            view.setChangeUsernameConditionMessage(LangKey.UsernameTaken.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()), Color.RED);
        }
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) return false;
        return Pattern.compile("(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*()_]).*").matcher(password).matches();
    }

    public void handleSetPasswordButton() {
        String password = view.getPassword().getText();
        if (isPasswordValid(password)) {
            view.setChangePasswordConditionMessage(LangKey.SuccessfulChangePassword.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()), Color.GREEN);
            GameData.getInstance().getCurrentPlayer().setPassword(password);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new ProfileMenuView(
                        new ProfileMenuController(),
                        GameAssetManager.getInstance().getSkin()
                    ));
                }
            }, 2);
        }
        else {
            view.setChangePasswordConditionMessage(LangKey.WeakPassword.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()), Color.RED);
        }
    }

    public void handleDeleteButton() {
        CheckBox selectedCheckBox = view.getDeleteAccount().getChecked();
        if (selectedCheckBox != null) {
            String text = selectedCheckBox.getText().toString();
            if (text.equals(LangKey.ProfileMenuYes.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()))) {
                GameData.getInstance().getPlayers().remove(GameData.getInstance().getCurrentPlayer());
                GameData.getInstance().setCurrentUser(null);
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
            }
            else if (text.equals(LangKey.ProfileMenuNo.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()))) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getInstance().getSkin()));
            }
        }
    }

    public void handleSelectAvatarButton() {
        CheckBox selectedCheckBox = view.getAvatarsGroup().getChecked();
        if (selectedCheckBox != null) {
            ArrayList<CheckBox> avatarButtons = view.getAvatars();

            int selectedIndex = -1;
            for (int i = 0; i < avatarButtons.size(); i++) {
                if (avatarButtons.get(i) == selectedCheckBox) {
                    selectedIndex = i;
                    break;
                }
            }

            if (selectedIndex != -1) {
                GameData.getInstance().getCurrentPlayer().setAvatarIndex(selectedIndex);
                view.setChangeAvatarConditionMessage(LangKey.ProfileMenuSuccessfulChangeAvatarMessage.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()), Color.GREEN);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Main.getMain().getScreen().dispose();
                        Main.getMain().setScreen(new ProfileMenuView(
                            new ProfileMenuController(),
                            GameAssetManager.getInstance().getSkin()
                        ));
                    }
                }, 2);
            }
            else {
                view.setChangeAvatarConditionMessage(LangKey.ProfileMenuSelectAvatarMessage.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()), Color.RED);
            }
        }
    }
}

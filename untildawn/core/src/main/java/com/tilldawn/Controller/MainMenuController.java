package com.tilldawn.Controller;

import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.View.*;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleSettingsMenuButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SettingsMenuView(new SettingsMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleProfileMenuButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handlePreGameMenuButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleScoreboardMenuButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ScoreboardMenuView(new ScoreboardMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleTalentMenuButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new TalentMenuView(new TalentMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleLogoutButton() {
        GameData.getInstance().setCurrentUser(null);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}

package com.tilldawn.Controller;

import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.Model.enums.Language;
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

    public void handleContinueSavedGameButton() {
        if (GameData.getInstance().getCurrentPlayer().getGame() == null) {
            view.getConditionMessage().setText(LangKey.MainMenuNoSaveGameMessage.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()));
            return;
        }
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GameView(GameData.getInstance().getCurrentPlayer().getGame(), new GameController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleLanguageSelectBox(String selected) {
        if (selected.equals(LangKey.EnglishLanguage.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()))) {
            GameData.getInstance().setLanguage(Language.English);
            GameData.getInstance().getCurrentPlayer().setLanguage(Language.English);
        }
        else if (selected.equals(LangKey.FrenchLanguage.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()))) {
            GameData.getInstance().setLanguage(Language.French);
            GameData.getInstance().getCurrentPlayer().setLanguage(Language.French);
        }
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}

package com.tilldawn.Controller;

import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.EndGameView;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.ScoreboardMenuView;

public class EndGameController {
    private EndGameView view;

    public void setView(EndGameView view) {
        this.view = view;
    }

    public String getTimeRemainingFormatted() {
        float remaining = Math.min(view.getGame().getTime() - view.getGame().getGameTimer(), view.getGame().getTime());
        int minutes = (int)(remaining / 60);
        int seconds = (int)(remaining % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public int getPoints() {
        float remaining = Math.min(view.getGame().getTime() - view.getGame().getGameTimer(), view.getGame().getTime());
        int minutes = (int)(remaining / 60);
        int seconds = (int)(remaining % 60);
        return (minutes * 60 + seconds)* view.getGame().getKill();

    }

    public void handleQuitButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}

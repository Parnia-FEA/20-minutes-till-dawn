package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.ScoreboardMenuView;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreboardMenuController {
    private ScoreboardMenuView view;

    public void setView(ScoreboardMenuView view) {
        this.view = view;
    }


    public void handleBackButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    private void updateLabels(ArrayList<Player> players) {
        for (int i = 0; i < Math.min(view.getNum(), players.size()); i++) {
            view.getUsernames().get(i).setText(players.get(i).getUsername());
            view.getScores().get(i).setText(players.get(i).getScore());
            view.getKills().get(i).setText(players.get(i).getKill());
            view.getTimes().get(i).setText(players.get(i).getMaxSurvivalTime() + "s");
            if (players.get(i).getUsername().equals(GameData.getInstance().getCurrentPlayer().getUsername())) {
                view.getUsernames().get(i).setText(players.get(i).getUsername() + "(" + LangKey.ScoreboardYou.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()) +")");
            }
        }
    }

    public void handleUsernameLabel() {
        ArrayList<Player> players = new ArrayList<>(GameData.getInstance().getPlayers());
        players.sort(Comparator.comparing(Player::getUsername));
        updateLabels(players);
    }

    public void handleScoreLabel() {
        ArrayList<Player> players = new ArrayList<>(GameData.getInstance().getPlayers());
        players.sort(Comparator.comparingInt(Player::getScore).reversed().thenComparing(Player::getUsername));
        updateLabels(players);
    }

    public void handleKillLabel() {
        ArrayList<Player> players = new ArrayList<>(GameData.getInstance().getPlayers());
        players.sort(Comparator.comparingInt(Player::getKill).reversed().thenComparing(Player::getUsername));
        updateLabels(players);
    }

    public void handleTimeLabel() {
        ArrayList<Player> players = new ArrayList<>(GameData.getInstance().getPlayers());
        players.sort(Comparator.comparingInt(Player::getMaxSurvivalTime).reversed().thenComparing(Player::getUsername));
        updateLabels(players);
    }
}

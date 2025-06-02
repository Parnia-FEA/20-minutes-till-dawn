package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.*;

public class LoginMenuController {
    private LoginMenuView view;

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    private Player getUserIfInfoValid(String username, String password) {
        Player player = GameData.getInstance().findPlayerByUsername(username);
        if (player == null) return null;
        if (player.getPassword().equals(password)) return player;
        return null;
    }

    public void handleLoginButton() {
        Player player = getUserIfInfoValid(view.getUsername().getText(), view.getPassword().getText());
        if (player != null) {
            view.setLoginConditionMessage("Logged in Successfully:)", Color.GREEN);
            GameData.getInstance().setCurrentUser(player);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new MainMenuView(
                        new MainMenuController(),
                        GameAssetManager.getInstance().getSkin()
                    ));
                }
            }, 2);
        }
        else {
            view.setLoginConditionMessage("Incorrect Username or Password!", Color.RED);
        }
    }

    public void handleSignUpButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleForgetPasswordButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ForgetPasswordMenuView(new ForgetPasswordMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleLanguageSelectBox(String selected) {

    }
}

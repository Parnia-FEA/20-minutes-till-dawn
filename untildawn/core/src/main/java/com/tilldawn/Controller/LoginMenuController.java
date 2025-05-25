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

    private User getUserIfInfoValid(String username, String password) {
        User user = GameData.getInstance().findUserByUsername(username);
        if (user == null) return null;
        if (user.getPassword().equals(password)) return user;
        return null;
    }

    public void handleLoginButton() {
        User user = getUserIfInfoValid(view.getUsername().getText(), view.getPassword().getText());
        if (user != null) {
            view.setLoginConditionMessage("Logged in Successfully:)", Color.GREEN);
            GameData.getInstance().setCurrentUser(user);
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
}

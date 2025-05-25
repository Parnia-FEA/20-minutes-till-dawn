package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.User;
import com.tilldawn.View.ForgetPasswordMenuView;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.SignUpMenuView;

import java.util.regex.Pattern;

public class SignUpMenuController {
    private SignUpMenuView view;

    public void setView(SignUpMenuView view) {
        this.view = view;
    }

    private boolean isUsernameTaken(String username) {
        return GameData.getInstance().findUserByUsername(username) != null;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) return false;
        return Pattern.compile("(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*()_]).*").matcher(password).matches();
    }

    public void handleSignUpButton() {
        String username = view.getUsername().getText();
        String password = view.getPassword().getText();
        if (isUsernameTaken(username)) {
            view.setSignUpConditionMessage("Username is already taken.", Color.RED);
        }
        else {
            if (isPasswordValid(password)) {
                view.setSignUpConditionMessage("Sign up Successful:)", Color.GREEN);
                User user = new User(username, password);
                GameData.getInstance().addUser(user);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Main.getMain().getScreen().dispose();
                        Main.getMain().setScreen(new LoginMenuView(
                            new LoginMenuController(),
                            GameAssetManager.getInstance().getSkin()
                        ));
                    }
                }, 2);
            }
            else {
                view.setSignUpConditionMessage("Password is too weak!", Color.RED);
            }
        }
    }

    public void handleGuestButton() {
        //TODO play as a guest
    }

    public void handleBackButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}

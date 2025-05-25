package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.User;
import com.tilldawn.View.ForgetPasswordMenuView;
import com.tilldawn.View.LoginMenuView;

import java.util.regex.Pattern;

public class ForgetPasswordMenuController {
    private ForgetPasswordMenuView view;

    public void setView(ForgetPasswordMenuView view) {
        this.view = view;
    }

    public void handleContinueButton() {
        User user = GameData.getInstance().findUserByUsername(view.getUsername().getText());
        if (user == null) {
            view.setUsernameConditionMessage("Invalid Username", Color.RED);
        }
        else {
            view.setUser(user);
            view.setQuestion(user.getSecurityQuestion());
            view.setupSecurityQuestionStage();
        }
    }

    public void handleSubmitAnswerButton() {
        User user = view.getUser();
        if (view.getAnswer().getText().equals(user.getAnswer())) {
            view.setupNewPasswordStage();
        }
        else {
            view.setSecurityQuestionConditionMessage("The answer is incorrect!", Color.RED);
        }
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) return false;
        return Pattern.compile("(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*()_]).*").matcher(password).matches();
    }

    public void handleChangePasswordButton() {
        User user = view.getUser();
        String password = view.getPassword().getText();
        if (isPasswordValid(password)) {
            view.setPasswordConditionMessage("Password is changed successfully:)", Color.GREEN);
            user.setPassword(password);
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
            view.setPasswordConditionMessage("The new password is too weak!", Color.RED);
        }
    }

    public void handleBackButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}

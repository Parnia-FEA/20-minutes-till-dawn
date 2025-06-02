package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.View.ForgetPasswordMenuView;
import com.tilldawn.View.LoginMenuView;

import java.util.regex.Pattern;

public class ForgetPasswordMenuController {
    private ForgetPasswordMenuView view;

    public void setView(ForgetPasswordMenuView view) {
        this.view = view;
    }

    public void handleContinueButton() {
        Player player = GameData.getInstance().findPlayerByUsername(view.getUsername().getText());
        if (player == null) {
            view.setUsernameConditionMessage(LangKey.ForgetMenuInvalidUsername.getTranslation(GameData.getInstance().getLanguage()), Color.RED);
        }
        else {
            view.setUser(player);
            view.setQuestion(player.getSecurityQuestion().getTranslation(GameData.getInstance().getLanguage()));
            view.setupSecurityQuestionStage();
        }
    }

    public void handleSubmitAnswerButton() {
        Player player = view.getUser();
        if (view.getAnswer().getText().equals(player.getAnswer())) {
            view.setupNewPasswordStage();
        }
        else {
            view.setSecurityQuestionConditionMessage(LangKey.ForgetMenuIncorrectAnswer.getTranslation(GameData.getInstance().getLanguage()), Color.RED);
        }
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) return false;
        return Pattern.compile("(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*()_]).*").matcher(password).matches();
    }

    public void handleChangePasswordButton() {
        Player player = view.getUser();
        String password = view.getPassword().getText();
        if (isPasswordValid(password)) {
            view.setPasswordConditionMessage(LangKey.SuccessfulChangePassword.getTranslation(GameData.getInstance().getLanguage()), Color.GREEN);
            player.setPassword(password);
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
            view.setPasswordConditionMessage(LangKey.WeakPassword.getTranslation(GameData.getInstance().getLanguage()), Color.RED);
        }
    }

    public void handleBackButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}

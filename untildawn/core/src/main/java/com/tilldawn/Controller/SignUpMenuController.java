package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.enums.LangKey;
import com.tilldawn.Model.enums.Language;
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
        return GameData.getInstance().findPlayerByUsername(username) != null;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) return false;
        return Pattern.compile("(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*()_]).*").matcher(password).matches();
    }

    public void handleSignUpButton() {
        String username = view.getUsername().getText();
        String password = view.getPassword().getText();
        String securityQuestion = view.getSecurityQuestionsBox().getSelected();
        String answer = view.getAnswer().getText();
        if (isUsernameTaken(username)) {
            view.setSignUpConditionMessage(LangKey.UsernameTaken.getTranslation(GameData.getInstance().getLanguage()), Color.RED);
        }
        else {
            if (isPasswordValid(password)) {
                if (answer == null || answer.isEmpty()) {
                    view.setSignUpConditionMessage(LangKey.SignUpMenuAnswerSecurityQuestion.getTranslation(GameData.getInstance().getLanguage()), Color.RED);
                }
                else {
                    LangKey[] questions = {
                        LangKey.FavoriteColorQuestion,
                        LangKey.PetNameQuestion,
                        LangKey.MotherNameQuestion,
                        LangKey.SchoolNameQuestion,
                        LangKey.FavoriteMovieQuestion,
                        LangKey.BornQuestion,
                        LangKey.FriendNameQuestion
                    };
                    LangKey securityQuestionLangKey = null;
                    for (LangKey question : questions) {
                        if (question.getTranslation(GameData.getInstance().getLanguage()).equals(securityQuestion)) {
                            securityQuestionLangKey = question;
                        }
                    }
                    view.setSignUpConditionMessage(LangKey.SignUpMenuSuccessful.getTranslation(GameData.getInstance().getLanguage()), Color.GREEN);
                    Player player = new Player(username, password, securityQuestionLangKey, answer, GameData.getInstance().getLanguage());
                    GameData.getInstance().addUser(player);
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
            }
            else {
                view.setSignUpConditionMessage(LangKey.WeakPassword.getTranslation(GameData.getInstance().getLanguage()), Color.RED);
            }
        }
    }

    public void handleGuestButton() {
        Player guest = new Player(LangKey.GuestUsername.getTranslation(GameData.getInstance().getLanguage()), null, null, null, GameData.getInstance().getLanguage());
        guest.setGuest(true);
        GameData.getInstance().setCurrentUser(guest);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleBackButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}

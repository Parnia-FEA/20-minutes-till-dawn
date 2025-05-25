package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.*;

public class LoginMenuController {
    private LoginMenuView view;

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    private boolean isInfoValid(String username, String password) {
        //TODO validation
        return false;
    }

    public void handleLoginMenuInputs() {
        if (view != null) {
            if (view.getSignUpButton().isChecked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
            else if (view.getForgetPasswordButton().isChecked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ForgetPasswordMenuView(new ForgetPasswordMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
            else if (view.getLoginButton().isChecked()) {
                if (isInfoValid(view.getUsername().getText(), view.getPassword().getText())) {
                    view.setLoginConditionMessage("Login Successful:)", Color.GREEN);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            Main.getMain().getScreen().dispose();
                            Main.getMain().setScreen(new MainMenuView(
                                new MainMenuController(),
                                GameAssetManager.getGameAssetManager().getSkin()
                            ));
                        }
                    }, 2);
                }
                else {
                    view.setLoginConditionMessage("Incorrect Username or Password!", Color.RED);
                }
            }
        }
    }
}

package com.tilldawn.View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Controller.PauseMenuController;

public class PauseMenuView implements Screen {
    private final PauseMenuController controller;

    public PauseMenuView(PauseMenuController controller, Skin skin) {
        this.controller = controller;

        controller.setView(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

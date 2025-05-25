package com.tilldawn.View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Controller.TalentMenuController;

public class TalentMenuView implements Screen {
    private final TalentMenuController controller;

    public TalentMenuView(TalentMenuController controller, Skin skin) {
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

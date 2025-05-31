package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.enums.Hero;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.TalentMenuView;

public class TalentMenuController {
    private TalentMenuView view;

    public void setView(TalentMenuView view) {
        this.view = view;
    }

    public void handleHeroesButton() {
        view.setUpHeroesInformationStage();
    }

    public void handleKeysButton() {
        view.setUpInputKeysInformationStage();
    }

    public void handleCheatCodesButton() {
        view.setUpCheatCodesInformationStage();
    }

    public void handleAbilitiesButton() {
        view.setUpAbilitiesInformationStage();
    }

    public void handleBackButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleBackToTalentMenuButton() {
        view.setUpTalentMenuStage();
    }

    public void updateHeroesInformation() {
        view.getHeroImage().setDrawable(new TextureRegionDrawable(new TextureRegion(GameAssetManager.getInstance().getHeroes().get(view.getHeroesGroup().getChecked().getText().toString()))));
        view.getHeroDescription().setText(Hero.valueOf(view.getHeroesGroup().getChecked().getText().toString()).getDescription());
    }
}

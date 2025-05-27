package com.tilldawn.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.utils.Timer;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.Weapon;
import com.tilldawn.Model.enums.Hero;
import com.tilldawn.Model.enums.WeaponEnum;
import com.tilldawn.View.GameView;
import com.tilldawn.View.PreGameMenuView;
import com.tilldawn.View.MainMenuView;

import java.util.ArrayList;

public class PreGameMenuController {
    private PreGameMenuView view;

    public void setView(PreGameMenuView view) {
        this.view = view;
    }

    public void handleStartGameButton() {
        CheckBox selectedHero = view.getHeroesGroup().getChecked();
        if (selectedHero != null) {
            ArrayList<CheckBox> heroesButtons = view.getHeroesCheckBox();
            int selectedHeroIndex = -1;
            for (int i = 0; i < heroesButtons.size(); i++) {
                if (heroesButtons.get(i) == selectedHero) {
                    selectedHeroIndex = i;
                    break;
                }
            }
            if (selectedHeroIndex != -1) {
                CheckBox selectedWeapon = view.getWeaponsGroup().getChecked();
                if (selectedWeapon != null) {
                    ArrayList<CheckBox> weaponsButtons = view.getWeaponsCheckBox();

                    int selectedWeaponIndex = -1;
                    for (int i = 0; i < weaponsButtons.size(); i++) {
                        if (weaponsButtons.get(i) == selectedWeapon) {
                            selectedWeaponIndex = i;
                            break;
                        }
                    }
                    if (selectedWeaponIndex != -1) {
                        CheckBox selectedTime = view.getTimesGroup().getChecked();
                        if (selectedTime != null) {
                            ArrayList<CheckBox> timesButtons = view.getTimes();

                            int selectedTimeIndex = -1;
                            for (int i = 0; i < timesButtons.size(); i++) {
                                if (timesButtons.get(i) == selectedTime) {
                                    selectedTimeIndex = i;
                                    break;
                                }
                            }
                            if (selectedTimeIndex != -1) {
                                Hero hero = Hero.valueOf(selectedHero.getText().toString());
                                WeaponEnum weapon = WeaponEnum.valueOf(selectedWeapon.getText().toString());
                                int time = view.getTimeAmount().get(selectedTimeIndex);
                                TillDawnGame game = new TillDawnGame(hero, new Weapon(weapon), time);
                                GameData.getInstance().getCurrentPlayer().setGame(game);
                                view.setConditionMessage("Loading...", Color.GREEN);
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        Main.getMain().getScreen().dispose();
                                        Main.getMain().setScreen(new GameView(game, new GameController(), GameAssetManager.getInstance().getSkin()));
                                    }
                                }, 2);
                            }
                            else {
                                view.setConditionMessage("Please select a time", Color.RED);
                            }
                        }
                    }
                    else {
                        view.setConditionMessage("Please select a weapon", Color.RED);
                    }
                }
            }
            else {
                view.setConditionMessage("Please select a hero", Color.RED);
            }
        }
    }

    public void handleBackButton() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}

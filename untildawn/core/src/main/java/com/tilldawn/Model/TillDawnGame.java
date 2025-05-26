package com.tilldawn.Model;

import com.tilldawn.Model.enums.Hero;
import com.tilldawn.Model.enums.Weapon;

public class TillDawnGame {
    private Hero hero;
    private Weapon weapon;
    private int time;

    public TillDawnGame(Hero hero, Weapon weapon, int time) {
        this.hero = hero;
        this.weapon = weapon;
        this.time = time;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

package com.tilldawn.Controller;

import com.tilldawn.Model.Weapon;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.enums.WeaponEnum;

import java.util.ArrayList;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public WeaponController(Weapon weapon){
        this.weapon = weapon;
    }

    public void update() {
    }
}

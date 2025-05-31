package com.tilldawn.Model.enums;

import com.badlogic.gdx.Input;

public enum InputKey {
    Down(Input.Keys.S, "Move Down"),
    Up(Input.Keys.W, "Move Up"),
    Left(Input.Keys.A, "Move Left"),
    Right(Input.Keys.D, "Move Right"),
    ShootProjectile(Input.Buttons.LEFT, "Shoot Projectile"),
    ReloadAmmo(Input.Keys.R, "Reload Ammo")
    ;

    private final int mainKey;
    private final String description;

    InputKey(int mainKey, String description) {
        this.mainKey = mainKey;
        this.description = description;
    }

    public int getMainKey() {
        return mainKey;
    }

    public String getDescription() {
        return description;
    }
}

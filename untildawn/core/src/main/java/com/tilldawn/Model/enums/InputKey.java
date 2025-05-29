package com.tilldawn.Model.enums;

import com.badlogic.gdx.Input;

public enum InputKey {
    Down(Input.Keys.S),
    Up(Input.Keys.W),
    Left(Input.Keys.A),
    Right(Input.Keys.D),
    ShootProjectile(Input.Buttons.LEFT),
    ReloadAmmon(Input.Keys.R)
    ;

    private final int mainKey;

    InputKey(int mainKey) {
        this.mainKey = mainKey;
    }

    public int getMainKey() {
        return mainKey;
    }
}

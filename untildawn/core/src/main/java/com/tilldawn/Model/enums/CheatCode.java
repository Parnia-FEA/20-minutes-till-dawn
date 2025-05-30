package com.tilldawn.Model.enums;

import com.badlogic.gdx.Input;

public enum CheatCode {
    DecrementTime(Input.Keys.T),
    IncrementLevel(Input.Keys.L),
    IncrementHealth(Input.Keys.H),
    KillAllEyebats(Input.Keys.K)
    ;

    private final int mainKey;

    CheatCode(int mainKey) {
        this.mainKey = mainKey;
    }

    public int getMainKey() {
        return mainKey;
    }
}

package com.tilldawn.Model.enums;

import com.badlogic.gdx.Input;

public enum CheatCode {
    DecrementTime(Input.Keys.T, "Decrease Time By One Minute"),
    IncrementLevel(Input.Keys.L, "Increment Level"),
    IncrementHealth(Input.Keys.H, "Increment HP"),
    KillAllEyebats(Input.Keys.K, "Kill All Eyebats (Only One Time)"),
    BossFight(Input.Keys.B, "Boss Fight")
    ;

    private final int mainKey;
    private final String description;

    CheatCode(int mainKey, String description) {
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

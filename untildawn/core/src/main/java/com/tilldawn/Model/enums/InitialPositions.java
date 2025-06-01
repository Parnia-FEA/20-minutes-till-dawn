package com.tilldawn.Model.enums;

import com.badlogic.gdx.Gdx;

public enum InitialPositions {
    AmmoIcon(5, (float) Gdx.graphics.getHeight() - 106),
    Ammo(30, (float) Gdx.graphics.getHeight() - 109),
    Hearts(5 , (float) Gdx.graphics.getHeight() - 68),
    Message(5, (float) Gdx.graphics.getHeight() - 35),
    Timer((float) Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 68),
    Kill((float) Gdx.graphics.getWidth() - 100, (float) Gdx.graphics.getHeight() - 106),
    Level((float) Gdx.graphics.getWidth() - 120, (float) Gdx.graphics.getHeight() - 144);

    private final float x;
    private final float y;

    InitialPositions(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

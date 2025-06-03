package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Drop {
    private Sprite sprite;

    public Drop() {
        sprite = new Sprite(GameAssetManager.getInstance().getCrystalTexture());
    }

    public Sprite getSprite() {
        return sprite;
    }
}

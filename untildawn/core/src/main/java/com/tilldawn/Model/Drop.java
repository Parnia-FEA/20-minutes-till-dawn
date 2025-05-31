package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Drop {
    private Texture texture;
    private Sprite sprite;

    public Drop() {
        texture = GameAssetManager.getInstance().getCrystalTexture();
        sprite = new Sprite(texture);
    }

    public Sprite getSprite() {
        return sprite;
    }
}

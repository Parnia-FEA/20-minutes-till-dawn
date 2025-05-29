package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet {
    private Texture texture;
    private Sprite sprite;
    private int damage = 5;
    private int x;
    private int y;

    public Bullet(int x, int y, float startX, float startY){
        this.texture = GameAssetManager.getInstance().getBulletTexture();
        this.sprite = new Sprite(texture);
        sprite.setSize(20 , 20);
        this.x = x;
        this.y = y;
        sprite.setX(startX);
        sprite.setY(startY);
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

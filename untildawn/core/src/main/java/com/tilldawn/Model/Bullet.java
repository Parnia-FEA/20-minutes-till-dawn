package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class Bullet {
    private Texture texture;
    private Sprite sprite;
    private int damage = 5;
    private float x;
    private float y;
    private Vector2 direction;

    public Bullet(float x, float y, Vector2 direction, Texture texture){
        this.texture = texture;
        this.sprite = new Sprite(texture);
        sprite.setSize(20 , 20);
        this.x = x;
        this.y = y;
        sprite.setX(x);
        sprite.setY(y);
        this.direction = direction;
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2 getDirection() {
        return direction;
    }
}

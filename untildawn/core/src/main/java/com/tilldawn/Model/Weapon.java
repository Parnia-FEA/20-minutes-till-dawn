package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.WeaponEnum;

public class Weapon {
    private WeaponEnum type;
    private Texture texture;
    private Sprite sprite;
    private int ammo;

    public Weapon(WeaponEnum type) {
        this.type = type;
        this.texture = GameAssetManager.getInstance().getWeaponTexture().get(type.toString());
        this.sprite = new Sprite(this.texture);
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
        this.ammo = type.getMaxAmmo();
    }

    public WeaponEnum getType() {
        return type;
    }

    public void setType(WeaponEnum type) {
        this.type = type;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}

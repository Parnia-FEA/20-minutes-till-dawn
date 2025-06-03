package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.WeaponEnum;

public class Weapon {
    private final WeaponEnum type;
    private final Sprite sprite;
    private int ammo;
    private int maxAmmo;
    private boolean isReloading = false;
    private final float weaponReloadTime;
    private float reloadAnimationTime = 0f;
    private float reloadTime = 0f;
    private final Texture reloadTexture;
    private Sprite reloadSprite = null;
    private int projectile;

    public Weapon(WeaponEnum type) {
        this.type = type;
        this.maxAmmo = type.getMaxAmmo();
        this.sprite = new Sprite(GameAssetManager.getInstance().getWeaponTexture().get(type.toString()));
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
        this.ammo = type.getMaxAmmo();
        this.weaponReloadTime = type.getReloadTime();
        this.projectile = type.getProjectile();
        this.reloadTexture = GameAssetManager.getInstance().getWeaponReloadTexture().get(type.toString()).get(0);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public void setReloading(boolean reloading) {
        isReloading = reloading;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }

    public WeaponEnum getType() {
        return type;
    }

    public Sprite getReloadSprite() {
        return reloadSprite;
    }

    public void setReloadSprite(Sprite reloadSprite) {
        this.reloadSprite = reloadSprite;
    }

    public Texture getReloadTexture() {
        return reloadTexture;
    }

    public float getWeaponReloadTime() {
        return weaponReloadTime;
    }

    public float getReloadAnimationTime() {
        return reloadAnimationTime;
    }

    public void setReloadAnimationTime(float reloadAnimationTime) {
        this.reloadAnimationTime = reloadAnimationTime;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public int getProjectile() {
        return projectile;
    }

    public void setProjectile(int projectile) {
        this.projectile = projectile;
    }
}

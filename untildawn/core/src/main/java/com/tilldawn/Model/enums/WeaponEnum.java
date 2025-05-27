package com.tilldawn.Model.enums;

public enum WeaponEnum {
    Revolver(6, 1, 1, 20),
    ShutGun(2, 1, 4, 10),
    SMG(24, 2, 1, 8);

    private final int maxAmmo;
    private final int reloadTime;
    private final int projectile;
    private final int damage;

    WeaponEnum(int maxAmmo, int reloadTime, int projectile, int damage) {
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.projectile = projectile;
        this.damage = damage;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getProjectile() {
        return projectile;
    }

    public int getDamage() {
        return damage;
    }
}

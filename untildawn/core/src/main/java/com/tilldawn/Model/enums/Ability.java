package com.tilldawn.Model.enums;

import com.badlogic.gdx.graphics.Texture;

public enum Ability {
    Vitality("Increases maximum HP by 1 unit"),
    Damager("Increases weapon damage by 25% for 10 seconds"),
    Procrease("Increases the weapon's projectile count by 1"),
    Amocrease("Increases the weapon's maximum ammo capacity by 5 units"),
    Speedy("Doubles the player's movement speed for 10 seconds")
    ;

    private final String description;

    Ability(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

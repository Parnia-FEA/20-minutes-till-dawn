package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.MonsterType;

public class Monster {
    private final MonsterType type;
    private Integer HP;
    private final Texture texture;
    private final Sprite sprite;
    private float animationTime = 0f;

    public Monster(MonsterType type) {
        this.type = type;
        this.HP= type.getHP();
        this.texture = type.getTexture();
        sprite = new Sprite(this.texture);
    }

    public MonsterType getType() {
        return type;
    }

    public Integer getHP() {
        return HP;
    }

    public void setHP(Integer HP) {
        this.HP = HP;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }
}

package com.tilldawn.Model.enums;

import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Model.GameAssetManager;

public enum MonsterType {
    Tree(null, GameAssetManager.getInstance().getTreeTexture().get(0)),
    Tentacle(25, GameAssetManager.getInstance().getTentacleTexture().get(0)),
    Eyebat(50, GameAssetManager.getInstance().getEyebatTexture().get(0)),
    Elder(400, GameAssetManager.getInstance().getElderTexture());

    private final Integer HP;
    private final Texture texture;

    MonsterType(Integer HP, Texture texture) {
        this.HP = HP;
        this.texture = texture;
    }

    public Integer getHP() {
        return HP;
    }

    public Texture getTexture() {
        return texture;
    }
}

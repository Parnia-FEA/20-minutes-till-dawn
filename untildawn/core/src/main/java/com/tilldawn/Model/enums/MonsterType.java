package com.tilldawn.Model.enums;

public enum MonsterType {
    Tree(null),
    Tentacle(25),
    Eyebat(50),
    Elder(400);

    private final Integer HP;

    MonsterType(Integer HP) {
        this.HP = HP;
    }

    public Integer getHP() {
        return HP;
    }
}

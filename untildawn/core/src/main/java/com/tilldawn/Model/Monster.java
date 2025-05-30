package com.tilldawn.Model;

import com.tilldawn.Model.enums.MonsterType;

public class Monster {
    private final MonsterType type;
    private Integer HP;

    public Monster(MonsterType type) {
        this.type = type;
        this.HP= type.getHP();
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
}

package com.tilldawn.Model.enums;

public enum Hero {
    Dasher(false, 4, 4),
    Diamond(true, 1, 7),
    Lilith(true, 5, 3),
    Scarlet(false, 3, 5),
    Shana(true, 10, 2);

    private final boolean isWalkable;
    private final int speed;
    private final int HP;

    Hero(boolean isWalkable, int speed, int HP) {
        this.isWalkable = isWalkable;
        this.speed = speed;
        this.HP = HP;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHP() {
        return HP;
    }

    public String getDescription() {
        return "Speed : " + speed + "   HP : " + HP;
    }
}

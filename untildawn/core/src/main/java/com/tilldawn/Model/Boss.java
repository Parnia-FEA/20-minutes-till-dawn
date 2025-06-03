package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.MonsterType;

public class Boss extends Monster{
    private final Sprite attackSprite;
    private float attackAnimationTime = 0f;
    private float DashTimer = 0f;
    private boolean isAttacking = false;
    private boolean isAttackEnded = false;
    private final float dashInterval = 5;
    private float attackTimer = 0f;
    private float attackEndingTimer = 0f;

    public Boss(MonsterType type) {
        super(type);
        attackSprite = new Sprite(GameAssetManager.getInstance().getBossAttackTexture().get(0));
    }


    public Sprite getAttackSprite() {
        return attackSprite;
    }

    public float getAttackAnimationTime() {
        return attackAnimationTime;
    }

    public void setAttackAnimationTime(float attackAnimationTime) {
        this.attackAnimationTime = attackAnimationTime;
    }

    public float getAttackTimer() {
        return attackTimer;
    }

    public void setAttackTimer(float attackTimer) {
        this.attackTimer = attackTimer;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public float getDashInterval() {
        return dashInterval;
    }

    public float getDashTimer() {
        return DashTimer;
    }

    public void setDashTimer(float dashTimer) {
        DashTimer = dashTimer;
    }

    public boolean isAttackEnded() {
        return isAttackEnded;
    }

    public void setAttackEnded(boolean attackEnded) {
        isAttackEnded = attackEnded;
    }

    public float getAttackEndingTimer() {
        return attackEndingTimer;
    }

    public void setAttackEndingTimer(float attackEndingTimer) {
        this.attackEndingTimer = attackEndingTimer;
    }
}

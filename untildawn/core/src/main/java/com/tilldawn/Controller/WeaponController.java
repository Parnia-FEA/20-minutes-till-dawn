package com.tilldawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.Weapon;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.enums.InputKey;

import java.util.ArrayList;

public class WeaponController {
    private TillDawnGame game;
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public WeaponController(TillDawnGame game, Weapon weapon) {
        this.game = game;
        this.weapon = weapon;
    }

    public void update(OrthographicCamera camera) {
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.ReloadAmmo))) {
            setReloadingTrue();
        }
        weapon.getSprite().setPosition(camera.position.x, camera.position.y - 8);
        updateBullets(camera);
        if (weapon.isReloading()) {
            updateWeaponReloading();
            weapon.getReloadSprite().setPosition(camera.position.x, camera.position.y - 8);
        }
    }

    private void setReloadingTrue() {
        weapon.setReloading(true);
        weapon.setReloadSprite(new Sprite(weapon.getReloadTexture()));
        weapon.setReloadAnimationTime(0);
        weapon.setReloadTime(0);
    }

    private void updateWeaponReloading() {
        Animation<Texture> animation = GameAssetManager.getInstance().getWeaponReloadAnimation().get(weapon.getType().toString());

        weapon.getReloadSprite().setRegion(animation.getKeyFrame(weapon.getReloadAnimationTime()));

        if (!animation.isAnimationFinished(weapon.getReloadAnimationTime())) {
            weapon.setReloadAnimationTime(weapon.getReloadAnimationTime() + Gdx.graphics.getDeltaTime());
            weapon.setReloadTime(weapon.getReloadTime() + Gdx.graphics.getDeltaTime());
            if (weapon.getWeaponReloadTime() <= weapon.getReloadTime()) {
                weapon.setReloading(false);
                weapon.setAmmo(weapon.getMaxAmmo());
            }
        }
        else {
            weapon.setReloadAnimationTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void draw() {
        if (weapon.isReloading()) {
            weapon.getReloadSprite().draw(Main.getBatch());
        }
        else {
            weapon.getSprite().draw(Main.getBatch());
        }
        for(Bullet bullet : bullets) {
            bullet.getSprite().draw(Main.getBatch());
        }
    }

    public void handleWeaponRotation(int x, int y) {
        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;
        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);
        weapon.getSprite().setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y, int button, OrthographicCamera camera) {
        if (button == game.getKeys().get(InputKey.ShootProjectile)) {
            if (weapon.getAmmo() > 0 && !weapon.isReloading()) {
                Vector3 mouse = new Vector3(x, y, 0);
                camera.unproject(mouse);
                //bullets.add(new Bullet(mousePos.x, mousePos.y, weapon.getSprite().getX(), weapon.getSprite().getY()));
                Vector2 direction = new Vector2(mouse.x - game.getPlayerPosX(), mouse.y - game.getPlayerPosY()).nor();
                Bullet bullet = new Bullet(game.getPlayerPosX(), game.getPlayerPosY(), direction);
                bullets.add(bullet);
                weapon.setAmmo(weapon.getAmmo() - 1);
                if (weapon.getAmmo() == 0 && game.getPlayer().isAutoReload()) {
                    setReloadingTrue();
                }
            }
            //TODO maybe printing something
        }
    }

    private void updateBullets(OrthographicCamera camera) {
        for(Bullet bullet : bullets) {
            bullet.getSprite().translate(
                bullet.getDirection().x * 5f,
                bullet.getDirection().y * 5f
            );
        }
    }
}

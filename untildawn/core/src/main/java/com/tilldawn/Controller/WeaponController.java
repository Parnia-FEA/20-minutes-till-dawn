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
import com.tilldawn.Model.*;
import com.tilldawn.Model.enums.InputKey;

import java.util.ArrayList;

public class WeaponController {
    private TillDawnGame game;

    public WeaponController(TillDawnGame game) {
        this.game = game;
    }

    public void update(OrthographicCamera camera) {
        if (Gdx.input.isKeyPressed(game.getKeys().get(InputKey.ReloadAmmo))) {
            setReloadingTrue();
        }
        game.getWeapon().getSprite().setPosition(camera.position.x, camera.position.y - 8);
        updateBullets();
        if (game.getWeapon().isReloading()) {
            updateWeaponReloading();
            game.getWeapon().getReloadSprite().setPosition(camera.position.x, camera.position.y - 8);
        }
    }

    private void setReloadingTrue() {
        game.getWeapon().setReloading(true);
        if (GameData.getInstance().getCurrentPlayer().isSfx())
            GameAssetManager.getInstance().getReloadWeaponSound().play();
        game.getWeapon().setReloadSprite(new Sprite(game.getWeapon().getReloadTexture()));
        game.getWeapon().setReloadAnimationTime(0);
        game.getWeapon().setReloadTime(0);
    }

    private void updateWeaponReloading() {
        Animation<Texture> animation = GameAssetManager.getInstance().getWeaponReloadAnimation().get(game.getWeapon().getType().toString());

        game.getWeapon().getReloadSprite().setRegion(animation.getKeyFrame(game.getWeapon().getReloadAnimationTime()));

        if (!animation.isAnimationFinished(game.getWeapon().getReloadAnimationTime())) {
            game.getWeapon().setReloadAnimationTime(game.getWeapon().getReloadAnimationTime() + Gdx.graphics.getDeltaTime());
            game.getWeapon().setReloadTime(game.getWeapon().getReloadTime() + Gdx.graphics.getDeltaTime());
            if (game.getWeapon().getWeaponReloadTime() <= game.getWeapon().getReloadTime()) {
                game.getWeapon().setReloading(false);
                game.getWeapon().setAmmo(game.getWeapon().getMaxAmmo());
            }
        }
        else {
            game.getWeapon().setReloadAnimationTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void draw() {
        if (game.getWeapon().isReloading()) {
            game.getWeapon().getReloadSprite().draw(Main.getBatch());
        }
        else {
            game.getWeapon().getSprite().draw(Main.getBatch());
        }
        for(Bullet bullet : game.getBullets()) {
            bullet.getSprite().draw(Main.getBatch());
        }
    }

    public void handleWeaponRotation(int x, int y) {
        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;
        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);
        game.getWeapon().getSprite().setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y, int button, OrthographicCamera camera) {
        if (button == game.getKeys().get(InputKey.ShootProjectile)) {
            if (game.getWeapon().getAmmo() > 0 && !game.getWeapon().isReloading()) {
                /*
                Vector3 mouse = new Vector3(x, y, 0);
                camera.unproject(mouse);
                //bullets.add(new Bullet(mousePos.x, mousePos.y, weapon.getSprite().getX(), weapon.getSprite().getY()));
                Vector2 direction = new Vector2(mouse.x - game.getPlayerPosX(), mouse.y - game.getPlayerPosY()).nor();
                for (int i = 0; i < weapon.getProjectile(); i++) {
                    Bullet bullet = new Bullet(game.getPlayerPosX(), game.getPlayerPosY(), direction);
                    bullets.add(bullet);
                }

                 */
                Vector3 mouse = new Vector3(x, y, 0);
                camera.unproject(mouse);

                Vector2 baseDirection = new Vector2(mouse.x - game.getPlayerPosX(), mouse.y - game.getPlayerPosY()).nor();

                int projectileCount = game.getWeapon().getProjectile();
                float spreadAngle = 10f;
                float offsetDistance = 10f;
                for (int i = 0; i < projectileCount; i++) {
                    if (GameData.getInstance().getCurrentPlayer().isSfx())
                        GameAssetManager.getInstance().getShotSound().play();
                    float angleOffset = ((i - (projectileCount - 1) / 2f) * spreadAngle);
                    Vector2 rotatedDirection = new Vector2(baseDirection).rotateDeg(angleOffset);
                    float lateralOffset = (i - (projectileCount - 1) / 2f) * offsetDistance;
                    Vector2 perpendicular = new Vector2(-baseDirection.y, baseDirection.x).nor().scl(lateralOffset);
                    game.getBullets().add(new Bullet(game.getPlayerPosX() + perpendicular.x, game.getPlayerPosY() + perpendicular.y, rotatedDirection, GameAssetManager.getInstance().getBulletTexture()));
                }
                game.getWeapon().setAmmo(game.getWeapon().getAmmo() - 1);
                if (game.getWeapon().getAmmo() == 0 && game.getPlayer().isAutoReload()) {
                    setReloadingTrue();
                }
            }
            //TODO maybe printing something
        }
    }

    private void updateBullets() {
        for(Bullet bullet : game.getBullets()) {
            bullet.getSprite().translate(
                bullet.getDirection().x * 5f,
                bullet.getDirection().y * 5f
            );
        }
    }

    public ArrayList<Bullet> getBullets() {
        return game.getBullets();
    }
}

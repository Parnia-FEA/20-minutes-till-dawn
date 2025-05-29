package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Model.enums.Hero;
import com.tilldawn.Model.enums.WeaponEnum;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class GameAssetManager {
    private static GameAssetManager instance;
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    private final ArrayList<Texture> avatars = new ArrayList<>();
    private final HashMap<String, Texture> heroes = new HashMap<>();
    private final HashMap<String, ArrayList<String>> idleImageFile = new HashMap<>();
    private final HashMap<String, ArrayList<Texture>> idleTexture = new HashMap<>();
    private final HashMap<String, Animation<Texture>> idleAnimation = new HashMap<>();
    private final HashMap<String, ArrayList<String>> runImageFile = new HashMap<>();
    private final HashMap<String, ArrayList<Texture>> runTexture = new HashMap<>();
    private final HashMap<String, Animation<Texture>> runAnimation = new HashMap<>();
    private final HashMap<String, ArrayList<String>> walkImageFile = new HashMap<>();
    private final HashMap<String, ArrayList<Texture>> walkTexture = new HashMap<>();
    private final HashMap<String, Animation<Texture>> walkAnimation = new HashMap<>();
    private final HashMap<String, String> weaponImageFile = new HashMap<>();
    private final HashMap<String, Texture> weaponTexture = new HashMap<>();
    private final HashMap<String, ArrayList<String>> weaponReloadImageFile = new HashMap<>();
    private final HashMap<String, ArrayList<Texture>> weaponReloadTexture = new HashMap<>();
    private final HashMap<String, Animation<Texture>> weaponReloadAnimation = new HashMap<>();
    private final String bulletImageFile;
    private final Texture bulletTexture;

    public GameAssetManager() {
        for (int i = 0 ; i < 7; i++) {
            avatars.add(new Texture(Gdx.files.internal("Avatars/avatar" + i + ".png")));
        }
        for (Hero value : Hero.values()) {
            heroes.put(value.toString(), new Texture(Gdx.files.internal("Heroes/" + value.toString() + "/idle/Portrait.png")));
            buildIdle(value);
            buildRun(value);
            buildWalk(value);
        }
        buildWeapons();
        bulletImageFile = "bullet.png";
        bulletTexture = new Texture(bulletImageFile);
    }

    private void buildWeapons() {
        for (WeaponEnum weapon : WeaponEnum.values()) {
            String file = "Weapons/" + weapon.toString() + "/Still.png";
            weaponImageFile.put(weapon.toString(), file);
            weaponTexture.put(weapon.toString(), new Texture(file));
            ArrayList<String> imageFiles = new ArrayList<>();
            ArrayList<Texture> textures = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                imageFiles.add("Weapons/" + weapon.toString() + "/Reload_" + i + ".png");
                textures.add(new Texture(imageFiles.get(i)));
            }
            weaponReloadImageFile.put(weapon.toString(), imageFiles);
            weaponReloadTexture.put(weapon.toString(), textures);
            weaponReloadAnimation.put(weapon.toString(), new Animation<>(0.1f, textures.toArray(new Texture[0])));
        }
    }

    private void buildIdle(Hero hero) {
        ArrayList<String> imageFiles = new ArrayList<>();
        ArrayList<Texture> textures = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            imageFiles.add("Heroes/" + hero.toString() + "/idle/Idle_" + i + ".png");
            textures.add(new Texture(imageFiles.get(i)));
        }
        idleImageFile.put(hero.toString(), imageFiles);
        idleTexture.put(hero.toString(), textures);
        idleAnimation.put(hero.toString(), new Animation<>(0.1f, textures.toArray(new Texture[0])));
    }

    private void buildRun(Hero hero) {
        ArrayList<String> imageFiles = new ArrayList<>();
        ArrayList<Texture> textures = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            imageFiles.add("Heroes/" + hero.toString() + "/run/Run_" + i + ".png");
            textures.add(new Texture(imageFiles.get(i)));
        }
        runImageFile.put(hero.toString(), imageFiles);
        runTexture.put(hero.toString(), textures);
        runAnimation.put(hero.toString(), new Animation<>(0.1f, textures.toArray(new Texture[0])));
    }

    private void buildWalk(Hero hero) {
        if (hero.isWalkable()) {
            ArrayList<String> imageFiles = new ArrayList<>();
            ArrayList<Texture> textures = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                imageFiles.add("Heroes/" + hero.toString() + "/walk/Walk_" + i + ".png");
                textures.add(new Texture(imageFiles.get(i)));
            }
            walkImageFile.put(hero.toString(), imageFiles);
            walkTexture.put(hero.toString(), textures);
            walkAnimation.put(hero.toString(), new Animation<>(0.1f, textures.toArray(new Texture[0])));
        }
        else {
            walkImageFile.put(hero.toString(), null);
            walkTexture.put(hero.toString(), null);
            walkAnimation.put(hero.toString(), null);
        }
    }

    public static GameAssetManager getInstance() {
        if (instance == null)
            instance = new GameAssetManager();
        return instance;
    }

    public Skin getSkin() {
        return skin;
    }

    public ArrayList<Texture> getAvatars() {
        return avatars;
    }

    public HashMap<String, Texture> getHeroes() {
        return heroes;
    }

    public HashMap<String, ArrayList<String>> getIdleImageFile() {
        return idleImageFile;
    }

    public HashMap<String, ArrayList<Texture>> getIdleTexture() {
        return idleTexture;
    }

    public HashMap<String, Animation<Texture>> getIdleAnimation() {
        return idleAnimation;
    }

    public HashMap<String, ArrayList<String>> getRunImageFile() {
        return runImageFile;
    }

    public HashMap<String, ArrayList<Texture>> getRunTexture() {
        return runTexture;
    }

    public HashMap<String, Animation<Texture>> getRunAnimation() {
        return runAnimation;
    }

    public HashMap<String, ArrayList<String>> getWalkImageFile() {
        return walkImageFile;
    }

    public HashMap<String, ArrayList<Texture>> getWalkTexture() {
        return walkTexture;
    }

    public HashMap<String, Animation<Texture>> getWalkAnimation() {
        return walkAnimation;
    }

    public HashMap<String, String> getWeaponImageFile() {
        return weaponImageFile;
    }

    public HashMap<String, Texture> getWeaponTexture() {
        return weaponTexture;
    }

    public String getBulletImageFile() {
        return bulletImageFile;
    }

    public Texture getBulletTexture() {
        return bulletTexture;
    }

    public HashMap<String, ArrayList<String>> getWeaponReloadImageFile() {
        return weaponReloadImageFile;
    }

    public HashMap<String, ArrayList<Texture>> getWeaponReloadTexture() {
        return weaponReloadTexture;
    }

    public HashMap<String, Animation<Texture>> getWeaponReloadAnimation() {
        return weaponReloadAnimation;
    }
}

package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Model.enums.Ability;
import com.tilldawn.Model.enums.Hero;
import com.tilldawn.Model.enums.MusicEnum;
import com.tilldawn.Model.enums.WeaponEnum;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class GameAssetManager {
    private static GameAssetManager instance;
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    private final ArrayList<Texture> avatars = new ArrayList<>();
    private final HashMap<String, Texture> heroes = new HashMap<>();
    private final HashMap<String, ArrayList<Texture>> idleTexture = new HashMap<>();
    private final HashMap<String, Animation<Texture>> idleAnimation = new HashMap<>();
    private final HashMap<String, Texture> weaponTexture = new HashMap<>();
    private final HashMap<String, ArrayList<Texture>> weaponReloadTexture = new HashMap<>();
    private final HashMap<String, Animation<Texture>> weaponReloadAnimation = new HashMap<>();
    private final Texture bulletTexture;
    private final Texture ammoIconTexture;
    private final ArrayList<String> heartImageFile = new ArrayList<>();
    private final ArrayList<Texture> heartTexture = new ArrayList<>();
    private final Animation<Texture> heartAnimation;
    private final Texture emptyHeartTexture;
    private final HashMap<String, Texture> abilityTexture = new HashMap<>();
    private final ArrayList<Texture> eyebatTexture = new ArrayList<>();
    private final Animation<Texture> eyebatAnimation;
    private final ArrayList<Texture> treeTexture = new ArrayList<>();
    private final Animation<Texture> treeAnimation;
    private final ArrayList<Texture> tentacleTexture = new ArrayList<>();
    private final Animation<Texture> tentacleAnimation;
    private final Texture eyebatBulletTexture;
    private final ArrayList<Texture> explosionTexture = new ArrayList<>();
    private final Animation<Texture> explosionAnimation;
    private final Texture crystalTexture;
    private final ArrayList<Texture> hitImpactTexture = new ArrayList<>();
    private final Animation<Texture> hitImpactAnimation;
    private final ArrayList<Texture> bossTexture = new ArrayList<>();
    private final Animation<Texture> bossAnimation;
    private final ArrayList<Texture> bossAttackTexture = new ArrayList<>();
    private final Animation<Texture> bossAttackAnimation;
    private final HashMap<MusicEnum, Music> backGroundMusics = new HashMap<>();
    private final Music menuMusic;
    private final Sound crystalRewardSound;
    private final Sound explosionSound;
    private final Sound levelUpSound;
    private final Sound loseSound;
    private final Sound winSound;
    private final Sound shotSound;
    private final Sound reloadWeaponSound;
    private final Texture cursor;




    public GameAssetManager() {
        for (int i = 0 ; i < 7; i++) {
            avatars.add(new Texture(Gdx.files.internal("Avatars/avatar" + i + ".png")));
        }
        for (Hero value : Hero.values()) {
            heroes.put(value.toString(), new Texture(Gdx.files.internal("Heroes/" + value.toString() + "/idle/Portrait.png")));
            buildIdle(value);
        }
        buildWeapons();
        bulletTexture = new Texture("bullet.png");
        ammoIconTexture = new Texture("ammoIcon.png");
        buildHeart();
        heartAnimation = new Animation<>(0.1f, heartTexture.toArray(new Texture[0]));
        emptyHeartTexture = new Texture("Heart/HeartAnimation_3.png");
        buildAbilities();

        for (int i = 0; i < 4; i++) {
            eyebatTexture.add(new Texture("Monsters/Eyebat/T" + i + ".png"));
        }
        eyebatAnimation = new Animation<>(0.1f, eyebatTexture.toArray(new Texture[0]));
        this.eyebatBulletTexture = new Texture("Monsters/Eyebat/bullet.png");

        for (int i = 0; i < 3; i++) {
            treeTexture.add(new Texture("Monsters/Tree/T" + i + ".png"));
        }
        treeAnimation = new Animation<>(0.5f, treeTexture.toArray(new Texture[0]));

        for (int i = 0; i < 4; i++) {
            tentacleTexture.add(new Texture("Monsters/Tentacle/TentacleIdle/T" + i + ".png"));
        }
        tentacleAnimation = new Animation<>(0.1f, tentacleTexture.toArray(new Texture[0]));

        for (int i = 0; i < 6; i++) {
            explosionTexture.add(new Texture("Monsters/ExplosionFX/ExplosionFX_" + i + ".png"));
        }
        explosionAnimation = new Animation<>(0.1f, explosionTexture.toArray(new Texture[0]));

        crystalTexture = new Texture("crystal.png");

        for (MusicEnum value : MusicEnum.values()) {
            backGroundMusics.put(value, Gdx.audio.newMusic(Gdx.files.internal( "Music/" + value.toString() + ".mp3")));
            backGroundMusics.get(value).setLooping(true);
            backGroundMusics.get(value).setVolume(0.5f);
        }

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/SneakySnitch.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);

        crystalRewardSound = Gdx.audio.newSound(Gdx.files.internal("Sound/CrystalReward.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Explosion.wav"));
        levelUpSound = Gdx.audio.newSound(Gdx.files.internal("Sound/LevelUp.wav"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Lose.wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Win.wav"));
        reloadWeaponSound = Gdx.audio.newSound(Gdx.files.internal("Sound/ReloadWeapon.wav"));
        shotSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Shot.wav"));

        for (int i = 0; i < 2; i++) {
            hitImpactTexture.add(new Texture("Hit/HitImpactFX_" + i + ".png"));
        }
        hitImpactAnimation = new Animation<>(0.1f, hitImpactTexture.toArray(new Texture[0]));
        cursor = new Texture("Cursor.png");

        for (int i = 0; i < 6; i++) {
            bossTexture.add(new Texture("Monsters/Boss/Boss_" + i + ".png"));
        }
        bossAnimation = new Animation<>(0.1f, bossTexture.toArray(new Texture[0]));

        for (int i = 0; i < 2; i++) {
            bossAttackTexture.add(new Texture("Monsters/Boss/BossAttack_" + i + ".png"));
        }
        bossAttackAnimation = new Animation<>(0.1f, bossAttackTexture.toArray(new Texture[0]));
    }

    private void buildAbilities() {
        for (Ability ability : Ability.values()) {
            abilityTexture.put(ability.toString(), new Texture("Ability/" + ability.toString() + ".png"));
        }
    }

    private void buildHeart() {
        for (int i = 0; i < 3; i++) {
            heartTexture.add(new Texture("Heart/HeartAnimation_" + i + ".png"));
        }
    }

    private void buildWeapons() {
        for (WeaponEnum weapon : WeaponEnum.values()) {
            weaponTexture.put(weapon.toString(), new Texture("Weapons/" + weapon.toString() + "/Still.png"));
            ArrayList<Texture> textures = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                textures.add(new Texture("Weapons/" + weapon.toString() + "/Reload_" + i + ".png"));
            }
            weaponReloadTexture.put(weapon.toString(), textures);
            weaponReloadAnimation.put(weapon.toString(), new Animation<>(0.1f, textures.toArray(new Texture[0])));
        }
    }

    private void buildIdle(Hero hero) {
        ArrayList<Texture> textures = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            textures.add(new Texture("Heroes/" + hero.toString() + "/idle/Idle_" + i + ".png"));
        }
        idleTexture.put(hero.toString(), textures);
        idleAnimation.put(hero.toString(), new Animation<>(0.1f, textures.toArray(new Texture[0])));
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

    public HashMap<String, ArrayList<Texture>> getIdleTexture() {
        return idleTexture;
    }

    public HashMap<String, Animation<Texture>> getIdleAnimation() {
        return idleAnimation;
    }

    public HashMap<String, Texture> getWeaponTexture() {
        return weaponTexture;
    }

    public Texture getBulletTexture() {
        return bulletTexture;
    }

    public HashMap<String, ArrayList<Texture>> getWeaponReloadTexture() {
        return weaponReloadTexture;
    }

    public HashMap<String, Animation<Texture>> getWeaponReloadAnimation() {
        return weaponReloadAnimation;
    }

    public Texture getAmmoIconTexture() {
        return ammoIconTexture;
    }

    public ArrayList<Texture> getHeartTexture() {
        return heartTexture;
    }

    public Animation<Texture> getHeartAnimation() {
        return heartAnimation;
    }

    public Texture getEmptyHeartTexture() {
        return emptyHeartTexture;
    }

    public HashMap<String, Texture> getAbilityTexture() {
        return abilityTexture;
    }

    public ArrayList<Texture> getEyebatTexture() {
        return eyebatTexture;
    }

    public Animation<Texture> getEyebatAnimation() {
        return eyebatAnimation;
    }

    public ArrayList<Texture> getTreeTexture() {
        return treeTexture;
    }

    public Animation<Texture> getTreeAnimation() {
        return treeAnimation;
    }

    public ArrayList<Texture> getTentacleTexture() {
        return tentacleTexture;
    }

    public Animation<Texture> getTentacleAnimation() {
        return tentacleAnimation;
    }

    public Texture getEyebatBulletTexture() {
        return eyebatBulletTexture;
    }

    public ArrayList<Texture> getExplosionTexture() {
        return explosionTexture;
    }

    public Animation<Texture> getExplosionAnimation() {
        return explosionAnimation;
    }

    public Texture getCrystalTexture() {
        return crystalTexture;
    }

    public HashMap<MusicEnum, Music> getBackGroundMusics() {
        return backGroundMusics;
    }

    public Music getMenuMusic() {
        return menuMusic;
    }

    public Sound getCrystalRewardSound() {
        return crystalRewardSound;
    }

    public Sound getExplosionSound() {
        return explosionSound;
    }

    public Sound getLevelUpSound() {
        return levelUpSound;
    }

    public Sound getLoseSound() {
        return loseSound;
    }

    public Sound getWinSound() {
        return winSound;
    }

    public Sound getShotSound() {
        return shotSound;
    }

    public Sound getReloadWeaponSound() {
        return reloadWeaponSound;
    }

    public ArrayList<Texture> getHitImpactTexture() {
        return hitImpactTexture;
    }

    public Animation<Texture> getHitImpactAnimation() {
        return hitImpactAnimation;
    }

    public Texture getCursor() {
        return cursor;
    }

    public ArrayList<Texture> getBossTexture() {
        return bossTexture;
    }

    public Animation<Texture> getBossAnimation() {
        return bossAnimation;
    }

    public ArrayList<Texture> getBossAttackTexture() {
        return bossAttackTexture;
    }

    public Animation<Texture> getBossAttackAnimation() {
        return bossAttackAnimation;
    }
}

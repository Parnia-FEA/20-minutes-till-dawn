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
    private final String ammoIconImageFile;
    private final Texture ammoIconTexture;
    private final ArrayList<String> heartImageFile = new ArrayList<>();
    private final ArrayList<Texture> heartTexture = new ArrayList<>();
    private final Animation<Texture> heartAnimation;
    private final String emptyHeartImageFile;
    private final Texture emptyHeartTexture;
    private final Animation<Texture> emptyHeartAnimation;
    private final HashMap<String, String> abilityImageFile = new HashMap<>();
    private final HashMap<String, Texture> abilityTexture = new HashMap<>();
    private final String elderImageFile;
    private final Texture elderTexture;
    private final ArrayList<String> eyebatImageFile = new ArrayList<>();
    private final ArrayList<Texture> eyebatTexture = new ArrayList<>();
    private final Animation<Texture> eyebatAnimation;
    private final ArrayList<String> treeImageFile = new ArrayList<>();
    private final ArrayList<Texture> treeTexture = new ArrayList<>();
    private final Animation<Texture> treeAnimation;
    private final ArrayList<String> tentacleImageFile = new ArrayList<>();
    private final ArrayList<Texture> tentacleTexture = new ArrayList<>();
    private final Animation<Texture> tentacleAnimation;
    private final String eyebatBulletImageFile;
    private final Texture eyebatBulletTexture;
    private final ArrayList<String> explosionImageFile = new ArrayList<>();
    private final ArrayList<Texture> explosionTexture = new ArrayList<>();
    private final Animation<Texture> explosionAnimation;
    private final String crystalImageFile;
    private final Texture crystalTexture;
    private final HashMap<MusicEnum, String> backGroundMusicFiles = new HashMap<>();
    private final HashMap<MusicEnum, Music> backGroundMusics = new HashMap<>();
    private final Music menuMusic;

    private final Sound crystalRewardSound;
    private final Sound explosionSound;
    private final Sound levelUpSound;
    private final Sound loseSound;
    private final Sound winSound;
    private final Sound shotSound;
    private final Sound reloadWeaponSound;

    private final ArrayList<String> hitImpactImageFiles = new ArrayList<>();
    private final ArrayList<Texture> hitImpactTexture = new ArrayList<>();
    private final Animation<Texture> hitImpactAnimation;

    private final Texture cursor;



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
        ammoIconImageFile = "ammoIcon.png";
        ammoIconTexture = new Texture(ammoIconImageFile);
        buildHeart();
        heartAnimation = new Animation<>(0.1f, heartTexture.toArray(new Texture[0]));
        emptyHeartImageFile = "Heart/HeartAnimation_3.png";
        emptyHeartTexture = new Texture(emptyHeartImageFile);
        emptyHeartAnimation = new Animation<>(0.1f, emptyHeartTexture);
        buildAbilities();
        elderImageFile = "Monsters/Elder/ElderBrain.png";
        elderTexture = new Texture(elderImageFile);

        for (int i = 0; i < 4; i++) {
            String image = "Monsters/Eyebat/T" + i + ".png";
            eyebatImageFile.add(image);
            eyebatTexture.add(new Texture(image));
        }
        eyebatAnimation = new Animation<>(0.1f, eyebatTexture.toArray(new Texture[0]));
        this.eyebatBulletImageFile = "Monsters/Eyebat/bullet.png";
        this.eyebatBulletTexture = new Texture(this.eyebatBulletImageFile);

        for (int i = 0; i < 3; i++) {
            String image = "Monsters/Tree/T" + i + ".png";
            treeImageFile.add(image);
            treeTexture.add(new Texture(image));
        }
        treeAnimation = new Animation<>(0.5f, treeTexture.toArray(new Texture[0]));

        for (int i = 0; i < 4; i++) {
            String image = "Monsters/Tentacle/TentacleIdle/T" + i + ".png";
            tentacleImageFile.add(image);
            tentacleTexture.add(new Texture(image));
        }
        tentacleAnimation = new Animation<>(0.1f, tentacleTexture.toArray(new Texture[0]));

        for (int i = 0; i < 6; i++) {
            String image = "Monsters/ExplosionFX/ExplosionFX_" + i + ".png";
            explosionImageFile.add(image);
            explosionTexture.add(new Texture(image));
        }
        explosionAnimation = new Animation<>(0.1f, explosionTexture.toArray(new Texture[0]));

        crystalImageFile = "crystal.png";
        crystalTexture = new Texture(crystalImageFile);

        for (MusicEnum value : MusicEnum.values()) {
            String music = "Music/" + value.toString() + ".mp3";
            backGroundMusicFiles.put(value, music);
            backGroundMusics.put(value, Gdx.audio.newMusic(Gdx.files.internal(music)));
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
            String image = "Hit/HitImpactFX_" + i + ".png";
            hitImpactImageFiles.add(image);
            hitImpactTexture.add(new Texture(image));
        }
        hitImpactAnimation = new Animation<>(0.1f, hitImpactTexture.toArray(new Texture[0]));

        cursor = new Texture("Cursor.png");
    }

    private void buildAbilities() {
        for (Ability ability : Ability.values()) {
            String file = "Ability/" + ability.toString() + ".png";
            abilityImageFile.put(ability.toString(), file);
            abilityTexture.put(ability.toString(), new Texture(file));
        }
    }

    private void buildHeart() {
        for (int i = 0; i < 3; i++) {
            heartImageFile.add("Heart/HeartAnimation_" + i + ".png");
            heartTexture.add(new Texture(heartImageFile.get(i)));
        }
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

    public String getAmmoIconImageFile() {
        return ammoIconImageFile;
    }

    public Texture getAmmoIconTexture() {
        return ammoIconTexture;
    }

    public ArrayList<String> getHeartImageFile() {
        return heartImageFile;
    }

    public ArrayList<Texture> getHeartTexture() {
        return heartTexture;
    }

    public Animation<Texture> getHeartAnimation() {
        return heartAnimation;
    }

    public String getEmptyHeartImageFile() {
        return emptyHeartImageFile;
    }

    public Texture getEmptyHeartTexture() {
        return emptyHeartTexture;
    }

    public Animation<Texture> getEmptyHeartAnimation() {
        return emptyHeartAnimation;
    }

    public HashMap<String, String> getAbilityImageFile() {
        return abilityImageFile;
    }

    public HashMap<String, Texture> getAbilityTexture() {
        return abilityTexture;
    }

    public String getElderImageFile() {
        return elderImageFile;
    }

    public Texture getElderTexture() {
        return elderTexture;
    }

    public ArrayList<String> getEyebatImageFile() {
        return eyebatImageFile;
    }

    public ArrayList<Texture> getEyebatTexture() {
        return eyebatTexture;
    }

    public Animation<Texture> getEyebatAnimation() {
        return eyebatAnimation;
    }

    public ArrayList<String> getTreeImageFile() {
        return treeImageFile;
    }

    public ArrayList<Texture> getTreeTexture() {
        return treeTexture;
    }

    public Animation<Texture> getTreeAnimation() {
        return treeAnimation;
    }

    public ArrayList<String> getTentacleImageFile() {
        return tentacleImageFile;
    }

    public ArrayList<Texture> getTentacleTexture() {
        return tentacleTexture;
    }

    public Animation<Texture> getTentacleAnimation() {
        return tentacleAnimation;
    }

    public String getEyebatBulletImageFile() {
        return eyebatBulletImageFile;
    }

    public Texture getEyebatBulletTexture() {
        return eyebatBulletTexture;
    }

    public ArrayList<String> getExplosionImageFile() {
        return explosionImageFile;
    }

    public ArrayList<Texture> getExplosionTexture() {
        return explosionTexture;
    }

    public Animation<Texture> getExplosionAnimation() {
        return explosionAnimation;
    }

    public String getCrystalImageFile() {
        return crystalImageFile;
    }

    public Texture getCrystalTexture() {
        return crystalTexture;
    }

    public HashMap<MusicEnum, String> getBackGroundMusicFiles() {
        return backGroundMusicFiles;
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

    public ArrayList<String> getHitImpactImageFiles() {
        return hitImpactImageFiles;
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
}

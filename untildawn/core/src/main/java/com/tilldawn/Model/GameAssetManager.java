package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Model.enums.Hero;

import java.util.ArrayList;
import java.util.HashMap;

public class GameAssetManager {
    private static GameAssetManager instance;
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    private final ArrayList<Texture> avatars = new ArrayList<>();
    private final HashMap<String, Texture> heroes = new HashMap<>();

    public GameAssetManager() {
        for (int i = 0 ; i < 7; i++) {
            avatars.add(new Texture(Gdx.files.internal("Avatars/avatar" + i + ".png")));
        }
        for (Hero value : Hero.values()) {
            heroes.put(value.toString(), new Texture(Gdx.files.internal("Heroes/" + value.toString() + "/idle/Portrait.png")));
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
}

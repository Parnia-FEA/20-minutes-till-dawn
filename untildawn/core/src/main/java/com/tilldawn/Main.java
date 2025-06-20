package com.tilldawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Controller.LoginMenuController;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.enums.MusicEnum;
import com.tilldawn.View.LoginMenuView;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private final static OrthographicCamera camera = new OrthographicCamera();
    private static Sprite cursor;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        cursor = new Sprite(GameAssetManager.getInstance().getCursor());
        cursor.setSize(32, 32);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2, 0);
        camera.update();
        main.setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        GameAssetManager.getInstance().getMenuMusic().dispose();
        for (MusicEnum musicEnum : GameAssetManager.getInstance().getBackGroundMusics().keySet()) {
            GameAssetManager.getInstance().getBackGroundMusics().get(musicEnum).dispose();
        }
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public static Sprite getCursor() {
        return cursor;
    }
    public static OrthographicCamera getCamera() {
        return camera;
    }
}

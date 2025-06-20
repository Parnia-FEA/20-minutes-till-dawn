package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.TillDawnGame;
import com.tilldawn.Model.enums.*;

import java.util.ArrayList;

public class GameView implements Screen, InputProcessor {
    private final TillDawnGame game;
    private Stage stage;
    private final Sprite ammoIcon;
    private final Label ammo;
    private final Texture firstHeartTexture;
    private final Texture emptyHeartTexture;
    private final ArrayList<Sprite> hearts = new ArrayList<>();
    private final ArrayList<Sprite> emptyHearts = new ArrayList<>();

    private final Label chooseAbilityLabel;
    private final ArrayList<CheckBox> abilitiesCheckBox = new ArrayList<>();
    private final ButtonGroup<CheckBox> abilitiesGroup = new ButtonGroup<>();
    private final ArrayList<Image> abilities = new ArrayList<>();
    private final ArrayList<Label> abilitiesDescription = new ArrayList<>();
    private final TextButton chooseAbilityButton;
    private Table abilitySelectTable;

    private final TextButton resumeButton;
    private final TextButton giveUpButton;
    private final TextButton saveButton;

    private final Label cheatCodesInformationLabel;
    private final ArrayList<Label> cheatCodesDescription = new ArrayList<>();
    private final ArrayList<Label> cheatCodes = new ArrayList<>();

    private final Label abilitiesInformationLabel;
    private final ArrayList<Label> gainedAbilities = new ArrayList<>();
    private final ArrayList<Label> numOfAbility = new ArrayList<>();
    private final ArrayList<Image> gainedAbilitiesImages = new ArrayList<>();
    private final Label pauseConditionMessage;
    private final Label kill;
    private final Label level;

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private Table pauseTable;

    private final Label timer;

    private GameController controller;

    public GameView(TillDawnGame game, GameController controller, Skin skin) {
        this.game = game;
        Language language = GameData.getInstance().getCurrentPlayer().getLanguage();
        this.ammoIcon = new Sprite(GameAssetManager.getInstance().getAmmoIconTexture());
        this.ammoIcon.setPosition(InitialPositions.AmmoIcon.getX(), InitialPositions.AmmoIcon.getY());
        this.kill = new Label("", skin);
        this.level = new Label("", skin);
        this.ammo = new Label(String.format(String.format("%02d", game.getWeapon().getAmmo()) + " / " + String.format("%02d", game.getWeapon().getMaxAmmo())) ,skin);
        this.controller = controller;
        this.firstHeartTexture = GameAssetManager.getInstance().getHeartTexture().get(0);
        this.emptyHeartTexture = GameAssetManager.getInstance().getEmptyHeartTexture();
        buildHeartsArray();
        this.abilitySelectTable = new Table();
        abilitySelectTable.setSize((float) Gdx.graphics.getWidth() * 4 / 5, (float) Gdx.graphics.getHeight() * 4 / 5);
        abilitySelectTable.setPosition(
            (Gdx.graphics.getWidth() - abilitySelectTable.getWidth()) / 2,
            (Gdx.graphics.getHeight() - abilitySelectTable.getHeight()) / 2
        );
        abilitySelectTable.setBackground(skin.getDrawable("shadow"));

        this.chooseAbilityLabel = new Label(LangKey.GameChooseAbilityLabel.getTranslation(language), skin, "subtitle");
        this.chooseAbilityLabel.setColor(Color.MAROON);
        for (int i = 0 ; i < 3; i++) {
            CheckBox checkBox = new CheckBox("", skin);
            this.abilitiesCheckBox.add(checkBox);
            this.abilitiesGroup.add(checkBox);
            Image image = new Image();
            this.abilities.add(image);
            Label label = new Label("", skin);
            label.setColor(Color.CORAL);
            this.abilitiesDescription.add(label);
        }
        this.abilitiesGroup.setMinCheckCount(1);
        this.abilitiesGroup.setMaxCheckCount(1);
        this.chooseAbilityButton = new TextButton(LangKey.GameChooseButton.getTranslation(language), skin);
        this.timer = new Label("", skin);

        this.pauseTable = new Table();
        pauseTable.setSize((float) Gdx.graphics.getWidth() * 9 / 10, (float) Gdx.graphics.getHeight() * 9 / 10);
        pauseTable.setPosition(
            (Gdx.graphics.getWidth() - pauseTable.getWidth()) / 2,
            (Gdx.graphics.getHeight() - pauseTable.getHeight()) / 2
        );
        pauseTable.setBackground(skin.getDrawable("shadow"));

        this.resumeButton = new TextButton(LangKey.GameResumeButton.getTranslation(language), skin);
        this.giveUpButton = new TextButton(LangKey.GameGiveUpButton.getTranslation(language), skin);
        this.saveButton = new TextButton(LangKey.GameSaveAndExitButton.getTranslation(language), skin);
        this.pauseConditionMessage = new Label("", skin);
        this.pauseConditionMessage.setColor(Color.GREEN);

        this.cheatCodesInformationLabel = new Label(LangKey.CheatCodesLabel.getTranslation(language), skin, "subtitle");
        for (CheatCode cheatCode : CheatCode.values()) {
            Label label = new Label(cheatCode.getDescription(), skin);
            label.setColor(Color.CYAN);
            this.cheatCodesDescription.add(label);
            label = new Label (Input.Keys.toString(cheatCode.getMainKey()), skin);
            this.cheatCodes.add(label);
        }

        this.abilitiesInformationLabel = new Label(LangKey.GameGainedAbilitiesLabel.getTranslation(language), skin, "subtitle");
        for (Ability ability : Ability.values()) {
            Image image = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager.getInstance().getAbilityTexture().get(ability.toString()))));
            this.gainedAbilitiesImages.add(image);
            Label label = new Label(ability.toString(), skin);
            this.gainedAbilities.add(label);
            label = new Label (game.getAbilities().get(ability).toString(), skin);
            label.setColor(Color.CYAN);
            this.numOfAbility.add(label);
        }
        Gdx.input.setCursorCatched(true);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);

        controller.setView(this);
    }

    private void buildHeartsArray() {
        for (int i = 0; i < this.game.getMaxHP(); i++) {
            Sprite sprite = new Sprite(this.firstHeartTexture);
            sprite.setPosition(InitialPositions.Hearts.getX() + 30 * i, InitialPositions.Hearts.getY());
            hearts.add(sprite);
            sprite = new Sprite(this.emptyHeartTexture);
            sprite.setPosition(InitialPositions.Hearts.getX() + 30 * i, InitialPositions.Hearts.getY());
            emptyHearts.add(sprite);
        }
    }

    @Override
    public void show() {
        GameAssetManager.getInstance().getBackGroundMusics().get(GameData.getInstance().getCurrentPlayer().getMusic()).play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        shapeRenderer.setProjectionMatrix(Main.getCamera().combined);
        ammo.setPosition(InitialPositions.Ammo.getX(), InitialPositions.AmmoIcon.getY());
        timer.setPosition(InitialPositions.Timer.getX(), InitialPositions.Timer.getY());
        timer.setText(controller.getTimeRemainingFormatted());
        timer.setColor(Color.GREEN);
        kill.setPosition(InitialPositions.Kill.getX(), InitialPositions.Kill.getY());
        kill.setText(LangKey.GameKillLabel.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()) + " " + game.getKill());
        level.setPosition(InitialPositions.Level.getX(), InitialPositions.Level.getY());
        level.setText(LangKey.GameLevelLabel.getTranslation(GameData.getInstance().getCurrentPlayer().getLanguage()) + " " + game.getLevel());
        level.setColor(Color.GOLD);
        stage.addActor(ammo);
        stage.addActor(timer);
        stage.addActor(kill);
        stage.addActor(level);

        abilitySelectTable.setVisible(false);
        abilitySelectTable.add(chooseAbilityLabel).colspan(7).center();
        abilitySelectTable.add().height(150).colspan(2);
        abilitySelectTable.row();
        abilitySelectTable.row().pad(10, 0, 10, 0);
        for (int i = 0; i < 3; i++) {
            abilitySelectTable.add(this.abilitiesCheckBox.get(i)).pad(5);
            abilitySelectTable.add(this.abilities.get(i)).pad(5);
            abilitySelectTable.add(this.abilitiesDescription.get(i)).pad(5);
            abilitySelectTable.row().pad(10, 0, 10, 0);
        }
        abilitySelectTable.row().pad(10, 0, 10, 0);
        abilitySelectTable.add(chooseAbilityButton).colspan(7).center();

        pauseTable.setVisible(false);

        Table cheatCodeTable = new Table();
        cheatCodeTable.add(cheatCodesInformationLabel).colspan(6).center();
        cheatCodeTable.row().pad(10, 0, 10, 0);
        for (int i = 0; i < cheatCodesDescription.size(); i++) {
            cheatCodeTable.add(cheatCodesDescription.get(i)).colspan(1).center();
            cheatCodeTable.add(cheatCodes.get(i)).colspan(1).center();
            cheatCodeTable.row().pad(10, 0, 10, 0);
        }

        Table abilityTable = new Table();
        abilityTable.add(abilitiesInformationLabel).colspan(9).center();
        abilityTable.row().pad(10, 0, 10, 0);
        for (int i = 0; i < gainedAbilities.size(); i++) {
            abilityTable.add(gainedAbilities.get(i)).colspan(3).center();
            abilityTable.add(gainedAbilitiesImages.get(i)).colspan(3).center();
            abilityTable.add(numOfAbility.get(i)).colspan(3).center();
            abilityTable.row().pad(10, 0, 10, 0);
        }

        pauseTable.add(cheatCodeTable).colspan(10).left();
        pauseTable.add(abilityTable).colspan(15).right();
        pauseTable.row().pad(10, 0, 10, 0);
        pauseTable.add(resumeButton).colspan(5);
        pauseTable.add(giveUpButton).colspan(5);
        pauseTable.add(saveButton).colspan(5);
        pauseTable.row().pad(10, 0, 10, 0);
        pauseTable.add(pauseConditionMessage).colspan(15);
        stage.addActor(abilitySelectTable);
        stage.addActor(pauseTable);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleResumeButton();
            }
        });

        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleGiveUpButton();
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSaveButton();
            }
        });
    }

    @Override
    public void render(float delta) {
        controller.updateGame(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        controller.draw();
        Main.getBatch().end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(5, Gdx.graphics.getHeight() - 30, (Gdx.graphics.getWidth() - 10), 30);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(5, Gdx.graphics.getHeight() - 30, (Gdx.graphics.getWidth() - 10) * controller.getXPPercent(), 30);

        shapeRenderer.end();
        controller.handleOtherTables();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Main.getCamera().unproject(mouse);
        Main.getBatch().begin();
        Main.getCursor().setPosition(mouse.x, mouse.y);
        Main.getCursor().draw(Main.getBatch());
        Main.getBatch().end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        GameAssetManager.getInstance().getBackGroundMusics().get(GameData.getInstance().getCurrentPlayer().getMusic()).stop();
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(screenX, screenY, button, Main.getCamera());
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public TillDawnGame getGame() {
        return game;
    }

    public Sprite getAmmoIcon() {
        return ammoIcon;
    }

    public Texture getFirstHeartTexture() {
        return firstHeartTexture;
    }

    public ArrayList<Sprite> getHearts() {
        return hearts;
    }

    public Texture getEmptyHeartTexture() {
        return emptyHeartTexture;
    }

    public ArrayList<Sprite> getEmptyHearts() {
        return emptyHearts;
    }

    public ArrayList<CheckBox> getAbilitiesCheckBox() {
        return abilitiesCheckBox;
    }

    public ButtonGroup<CheckBox> getAbilitiesGroup() {
        return abilitiesGroup;
    }

    public Table getAbilitySelectTable() {
        return abilitySelectTable;
    }

    public Label getTimer() {
        return timer;
    }

    public ArrayList<Image> getAbilities() {
        return abilities;
    }

    public ArrayList<Label> getAbilitiesDescription() {
        return abilitiesDescription;
    }

    public Table getPauseTable() {
        return pauseTable;
    }

    public ArrayList<Label> getGainedAbilities() {
        return gainedAbilities;
    }

    public ArrayList<Label> getNumOfAbility() {
        return numOfAbility;
    }

    public Label getAmmo() {
        return ammo;
    }

    public Stage getStage() {
        return stage;
    }

    public Label getPauseConditionMessage() {
        return pauseConditionMessage;
    }

    public Label getKill() {
        return kill;
    }

    public Label getLevel() {
        return level;
    }
}

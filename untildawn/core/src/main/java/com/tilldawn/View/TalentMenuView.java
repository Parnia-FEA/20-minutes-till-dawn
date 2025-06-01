package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Controller.TalentMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameData;
import com.tilldawn.Model.enums.Ability;
import com.tilldawn.Model.enums.CheatCode;
import com.tilldawn.Model.enums.Hero;
import com.tilldawn.Model.enums.InputKey;

import java.util.ArrayList;

public class TalentMenuView implements Screen {
    private Stage stage;
    private final Label talentMenuLabel;

    private final TextButton heroesButton;
    private final Label heroesInformationLabel;
    private final ArrayList<CheckBox> heroes = new ArrayList<>();
    private final ButtonGroup<CheckBox> heroesGroup = new ButtonGroup<>();
    private final Image heroImage;
    private final Label heroDescription;

    private final TextButton keysButton;
    private final Label inputKeysInformationLabel;
    private final ArrayList<Label> inputKeysDescription = new ArrayList<>();
    private final ArrayList<Label> inputKeys = new ArrayList<>();

    private final TextButton cheatCodesButton;
    private final Label cheatCodesInformationLabel;
    private final ArrayList<Label> cheatCodesDescription = new ArrayList<>();
    private final ArrayList<Label> cheatCodes = new ArrayList<>();

    private final TextButton abilitiesButton;
    private final Label abilitiesInformationLabel;
    private final ArrayList<Label> abilitiesDescription = new ArrayList<>();
    private final ArrayList<Label> abilities = new ArrayList<>();
    private final ArrayList<Image> abilitiesImages = new ArrayList<>();

    private final TextButton backToTalentMenuButton;
    private final TextButton backButton;
    public Table table;
    private final TalentMenuController controller;

    public TalentMenuView(TalentMenuController controller, Skin skin) {
        this.controller = controller;
        this.talentMenuLabel = new Label("Talent Menu", skin, "subtitle");

        this.heroesButton = new TextButton("Heroes Information", skin);
        this.heroesInformationLabel = new Label("Heroes Information", skin, "subtitle");
        for (Hero hero : Hero.values()) {
            CheckBox checkBox = new CheckBox(hero.toString(), skin);
            this.heroes.add(checkBox);
            this.heroesGroup.add(checkBox);
        }
        this.heroesGroup.setMaxCheckCount(1);
        this.heroesGroup.setMinCheckCount(1);
        this.heroImage = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager.getInstance().getHeroes().get(this.heroesGroup.getChecked().getText().toString()))));
        this.heroDescription = new Label(Hero.valueOf(this.heroesGroup.getChecked().getText().toString()).getDescription(), skin);
        this.heroDescription.setColor(Color.MAROON);

        this.keysButton = new TextButton("Input Keys Information", skin);
        this.inputKeysInformationLabel = new Label("Input Keys Information", skin, "subtitle");
        for (InputKey inputKey : GameData.getInstance().getCurrentPlayer().getKeys().keySet()) {
            Label label = new Label(inputKey.getDescription(), skin);
            label.setColor(Color.CYAN);
            this.inputKeysDescription.add(label);
            if (inputKey.equals(InputKey.ShootProjectile)) {
                if (GameData.getInstance().getCurrentPlayer().getKeys().get(inputKey).equals(Input.Buttons.LEFT)) {
                    label = new Label("Left Mouse Button", skin);
                }
                else if (GameData.getInstance().getCurrentPlayer().getKeys().get(inputKey).equals(Input.Buttons.RIGHT)) {
                    label = new Label("Right Mouse Button", skin);
                }
                else if (GameData.getInstance().getCurrentPlayer().getKeys().get(inputKey).equals(Input.Buttons.MIDDLE)) {
                    label = new Label("Middle Mouse Button", skin);
                }
                else {
                    label = new Label (Input.Keys.toString(GameData.getInstance().getCurrentPlayer().getKeys().get(inputKey)), skin);
                }
            }
            else {
                label = new Label (Input.Keys.toString(GameData.getInstance().getCurrentPlayer().getKeys().get(inputKey)), skin);
            }
            this.inputKeys.add(label);
        }

        this.cheatCodesButton = new TextButton("Cheat Codes Information", skin);
        this.cheatCodesInformationLabel = new Label("Cheat Codes Information", skin, "subtitle");
        for (CheatCode cheatCode : CheatCode.values()) {
            Label label = new Label(cheatCode.getDescription(), skin);
            label.setColor(Color.CYAN);
            this.cheatCodesDescription.add(label);
            label = new Label (Input.Keys.toString(cheatCode.getMainKey()), skin);
            this.cheatCodes.add(label);
        }

        this.abilitiesButton = new TextButton("Abilities Information", skin);
        this.abilitiesInformationLabel = new Label("Abilities Information", skin, "subtitle");
        for (Ability ability : Ability.values()) {
            Image image = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager.getInstance().getAbilityTexture().get(ability.toString()))));
            this.abilitiesImages.add(image);
            Label label = new Label(ability.toString(), skin);
            this.abilities.add(label);
            label = new Label (ability.getDescription(), skin);
            label.setColor(Color.CYAN);
            this.abilitiesDescription.add(label);
        }

        this.backToTalentMenuButton = new TextButton("Back", skin);
        this.backButton = new TextButton("Back", skin);
        this.table = new Table();
        Gdx.input.setCursorCatched(true);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
        controller.setView(this);
    }

    @Override
    public void show() {
        GameAssetManager.getInstance().getMenuMusic().play();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center();

        setUpTalentMenuStage();

        heroesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleHeroesButton();
            }
        });

        keysButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleKeysButton();
            }
        });

        cheatCodesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleCheatCodesButton();
            }
        });

        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleAbilitiesButton();
            }
        });

        backToTalentMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackToTalentMenuButton();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleBackButton();
            }
        });

        stage.addActor(table);
    }

    public void setUpTalentMenuStage() {
        table.clear();
        table.add(talentMenuLabel).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(heroesButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(keysButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(cheatCodesButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(abilitiesButton).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).colspan(2).center();
    }

    public void setUpHeroesInformationStage() {
        table.clear();
        table.add(heroesInformationLabel).colspan(5).center();
        table.row().pad(10, 0, 10, 0);
        for (CheckBox hero : this.heroes) {
            table.add(hero).pad(5);
        }
        table.row().pad(10, 0, 10, 0);
        table.add(heroImage).colspan(2).center();
        table.add(heroDescription).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(backToTalentMenuButton).colspan(5).center();
    }

    public void setUpInputKeysInformationStage() {
        table.clear();
        table.add(inputKeysInformationLabel).colspan(6).center();
        table.row().pad(10, 0, 10, 0);
        for (int i = 0; i < inputKeysDescription.size(); i++) {
            table.add(inputKeysDescription.get(i)).colspan(3).center();
            table.add(inputKeys.get(i)).colspan(3).center();
            table.row().pad(10, 0, 10, 0);
        }
        table.row().pad(10, 0, 10, 0);
        table.add(backToTalentMenuButton).colspan(6).center();
    }

    public void setUpCheatCodesInformationStage() {
        table.clear();
        table.add(cheatCodesInformationLabel).colspan(6).center();
        table.row().pad(10, 0, 10, 0);
        for (int i = 0; i < cheatCodesDescription.size(); i++) {
            table.add(cheatCodesDescription.get(i)).colspan(3).center();
            table.add(cheatCodes.get(i)).colspan(3).center();
            table.row().pad(10, 0, 10, 0);
        }
        table.row().pad(10, 0, 10, 0);
        table.add(backToTalentMenuButton).colspan(6).center();
    }

    public void setUpAbilitiesInformationStage() {
        table.clear();
        table.add(abilitiesInformationLabel).colspan(9).center();
        table.row().pad(10, 0, 10, 0);
        for (int i = 0; i < abilities.size(); i++) {
            table.add(abilitiesImages.get(i)).colspan(3).center();
            table.add(abilities.get(i)).colspan(3).center();
            table.add(abilitiesDescription.get(i)).colspan(3).center();
            table.row().pad(10, 0, 10, 0);
        }
        table.row().pad(10, 0, 10, 0);
        table.add(backToTalentMenuButton).colspan(9).center();
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        controller.updateHeroesInformation();
        stage.draw();
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        stage.getCamera().unproject(mouse);
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
        GameAssetManager.getInstance().getMenuMusic().pause();
    }

    public ButtonGroup<CheckBox> getHeroesGroup() {
        return heroesGroup;
    }

    public Image getHeroImage() {
        return heroImage;
    }

    public Label getHeroDescription() {
        return heroDescription;
    }
}

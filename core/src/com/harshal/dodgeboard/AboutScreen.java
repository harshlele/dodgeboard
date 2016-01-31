package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by harshal on 30/1/16.
 * about screen
 */
public class AboutScreen implements Screen {


    //stage and actors
    private Stage stage;
    private Skin mainSkin;
    private MainGame mainGame;
    private TextButton backButton;
    private Label libgdxPlug,sounds,powerup,negativebeep,backSound,clickSound, goToSource;


    public AboutScreen(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @Override
    public void show() {


        //initialise stage and skin
        stage=new Stage(new FitViewport(1080,1920));
        //initialize skins
        mainSkin=new Skin();
        mainSkin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        mainSkin.add("default-font", new BitmapFont(Gdx.files.internal("fonts/font.fnt")));
        mainSkin.load(Gdx.files.internal("uiskin.json"));


        /*Labels and listeners*/

        //mention libgdx
        libgdxPlug=new Label("Made using Libgdx",mainSkin);
        libgdxPlug.setFontScale(0.8f);
        libgdxPlug.setWrap(true);
        libgdxPlug.setPosition(stage.getWidth() / 2 - libgdxPlug.getWidth() / 2 + 130, (float) (stage.getHeight() * 0.8) - (libgdxPlug.getHeight() / 2));

        libgdxPlug.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://libgdx.badlogicgames.com/");
            }
        });


        sounds=new Label("Sounds",mainSkin);
        sounds.setWrap(true);
        sounds.setFontScale(0.8f);
        sounds.setPosition(stage.getWidth() / 2 - sounds.getWidth() / 2, (float) (stage.getHeight() * 0.7) - (sounds.getHeight() / 2));


        goToSource =new Label("(Click to go to source)",mainSkin);
        goToSource.setWrap(true);
        goToSource.setFontScale(0.5f);
        goToSource.setPosition(stage.getWidth() / 2 - goToSource.getWidth() / 2 + 350, (float) (stage.getHeight() * 0.65) - (goToSource.getHeight() / 2));



        //Collision sounds
        powerup=new Label("Collision Sound",mainSkin);
        powerup.setWrap(true);
        powerup.setFontScale(0.8f);
        powerup.setPosition(stage.getWidth() / 2 - powerup.getWidth() / 2 + 100, (float) (stage.getHeight() * 0.6) - (powerup.getHeight() / 2));

        powerup.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://www.freesound.org/people/n_audioman/sounds/266746/");
            }
        });

        //Game over sound
        negativebeep=new Label("Game Over sound",mainSkin);
        negativebeep.setWrap(true);
        negativebeep.setFontScale(0.8f);
        negativebeep.setPosition(stage.getWidth() / 2 - negativebeep.getWidth() / 2 + 100, (float) (stage.getHeight() * 0.5) - (negativebeep.getHeight() / 2));

        negativebeep.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://www.freesound.org/people/themusicalnomad/sounds/253886/");

            }
        });

        //Background Music
        backSound=new Label("Background Music",mainSkin);
        backSound.setWrap(true);
        backSound.setFontScale(0.8f);
        backSound.setPosition(stage.getWidth() / 2 - backSound.getWidth() / 2 + 100, (float) (stage.getHeight() * 0.4) - backSound.getHeight() / 2);

        backSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://www.freesound.org/people/RutgerMuller/sounds/51239/");

            }
        });


        //Click sound
        clickSound=new Label("Click Sounds",mainSkin);
        clickSound.setWrap(true);
        clickSound.setFontScale(0.8f);
        clickSound.setPosition(stage.getWidth() / 2 - clickSound.getWidth() / 2+100, (float) (stage.getHeight() * 0.3) - clickSound.getHeight() / 2);

        clickSound.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://www.freesound.org/people/alph4tg/sounds/333429/");

            }
        });



        //initialise back button
        backButton=new TextButton("back",mainSkin);
        backButton.setSize(700, 150);
        backButton.setPosition((stage.getWidth() / 2 - backButton.getWidth() / 2), (float) (backButton.getHeight() / 2));

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGame.clickSound.play();
                stage.addAction(Actions.sequence(Actions.moveBy(0, stage.getHeight(), 0.3f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        mainGame.setScreen(new MainMenuScreen(mainGame));
                    }
                })));
            }
        });





        stage.addActor(libgdxPlug);
        stage.addActor(sounds);
        stage.addActor(powerup);
        stage.addActor(negativebeep);
        stage.addActor(backSound);
        stage.addActor(clickSound);
        stage.addActor(goToSource);
        stage.addActor(backButton);
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.3f)));
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

    }


    @Override
    public void dispose() {
        stage.dispose();
        mainSkin.dispose();
    }















    @Override
    public void resize(int width, int height) {

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



}

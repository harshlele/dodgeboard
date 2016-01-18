package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by harshal on 18/1/16
 * class to show the main menu screen
 */
public class MainMenuScreen implements Screen {

    //stage and actors
    private Stage stage;
    private Skin mainSkin;
    private TextButton playButton;

    //instance of MainGame that will be used to change the screen
    private MainGame mainGame;


    public MainMenuScreen(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @Override
    public void show() {
        //initialise stage and skin
        stage=new Stage(new FitViewport(1080,1920));
        Gdx.input.setInputProcessor(stage);
        //initialize skins
        mainSkin=new Skin();
        mainSkin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        mainSkin.add("default-font", new BitmapFont(Gdx.files.internal("fonts/font2.fnt")));
        mainSkin.load(Gdx.files.internal("uiskin.json"));

        //initialise play button
        playButton=new TextButton("Play",mainSkin);
        playButton.setSize(700, 150);
        playButton.setPosition((stage.getWidth() / 2 - playButton.getWidth() / 2), (float) (stage.getHeight() * 0.6 - playButton.getHeight() / 2));

        //start game when play button is pressed
        playButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGame.setScreen(new GameScreen(mainGame));
            }
        });

        // add actors to stage
        stage.addActor(playButton);

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










    /*CURRENTLY UNUSED METHODS*/
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

package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private TextButton playButton,aboutButton;
    private Texture titleTex;
    private Image titleImg;

    //instance of MainGame that will be used to change the screen
    private MainGame mainGame;


    public MainMenuScreen(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @Override
    public void show() {
        //disable ads
        mainGame.handler.setAdVis(false);

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

        //initialise about button
        aboutButton=new TextButton("About",mainSkin);
        aboutButton.setSize(700, 150);
        aboutButton.setPosition((stage.getWidth() / 2 - aboutButton.getWidth() / 2), (float) (stage.getHeight() * 0.4 - aboutButton.getHeight() / 2));


        //start game when play button is pressed
        playButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGame.clickSound.play();
                stage.addAction(Actions.sequence(Actions.moveBy(0, stage.getHeight(), 0.3f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        mainGame.setScreen(new GameScreen(mainGame, false, 0));
                    }
                })));

                //mainGame.setScreen(new GameScreen(mainGame,false));
            }
        });

        aboutButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGame.clickSound.play();
                stage.addAction(Actions.sequence(Actions.moveBy(0, stage.getHeight(), 0.3f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        mainGame.setScreen(new AboutScreen(mainGame));
                    }
                })));
            }
        });



        //initialise the title Texture and Actor
        titleTex=new Texture(Gdx.files.internal("title.png"));
        titleImg=new Image(titleTex);
        titleImg.setSize(titleTex.getWidth(), titleTex.getHeight());
        titleImg.setPosition(0, 1400);




        // add actors to stage
        stage.addActor(playButton);
        stage.addActor(aboutButton);
        stage.addActor(titleImg);


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

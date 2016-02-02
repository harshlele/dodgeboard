package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

/*
 * Created by harshal on 31/1/16.
 */
public class SplashScreen implements Screen {
    //stages and actors
    private Stage stage;
    private Texture titleTex;
    private Image titleImg;
    private boolean changeScreen;

    //total time for which splash screen has been displayed(used to switch the screen after 2 seconds)
    private float splashTime;
    //instance of MainGame used to switch screen
    private MainGame mainGame;

    public SplashScreen(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    @Override
    public void show() {
        //disable ads
        mainGame.handler.setAdVis(false);


        stage=new Stage(new FitViewport(1080,1920));
        splashTime=0;
        changeScreen=false;

        titleTex=new Texture(Gdx.files.internal("title.png"));
        titleImg=new Image(titleTex);
        titleImg.setSize(titleTex.getWidth(), titleTex.getHeight());
        titleImg.setPosition(stage.getWidth() / 2 - titleImg.getWidth() / 2, stage.getHeight() / 2 - titleImg.getHeight() / 2);



        stage.addActor(titleImg);

        stage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.2f)));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        splashTime+=delta;

        if(changeScreen){
            mainGame.setScreen(new MainMenuScreen(mainGame));
        }

        if(splashTime >= 2){
            stage.addAction(Actions.moveBy(0,1400-(titleImg.getHeight()/2),0.3f));
            changeScreen=true;
        }



        stage.act(delta);
        stage.draw();

    }



    @Override
    public void dispose() {
        stage.dispose();
        titleTex.dispose();


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

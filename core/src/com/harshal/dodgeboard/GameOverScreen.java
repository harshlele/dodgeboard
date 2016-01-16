package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by harshal on 14/1/16.
 * Screen to show after the game is over
 * uses scene2D
 */
public class GameOverScreen implements Screen {

    private Stage stage;
    private Skin mainSkin;
    private TextButton playAgainButton,mainMenuButton;

    @Override
    public void show() {

        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        //initialize skins
        mainSkin=new Skin();
        mainSkin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        mainSkin.add("default-font", new BitmapFont(Gdx.files.internal("fonts/font.fnt")));
        mainSkin.load(Gdx.files.internal("uiskin.json"));

        //initialize the play again button
        playAgainButton =new TextButton("Play again",mainSkin);
        playAgainButton.setSize(700,200);
        playAgainButton.setPosition((stage.getWidth() / 2 - playAgainButton.getWidth() / 2), (float) (stage.getHeight() * 0.3 - playAgainButton.getHeight() / 2));

        //initialize the main menu button
        mainMenuButton =new TextButton("Main Menu",mainSkin);
        mainMenuButton.setSize(700,200);
        mainMenuButton.setPosition((stage.getWidth() / 2 - playAgainButton.getWidth() / 2), (float) (stage.getHeight() * 0.1 - playAgainButton.getHeight() / 2));


        stage.addActor(playAgainButton);
        stage.addActor(mainMenuButton);


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

    }




    /*UNUSED METHODS*/

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

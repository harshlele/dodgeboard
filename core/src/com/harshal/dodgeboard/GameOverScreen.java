package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import javax.swing.Action;

/**
 * Created by harshal on 14/1/16.
 * Screen to show after the game is over
 * uses scene2D
 */
public class GameOverScreen implements Screen {

    //stage,skin and buttons
    private Stage stage;
    private Skin mainSkin;
    private TextButton playAgainButton,mainMenuButton;
    private Label scoreLabel,timetextLabel;
    private BitmapFont scoreFont;


    //String that stores the game time
    private String gamePlayTime;

    //instance of MainGame that can be used for changing screens
    private MainGame mainGame;

    public GameOverScreen(String gamePlayTime,MainGame game)
    {
        this.gamePlayTime = gamePlayTime;
        mainGame=game;
    }

    @Override
    public void show() {

        stage=new Stage(new FitViewport(1080,1920));
        Gdx.input.setInputProcessor(stage);
        //initialize skins
        mainSkin=new Skin();
        mainSkin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        mainSkin.add("default-font", new BitmapFont(Gdx.files.internal("fonts/font2.fnt")));
        mainSkin.load(Gdx.files.internal("uiskin.json"));

        scoreFont=new BitmapFont(Gdx.files.internal("fonts/font.fnt"));

        //initialize the play again button
        playAgainButton =new TextButton("Play again",mainSkin);
        playAgainButton.setSize(700, 150);
        playAgainButton.setPosition((stage.getWidth() / 2 - playAgainButton.getWidth() / 2), (float) (stage.getHeight() * 0.3 - playAgainButton.getHeight() / 2));


        //initialize the main menu button
        mainMenuButton =new TextButton("Main Menu",mainSkin);
        mainMenuButton.setSize(700, 150);
        mainMenuButton.setPosition((stage.getWidth() / 2 - playAgainButton.getWidth() / 2), (float) (stage.getHeight() * 0.2 - playAgainButton.getHeight() / 2));



        //initialize label that shows "Game Over"
        timetextLabel=new Label("Game Over",new Label.LabelStyle(scoreFont,scoreFont.getColor()));
        timetextLabel.setWrap(true);
        timetextLabel.setPosition((stage.getWidth() / 2 - timetextLabel.getWidth() / 2), (float) (stage.getHeight() * 0.7 - timetextLabel.getHeight() / 2));

        //initialize label that shows the score
        scoreLabel=new Label(gamePlayTime,new Label.LabelStyle(scoreFont, scoreFont.getColor()));
        scoreLabel.setSize(400, 200);
        scoreLabel.setFontScale(2f);
        scoreLabel.setPosition(((stage.getWidth() / 2 - scoreLabel.getWidth() / 2)-75), (float) (stage.getHeight() * 0.6 - scoreLabel.getHeight() / 2));


        //start new game when play again button is tapped
        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGame.clickSound.play();
                stage.addAction(Actions.sequence(Actions.moveBy(0, stage.getHeight(), 0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        mainGame.setScreen(new GameScreen(mainGame, false));
                    }
                })));
                //mainGame.setScreen(new GameScreen(mainGame,false));
            }
        });

        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGame.clickSound.play();
                stage.addAction(Actions.sequence(Actions.moveBy(0, stage.getHeight(), 0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        mainGame.setScreen(new MainMenuScreen(mainGame));
                    }
                })));
                //mainGame.setScreen(new MainMenuScreen(mainGame));
            }
        });

        //add buttons and laberls to stage
        stage.addActor(playAgainButton);
        stage.addActor(mainMenuButton);
        stage.addActor(scoreLabel);
        stage.addActor(timetextLabel);

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
        scoreFont.dispose();
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

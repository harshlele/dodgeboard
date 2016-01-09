package com.harshal.dodgeboard;

import com.badlogic.gdx.Game;

/**
 * Created by harshal on 8/1/16.
 * Game class that controls everything.
 */
public class MainGame extends Game {

    //GameScreen object that can be used to resume the game
    private GameScreen storedScreen;

    public int screenWidth,screenHeight;

    public MainGame(int width,int height) {
       screenWidth=width;
       screenHeight=height;
    }

    //create method is fired when the game begins
    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }

    //store the gamescreen so that the game can be resumed from a paused state.
    public void storeScreen(GameScreen screen){
        storedScreen=screen;

    }






}

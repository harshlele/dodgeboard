package com.harshal.dodgeboard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/**
 * Created by harshal on 8/1/16.
 * Game class that controls everything.
 */
public class MainGame extends Game {


    //SavedState instance used to resume the game from pause screen
    protected SavedState storedState;
    //the common Sound that plays when a button is pressed
    protected Sound clickSound;

    public int screenWidth,screenHeight;

    public MainGame(int width,int height) {
       screenWidth=width;
       screenHeight=height;
       //clickSound= Gdx.audio.newSound(Gdx.files.internal("Music/clickSound.ogg"));
    }

    //create method is fired when the game begins
    @Override
    public void create() {
        clickSound= Gdx.audio.newSound(Gdx.files.internal("Music/clickSound.ogg"));
        setScreen(new MainMenuScreen(this));
    }

    public void storeState(SavedState toStore){
        storedState=toStore;
    }

    @Override
    public void dispose() {
        clickSound.dispose();
        super.dispose();
    }
}





//a class that stores data about the game so that the game can be resumed from a certain state
class SavedState {

    //indicates whether finger is touching the screen
    protected boolean isFingerDown;

    //position of finger
    //private int fingerPosX,fingerPosY,prevFingerX,prevFingerY;
    protected Finger f;

    //multiplier that is used to adjust relation between movement of board and movement of finger
    protected double boardPosMultiplier;

    //Timekeeper object to track time
    protected TimeKeeper timer;

    //time that is used to change length of board
    protected long timeMilli;

    //array of dropables that will be drawn on screen
    protected Array<Dropable> droppedArray;
    //time since last object was dropped.
    //lastDroppedLimit is value after which next dropable will be sent
    protected float lastDroppedTime,lastDroppedLimit;
    //Value that Vertical component of motion of dropables will be changed every frame
    protected int dropableYIncrement;
    //random no between 0 and 100 that determines whether dropped item is a normal sphere or a cut sphere.
    protected int dropableCode;
    //boolean indicating whether board has been shortened.
    protected boolean isBoardShort;
    //Timer to keep track of time since board was shortened so that we can lengthen the board again
    //after 30 seconds
    protected TimeKeeper shortTime;
    //No of lives
    protected int lives;
    //boolean indicating whether game is over
    protected boolean isGameOver;
    //boolean indicating whether game is running
    protected boolean isGameRunning;

    //"official" time of system
    //this is the time that will be stored for saving high scores and such
    protected Time officialTime;

    //Rectangle instance that saves location and dimensions of the board
    protected Rectangle boardRect=new Rectangle();

    //background music
    protected Music bMusic;




}



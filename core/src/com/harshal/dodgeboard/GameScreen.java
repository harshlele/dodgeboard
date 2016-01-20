package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * Created by harshal on 7/1/16.
 * Screen for the game
 *
 * THIS RENDERS GameObsolete OBSOLETE
 * GameObsolete WILL BE KEPT FOR SOMETIME JUST IN CASE SOMETHING
 * WRONG HAPPENS WITH THIS SCREEN
 */
public class GameScreen implements Screen,InputProcessor {

    //camera
    private OrthographicCamera camera ;
    private SpriteBatch batch;

    //texture for board
    private Texture boardTex;
    //Rectangle instance for holding info about the position of object
    private Rectangle boardRect;

    //Tag for debugging
    private static final String TAG="DodgeBoard";
    private static final String REPTAG="Rep";

    //indicates whether finger is touching the screen
    private boolean isFingerDown;

    //position of finger
    //private int fingerPosX,fingerPosY,prevFingerX,prevFingerY;
    private Finger f;

    //multiplier that is used to adjust relation between movement of board and movement of finger
    private double boardPosMultiplier;

    //Timekeeper object to track time
    private TimeKeeper timer;

    //time that is used to change length of board
    private long timeMilli;

    //Game object that called this screen,will be stored here so that other screens
    //can be set from inside this one.
    private MainGame mainGame;

    //array of dropables that will be drawn on screen
    private Array<Dropable> droppedArray;
    //time since last object was dropped.
    //lastDroppedLimit is value after which next dropable will be sent
    private float lastDroppedTime,lastDroppedLimit;
    //Value that Vertical component of motion of dropables will be changed every frame
    private int dropableYIncrement;
    //random no between 0 and 100 that determines whether dropped item is a normal sphere or a cut sphere.
    private int dropableCode;
    //boolean indicating whether board has been shortened.
    private boolean isBoardShort;
    //Timer to keep track of time since board was shortened so that we can lengthen the board again
    //after 30 seconds
    private TimeKeeper shortTime;
    //No of lives
    private int lives;
    //Game over texture
    private Texture gameOverTex;
    //boolean indicating whether game is over
    private boolean isGameOver;
    //boolean indicating whether game is running
    private boolean isGameRunning;
    //Font for drawinng no of lines and time on screen
    private BitmapFont font;
    //object for getting bounds of text so that we can correctly display the score and time
    private GlyphLayout glyphLayout;
    //"official" time of system
    //this is the time that will be stored for saving high scores and such
    private Time officialTime;

    //stage,skin and button for the pause button
    private Stage stage;
    private Skin mainSkin;
    private TextButton pauseButton;


    //indicates whether the game is being set from a saved state
    private boolean isGameSaved;

    //store an instance of MainGame so that we can change screens from inside this screen
    public GameScreen(MainGame game,boolean gameSaved){
        isGameSaved=gameSaved;
        mainGame=game;

    }





    @Override
    public void show() {
        batch = new SpriteBatch();

        // initialize camera
        camera=new OrthographicCamera();
        camera.setToOrtho(false, 1080, 1920);

        //define the board and its starting position
        boardTex =new Texture(Gdx.files.internal("board360.png"));
        boardRect=new Rectangle();
        boardRect.x=360;
        boardRect.y=556;
        boardRect.width=360;
        boardRect.height=60;


        //if the game is being resumed,then load key variables from the saved state
        if(isGameSaved){

            isFingerDown=mainGame.storedState.isFingerDown;
            boardPosMultiplier=mainGame.storedState.boardPosMultiplier;
            f=mainGame.storedState.f;
            lastDroppedTime=mainGame.storedState.lastDroppedTime;
            lastDroppedLimit=mainGame.storedState.lastDroppedLimit;
            dropableYIncrement=mainGame.storedState.dropableYIncrement;
            isBoardShort=mainGame.storedState.isBoardShort;
            lives=mainGame.storedState.lives;
            isGameOver=mainGame.storedState.isGameOver;
            isGameRunning=mainGame.storedState.isGameRunning;
            if(!isGameRunning){
                isGameRunning=true;
            }
            officialTime=mainGame.storedState.officialTime;
            droppedArray=mainGame.storedState.droppedArray;
            timer=mainGame.storedState.timer;
            timeMilli=mainGame.storedState.timeMilli;
            shortTime=mainGame.storedState.shortTime;



        }
        else {
            //initialize variables
            isFingerDown = false;
            boardPosMultiplier = 1.25;
            f = new Finger();
            lastDroppedTime = 0;
            lastDroppedLimit = 0.6f;
            dropableYIncrement = 25;
            isBoardShort = false;
            lives = 5;
            isGameOver = false;
            isGameRunning = true;
            officialTime = new Time();
            //initialize the array
            droppedArray = new Array<Dropable>();
            //class to measure time
            timer = new TimeKeeper();
            timer.initTimer();
            timeMilli = timer.updateTime();
            shortTime = new TimeKeeper();

        }


        stage = new Stage(new FitViewport(1080, 1920));

        font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        glyphLayout = new GlyphLayout();

        //initialize skins
        mainSkin=new Skin();
        mainSkin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        mainSkin.add("default-font", new BitmapFont(Gdx.files.internal("fonts/font2.fnt")));
        mainSkin.load(Gdx.files.internal("uiskin.json"));


        //set the screen such that the stage gets all input events first.
        InputMultiplexer multiplexer=new InputMultiplexer(stage,this);
        Gdx.input.setInputProcessor(multiplexer);

        //define the pause button and set its click listeners
        pauseButton=new TextButton("Pause",mainSkin);
        pauseButton.setSize(380, 120);
        pauseButton.setPosition(720, 1600);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isGameOver = false;
                isGameRunning = false;
                SavedState s=saveState();
                mainGame.storeState(s);
                mainGame.setScreen(new PauseScreen(mainGame, officialTime.timeStr));

            }
        });

        stage.addActor(pauseButton);

    }

    //Update positions of player, and other objects
    public void update(float delta){

        //check for things such as whether the game is paused outside the main if statement, and control
        //whether the game updates itself using isGameRunning
        if(lives <=0){
            isGameRunning=false;
            isGameOver=true;

        }


        if(isGameOver){

            mainGame.setScreen(new GameOverScreen(officialTime.timeStr,mainGame));
        }

        if(isGameRunning) {

            /*______________UPDATING THE STATE OF THE GAME__________________*/

            //If the player has touched the screen,then get the difference between his previous and current finger
            //position and change the position of the board accordingly.
            if (isFingerDown) {
                int Xdiff = (int) f.Rect.x - f.prevFingerX;
                int newPosX = (int) (boardRect.x + (Xdiff * boardPosMultiplier));

                int Ydiff = (int) f.Rect.y - f.prevFingerY;
                int newPosY = (int) (boardRect.y - (Ydiff * boardPosMultiplier));

                //Update x
                if (newPosX > 0 && newPosX + boardRect.width < 1080) {
                    boardRect.x = newPosX;
                } else if (newPosX <= 0) {
                    boardRect.x = 0;
                } else if (newPosX + boardRect.width >= 1080) {
                    boardRect.x = 1080 - boardRect.width;
                }

                //Update y
                if (newPosY > 0 && newPosY + boardRect.height < 1920) {
                    boardRect.y = newPosY;
                } else if (newPosY <= 0) {
                    boardRect.y = 0;
                } else if (newPosY + boardRect.height >= 1920) {
                    boardRect.y = 1920 - boardRect.height;
                }


            }


            //Get the time something was dropped, if it was 1 second(or more) ago
            //Add a Dopable to the array to be drawn
            lastDroppedTime += Gdx.graphics.getDeltaTime();
            if (lastDroppedTime >= lastDroppedLimit) {
                dropableCode = (int) (Math.random() * 100);
                Dropable d;
                //With this,theoritically,10% of all dropped items should be cut spheres
                if (dropableCode > 10) {
                    d = new Dropable(Dropable.NORMAL);
                } else {
                    d = new Dropable(Dropable.CUT);
                }
                d.setLoc((int) (Math.random() * 960), 1920);
                d.index = droppedArray.size;
                droppedArray.add(d);
                lastDroppedTime = 0;
            }


            //If any objects have reached the end of the screen or, if the board has collided with
            //any of them, clear them out.
            //Else, increase lower their position by 10
            for (int i = 0; i < droppedArray.size - 1; i++) {
                Dropable d = droppedArray.get(i);
                if (d != null) {
                    //check for end of screen
                    if (d.Rect.y <= 0 - d.Rect.height) {
                        droppedArray.removeIndex(i);
                        droppedArray.insert(i, null);

                    }
                    //check for collisions with board
                    //Depending upon the type of sphere that collided, either decrease player's
                    // life or decrease length of board for 30 seconds
                    //WORK IN PROGRESS
                    else if (d.Rect.overlaps(boardRect)) {
                        if (d.TYPE.equals(Dropable.CUT)) {
                            if (boardRect.width == 360) {
                                boardRect.width = 240;
                                isBoardShort = true;
                                shortTime.initTimer();
                            }
                        }
                        else if(d.TYPE.equals(Dropable.NORMAL)){
                            lives--;
                        }
                        droppedArray.removeIndex(i);
                        droppedArray.insert(i, null);

                    }
                    //if neither has happened, increase height by 10
                    else {
                        d.Rect.y -= dropableYIncrement;
                    }
                }
            }

            //If the board has been shortened,check if 30 seconds have elapsed since that happened.
            //If more than 30 seconds have elapsed, lengthen the board again
            if (isBoardShort) {
                if (shortTime.getTimerValMSec(true) >= 15000) {
                    boardRect.width = 360;
                    isBoardShort = false;
                    shortTime = null;
                }
            }

            //if it has been 1 minute or more since the game has started,
            //then increase the difficulty by dropping objects more frequently.
            //Do the same after 3 minutes
            if(officialTime.timeMilli > 30000){
                lastDroppedLimit=0.5f;
            }

            if(officialTime.timeMilli > 60000){
                lastDroppedLimit=0.4f;
            }
            if(officialTime.timeMilli > 90000){
                dropableYIncrement=30;
            }
            if(officialTime.timeMilli > 120000){
                lastDroppedLimit=0.3f;
            }



                //update the game time
            officialTime.timeStr=timer.getTimerValStr();
            officialTime.timeMilli=timer.getTimerValMSec(false);


        }

    }


    //render frame
    @Override
    public void render(float delta) {

        update(delta);

        //clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //draw textures
        batch.begin();
        //draw board
        batch.draw(boardTex, boardRect.x, boardRect.y, boardRect.width, boardRect.height);

        //draw all Dropable objects in the array
        for(int i=0;i<droppedArray.size-1;i++){
            Dropable d=droppedArray.get(i);
            if(d!=null){
                batch.draw(d.Tex,d.Rect.x,d.Rect.y,d.Rect.width,d.Rect.height);

            }

        }
        batch.end();

        //render the lives remaining
        //and the game time
        batch.begin();

        glyphLayout.setText(font, "Lives:" + String.valueOf(lives));
        font.draw(batch, glyphLayout, 1080 - glyphLayout.width, 1920 - glyphLayout.height);
        glyphLayout.setText(font,officialTime.timeStr);
        font.draw(batch,glyphLayout,10,1920-glyphLayout.height);

        batch.end();

        stage.act(delta);
        stage.draw();



    }


    @Override
    public void dispose() {
        //dispose the SpriteBatch
        batch.dispose();
        //dispose the board textures
        boardTex.dispose();
        //dispose all the Dropable textures
        for(int i=0;i<droppedArray.size-1;i++){
            Dropable d= droppedArray.get(i);
            if(d!=null){
            d.Tex.dispose();
            }
        }

        //dispose the scene2D objects
        stage.dispose();
        mainSkin.dispose();

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isFingerDown = true;
        f.setNoDiffLoc(screenX, screenY);
        Gdx.app.log(TAG, "Finger Down: " + String.valueOf(f.Rect.x) + "," + String.valueOf(f.Rect.y));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isFingerDown=false;
        f.setNewLoc(screenX, screenY);
        Gdx.app.log(TAG, "Finger Up: " + String.valueOf(f.Rect.x) + "," + String.valueOf(f.Rect.y));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        f.setNewLoc(screenX,screenY);
        Gdx.app.log(TAG, "Finger Moved: " + String.valueOf(f.Rect.x) + "," + String.valueOf(f.Rect.y));
        return true;
    }


    @Override
    public void pause() {
        Gdx.app.log(TAG,"pause");

    }

    //save the state of the game
    public SavedState saveState(){
        SavedState s = new SavedState();
        s.isFingerDown=isFingerDown;
        s.boardPosMultiplier=boardPosMultiplier;
        s.dropableCode=dropableCode;
        s.dropableYIncrement=dropableYIncrement;
        s.droppedArray=droppedArray;
        s.f=f;
        s.timer=timer;
        s.timeMilli=timeMilli;
        s.lastDroppedLimit=lastDroppedLimit;
        s.lastDroppedTime=lastDroppedTime;
        s.isBoardShort=isBoardShort;
        s.shortTime=shortTime;
        s.lives=lives;
        s.isGameOver=isGameOver;
        s.isGameRunning=isGameRunning;
        s.officialTime=officialTime;
        return s;

    }


    /*CURRENTLY UNUSED METHODS*/

    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }











    /* DESKTOP METHODS THAT I HAVE TO OVERRIDE BUT HAVE NO USE OF*/

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }



}



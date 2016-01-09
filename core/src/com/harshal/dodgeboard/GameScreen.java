package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harshal on 7/1/16.
 * Screen fot the game
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
    private float lastDroppedTime;

    //store an instance of MainGame so that we can change screens from inside this screen
    public GameScreen(MainGame game){
        mainGame=game;

    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        Gdx.app.log(TAG, "create");

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

        //store this class in the Game object so that it can be reused while resuming from a paused game
        mainGame.storeScreen(this);

        //initialize variables
        isFingerDown=false;
        boardPosMultiplier=1.25;
        f=new Finger();


        //class to measure time
        timer=new TimeKeeper();
        timer.initTimer();

        Gdx.input.setInputProcessor(this);

        //initialize the array
        droppedArray =new Array<Dropable>();

        timeMilli= timer.updateTime();

        lastDroppedTime=0;


    }

    @Override
    public void render(float delta) {


        //clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        /*______________UPDATING THE STATE OF THE GAME__________________*/

        //If the player has touched the screen,then get the difference between his previous and current finger
        //position and change the position of the board accordingly.
        if(isFingerDown){
            int Xdiff=(int)f.Rect.x-f.prevFingerX;
            int newPosX= (int)(boardRect.x+(Xdiff*boardPosMultiplier));

            int Ydiff=(int)f.Rect.y-f.prevFingerY;
            int newPosY= (int)(boardRect.y-(Ydiff*boardPosMultiplier));

            //Update x
            if(newPosX>0 && newPosX+boardRect.width<1080){
                boardRect.x=newPosX;
            }
            else if(newPosX<=0){
                boardRect.x=0;
            }
            else if(newPosX+boardRect.width>=1080){
                boardRect.x=1080-boardRect.width;
            }

            //Update y
            if(newPosY>0 && newPosY+boardRect.height<1920){
                boardRect.y=newPosY;
            }
            else if(newPosY<=0){
                boardRect.y=0;
            }
            else if(newPosY+boardRect.height>=1920){
                boardRect.y=1920-boardRect.height;
            }


        }

        //increase width of board every 30 secs
        if((timer.updateTime()-timeMilli)/1000 >= 30 ){
            boardRect.width+=54;
            timeMilli=timer.updateTime();
        }


        //Change teture of board with increase in width so that it doesn't look weirdly stretched.
        if(boardRect.width>=900){
            //boardRect.height=90;
            boardTex=new Texture(Gdx.files.internal("board900.png"));
        }
        else if(boardRect.width>=720){
            //boardRect.height=80;
            boardTex=new Texture(Gdx.files.internal("board720.png"));
        }
        else if(boardRect.width>=540){
            //boardRect.height=70;
            boardTex=new Texture(Gdx.files.internal("board540.png"));
        }


        //Get the time something was dropped, if it was 1 second(or more) ago
        //Add a Dopable to the array to be drawn
        lastDroppedTime+=Gdx.graphics.getDeltaTime();
        if(lastDroppedTime>=1){
            Dropable d=new Dropable("dropable.png");
            d.setLoc((int)(Math.random()*960),1920);
            d.index=droppedArray.size;
            droppedArray.add(d);
            lastDroppedTime=0;
        }



        //If any objects have reached the end of the screen or, if the board has collided with
        //any of them, clear them out.
        //Else, increase lower their position by 10
        for(int i=0;i<droppedArray.size-1;i++){
            Dropable d= droppedArray.get(i);
            if(d!=null) {
                //check for end of screen
                if (d.Rect.y <= 0) {
                    droppedArray.removeIndex(i);
                    droppedArray.insert(i, null);

                }
                //check for collisions with board
                else if(d.Rect.overlaps(boardRect)){
                    droppedArray.removeIndex(i);
                    droppedArray.insert(i, null);
                }
                //if neither has happened, increase height by 10
                else {
                    d.Rect.y -= 10;
                }
            }
        }




        /*________________________RENDERING TO SCREEN__________________________*/

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



    }


    @Override
    public void pause() {
        Gdx.app.log(TAG,"pause");

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

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isFingerDown = true;
        f.setNoDiffLoc(screenX,screenY);
        Gdx.app.log(TAG, "Finger Down: " + String.valueOf(f.Rect.x) + "," + String.valueOf(f.Rect.y));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isFingerDown=false;
        f.setNewLoc(screenX,screenY);
        Gdx.app.log(TAG, "Finger Up: " + String.valueOf(f.Rect.x) + "," + String.valueOf(f.Rect.y));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        f.setNewLoc(screenX,screenY);
        Gdx.app.log(TAG, "Finger Moved: " + String.valueOf(f.Rect.x) + "," + String.valueOf(f.Rect.y));
        return true;
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



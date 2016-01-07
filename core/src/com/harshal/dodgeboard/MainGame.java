package com.harshal.dodgeboard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;




public class MainGame extends ApplicationAdapter implements InputProcessor{

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
    private int fingerPosX,fingerPosY,prevFingerX,prevFingerY;
    //multiplier that is used to adjust relation between movement of board and movement of finger
    private double boardPosMultiplier;
    //Timekeeper object to track time
    private TimeKeeper timer;
    //time that is used to change length of board
    private long timeMilli;




    @Override
	public void create () {
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

        //initialize variables
        isFingerDown=false;
        fingerPosX=0;
        fingerPosY=0;
        boardPosMultiplier=1;


        //class to measure time
        timer=new TimeKeeper();
        timer.initTimer();

        timeMilli= timer.updateTime();

        Gdx.input.setInputProcessor(this);



    }

	@Override
    public void render () {
        //clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //If the player has touched the screen,then get the difference between his previous and current finger
        //position and change the position of the board accordingly.
        if(isFingerDown){
            int Xdiff=fingerPosX-prevFingerX;
            int newPosX= (int)(boardRect.x+(Xdiff*boardPosMultiplier));

            int Ydiff=fingerPosY-prevFingerY;
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


        //increase height of board by a bit with increase in width so that it doesn't look weirdly stretched.
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




        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //draw textures
        batch.begin();
        batch.draw(boardTex, boardRect.x, boardRect.y, boardRect.width, boardRect.height);

        batch.end();


    }





    @Override
    public void dispose() {

        batch.dispose();
        boardTex.dispose();
        super.dispose();
    }



    //if the finger is down,clear out previous touch co-ordinates(so that the board doesn't sporadically move around the screen)
    //and save new co-ordinates
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        isFingerDown = true;
        prevFingerX = screenX;
        prevFingerY = screenY;
        fingerPosX = screenX;
        fingerPosY = screenY;
        Gdx.app.log(TAG, "Finger Down: " + String.valueOf(fingerPosX) + "," + String.valueOf(fingerPosY));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isFingerDown=false;
        prevFingerX=fingerPosX;
        prevFingerY=fingerPosY;
        fingerPosX=screenX;
        fingerPosY=screenY;
        Gdx.app.log(TAG,"Finger Up: "+String.valueOf(fingerPosX)+","+String.valueOf(fingerPosY));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        prevFingerX=fingerPosX;
        prevFingerY=fingerPosY;
        fingerPosX=screenX;
        fingerPosY=screenY;
        Gdx.app.log(TAG,"Finger Moved: "+String.valueOf(fingerPosX)+","+String.valueOf(fingerPosY));
        return true;
    }

     @Override
     public void pause() {
         Gdx.app.log(TAG,"pause");
         super.pause();
     }






















    /*METHODS FOR DESKTOP THAT I HAVE TO OVERRIDE BUT ARE OF NO USE*/

     @Override
     public boolean scrolled(int amount) {
         return false;
     }

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

 }

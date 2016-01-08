package com.harshal.dodgeboard;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by harshal on 8/1/16.
 * Class for storing data about touch inputs.
 *Uses the Rectangle class for making touch events very easy.
 */
public class Finger {
    //Rectangle class to store location of finger
    protected Rectangle Rect;
    //integers to store previous location of finger
    //used for calculating movement of board.
    protected int prevFingerX,prevFingerY;

    //initiate a new Rectangle instance with dimensions of 1x1
    //This object can be used for collision detection,making handling touch events very easy
    public Finger() {
        Rect=new Rectangle();
        Rect.width=Rect.height=1;
        prevFingerX=prevFingerY=0;
    }

    //set new location, and store previous location
    public void setNewLoc(int X,int Y ){
        prevFingerX=(int)Rect.x;
        prevFingerY=(int)Rect.y;
        Rect.x=X;
        Rect.y=Y;

    }

    //set new and previous locations as the same.
    //used when finger is put down
    public void setNoDiffLoc(int X,int Y){
        prevFingerX=X;
        prevFingerY=Y;
        Rect.x=X;
        Rect.y=Y;

    }
}

package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by harshal on 9/1/16.
 * Class for all the objects that will be dropped
 * Will be expanded for including different types of objects
 */
public class Dropable {
    //Texture
    protected Texture Tex;
    //Rectange to hold co-ordinates and dimensions
    //Makes collision detection with board very easy
    protected Rectangle Rect;
    //Index of object in the Dropable array
    //Used while clearing  out objects
    protected int index;

    protected String TYPE;

    public static final String NORMAL="dropable.png";
    public static final String CUT="dropable_cut.png";


    public Dropable(String FileName) {
        //Load texture and store dimensions in the Rectangle
        Tex = new Texture(Gdx.files.internal(FileName));
        TYPE=FileName;
        Rect=new Rectangle();
        Rect.width=Tex.getWidth();
        Rect.height=Tex.getHeight();
    }

    public void setLoc(int X,int Y){
        //Set the location of the object
        Rect.x=X;
        Rect.y=Y;
    }


}

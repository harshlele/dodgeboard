package com.harshal.dodgeboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by harshal on 8/1/16.
 * Class to handle button in the menu screen and other various places
 * Uses Rectangle class to make touch events easier
 */
public class Button {
    //Texture and button
    protected Texture Tex;
    protected Rectangle Rect;

    //Load the texture from FileName
    public Button(String FileName) {
        Rect =new Rectangle();
        Tex =new Texture(Gdx.files.internal(FileName));

    }

    //set co-ordinates and dimensions
    public void setInfo(int X, int Y,int width,int height){
        Rect.x=X;
        Rect.y=Y;
        Rect.width=width;
        Rect.height=height;
    }




}

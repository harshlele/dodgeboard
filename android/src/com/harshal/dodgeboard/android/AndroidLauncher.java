package com.harshal.dodgeboard.android;

import android.os.Bundle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.harshal.dodgeboard.GameScreen;
import com.harshal.dodgeboard.MainGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer=false;
        config.useCompass=false;
		initialize(new Game() {

			//Game object to control the game

			//variable to store the screen that can be used to resume the game
			private GameScreen storedScreen;

			@Override
			public void create() {
				setScreen(new GameScreen(this));
			}

			public void storeGameScreen(GameScreen screen){
				storedScreen=screen;
			}


		}, config);
	}
}

package com.harshal.dodgeboard.android;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.harshal.dodgeboard.AdHandler;
import com.harshal.dodgeboard.MainGame;

public class AndroidLauncher extends AndroidApplication implements AdHandler {

	/*ALL CODE RELATED TO ADS HAS BEEN COMMENTED OUT, WILL ONLY BE USED FOR TESTING*/

	//adview
	//protected AdView adView;

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//disable compass and accelerometer
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer=false;
        config.useCompass=false;
		//get display dimensions
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		//Relative layout that will have both the ads and the game
		RelativeLayout layout=new RelativeLayout(this);
		//set the game View to fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		//return an instance of the View that will be used in the Relative Layout
		View gameView=initializeForView(new MainGame(width,height,this),config);


		//Advertisement code, runs in release version
		/*
		//initialize the adview and set the type to SMART_BANNER(scales to screen size)
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		//set test device by passing in the device id
		final TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		String deviceid = tm.getDeviceId();

		AdRequest request=new AdRequest.Builder().addTestDevice(deviceid).build();
		//this unit ID is ONLY THERE FOR TESTING
		adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		//adView.loadAd(request);
		*/


		//add the ad and game to Relative Layout and show the relative layout
		layout.addView(gameView);

		//Advertisement code, runs in release version
		/*
		RelativeLayout.LayoutParams adParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.addView(adView,adParams);
		*/

		setContentView(layout);




	}

	//Handler instance to respond to messages from the game thread
	protected Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case SHOW_ADS:
				{
					//adView.setVisibility(View.VISIBLE);
					break;
				}
				case HIDE_ADS:
				{
					//adView.setVisibility(View.GONE);
					break;
				}
			}
		}
	};


	//overridden method that sets ad visiiblity
	@Override
	public void setAdVis(boolean vis) {
		handler.sendEmptyMessage(vis ? SHOW_ADS : HIDE_ADS);
	}
}

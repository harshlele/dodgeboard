package com.harshal.dodgeboard.android;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.harshal.dodgeboard.GameScreen;
import com.harshal.dodgeboard.MainGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer=false;
        config.useCompass=false;

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		RelativeLayout layout=new RelativeLayout(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		View gameView=initializeForView(new MainGame(width,height),config);

		AdView adView=new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);

		//AdRequest request=new AdRequest.Builder().addTestDevice(deviceid).build();

		AdRequest request=new AdRequest.Builder().build();
		//adView.loadAd(request);

		layout.addView(gameView);
		RelativeLayout.LayoutParams adParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.addView(adView,adParams);
		setContentView(layout);


		//initialize(new MainGame(width,height), config);
	}
}

package com.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity{
	int SPLASH_TIME=100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		new Handler().postDelayed(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				overridePendingTransition(0, android.R.anim.fade_in);
				startActivity(new Intent(Splash.this, SampleActivity.class));
				finish();
			}
			
		},SPLASH_TIME);
		
	
	}
}

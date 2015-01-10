package com.example.maplefreemarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import com.code.freeMarket.R;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class LoadingScreenActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent i = new Intent(this, HomeActivity.class);
		setContentView(R.layout.loading_screen);
		AdBuddiz.setPublisherKey(getResources().getString(R.string.adBuddizPublishKey));
	    AdBuddiz.cacheAds(this); // this = current Activity
	    if (AdBuddiz.isReadyToShowAd(this)) { // this = current Activity
	    	AdBuddiz.showAd(this);
	     }
		CountDownTimer timer = new CountDownTimer(3000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {
				startActivity(i);
				AdBuddiz.onDestroy();
				finish();
			}

		};
		timer.start();
	}

}

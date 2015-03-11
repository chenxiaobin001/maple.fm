package com.example.articlesManagement;

import com.code.freeMarket.R;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SellerAndShopActivity extends ActionBarActivity {

	private Button backButton;
	private ViewPager mViewPager;
//	private CharacterInfo charInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.seller_and_shop);
		Intent myIntent = getIntent(); // gets the previously created intent

		setupActionBarTab();
		backButton = (Button) findViewById(R.id.ssBackButton);
		backButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
	private void setupActionBarTab(){
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction arg1) {
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
	    };

	    // Add 3 tabs, specifying the tab's text and TabListener
	    actionBar.addTab(actionBar.newTab().setText("Shop").setTabListener(tabListener));
	    actionBar.addTab(actionBar.newTab().setText("Character").setTabListener(tabListener));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}

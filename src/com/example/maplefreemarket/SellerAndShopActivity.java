package com.example.maplefreemarket;

import java.util.ArrayList;
import java.util.List;

import com.code.freeMarket.R;
import com.example.maplefreemarket.SellerInfoFragment.OnImageLoadedListener;
import com.example.maplefreemarket.ShopItemsFragment.OnItemsLoadedListener;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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

public class SellerAndShopActivity extends ActionBarActivity implements MyDialogFragmentListener, OnImageLoadedListener, OnItemsLoadedListener{

	private String characterName;
	private String shopName;
	private Button backButton;
	private MyAdapter fragmentAdapter;
	private ViewPager mViewPager;
	final private int fragmentNum = 2;
//	private CharacterInfo charInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.seller_and_shop);
		Intent myIntent = getIntent(); // gets the previously created intent
		characterName = myIntent.getStringExtra("charName"); 
		shopName = myIntent.getStringExtra("shopName");
		fragmentAdapter = new MyAdapter(getSupportFragmentManager(), characterName);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(fragmentAdapter);
		mViewPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @SuppressWarnings("deprecation")
					@Override
	                public void onPageSelected(int position) {
	                    // When swiping between pages, select the
	                    // corresponding tab.
	                    getActionBar().setSelectedNavigationItem(position);
	                }
	            });
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
	
	class MyAdapter extends FragmentStatePagerAdapter {

		private List<Fragment> fragments;

		public MyAdapter(FragmentManager fm, String charName) {
			super(fm);
			this.fragments = new ArrayList<Fragment>();
			fragments.add(new ShopItemsFragment());
	//		fragments.add(new SellerInfoFragment());
			fragments.add(new SellerInfoFragment());
			for (int i = 0; i < fragments.size(); i++) {
				Fragment f = fragments.get(i);
				Bundle bundle = new Bundle();
				bundle.putString("characterName", charName);
				bundle.putString("shopName", shopName);
				f.setArguments(bundle);
			}
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);

		}

		@Override
		public int getCount() {
			return fragmentNum;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String title = new String();
			switch (position) {
			case 0: {
				title = "Shop";
				break;
			}
			case 1: {
				title = "Character";
				break;
			}
			}
			return title;
		}

	}

	@Override
	public void onReturnValue(String foo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onImageLoaded(Bitmap bitmap, boolean isSeller) {
		
		if (!isSeller)	return;
		ShopItemsFragment fragment = (ShopItemsFragment)
				fragmentAdapter.getItem(0);
		fragment.setSellerImage(bitmap);
	}


	@Override
	public void onItemsLoaded() {
		
		SellerInfoFragment fragment = (SellerInfoFragment)
				fragmentAdapter.getItem(1);
		fragment.getSellerInfo();
	}
	
}

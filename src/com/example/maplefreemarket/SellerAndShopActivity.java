package com.example.maplefreemarket;

import java.util.ArrayList;
import java.util.List;

import com.code.freeMarket.R;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SellerAndShopActivity extends ActionBarActivity {

	private String characterName;
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
		fragmentAdapter = new MyAdapter(getSupportFragmentManager(), characterName);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(fragmentAdapter);
		
		
		
		backButton = (Button) findViewById(R.id.ssBackButton);
		backButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
			fragments.add(new SellerInfoFragment());
			fragments.add(new SellerInfoFragment());
			for (int i = 0; i < fragments.size(); i++) {
				Fragment f = fragments.get(i);
				Bundle bundle = new Bundle();
				bundle.putString("characterName", charName);
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
	
}

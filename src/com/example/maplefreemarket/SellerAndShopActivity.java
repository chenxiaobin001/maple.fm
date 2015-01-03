package com.example.maplefreemarket;

import java.util.ArrayList;

import com.code.freeMarket.R;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
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

	private Button backButton;
	private ListView listView;
	private MapleFreeMarketApplication myApp;
	private HandleSellerAndShopJSON obj;
	private ItemArrayAdapter adapter;
	private String characterName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent myIntent = getIntent(); // gets the previously created intent
		characterName = myIntent.getStringExtra("charName"); 
		
		myApp = (MapleFreeMarketApplication) this.getApplication();
		setContentView(R.layout.seller_and_shop);
		listView = (ListView) findViewById(R.id.itemInShopListView);
		adapter = new ItemArrayAdapter(SellerAndShopActivity.this, new ArrayList<Item>());
		myApp.setItemAdapter(adapter);
		listView.setAdapter(adapter);
		backButton = (Button) findViewById(R.id.ssBackButton);
		setListView();
		
		backButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Item item = (Item) adapter.getAdapter().getItem(position);
				myApp.setDrawable(item.getDrawableImage());
				ItemDetailDialog dialog = ItemDetailDialog.newInstance(item.getJSONString());
				if (dialog == null)	return;
				FragmentManager fm = getFragmentManager();
				dialog.show(fm, "language");
			}

		});
	}
	
	private void setListView(){
		myApp.getItemAdapter().getFilter().filter(characterName);
	}
	
	private void fetchSellerData(){
		
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

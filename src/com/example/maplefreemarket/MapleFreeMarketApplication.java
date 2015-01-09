package com.example.maplefreemarket;

import android.app.Application;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MapleFreeMarketApplication extends Application{
	private int server;
	private BitmapDrawable drawable;
	private ItemArrayAdapter itemAdapter;
	private FMItem selectedItem;
	
    public FMItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(FMItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public ItemArrayAdapter getItemAdapter() {
		return itemAdapter;
	}

	public void setItemAdapter(ItemArrayAdapter itemAdapter) {
		this.itemAdapter = itemAdapter;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(BitmapDrawable drawable) {
		this.drawable = drawable;
	}

	public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
      /*  Parse.enableLocalDatastore(this); 
        ParseObject.registerSubclass(WorkoutDataStore.class);
        
        Parse.initialize(this, getString(R.string.app_id), getString(R.string.client_id));
        ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
 
        ParseACL.setDefaultACL(defaultACL, true);*/
    }
}

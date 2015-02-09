package com.example.maplefreemarket;

import java.util.HashMap;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class MapleFreeMarketApplication extends Application{
	private int server;
	private BitmapDrawable drawable;
	private ItemArrayAdapter itemAdapter;
	private HashMap<String, List<FMItem>> shops;
	private FMItem selectedItem;
	private AsyncTask<String, Void, String> preTask;
	public static final String PREFS_NAME = "MyPrefsFile";
	
	private static Context mContext;

    public static Context getContext() {
        return mContext;
    }
	
    public HashMap<String, List<FMItem>> getShops() {
		return shops;
	}

	public void setShops(HashMap<String, List<FMItem>> shops) {
		this.shops = shops;
	}

	public FMItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(FMItem selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public AsyncTask<String, Void, String> getPreTask() {
		return preTask;
	}

	public void setPreTask(AsyncTask<String, Void, String> preTask) {
		this.preTask = preTask;
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
    
    public void saveServerConfiguration(int server){
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putInt("Server", server);
    	editor.commit();
    }
    
    public int getServerConfiguration(){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	return settings.getInt("Server", 0);
    }
    
    public void saveSortConfiguration(int sort){
    	
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putInt("Sort", sort);
    	editor.commit();
    }
    
    public int getSortConfiguration(){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	return settings.getInt("Sort", 0);
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
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

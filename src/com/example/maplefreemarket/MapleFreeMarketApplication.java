package com.example.maplefreemarket;

import android.app.Application;

public class MapleFreeMarketApplication extends Application{
	private String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
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

package com.example.acountManagement;

import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.maplefreemarket.MapleFreeMarketApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class AccessAcountSettings {
	private static final String PREFS_NAME = "MyPrefsAccountFile";
	private Context mContext;
	//{"id":12,"email":"1231#@gmail.com","created_at":"2015-03-10T00:02:40.149Z","updated_at":"2015-03-10T00:02:40.197Z","name":"123123","role":"user","device_token":"","server":0,"authentication_token":"X1NfoFbm2sqtC8Krt2jq"}

	private static AccessAcountSettings instance = null;
    private AccessAcountSettings(Context mContext) { 
    	this.mContext = mContext;
    }
 
    public boolean isSignIn() {
    	SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	String email = settings.getString("Email", null);
    	String authToken = settings.getString("AuthToken", null);
    	return (email != null && authToken != null);
    }
    
    public static synchronized AccessAcountSettings getInstance() {
        if (instance == null) {
        	MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) MapleFreeMarketApplication.getContext();
        	if (myApp == null)	return null;
            instance = new AccessAcountSettings(myApp);
            myApp = null;
        }
        return instance;
    }
	
	
	public void SaveAccountInformation(String accountInfoJSON) {
		JSONObject jObject;
		try {
			jObject = new JSONObject(accountInfoJSON);
			setAccountName(jObject.optString("name"));	
			setAccountEmail(jObject.optString("email"));
			setAccountID(jObject.optInt("id"));
			setAccountServer(jObject.optInt("id"));
			setAccountDeviceToken(jObject.optString("device_token"));
			setAccountAuthToken(jObject.optString("authentication_token"));
		} catch (JSONException e) {
		//	e.printStackTrace();
		}
			
    }
	
	public String getAccountAuthToken() {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	return settings.getString("AuthToken", null);
    }
	
	public String getAccountDeviceToken() {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	return settings.getString("DeviceToken", null);
    }
	
	
	public int getAccountServer() {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	return settings.getInt("Server", 0);
    }
	
	public int getAccountID() {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	return settings.getInt("ID", -1);
    }
	
	public String getAccountEmail() {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	return settings.getString("Email", null);
    }
	
	public String getAccountName() {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	return settings.getString("Name", null);
    }
	
	
	public void setAccountAuthToken(String value) {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("AuthToken", value);
    	editor.commit();
	}
	
	public void setAccountDeviceToken(String value) {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("DeviceToken", value);
    	editor.commit();
	}
	
	public void setAccountServer(int value) {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putInt("Server", value);
    	editor.commit();
	}
	
	
	public void setAccountID(int value) {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putInt("ID", value);
    	editor.commit();
	}
	
	public void setAccountEmail(String value) {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("Email", value);
    	editor.commit();
	}
	
	public void setAccountName(String value) {
		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putString("Name", value);
    	editor.commit();
	}
	
	public void setAccountImage(Bitmap bitmap) {
		FileOutputStream out = null;
		try { 
		    out = new FileOutputStream("characterImage");
		    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
		    // PNG is a lossless format, the compression factor (100) is ignored 
		} catch (Exception e) {
		    e.printStackTrace();
		} finally { 
		    try { 
		        if (out != null) {
		            out.close();
		        } 
		    } catch (IOException e) {
		        e.printStackTrace();
		    } 
		} 
	}
	public void clearAccountInfo() {
		setAccountName(null);	
		setAccountEmail(null);
		setAccountID(-1);
		setAccountServer(0);
		setAccountDeviceToken(null);
		setAccountAuthToken(null);
	}

	
}

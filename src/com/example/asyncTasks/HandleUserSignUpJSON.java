package com.example.asyncTasks;


import org.json.JSONException;
import org.json.JSONObject;

import com.example.acountManagement.AccessAcountSettings;
import com.example.maplefreemarket.MapleFreeMarketApplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class HandleUserSignUpJSON extends AsyncTask<String, Void, String> {

	

	public HandleUserSignUpJSON(Context context) {

	}
	
	private String handleJson(String[] strs) throws JSONException{
		JSONObject jObject = new JSONObject(strs[0]);
		if (jObject.has("errors")) {
			return jObject.getJSONObject("errors").toString();
		} else {
			AccessAcountSettings account = AccessAcountSettings.getInstance();
			account.SaveAccountInformation(strs[0]);
			return "Successfully signed up!";
		}
	}

	@Override
	protected String doInBackground(String... JSonString) {
		try {

			return handleJson(JSonString);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) MapleFreeMarketApplication.getContext();
		Toast.makeText(myApp, result, Toast.LENGTH_SHORT).show();
		myApp = null;
    }
}

package com.example.asyncTasks;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.maplefreemarket.MapleFreeMarketApplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class HandleNotificationJSON extends AsyncTask<String, Void, String> {
	
	String id;
	String date;
	String content;
	Context context;
	int mode;
	public HandleNotificationJSON(Context context, int mode) {
		this.mode = mode;
		this.context = context;
	}
	private void handleJson(String[] strs) throws JSONException{
		JSONObject jObject = new JSONObject(strs[0]);
		id = jObject.getString("id");
		date = jObject.getString("date");
		content = jObject.getString("content");
	}
	@Override
	protected String doInBackground(String... JSonString) {
		try {
			handleJson(JSonString);
			return "OK";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (result == null)	return;
		if (mode == 0 && !"2025mapleNotify".equals(id))	return;
		if (!"2025mapleNotify".equals(id)) {
			((MapleFreeMarketApplication) MapleFreeMarketApplication.getContext()).init = 1;
		}
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("Notification"); 
		alertDialog.setMessage(date + "\n\n" + content + "\n\n" + "Happy mapling!"); 
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", 
		    new DialogInterface.OnClickListener() { 
		        public void onClick(DialogInterface dialog, int which) {
		            dialog.dismiss();
		        } 
		    }); 
		alertDialog.show(); 
		context = null;
    }
	
	

}

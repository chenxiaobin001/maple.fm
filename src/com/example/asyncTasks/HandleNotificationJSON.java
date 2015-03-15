package com.example.asyncTasks;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.maplefreemarket.MapleFreeMarketApplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.TextView;

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
	protected void onPostExecute(String result) {		//mode - 0 show as needed, 1 must show this time
		if (result == null)	return;
		if ("2024mapleNotify".equals(id)) {
			int tmp = ((MapleFreeMarketApplication) MapleFreeMarketApplication.getContext()).init;
			((MapleFreeMarketApplication) MapleFreeMarketApplication.getContext()).init = 0;	//back to normal
			if (mode == 0 || tmp == -1)	return;		//2024 quiet mode
		}else if ("2025mapleNotify".equals(id)) {	//2025
			((MapleFreeMarketApplication) MapleFreeMarketApplication.getContext()).init = 1;	//set to emergency
		} else {		//2026 show once at start up
			((MapleFreeMarketApplication) MapleFreeMarketApplication.getContext()).init = 2;	//set to emergency
			if (mode == 0)	return;		//2026 quiet mode
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
		TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(16);
		context = null;
    }
	
	

}

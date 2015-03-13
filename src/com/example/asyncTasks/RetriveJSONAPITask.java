package com.example.asyncTasks;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.code.freeMarket.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class RetriveJSONAPITask extends AsyncTask<String, Void, String> {

    private OkHttpClient client;
    private Context mContext;
    private AsyncTask<String, Void, String> parseJSONAsyncTask;
    private String ret;
    private int type;
    private String recourceID;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8"); 
    public RetriveJSONAPITask (Context context, AsyncTask<String, Void, String> asyncTask, int type){
         mContext = context;
         client = new OkHttpClient();
         this.parseJSONAsyncTask = asyncTask;
         this.type = type;
    }
    protected String doInBackground(String... contents) {
    	ret = null;
        try {
        	recourceID = contents[0];
        	String serverUrl = getRequestURL(type);
        	if ("".equals(serverUrl))	return null;
        	Request request = new Request.Builder() 
            .url(serverUrl) 
  //          .header("User-Agent", "OkHttp Headers.java") 
  //          .addHeader("Accept", "application/json; q=0.5") 
  //          .addHeader("Accept", "application/vnd.github.v3+json") 
            .build(); 
        	
		    Response response = client.newCall(request).execute();
		    ret = response.body().string();
		    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		 		    
    	    return ret;
        } catch (IOException e) {
        //    e.printStackTrace();
        	return ret;
        }
    }

    protected void onPostExecute(String result) {

    	if (result == null)
    		Toast.makeText(mContext, "Failed to get data, please check your network.",  Toast.LENGTH_SHORT).show();
    	if (parseJSONAsyncTask != null && parseJSONAsyncTask.isCancelled()){
    		return;
    	}
    	mContext = null;
    	if (parseJSONAsyncTask != null)
    		parseJSONAsyncTask.execute(result);
    }
    
    private String getRequestURL(int type) {
    	String ret = "";
    	switch(type) {
    	case 0: {	//create new user
    		String serverUrl = mContext.getResources().getString(R.string.myServerUrl);
        	serverUrl += "users.json";
        	ret = serverUrl;
        	break;
    	}
    	case 1: {	//get notification
    		String serverUrl = mContext.getResources().getString(R.string.myServerUrl);
    		String notificationURL = serverUrl + "notification/index.json";
    		ret = notificationURL;
    		break;
    	}
    	case 2: {	//get articles
    		String serverUrl = mContext.getResources().getString(R.string.myServerUrl);
    		String notificationURL = serverUrl + "articles.json";
    		ret = notificationURL;
    		break;
    	}
    	case 3: {	//get comments
    		String serverUrl = mContext.getResources().getString(R.string.myServerUrl);
    		String notificationURL = serverUrl + "articles/" + recourceID + "/comments.json";
    		ret = notificationURL;
    		break;
    	}
    	}
    	return ret;
    }

}
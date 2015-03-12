package com.example.asyncTasks;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.code.freeMarket.R;
import com.example.acountManagement.AccessAcountSettings;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class PostJSONAPITask extends AsyncTask<String, Void, String> {

    private OkHttpClient client;
    private Context mContext;
    private AsyncTask<String, Void, String> parseJSONAsyncTask;
    private String ret;
    private int type;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8"); 
    public PostJSONAPITask (Context context, AsyncTask<String, Void, String> asyncTask, int type){
         mContext = context;
         client = new OkHttpClient();
         this.parseJSONAsyncTask = asyncTask;
         this.type = type;
    }
    protected String doInBackground(String... contents) {
    	ret = null;
        try {
        	AccessAcountSettings account = AccessAcountSettings.getInstance();
        	RequestBody body = RequestBody.create(JSON, contents[0]);
        	String serverUrl = getRequestURL(type);
        	Builder request = new Request.Builder() 
            .url(serverUrl) 
  //          .header("User-Agent", "OkHttp Headers.java")  
            .post(body);
        	if (type == 2) {
        		String auth = account.getAccountAuthToken();
        		String email = account.getAccountEmail();
        		if (auth == null || email == null) return "login";
        		request.addHeader("X-User-Token", auth) 
                .addHeader("X-User-Email", email); 
        	}
        	
		    Response response = client.newCall(request.build()).execute();
	//        		    System.out.println(response.body().string());
		    ret = response.body().string();
		    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		 
		    System.out.println(response.body().string());
		    
    	    return ret;
        } catch (Exception e) {
            e.printStackTrace();
        	return ret;
        }
    }

    protected void onPostExecute(String result) {
    //	HandleItemListJSON obj = ((HomeActivity)mContext).getObj();
  	//	obj = new HandleItemListJSON(mContext);
    	if ("login".equals(result)) {
    		Toast.makeText(mContext, "Log in or sign up first.",  Toast.LENGTH_SHORT).show();
    	}
    	if (result == null)
    		Toast.makeText(mContext, "Failed to get data, please check your network.",  Toast.LENGTH_SHORT).show();
//		Toast.makeText(mContext, "processing", Toast.LENGTH_SHORT).show();
    	if (parseJSONAsyncTask != null && parseJSONAsyncTask.isCancelled()){
    		return;
    	}
  //  	Toast.makeText(mContext, result,  Toast.LENGTH_SHORT).show();
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
    	case 1: {	//sign in
    		String serverUrl = mContext.getResources().getString(R.string.myServerUrl);
        	serverUrl += "users/sign_in.json";
        	ret = serverUrl;
        	break;
    	}
    	case 2: {	//post article
    		String serverUrl = mContext.getResources().getString(R.string.myServerUrl);
        	serverUrl += "articles.json";
        	ret = serverUrl;
        	break;
    	}
    	}
    	return ret;
    }

}
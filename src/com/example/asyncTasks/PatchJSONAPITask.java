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
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request.Builder;

public class PatchJSONAPITask extends AsyncTask<String, Void, String> {

    private OkHttpClient client;
    private Context mContext;
    private AsyncTask<String, Void, String> parseJSONAsyncTask;
    private String ret;
    private int type;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8"); 
    public PatchJSONAPITask (Context context, AsyncTask<String, Void, String> asyncTask, int type){
         mContext = context;
         client = new OkHttpClient();
         this.parseJSONAsyncTask = asyncTask;
         this.type = type;
    }
    protected String doInBackground(String... contents) {		//pass type and resource id
    	ret = null;
        try {
        	AccessAcountSettings account = AccessAcountSettings.getInstance();
        	RequestBody body = RequestBody.create(JSON, contents[0]);
        	String serverUrl = getRequestURL(type, contents[1]);

        	Builder request = new Request.Builder() 
            .url(serverUrl) 
            .patch(body);

          	if (true) {
        		String auth = account.getAccountAuthToken();
        		String email = account.getAccountEmail();
        		if (auth == null || email == null) return "login";
        		request.addHeader("X-User-Token", auth) 
                .addHeader("X-User-Email", email); 
        	}
		    Response response = client.newCall(request.build()).execute();
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
    
    private String getRequestURL(int type, String id) {
    	String ret = "";
    	switch(type) {
    	case 0: {	//edit post
    		String serverUrl = mContext.getResources().getString(R.string.myServerUrl);
        	serverUrl += "articles/" + id +".json";
        	ret = serverUrl;
    	}
    	}
    	return ret;
    }

}
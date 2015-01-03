package com.example.maplefreemarket;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class RetrieveJSonTask extends AsyncTask<String, Void, String> {

    private OkHttpClient client;
    private Context mContext;
    private AsyncTask<String, Void, String> parseJSONAsyncTask;
    
    public RetrieveJSonTask (Context context, AsyncTask<String, Void, String> asyncTask){
         mContext = context;
         client = new OkHttpClient();
         this.parseJSONAsyncTask = asyncTask;
    }
    protected String doInBackground(String... urls) {
        try {

          Request request = new Request.Builder()
   	      .url(urls[0])
   	      .build();
          Response response = client.newCall(request).execute();
    	  return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String result) {
    //	HandleItemListJSON obj = ((HomeActivity)mContext).getObj();
  	//	obj = new HandleItemListJSON(mContext);
		parseJSONAsyncTask.execute(result);
    }
    

}
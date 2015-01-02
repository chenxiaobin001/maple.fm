package com.example.maplefreemarket;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class RetrieveJSonTask extends AsyncTask<String, Void, String> {

    private Exception exception;
    private OkHttpClient client;
    private Context mContext;
    private AsyncTask<String, Void, String> asyncTask;
    
    public RetrieveJSonTask (Context context, AsyncTask<String, Void, String> asyncTask){
         mContext = context;
         client = new OkHttpClient();
         this.asyncTask = asyncTask;
    }
    protected String doInBackground(String... urls) {
        try {

          Request request = new Request.Builder()
   	      .url(urls[0])
   	      .build();
          Response response = client.newCall(request).execute();
    	  return response.body().string();
        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String result) {
    //	HandleItemListJSON obj = ((HomeActivity)mContext).getObj();
  	//	obj = new HandleItemListJSON(mContext);
		asyncTask.execute(result);
    }
    

}
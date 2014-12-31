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
    
    public RetrieveJSonTask (Context context){
         mContext = context;
         client = new OkHttpClient();
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
    	try {
			
    		HandleItemJson obj = ((HomeActivity)mContext).getObj();
    		obj = new HandleItemJson(mContext);
    		obj.execute(result);
			
		} catch (JSONException e) {
			Toast.makeText(mContext, "please check your Internet connection", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
    }
    

}
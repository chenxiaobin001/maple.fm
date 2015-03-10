package com.example.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class RetriveJSONTask extends AsyncTask<String, Void, String> {

    private OkHttpClient client;
    private Context mContext;
    private AsyncTask<String, Void, String> parseJSONAsyncTask;
    
    public RetriveJSONTask (Context context, AsyncTask<String, Void, String> asyncTask){
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
    	if (result == null) {
    		Toast.makeText(mContext, "Failed to get data, please check your network.",  Toast.LENGTH_SHORT).show();
    		return;
    	}
    	mContext = null;
//		Toast.makeText(mContext, "processing", Toast.LENGTH_SHORT).show();
    	if (parseJSONAsyncTask.isCancelled()){
    		return;
    	}
    	parseJSONAsyncTask.execute(result);
    }
    

}
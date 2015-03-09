package com.example.asyncTasks;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.code.freeMarket.R;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class SignUpAccountTask extends AsyncTask<String, Void, String> {

    private OkHttpClient client;
    private Context mContext;
    private AsyncTask<String, Void, String> parseJSONAsyncTask;
    
    public SignUpAccountTask (Context context, AsyncTask<String, Void, String> asyncTask){
         mContext = context;
         client = new OkHttpClient();
         this.parseJSONAsyncTask = asyncTask;
    }
    protected String doInBackground(String... urls) {
        try {

        	RequestBody formBody = new FormEncodingBuilder()
/*	        .add("user_name", username.getText().toString()) 
	        .add("user_email", email.getText().toString()) 
	        .add("user_password", password.getText().toString()) 
	        .add("user_password_confirmation", passwordConfirm.getText().toString()) 
	        .add("user_server", "a") 
	        .add("user_device_token", "a") */
        	.add("user_name", "qq") 
	        .add("user_email", "qq@gmail.com") 
	        .add("user_password", "111111111") 
	        .add("user_password_confirmation", "11111111") 
	        .add("user_server", "a") 
	        .add("user_device_token", "a") 
/*	    	.add("server", "1") 
	    	.add("text", "aaaaa") 
	    	.add("sender","cxb") */
	        .build(); 
		    String serverUrl = mContext.getResources().getString(R.string.myServerUrl);
		    serverUrl += "users/new";
		    Request request = new Request.Builder() 
		        .url(serverUrl) 
		        .post(formBody)
		        .build(); 
		    Response response = client.newCall(request).execute();
	//        		    System.out.println(response.body().string());
		    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		 
		    System.out.println(response.body().string());
    	    return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String result) {
    //	HandleItemListJSON obj = ((HomeActivity)mContext).getObj();
  	//	obj = new HandleItemListJSON(mContext);
    	if (result == null)
    		Toast.makeText(mContext, "Failed to get data, please check your network.",  Toast.LENGTH_SHORT).show();
    	mContext = null;
//		Toast.makeText(mContext, "processing", Toast.LENGTH_SHORT).show();
    	if (parseJSONAsyncTask.isCancelled()){
    		return;
    	}
    	Toast.makeText(mContext, result,  Toast.LENGTH_SHORT).show();
   // 	parseJSONAsyncTask.execute(result);
    }
    

}
package com.example.asyncTasks;


import org.json.JSONException;
import org.json.JSONObject;
import com.code.freeMarket.R;
import com.example.interfaces.MyAsyncTaskListener;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HandleArticleCreateTask extends AsyncTask<String, Void, String> {

	private Context mContext;
	private View view;
	public HandleArticleCreateTask(Context context, View view) {
		this.mContext = context;
		this.view = view;
	}
	
	private String handleJson(String[] strs) throws JSONException{		
		if (strs[0] == null)	return null;
		return strs[0];
	}

	@Override
	protected String doInBackground(String... JSonString) {
		try {

			return handleJson(JSonString);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.articleProgress);
		progressBar.setVisibility(View.GONE);
		JSONObject jObject;
		try {
			jObject = new JSONObject(result);
		} catch (JSONException e) {
			Toast.makeText(mContext, "failed to post", Toast.LENGTH_SHORT).show();
			return;
		}
		if (result == null) {
			Toast.makeText(mContext, "failed to post", Toast.LENGTH_SHORT).show();
		} else {
			
			if (jObject.has("errors")) {
				Toast.makeText(mContext, jObject.optString("errors").toString(), Toast.LENGTH_SHORT).show();
			} else if (jObject.has("error")) {
				Toast.makeText(mContext, jObject.optString("error").toString(), Toast.LENGTH_SHORT).show();
			} else {
				MyAsyncTaskListener activity = (MyAsyncTaskListener)mContext;
		    	activity.onAsyncTaskFinished(result);
			}
		}
		
    }
}

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
		JSONObject jObject = new JSONObject(strs[0]);
		if (strs[0] == null)	return null;
		if (jObject.has("errors")) {
			return jObject.optString("errors").toString();
		} else if (jObject.has("error")) {
			return jObject.optString("error").toString();
		} else {
			return "Successfully posted!";
		}
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
		if (result == null) {
			Toast.makeText(mContext, "failed to post", Toast.LENGTH_SHORT).show();
		} else {
			MyAsyncTaskListener activity = (MyAsyncTaskListener)mContext;
	    	activity.onAsyncTaskFinished(result);
			Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
		}
		
    }
}

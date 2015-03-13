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

public class HandleCommentCreate extends AsyncTask<String, Void, String> {

	private Context mContext;
	private View view;
	private int mode;
	public HandleCommentCreate(Context context, View view) {
		this.mContext = context;
		this.view = view;
	}
	
	private String handleJson(String[] strs) throws JSONException{
		mode = 0;
		if (strs[0] == null)	return null;
		
		if (strs[0].contains("errors") || strs[0].contains("error")) {
			JSONObject jObject = new JSONObject(strs[0]);
			if (jObject.has("errors")) {
				return jObject.optString("errors").toString();
			} else {
				return jObject.optString("error").toString();
			}
		}
		else {
			mode = 1;
			return strs[0];
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
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.commentsProgress);
		progressBar.setVisibility(View.GONE);
		if (result == null) {
			Toast.makeText(mContext, "failed to reply", Toast.LENGTH_SHORT).show();
		} else {
			MyAsyncTaskListener activity = (MyAsyncTaskListener)mContext;
			if (mode == 1)
				activity.onAsyncTaskFinished(result);
			else
				Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
		}
		
    }
}

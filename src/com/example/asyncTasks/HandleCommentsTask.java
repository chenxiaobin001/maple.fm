package com.example.asyncTasks;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import com.code.freeMarket.R;
import com.example.articlesManagement.CommentArrayAdapter;
import com.example.infoClasses.Comment;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HandleCommentsTask extends AsyncTask<String, Void, String> {

	private Context mContext;
	private View view;
	private List<Comment> comments;
	private CommentArrayAdapter adapter;
	
	public HandleCommentsTask(Context context, View view, CommentArrayAdapter adapter) {
		this.mContext = context;
		this.view = view;
		this.adapter = adapter;
	}
	
	private boolean handleJson(String[] strs) throws JsonParseException, JsonMappingException, IOException{
		if (strs == null)	return false;
		ObjectMapper objectMapper = new ObjectMapper();
		comments = objectMapper.readValue(
				strs[0], objectMapper.getTypeFactory().constructCollectionType(
	                    List.class, Comment.class));
		return true;
	}
	
	@Override
	protected String doInBackground(String... JSonString) {
		try {
			if (handleJson(JSonString)) {
				return "OK";
			}
			return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.commentsProgress);
		progressBar.setVisibility(View.GONE);
		if (result == null){
            Toast.makeText(mContext, "Failed to get comments", Toast.LENGTH_SHORT).show();
            return;
		}
		//render view
		Toast.makeText(mContext, "Updated.", Toast.LENGTH_SHORT).show();
		Collections.sort(comments, Comment.getUpdateDateComparator());
//		Collections.reverse(comments);
		adapter.setComments(comments);
		adapter.notifyDataSetChanged();
    }

}

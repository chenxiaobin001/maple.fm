package com.example.asyncTasks;

import java.io.IOException;
import java.util.List;


import com.code.freeMarket.R;
import com.example.articlesManagement.ArticleArrayAdapter;
import com.example.infoClasses.Article;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HandleArticlesTask extends AsyncTask<String, Void, String> {

	private Context mContext;
	private View view;
	private List<Article> articles;
	private ArticleArrayAdapter adapter;
	
	public HandleArticlesTask(Context context, View view, ArticleArrayAdapter adapter) {
		this.mContext = context;
		this.view = view;
		this.adapter = adapter;
	}
	
	private boolean handleJson(String[] strs) throws JsonParseException, JsonMappingException, IOException{
		if (strs == null)	return false;
		ObjectMapper objectMapper = new ObjectMapper();
		articles = objectMapper.readValue(
				strs[0], objectMapper.getTypeFactory().constructCollectionType(
	                    List.class, Article.class));
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
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.articleProgress);
		progressBar.setVisibility(View.GONE);
		if (result == null){
            Toast.makeText(mContext, "Failed to get character's info", Toast.LENGTH_SHORT).show();
            return;
		}
		//render view
		adapter.setArticles(articles);
		adapter.notifyDataSetChanged();
    }

}

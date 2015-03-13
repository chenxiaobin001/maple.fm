package com.example.asyncTasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	private List<TmpObject> objects;
	private ArticleArrayAdapter adapter;
	
	//your inner class should be defined as static
	private static class TmpObject {
		private Article article;
		private int comment;
		public TmpObject(){
			
		}
		public Article getArticle() {
			return article;
		}
		public void setArticle(Article article) {
			this.article = article;
		}
		public int getComment() {
			return comment;
		}
		public void setComment(int comment) {
			this.comment = comment;
		}
	}
	public HandleArticlesTask(Context context, View view, ArticleArrayAdapter adapter) {
		this.mContext = context;
		this.view = view;
		this.adapter = adapter;
	}
	
	private boolean handleJson(String[] strs) throws JsonParseException, JsonMappingException, IOException{
		if (strs == null)	return false;
		ObjectMapper objectMapper = new ObjectMapper();
		objects = objectMapper.readValue(
				strs[0], objectMapper.getTypeFactory().constructCollectionType(
	                    List.class, TmpObject.class));
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
            Toast.makeText(mContext, "Failed to get posts", Toast.LENGTH_SHORT).show();
            return;
		}
		//render view
		articles = new ArrayList<Article>();
		for (int i = 0; i < objects.size(); i++) {
			Article article = objects.get(i).getArticle();
			article.setComment(objects.get(i).getComment());
			articles.add(article);
		}
		objects.clear();
		Toast.makeText(mContext, "Updated.", Toast.LENGTH_SHORT).show();
		Collections.sort(articles, Article.getCreateDateComparator());
		Article head = articles.get(0);
		articles.remove(0);
		Collections.sort(articles, Article.getUpdateDateComparator());
		articles.add(0, head);
		adapter.setArticles(articles);
		adapter.notifyDataSetChanged();
    }

}

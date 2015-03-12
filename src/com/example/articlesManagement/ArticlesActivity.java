package com.example.articlesManagement;

import java.util.ArrayList;
import java.util.List;

import com.code.freeMarket.R;
import com.example.asyncTasks.HandleArticlesTask;
import com.example.asyncTasks.HandleNotificationJSON;
import com.example.asyncTasks.HandleProfileStatTask;
import com.example.asyncTasks.RetriveJSONAPITask;
import com.example.asyncTasks.RetriveJSONTask;
import com.example.infoClasses.Article;
import com.example.maplefreemarket.HomeActivity;
import com.example.maplefreemarket.InfiniteScrollListener;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;

public class ArticlesActivity extends ActionBarActivity {

	private ListView listView;
//	private Button backButton;
	private ArticleArrayAdapter adapter;
	private AnimationAdapter animator;
	private ProgressBar progressBar;
//	private CharacterInfo charInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.article_activity);
		
		listView = (ListView) findViewById(R.id.articleListView);
		adapter = new ArticleArrayAdapter(this, new ArrayList<Article>());
		ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
		animator = animationAdapter;
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);
		progressBar = (ProgressBar) findViewById(R.id.articleProgress);
/*		backButton = (Button) findViewById(R.id.articleBackButton);
		backButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});*/
		listView.setOnScrollListener(newOnScrollListener());
		loadArticles();
/*		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {

		
			}

		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.profile_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
        case R.id.action_refresh:
    		try {
    			loadArticles();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	private OnScrollListener newOnScrollListener(){
		OnScrollListener onScrollListener = new InfiniteScrollListener() {
	        @Override
	        public void loadMore(int page, int totalItemsCount) {
	        	List<Article> curDataList = adapter.getArticles();
	//        	System.out.print(totalItemsCount + "load more");
	        	int size = curDataList.size();
	        	//TODO

	        	adapter.addArticles(null);
	        }
	    };
	    return onScrollListener;
	}
	
	
	private void loadArticles() {
		AsyncTask<String, Void, String> asyncTask = new HandleArticlesTask(this, findViewById(android.R.id.content), adapter);
		new RetriveJSONAPITask(this, asyncTask, 2).execute("");
		progressBar.setVisibility(View.VISIBLE);
	}
}

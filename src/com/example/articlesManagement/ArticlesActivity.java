package com.example.articlesManagement;

import java.io.IOException;
import java.util.ArrayList;
import com.code.freeMarket.R;
import com.example.asyncTasks.HandleArticlesTask;
import com.example.asyncTasks.RetriveJSONAPITask;
import com.example.infoClasses.Article;
import com.example.maplefreemarket.InfiniteScrollListener;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;

public class ArticlesActivity extends ActionBarActivity {
	
	static final int REQUEST = 2;  // The request code
	private ListView listView;
//	private Button backButton;
	private ArticleArrayAdapter adapter;
	private ProgressBar progressBar;
//	private CharacterInfo charInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.article_activity);
		
		listView = (ListView) findViewById(R.id.articleListView);
		adapter = new ArticleArrayAdapter(this, new ArrayList<Article>());
		ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);
		progressBar = (ProgressBar) findViewById(R.id.articleProgress);
		progressBar.setVisibility(View.GONE);
		listView.setOnScrollListener(newOnScrollListener());
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Intent myIntent = new Intent(ArticlesActivity.this, ArticleDetailActivity.class);
				myIntent.putExtra("article", (Parcelable) adapter.getItemAtPosition(position));
				startActivity(myIntent);
			} 

		});
		listView.postDelayed(new Runnable() {
		    public void run() {
		    	loadArticles();
		    }
		 }, 1000); //Every 120000 ms (2 minutes)

/*		listView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

	        public void onGlobalLayout() {
	        	listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	        	
	        }
	    });*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.article_menu, menu);
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
        case R.id.action_edit:
        	Intent myIntent = new Intent(this, NewPostActivity.class);
        	startActivityForResult(myIntent, REQUEST);		
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	            String articles = data.getStringExtra("articles");
	            ObjectMapper mapper = new ObjectMapper();
	            try {
					Article article = mapper.readValue(articles, Article.class);
					adapter.add(article);
					adapter.notifyDataSetChanged();
					Toast.makeText(getApplicationContext(), "Successfully posted.", Toast.LENGTH_SHORT).show();
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}
	
	private OnScrollListener newOnScrollListener(){
		OnScrollListener onScrollListener = new InfiniteScrollListener() {
	        @Override
	        public void loadMore(int page, int totalItemsCount) {
	//        	List<Article> curDataList = adapter.getArticles();
	//        	System.out.print(totalItemsCount + "load more");
	//        	int size = curDataList.size();
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

package com.example.articlesManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.code.freeMarket.R;
import com.example.acountManagement.AccessAcountSettings;
import com.example.asyncTasks.HandleCommentsTask;
import com.example.asyncTasks.RetriveJSONAPITask;
import com.example.infoClasses.Article;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.example.infoClasses.Comment;
import com.example.interfaces.MyAsyncTaskListener;

public class ArticleDetailActivity extends ActionBarActivity implements MyAsyncTaskListener{

	private ListView listView;
	private CommentArrayAdapter adapter;
	private ProgressBar progressBar;
	static final int REQUEST = 1;  // The request code
	private ViewHolder viewHolder;
	private Article article;

	static class ViewHolder {
	    public TextView articleTitleTextView;
	    public RelativeTimeTextView articleTimeTextView;
	    public TextView articleContentTextView;
	    public TextView articleAuthorTextView;
	    public TextView articleLikeTextView;
	    public TextView articleDislikeTextView;
	    public TextView articleCommentTextView;
	    public TextView articleEditTextView;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		article = ((Article) i.getParcelableExtra("article"));
		setContentView(R.layout.article_detail_activity);
		
		listView = (ListView) findViewById(R.id.commentListView);
		adapter = new CommentArrayAdapter(this, new ArrayList<Comment>());
		ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);
		progressBar = (ProgressBar) findViewById(R.id.commentsProgress);
		progressBar.setVisibility(View.GONE);

		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				replyDialog((Comment) adapter.getItemAtPosition(position));
			}

		});	
		setArticleInfo();
		listView.postDelayed(new Runnable() {
		    public void run() {
		    	loadComments();
		    }
		 }, 1000); //Every 120000 ms (2 minutes)
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
    			loadComments();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            return true;
        case R.id.action_edit:
        	replyDialog(new Comment());
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
	            Article article = data.getParcelableExtra("article");
	            viewHolder.articleTitleTextView.setText(article.getTitle());
	            viewHolder.articleContentTextView.setText(article.getContent());
	            this.article.setContent(article.getContent());
	            this.article.setTitle(article.getTitle());
	        }
	    }
	}
	
	private void loadComments() {
		AsyncTask<String, Void, String> asyncTask = new HandleCommentsTask(this, findViewById(android.R.id.content), adapter);
		new RetriveJSONAPITask(this, asyncTask, 3).execute(String.valueOf(article.getId()));
		progressBar.setVisibility(View.VISIBLE);
	}
	
	private void setArticleInfo() {
		viewHolder = new ViewHolder(); 
		viewHolder.articleAuthorTextView = (TextView) findViewById(R.id.articleAuthorTextView);
	    viewHolder.articleTitleTextView = (TextView) findViewById(R.id.articleTitleTextView);
	    viewHolder.articleTimeTextView = (RelativeTimeTextView) findViewById(R.id.articleTimeTextView);
	    viewHolder.articleContentTextView = (TextView) findViewById(R.id.articleContentTextView);
	    viewHolder.articleLikeTextView = (TextView) findViewById(R.id.articleLikeTextView);
	    viewHolder.articleDislikeTextView = (TextView) findViewById(R.id.articleDislikeTextView);
	    viewHolder.articleCommentTextView = (TextView) findViewById(R.id.articleCommentTextView);
	    viewHolder.articleEditTextView = (TextView) findViewById(R.id.articleEditTextView);
	    viewHolder.articleContentTextView.setMovementMethod(new ScrollingMovementMethod());
	    AccessAcountSettings account = AccessAcountSettings.getInstance();
	    String user = account.getAccountName();
	    if (user != null && user.equals(article.getAuthor())) {
	    	viewHolder.articleEditTextView.setVisibility(View.VISIBLE);
			viewHolder.articleEditTextView.setOnClickListener(new OnClickListener() {
				
			@Override
				public void onClick(View v) {
					Intent myIntent = new Intent(ArticleDetailActivity.this, NewPostActivity.class);
					myIntent.putExtra("type", 1);				//1-edit mode, 0-new mode
					myIntent.putExtra("article", article);
					startActivityForResult(myIntent, REQUEST);					
				}
			});
	    }
		String dateString = article.getUpdateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(dateString);
			long startDate = date.getTime() - 10800000 - 3600000;
			viewHolder.articleTimeTextView.setReferenceTime(startDate);
		} catch (ParseException e) {
			viewHolder.articleTimeTextView.setText("unexpected");
			e.printStackTrace();
		} 
		viewHolder.articleTitleTextView.setText(article.getTitle());
		viewHolder.articleAuthorTextView.setText(article.getAuthor());
		viewHolder.articleContentTextView.setText(article.getContent());
		viewHolder.articleLikeTextView.setText("O " + String.valueOf(article.getLike()));
		viewHolder.articleDislikeTextView.setText("X " + String.valueOf(article.getDislike()));
		viewHolder.articleCommentTextView.setText("Reply " + String.valueOf(article.getComment()));
	

	}
	
	private void replyDialog(Comment comment) {
		String[] args = new String[4];
		AccessAcountSettings account = AccessAcountSettings.getInstance();
		args[0] = account.getAccountName();
		args[1] = String.valueOf(comment.getId());
		args[2] = comment.getCommenter1();
		args[3] = String.valueOf(article.getId());
		ReplyArticleDialog dialog = ReplyArticleDialog.newInstance(args);
		dialog.show(getFragmentManager(), "reply"); 
	}

	@Override
	public void onAsyncTaskFinished(String foo) {
		HandleCommentsTask task = new HandleCommentsTask(this, findViewById(android.R.id.content), adapter);
		task.execute(foo);
	}
}

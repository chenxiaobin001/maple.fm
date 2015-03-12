package com.example.articlesManagement;


import com.code.freeMarket.R;
import com.example.asyncTasks.HandlePostArticleTask;
import com.example.asyncTasks.PostJSONAPITask;
import com.example.interfaces.MyAsyncTaskListener;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class NewPostActivity extends ActionBarActivity implements MyAsyncTaskListener{

	private Button backButton;
	private Button sendButton;
	private ProgressBar progressBar;
	private EditText titleEditText;
	private EditText contentEditText;
//	private CharacterInfo charInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_article_activity);
		backButton = (Button) findViewById(R.id.articleBackButton);
		sendButton = (Button) findViewById(R.id.articleSendButton);
		titleEditText = (EditText) findViewById(R.id.articleTitleEditText);
		contentEditText = (EditText) findViewById(R.id.articleContentEditText);
		progressBar = (ProgressBar) findViewById(R.id.articleProgress);
		progressBar.setVisibility(View.GONE);
		backButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		sendButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				sendPost(); 
			}
		});
	}

	private String bowlingJson() {
		String text = titleEditText.getText().toString().replace("\n", "\\n");
		String title = contentEditText.getText().toString().replace("\n", "\\n");
        return "{" +
        		"\"article\" :" +
        	      "{ \"title\": \"" + text + "\"," +
        	       " \"text\" : \"" + title + "\"" +
        	       "}" + 
       	    "}"; 
      } 
 
	private void sendPost() {
		HandlePostArticleTask task = new HandlePostArticleTask(this, findViewById(android.R.id.content));
		PostJSONAPITask post = new PostJSONAPITask(this, task, 2);
		
		post.execute(bowlingJson());
	}

	@Override
	public void onAsyncTaskFinished(String foo) {
		finish();		
	}


}

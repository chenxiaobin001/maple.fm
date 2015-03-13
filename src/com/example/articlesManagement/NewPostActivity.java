package com.example.articlesManagement;


import com.code.freeMarket.R;
import com.example.asyncTasks.HandleArticleCreateTask;
import com.example.asyncTasks.HandleArticleEditTask;
import com.example.asyncTasks.PatchJSONAPITask;
import com.example.asyncTasks.PostJSONAPITask;
import com.example.infoClasses.Article;
import com.example.interfaces.MyAsyncTaskListener;


import android.content.Intent;
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
	private Article articleReturn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_new_activity);
		Intent i = getIntent();
		final int type = i.getIntExtra("type", 0);
		Article article = i.getParcelableExtra("article");
		if (article == null) article = new Article();
		articleReturn = article;
		final int id = article.getId();
		backButton = (Button) findViewById(R.id.articleBackButton);
		sendButton = (Button) findViewById(R.id.articleSendButton);
		titleEditText = (EditText) findViewById(R.id.articleTitleEditText);
		contentEditText = (EditText) findViewById(R.id.articleContentEditText);
		progressBar = (ProgressBar) findViewById(R.id.articleProgress);
		progressBar.setVisibility(View.GONE);
		titleEditText.setText(article.getTitle());
		contentEditText.setText(article.getContent());
		backButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		sendButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				if (type == 0)
					sendPost(); 
				else
					sendPatch(id);
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
		HandleArticleCreateTask task = new HandleArticleCreateTask(this, findViewById(android.R.id.content));
		PostJSONAPITask post = new PostJSONAPITask(this, task, 2);	
		post.execute(bowlingJson());
	}

	private void sendPatch(int id) {
		HandleArticleEditTask task = new HandleArticleEditTask(this, findViewById(android.R.id.content));
		PatchJSONAPITask patch = new PatchJSONAPITask(this, task, 0);
		String[] contents = new String[2];
		contents[0] = bowlingJson();
		contents[1] = String.valueOf(id);
		patch.execute(contents);
	}
	
	@Override
	public void onAsyncTaskFinished(String foo) {
		if ("1".equals(foo)) {	//success
			Intent returnIntent = new Intent();
			articleReturn.setTitle(titleEditText.getText().toString());
			articleReturn.setContent(contentEditText.getText().toString());
			returnIntent.putExtra("article", articleReturn);
			setResult(RESULT_OK,returnIntent);	
		}
		finish();		
	}


}

package com.example.articlesManagement;

import com.code.freeMarket.R;
import com.example.asyncTasks.HandleCommentCreate;
import com.example.asyncTasks.PostJSONAPITask;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
 

 
public class ReplyArticleDialog extends DialogFragment{
	 
	private EditText content;
	private Dialog mDialog;
	private String[] args;
	 
	static ReplyArticleDialog newInstance(String[] arguments) {
		ReplyArticleDialog f = new ReplyArticleDialog();
        Bundle args = new Bundle();
        args.putStringArray("args", arguments);
        f.setArguments(args);
        return f;
    }

	@Override 
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		args = getArguments().getStringArray("args");
		View view = inflater.inflate(R.layout.popup_window_reply_dialog, null);
		content = (EditText) view.findViewById(R.id.replyContentEditText);
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			 
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub 
				 
			} 
		}); 
		builder.setPositiveButton("Reply", new DialogInterface.OnClickListener() {
			 
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				try {
					reply();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "Failed to reply.", Toast.LENGTH_SHORT).show();
				} 
			} 
		}); 
	 
		builder.setView(view);
		builder.setTitle("Reply");
		Dialog dialog = builder.create();
		mDialog = dialog;
		 
		((AlertDialog) dialog).setOnShowListener(new OnShowListener() {
			 
			@Override 
			public void onShow(DialogInterface dialog) {
				 
				((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				 
			} 
		}); 
		 
		content.addTextChangedListener(new TextWatcher() {
			 
			@Override 
			public void afterTextChanged(Editable s) {
				if (!isContentEmpty()){ 
					((AlertDialog) mDialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
				}else{ 
					((AlertDialog) mDialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				} 
				 
			} 
 
 
			@Override 
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub 
				 
			} 
 
 
			@Override 
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub 
				 
			} 
		}); 
		 
		return dialog;
	}	 
	 
	private boolean isContentEmpty(){ 
		if (content.getText().toString().trim().equals("")){
			return true; 
		} 
		return false; 
	} 
 
	private String bowlingJson() { 
		String commenter1 = args[0];
		String commenter2 = args[2];
		String commenterID2 = args[1];
		
        return "{" +
        		"\"comment\" :" +
        	      "{ \"commenter\": \"" + commenter1 + "\"," +
        	       " \"body\" : \"" + content.getText().toString() + "\"," +
        	       " \"comment_id2\" : \"" + commenterID2 + "\"," +
        	       " \"commenter2\" : \"" + commenter2 + "\"" +
        	       "}" + 
       	    "}"; 
      } 
 
	private void reply() throws Exception {
		AsyncTask<String, Void, String> asyncTask = new HandleCommentCreate(getActivity(), getActivity().findViewById(android.R.id.content));
		PostJSONAPITask task = new PostJSONAPITask(getActivity(), asyncTask, 3);
		String[] args = new String[2];
		args[0] = bowlingJson();
		args[1] = this.args[3];
		task.execute(args);
	} 
	 
 
} 
 
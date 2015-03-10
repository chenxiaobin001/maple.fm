package com.example.acountManagement;


import com.code.freeMarket.R;
import com.example.asyncTasks.HandleUserSignInJSON;
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
 

 
public class SignInDialog extends DialogFragment{
	 
	private EditText password;
	private EditText email;
	private View view;
	private Dialog mDialog;
	 
	@Override 
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.popup_window_signin, null);
		this.view = view;
		password = (EditText) view.findViewById(R.id.passwordSignInEditText);
		email = (EditText) view.findViewById(R.id.emailSignInEditText);
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			 
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub 
				 
			} 
		}); 
		builder.setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
			 
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				try {
					signIn();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "Failed to sign in.", Toast.LENGTH_SHORT).show();
				} 
			} 
		}); 
	 
		builder.setView(view);
		builder.setTitle("Sign In");
		Dialog dialog = builder.create();
		mDialog = dialog;
		 
		((AlertDialog) dialog).setOnShowListener(new OnShowListener() {
			 
			@Override 
			public void onShow(DialogInterface dialog) {
				 
				((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				 
			} 
		}); 
		 
		email.addTextChangedListener(new TextWatcher() {
			 
			@Override 
			public void afterTextChanged(Editable s) {
				if (!usernameOrPasswordEmpty()){ 
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
		 
		password.addTextChangedListener(new TextWatcher() {
			 
			@Override 
			public void afterTextChanged(Editable s) {
				if (!usernameOrPasswordEmpty()){ 
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
	 
	private boolean usernameOrPasswordEmpty(){ 
		if (email.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")){
			return true; 
		} 
		return false; 
	} 
 
	private String bowlingJson() {
        return "{" +
        		"\"user\" :" +
        	      "{ \"email\": \"" + email.getText().toString() + "\"," +
        	       " \"password\" : \"" + password.getText().toString() + "\"" +
        	       "}" + 
       	    "}"; 
      } 
 
	private void signIn() throws Exception {
		AsyncTask<String, Void, String> asyncTask = new HandleUserSignInJSON(getActivity());
		PostJSONAPITask task = new PostJSONAPITask(getActivity(), asyncTask, 1);
		task.execute(bowlingJson());
	} 
	 
 
} 
 
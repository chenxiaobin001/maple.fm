package com.example.acountManagement;


import com.code.freeMarket.R;
import com.example.asyncTasks.SignUpAccountTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
 

 
public class SignupDialog extends DialogFragment{
	 
	private EditText username;
	private EditText password;
	private EditText passwordConfirm;
	private EditText email;
	private EditText server;
	private EditText deviceToken;
	private View view;
	private Dialog mDialog;
	 
	@Override 
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.popup_window_signup, null);
		this.view = view;
		username = (EditText) view.findViewById(R.id.usernameEditText);
		password = (EditText) view.findViewById(R.id.passwordEditText);
		passwordConfirm = (EditText) view.findViewById(R.id.passwordConfirmEditText);
		server = (EditText) view.findViewById(R.id.serverEditText);
		deviceToken = (EditText) view.findViewById(R.id.deviceTokenEditText);
		email = (EditText) view.findViewById(R.id.emailEditText);
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			 
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub 
				 
			} 
		}); 
		builder.setPositiveButton("SignUp", new DialogInterface.OnClickListener() {
			 
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				try {
					signup();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "Failed to sign up.", Toast.LENGTH_SHORT).show();
				} 
			} 
		}); 
	 
		builder.setView(view);
		builder.setTitle("Sign up your account");
		Dialog dialog = builder.create();
		mDialog = dialog;
		 
		((AlertDialog) dialog).setOnShowListener(new OnShowListener() {
			 
			@Override 
			public void onShow(DialogInterface dialog) {
				 
				((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				 
			} 
		}); 
		 
		username.addTextChangedListener(new TextWatcher() {
			 
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
		 
		return dialog;
	}	 
	 
	private boolean usernameOrPasswordEmpty(){ 
		username = (EditText) view.findViewById(R.id.usernameEditText);
		password = (EditText) view.findViewById(R.id.passwordEditText);
		password = (EditText) view.findViewById(R.id.emailEditText);
		if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")){
			return true; 
		} 
		return false; 
	} 
 
 
	private void signup() throws Exception {
		
		SignUpAccountTask task = new SignUpAccountTask(getActivity(), null);
		task.execute("");
	} 
	 
 
 
} 
 
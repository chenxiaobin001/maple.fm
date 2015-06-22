package com.example.acountManagement;


import com.code.freeMarket.R;
import com.example.asyncTasks.HandleUserSignUpJSON;
import com.example.asyncTasks.PostJSONAPITask;
import com.example.maplefreemarket.MyAdapter;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
 

 
public class SignupDialog extends DialogFragment{
	 
	private EditText username;
	private EditText password;
	private EditText passwordConfirm;
	private EditText email;
	private int server;
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
		deviceToken = (EditText) view.findViewById(R.id.deviceTokenEditText);
		deviceToken.setEnabled(false); 
		email = (EditText) view.findViewById(R.id.emailEditText);
		setSpinnerContent( );
		builder.setNegativeButton("Sign In", new DialogInterface.OnClickListener() {
			 
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub 
				SignInDialog signinDialog = new SignInDialog();
	    		signinDialog.show(getFragmentManager(), "signin"); 
			} 
		}); 
		builder.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
			 
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
		email = (EditText) view.findViewById(R.id.emailEditText);
		if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")
				|| email.getText().toString().trim().equals("")){
			return true; 
		} 
		return false; 
	} 
 
	private String bowlingJson() {
        return "{" +
        		"\"user\" :" +
        	      "{ \"name\": \"" + username.getText().toString() + "\"," +
        	       " \"email\": \"" + email.getText().toString() + "\"," +
        	       " \"password\" : \"" + password.getText().toString() + "\"," +
        	       " \"password_confirmation\" : \"" + passwordConfirm.getText().toString() + "\"," +
        	       " \"server\" : \"" + String.valueOf(server) + "\"," +
        	       " \"device_token\" : \"" + deviceToken.getText().toString() + "\"}" + 
       	    "}"; 
      } 
 
	private void signup() throws Exception {
		AsyncTask<String, Void, String> asyncTask = new HandleUserSignUpJSON(getActivity());
		PostJSONAPITask task = new PostJSONAPITask(getActivity(), asyncTask, 0);
		task.execute(bowlingJson());
	} 
	 
	private void setSpinnerContent( )
	{	
		Integer[] serverImages = new Integer[] {  R.drawable.scania, R.drawable.windia,
				R.drawable.bera, R.drawable.broa, R.drawable.khaini, R.drawable.mardia, R.drawable.arcania,
				R.drawable.bellocan, R.drawable.renegades};
		String[] serverNames = getResources().getStringArray(R.array.servers1); 
		Spinner spinner = (Spinner) view.findViewById(R.id.signupServerSpinner);
		spinner.setAdapter(new MyAdapter(getActivity(), R.layout.row,
				serverNames, serverImages));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				server = position;
				switch(position) {
				case 0:

					break;
				case 1:

					break;
				case 2:

					break;
				case 3:
		
					break;
				case 4:
	
					break;
				case 5:
		
					break;
				case 6:
	
					break;
				case 7:
		
					break;
				case 8:
	
				}				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});

	}
 
} 
 
package com.example.acountManagement;

import com.code.freeMarket.R;
import com.example.asyncTasks.HandleProfileStatTask;
import com.example.asyncTasks.RetriveJSONTask;

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
 

 
public class GetCharacterDialog extends DialogFragment{
	 
	private EditText characterName;
	private Dialog mDialog;
	 
	@Override 
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.popup_window_getcharacter, null);
		characterName = (EditText) view.findViewById(R.id.characterNameSignInEditText);
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			 
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub 
				 
			} 
		}); 
		builder.setPositiveButton("Get", new DialogInterface.OnClickListener() {
			 
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				try {
					getCharacter(characterName.getText().toString());
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "Failed to sign in.", Toast.LENGTH_SHORT).show();
				} 
			} 
		}); 
	 
		builder.setView(view);
		builder.setTitle("Get Character Stats");
		Dialog dialog = builder.create();
		mDialog = dialog;
		 
		((AlertDialog) dialog).setOnShowListener(new OnShowListener() {
			 
			@Override 
			public void onShow(DialogInterface dialog) {
				 
				((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				 
			} 
		}); 
		 
		characterName.addTextChangedListener(new TextWatcher() {
			 
			@Override 
			public void afterTextChanged(Editable s) {
				if (!isCharacterNameEmpty()){ 
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
	 
	private boolean isCharacterNameEmpty(){ 
		if (characterName.getText().toString().trim().equals("")){
			return true; 
		} 
		return false; 
	} 
 
	private void getCharacter(String name) throws Exception {
		String URL = getActivity().getResources().getString(R.string.api_rankings);
		URL += ("name=" + name);
		AsyncTask<String, Void, String> asyncTask = new HandleProfileStatTask(getActivity(), getActivity().findViewById(android.R.id.content), true);
		RetriveJSONTask task = new RetriveJSONTask(getActivity(), asyncTask);
		task.execute(URL);
	} 
	 
 
} 
 
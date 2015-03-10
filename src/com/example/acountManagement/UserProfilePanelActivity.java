package com.example.acountManagement;

import com.code.freeMarket.R;
import com.example.asyncTasks.DeleteJSONAPITask;
import com.example.asyncTasks.HandleUserSignUpJSON;
import com.example.asyncTasks.PostJSONAPITask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class UserProfilePanelActivity extends Activity {

	private Button backButton;
	private Button signoutButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		
		
		backButton = (Button) findViewById(R.id.profileBackButton);
		backButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		signoutButton = (Button) findViewById(R.id.profileSignOutButton);
		signoutButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				AccessAcountSettings settings = AccessAcountSettings.getInstance();
				settings.clearAccountInfo();
				DeleteJSONAPITask signOut = new DeleteJSONAPITask(UserProfilePanelActivity.this, null, 0);
				signOut.execute();
				Toast.makeText(getApplicationContext(), "Successfully sign out!", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
}

package com.example.acountManagement;

import java.io.File;

import com.code.freeMarket.R;
import com.example.asyncTasks.DeleteJSONAPITask;
import com.example.asyncTasks.HandleProfileStatTask;
import com.example.maplefreemarket.SellerInfoFragment.OnImageLoadedListener;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfilePanelActivity extends Activity implements  OnImageLoadedListener {

	private Button backButton;
	private Button signoutButton;

	private TextView accountNameTextView;
	private TextView accountEmailTextView;
	private TextView accountServerTextView;
	private ImageView accountPetImage;
	private ImageView accountCharImage;
	private AccessAcountSettings account;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		account = AccessAcountSettings.getInstance();
		accountNameTextView = (TextView) findViewById(R.id.profileNameTextView);
		accountEmailTextView = (TextView) findViewById(R.id.profileEmailTextView);
		accountServerTextView = (TextView) findViewById(R.id.profileServerTextView);
		accountPetImage = (ImageView) findViewById(R.id.petImageimageView);
		accountCharImage = (ImageView) findViewById(R.id.characterImageView);
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
		
		accountCharImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GetCharacterDialog getCharacterDialog = new GetCharacterDialog();
				getCharacterDialog.show(getFragmentManager(), "getCharacter"); 
			}
		});
		
		loadAccountInfo();
	}
	
	private void loadAccountInfo() {		
		accountNameTextView.setText(account.getAccountName());
		accountEmailTextView.setText(account.getAccountEmail());
		accountServerTextView.setText(getServerName(account.getAccountServer())); 
		final String json = account.getAccountCharacterInfo();
		AsyncTask<String, Void, String> asyncTask0 = new HandleProfileStatTask(this, findViewById(android.R.id.content), false);
		asyncTask0.execute(json); 
		if (json != null) {
			ContextWrapper cw = new ContextWrapper(getApplicationContext());
			File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
			Bitmap bitmap = account.getAccountImage(directory.getAbsolutePath());
			if (bitmap != null) 
				accountCharImage.setImageBitmap(bitmap);
			final AsyncTask<String, Void, String> asyncTask = new HandleProfileStatTask(this, findViewById(android.R.id.content), true);
			accountCharImage.post(new Runnable() {
			    @Override
			    public void run() {
			    	
					asyncTask.execute(json);
			    }
			});

		}	
	}
	
	private String getServerName(int server) {
		String ret = "unknown";
		switch (server) {
		case 0: ret = "Scania"; break;
		case 1: ret = "Windia"; break;
		case 2: ret = "Bera"; break;
		case 3: ret = "Bora"; break;
		case 4: ret = "Khaini"; break;
		case 5: ret = "YMCK"; break;
		case 6: ret = "GAZED"; break;
		case 7: ret = "BelloNova"; break;
		case 8: ret = "Renegades"; break;
		}
		return ret;
	}

	@Override
	public void onImageLoaded(Bitmap bitmap, boolean isSeller) {
		account.setAccountImage(bitmap);
	}

}

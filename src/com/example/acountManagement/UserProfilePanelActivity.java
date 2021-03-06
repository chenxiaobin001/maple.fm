package com.example.acountManagement;

import java.io.File;

import com.code.freeMarket.R;
import com.example.asyncTasks.DeleteJSONAPITask;
import com.example.asyncTasks.HandleProfileStatTask;
import com.example.asyncTasks.RetriveJSONTask;
import com.example.maplefreemarket.SellerInfoFragment.OnImageLoadedListener;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfilePanelActivity extends ActionBarActivity implements  OnImageLoadedListener {

	private Button backButton;
	private Button signoutButton;
	private Integer[] serverImages = new Integer[] { R.drawable.scania, R.drawable.windia,
			R.drawable.bera, R.drawable.broa, R.drawable.khaini, R.drawable.mardia, R.drawable.arcania,
			R.drawable.bellocan, R.drawable.renegades};
	private TextView accountNameTextView;
	private TextView accountEmailTextView;
	private TextView accountServerTextView;
//	private ImageView accountPetImage;
	private ImageView accountCharImage;
	private ImageView accountServerImage;
	private AccessAcountSettings account;
	private ProgressBar progressBar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		account = AccessAcountSettings.getInstance();
		accountNameTextView = (TextView) findViewById(R.id.profileNameTextView);
		accountEmailTextView = (TextView) findViewById(R.id.profileEmailTextView);
		accountServerTextView = (TextView) findViewById(R.id.profileServerTextView);
		accountServerImage = (ImageView) findViewById(R.id.profileServerImageView);
//		accountPetImage = (ImageView) findViewById(R.id.petImageimageView);
		accountCharImage = (ImageView) findViewById(R.id.characterImageView);
		backButton = (Button) findViewById(R.id.profileBackButton);
		progressBar = (ProgressBar) findViewById(R.id.profileProgress);
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
		accountServerImage.setImageResource(serverImages[account.getAccountServer()]);
		final String json = account.getAccountCharacterInfo();
		AsyncTask<String, Void, String> asyncTask0 = new HandleProfileStatTask(this, findViewById(android.R.id.content), false);
		asyncTask0.execute(json); 
		if (json != null) {
			ContextWrapper cw = new ContextWrapper(getApplicationContext());
			File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
			Bitmap bitmap = account.getAccountImage(directory.getAbsolutePath());
			if (bitmap != null) 
				accountCharImage.setImageBitmap(bitmap);
			
		}
/*		try {
			getCharacter(account.getAccountCharacter());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
//		account.setAccountImage(bitmap);
	}
	
	private void getCharacter(String name) throws Exception {
		String URL = getResources().getString(R.string.api_rankings);
		URL += ("name=" + name);
		AsyncTask<String, Void, String> asyncTask = new HandleProfileStatTask(this, findViewById(android.R.id.content), true);
		RetriveJSONTask task = new RetriveJSONTask(this, asyncTask);
		task.execute(URL);
		progressBar.setVisibility(View.VISIBLE);
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.profile_menu, menu);
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
    			getCharacter(account.getAccountCharacter());
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}
	}
}

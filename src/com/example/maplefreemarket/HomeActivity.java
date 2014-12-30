package com.example.maplefreemarket;



import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity {

	private Spinner spinner;
	private MapleFreeMarketApplication myApp;
	private String selection;
	private Integer[] serverImages;
	private String[] serverNames;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spinner = (Spinner) findViewById(R.id.serverSpinner);
		myApp = (MapleFreeMarketApplication) this.getApplication();
		serverImages = new Integer[] { R.drawable.scania, R.drawable.windia,
				R.drawable.bera, R.drawable.broa, R.drawable.khaini, R.drawable.mardia, R.drawable.arcania,
				R.drawable.bellocan, R.drawable.renegades};
		serverNames = getResources().getStringArray(R.array.servers); 
		setSpinnerContent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setSpinnerContent( )
	{	
		spinner = (Spinner) findViewById(R.id.serverSpinner);
		spinner.setAdapter(new MyAdapter(HomeActivity.this, R.layout.row,
				serverNames));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				myApp.setServer(position);
				switch(position) {
				case 0:
					selection = "Scania";
					break;
				case 1:
					selection = "Windia";
					break;
				case 2:
					selection = "Bera";
					break;
				case 3:
					selection = "Broa";
					break;
				case 4:
					selection = "Khaini";
					break;
				case 5:
					selection = "YMCK";
					break;
				case 6:
					selection = "GAZED";
					break;
				case 7:
					selection = "BelloNova";
					break;
				case 8:
					selection = "Renegades";
		
				}
				Toast.makeText(myApp, selection +"is selected", Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

	}

	// Creating an Adapter Class
	public class MyAdapter extends ArrayAdapter {

		public MyAdapter(Context context, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

			// Inflating the layout for the custom Spinner
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.row, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView serverName = (TextView) layout.findViewById(R.id.serverName);
		
			// Setting the text using the array
			serverName.setText(serverNames[position]);
		
			// Declaring and Typecasting the imageView in the inflated layout
			ImageView img = (ImageView) layout.findViewById(R.id.serverImage);
		
			// Setting an image using the id's in the array
			img.setImageResource(serverImages[position]);

			return layout;
		}

		// It gets a View that displays in the drop down popup the data at the specified position
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
	
		// It gets a View that displays the data at the specified position
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
	}
	
}

package com.example.maplefreemarket;



import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

public class HomeActivity extends ActionBarActivity {

	private Spinner spinner;
	private Button refreshButton;
	private ListView listView;
	private MapleFreeMarketApplication myApp;
	private String selection;
	private Integer[] serverImages;
	private String[] serverNames;
	private HandleItemJson obj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spinner = (Spinner) findViewById(R.id.serverSpinner);
		listView = (ListView) findViewById(R.id.itemListView);
		refreshButton = (Button) findViewById(R.id.refreshButton);
		myApp = (MapleFreeMarketApplication) this.getApplication();
		serverImages = new Integer[] { R.drawable.scania, R.drawable.windia,
				R.drawable.bera, R.drawable.broa, R.drawable.khaini, R.drawable.mardia, R.drawable.arcania,
				R.drawable.bellocan, R.drawable.renegades};
		serverNames = getResources().getStringArray(R.array.servers); 
		setSpinnerContent();
		
		String result = "{\"result\":[{\"fm_items\":[{\"U\":\"1102484\",\"a\":\"1\",\"b\":\"1\",\"c\":\"3750000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Tyrant Lycaon Cloak\",\"T\":\"1102481\",\"X\":3471928570,\"Q\":\"Equip\",\"R\":\"Armor\",\"S\":\"Cape\",\"Y\":\"0\",\"h\":\"2\",\"j\":\"50\",\"k\":\"50\",\"l\":\"50\",\"m\":\"50\",\"p\":\"30\",\"q\":\"30\",\"r\":\"150\",\"s\":\"150\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"150\"},{\"U\":\"1012306\",\"a\":\"1\",\"b\":\"1\",\"c\":\"700000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Lucky Tree Branch Nose\",\"T\":\"1012058\",\"X\":136666666,\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Face Accessory\",\"i\":\"10\",\"j\":\"10\",\"k\":\"10\",\"l\":\"10\",\"m\":\"14\",\"p\":\"10\",\"r\":\"6\",\"s\":\"6\",\"t\":\"3\",\"u\":\"3\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"10\"},{\"U\":\"1432187\",\"a\":\"1\",\"b\":\"1\",\"c\":\"1500000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Sweetwater Spear\",\"T\":\"1432187\",\"X\":91365064,\"Q\":\"Equip\",\"R\":\"Two-Handed Weapon\",\"S\":\"Spear\",\"Y\":\"0\",\"i\":\"6\",\"j\":\"97\",\"k\":\"85\",\"n\":\"255\",\"o\":\"255\",\"p\":\"294\",\"t\":\"173\",\"C\":\"30\",\"D\":\"10\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"160\"},{\"U\":\"1122057\",\"a\":\"1\",\"b\":\"1\",\"c\":\"5000000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Awakening Mind of Maple Necklace\",\"T\":\"1122052\",\"P\":\"A Mind of Maple Necklace that is beginning to be restored. One more gem, and its mystical powers will be amplified and awakened into a power on another level.\",\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Pendant\",\"Y\":\"0\",\"p\":\"15\",\"q\":\"15\",\"r\":\"5\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"70\"}]},{\"seconds_ago\":\"797\"}]}";
		try {
			obj = new HandleItemJson(result);
			Toast.makeText(myApp, obj.getSecondsAgo(), Toast.LENGTH_SHORT).show();
			
		} catch (JSONException e) {
			Toast.makeText(myApp, "aaa", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		Item[] itemArr = obj.getItems().toArray(new Item[obj.getItems().size()]);
		ItemArrayAdapter adapter =  new ItemArrayAdapter(HomeActivity.this, itemArr);
		listView.setAdapter(adapter);
		
		refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String result = "{\"result\":[{\"fm_items\":[{\"U\":\"1102484\",\"a\":\"1\",\"b\":\"1\",\"c\":\"3750000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Tyrant Lycaon Cloak\",\"T\":\"1102481\",\"X\":3471928570,\"Q\":\"Equip\",\"R\":\"Armor\",\"S\":\"Cape\",\"Y\":\"0\",\"h\":\"2\",\"j\":\"50\",\"k\":\"50\",\"l\":\"50\",\"m\":\"50\",\"p\":\"30\",\"q\":\"30\",\"r\":\"150\",\"s\":\"150\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"150\"},{\"U\":\"1012306\",\"a\":\"1\",\"b\":\"1\",\"c\":\"700000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Lucky Tree Branch Nose\",\"T\":\"1012058\",\"X\":136666666,\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Face Accessory\",\"i\":\"10\",\"j\":\"10\",\"k\":\"10\",\"l\":\"10\",\"m\":\"14\",\"p\":\"10\",\"r\":\"6\",\"s\":\"6\",\"t\":\"3\",\"u\":\"3\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"10\"},{\"U\":\"1432187\",\"a\":\"1\",\"b\":\"1\",\"c\":\"1500000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Sweetwater Spear\",\"T\":\"1432187\",\"X\":91365064,\"Q\":\"Equip\",\"R\":\"Two-Handed Weapon\",\"S\":\"Spear\",\"Y\":\"0\",\"i\":\"6\",\"j\":\"97\",\"k\":\"85\",\"n\":\"255\",\"o\":\"255\",\"p\":\"294\",\"t\":\"173\",\"C\":\"30\",\"D\":\"10\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"160\"},{\"U\":\"1122057\",\"a\":\"1\",\"b\":\"1\",\"c\":\"5000000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Awakening Mind of Maple Necklace\",\"T\":\"1122052\",\"P\":\"A Mind of Maple Necklace that is beginning to be restored. One more gem, and its mystical powers will be amplified and awakened into a power on another level.\",\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Pendant\",\"Y\":\"0\",\"p\":\"15\",\"q\":\"15\",\"r\":\"5\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"70\"}]},{\"seconds_ago\":\"797\"}]}";
				try {
					obj = new HandleItemJson(result);
					Toast.makeText(myApp, obj.getSecondsAgo(), Toast.LENGTH_SHORT).show();
					
				} catch (JSONException e) {
					Toast.makeText(myApp, "aaa", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
			}
		});
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
				Toast.makeText(myApp, selection +" is selected", Toast.LENGTH_SHORT).show();
				
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
	
	class ItemArrayAdapter extends ArrayAdapter<Item> {
		  private final Context context;
		  private final Item[] items;

		  public ItemArrayAdapter(Context context, Item[] items) {
		    super(context, R.layout.item_row, items);
		    this.context = context;
		    this.items = items;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
			
		    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.item_row, parent, false);
		    TextView itemNameTextView = (TextView) rowView.findViewById(R.id.itemNameTextView);
		    TextView itemPriceTextView = (TextView) rowView.findViewById(R.id.itemPriceTextView);
		    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		    itemNameTextView.setText(items[position].getItemName());
		    itemPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(items[position].getPrice()));
		    int id = items[position].getIconID();
		    String url = getString(R.string.item_icon_url) + String.valueOf(id) + ".png";
		    Picasso.with(context).load(url).into(imageView);
		    
		    return rowView;
		  }
		}
	
}

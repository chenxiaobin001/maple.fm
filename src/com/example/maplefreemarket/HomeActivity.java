package com.example.maplefreemarket;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;


public class HomeActivity extends ActionBarActivity {

	private Spinner spinner;
	private Button refreshButton;
	private ListView listView;
	private MapleFreeMarketApplication myApp;
	private String selection;
	private Integer[] serverImages;
	private String[] serverNames;
	final private Boolean[] descs = new Boolean[6];
	private HandleItemJson obj;
	private OkHttpClient client;
	private ItemArrayAdapter adapter;
	private EditText searchEditText;
	private TableRow tableRow;
	public EditText getSearchEditText() {
		return searchEditText;
	}

	public ItemArrayAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ItemArrayAdapter adapter) {
		this.adapter = adapter;
	}

	public HandleItemJson getObj() {
		return obj;
	}

	public void setObj(HandleItemJson obj) {
		this.obj = obj;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApp = (MapleFreeMarketApplication) this.getApplication();
		Arrays.fill(descs, Boolean.FALSE);
		setContentView(R.layout.activity_main);
		client = new OkHttpClient();
		spinner = (Spinner) findViewById(R.id.serverSpinner);
		listView = (ListView) findViewById(R.id.itemListView);
		tableRow = (TableRow) findViewById(R.id.sortableColumn);
		findViewById(R.id.loadingPanel).setVisibility(View.GONE);
		adapter = new ItemArrayAdapter(HomeActivity.this, new ArrayList<Item>());
		myApp.setItemAdapter(adapter);
		listView.setAdapter(adapter);
		refreshButton = (Button) findViewById(R.id.refreshButton);
		serverImages = new Integer[] { R.drawable.scania, R.drawable.windia,
				R.drawable.bera, R.drawable.broa, R.drawable.khaini, R.drawable.mardia, R.drawable.arcania,
				R.drawable.bellocan, R.drawable.renegades};
		serverNames = getResources().getStringArray(R.array.servers); 
		setSpinnerContent();
		sortableColumnSetup();
		String result = "[{\"fm_items\":[{\"U\":\"1102484\",\"P\":\"#cDouble-click#This potion is truly<Decent Hyper Body> legendary. \\nRecovers all Max HP and MP, #*Double-click#with a limit of 99,999 HP and MP if your Max HP and MP is over 99,999.\",\"a\":\"1\",\"b\":\"1\",\"c\":\"3750000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Tyrant Lycaon Cloak\",\"T\":\"1102481\",\"X\":3471928570,\"Q\":\"Equip\",\"R\":\"Armor\",\"S\":\"Cape\",\"Y\":\"0\",\"h\":\"2\",\"j\":\"50\",\"k\":\"50\",\"l\":\"50\",\"m\":\"50\",\"p\":\"30\",\"q\":\"30\",\"r\":\"150\",\"s\":\"150\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"150\"},{\"U\":\"1012306\",\"a\":\"1\",\"b\":\"1\",\"c\":\"700000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Lucky Tree Branch Nose\",\"T\":\"1012058\",\"X\":136666666,\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Face Accessory\",\"i\":\"10\",\"j\":\"10\",\"k\":\"10\",\"l\":\"10\",\"m\":\"14\",\"p\":\"10\",\"r\":\"6\",\"s\":\"6\",\"t\":\"3\",\"u\":\"3\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"10\"},{\"U\":\"1432187\",\"a\":\"1\",\"b\":\"1\",\"c\":\"1500000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Sweetwater Spear\",\"T\":\"1432187\",\"X\":91365064,\"Q\":\"Equip\",\"R\":\"Two-Handed Weapon\",\"S\":\"Spear\",\"Y\":\"0\",\"i\":\"6\",\"j\":\"97\",\"k\":\"85\",\"n\":\"255\",\"o\":\"255\",\"p\":\"294\",\"t\":\"173\",\"C\":\"30\",\"D\":\"10\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"160\"},{\"U\":\"1122057\",\"a\":\"1\",\"b\":\"1\",\"c\":\"5000000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Awakening Mind of Maple Necklace\",\"T\":\"1122052\",\"P\":\"A Mind of Maple Necklace that is beginning to be restored. One more gem, and its mystical powers will be amplified and\\n\\n awakened into a power on another level.\",\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Pendant\",\"Y\":\"0\",\"p\":\"15\",\"q\":\"15\",\"r\":\"5\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"70\"}]},{\"seconds_ago\":\"797\"}]";
		try {
			obj = new HandleItemJson(HomeActivity.this);
			obj.execute(result);
			
		} catch (JSONException e) {
			Toast.makeText(myApp, "aaa", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		
		searchEditText = (EditText) findViewById(R.id.searchEditText);
//		searchEditText.setSelectAllOnFocus(true);
		searchEditText.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				searchEditText.setText("");
		        return false;
			}
		});
		searchEditText.addTextChangedListener(new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		        System.out.println("Text ["+s+"]");
		        adapter.getFilter().filter(s.toString());    
		       
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
		            int after) {

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    }
		});
		refreshButton.setOnClickListener(new OnClickListener() {
			
			private String urlToRssFeed;

			@Override
			public void onClick(View v) {

	//			String result = "{\"result\":[{\"fm_items\":[{\"U\":\"1102484\",\"a\":\"1\",\"b\":\"1\",\"c\":\"3750000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Tyrant Lycaon Cloak\",\"T\":\"1102481\",\"X\":3471928570,\"Q\":\"Equip\",\"R\":\"Armor\",\"S\":\"Cape\",\"Y\":\"0\",\"h\":\"2\",\"j\":\"50\",\"k\":\"50\",\"l\":\"50\",\"m\":\"50\",\"p\":\"30\",\"q\":\"30\",\"r\":\"150\",\"s\":\"150\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"150\"},{\"U\":\"1012306\",\"a\":\"1\",\"b\":\"1\",\"c\":\"700000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Lucky Tree Branch Nose\",\"T\":\"2870295\",\"X\":136666666,\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Face Accessory\",\"i\":\"10\",\"j\":\"10\",\"k\":\"10\",\"l\":\"10\",\"m\":\"14\",\"p\":\"10\",\"r\":\"6\",\"s\":\"6\",\"t\":\"3\",\"u\":\"3\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"10\"},{\"U\":\"1432187\",\"a\":\"1\",\"b\":\"1\",\"c\":\"1500000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Sweetwater Spear\",\"T\":\"1432187\",\"X\":91365064,\"Q\":\"Equip\",\"R\":\"Two-Handed Weapon\",\"S\":\"Spear\",\"Y\":\"0\",\"i\":\"6\",\"j\":\"97\",\"k\":\"85\",\"n\":\"255\",\"o\":\"255\",\"p\":\"294\",\"t\":\"173\",\"C\":\"30\",\"D\":\"10\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"160\"},{\"U\":\"1122057\",\"a\":\"1\",\"b\":\"1\",\"c\":\"5000000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Awakening Mind of Maple Necklace\",\"T\":\"1122052\",\"P\":\"A Mind of Maple Necklace that is beginning to be restored. One more gem, and its mystical powers will be amplified and awakened into a power on another level.\",\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Pendant\",\"Y\":\"0\",\"p\":\"15\",\"q\":\"15\",\"r\":\"5\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"70\"}]},{\"seconds_ago\":\"797\"}]}";
				String result = null;
				findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
				refreshButton.setVisibility(View.GONE);
				new RetrieveJSonTask(HomeActivity.this).execute(getSearchRequestURL());		
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener(){


			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Item item = (Item) adapter.getAdapter().getItem(position);
				myApp.setDrawable(item.getDrawableImage());
				ItemDetailDialog dialog = ItemDetailDialog.newInstance(item.getJSONString());
				if (dialog == null)	return;
				FragmentManager fm = getFragmentManager();
				dialog.show(fm, "language");
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
	
	private String getSearchRequestURL(){
		int server = myApp.getServer();
		String URL =  getResources().getString(R.string.api_search);
		URL = URL + "server=" + server;
		return URL;
	}
	
	public MapleFreeMarketApplication getMyApp() {
		return myApp;
	}
	
	private void sortableColumnSetup(){
		List<TextView> columns = new ArrayList<TextView>();
		columns.add((TextView) findViewById(R.id.itemColTextView));
		columns.add((TextView) findViewById(R.id.QtyColTextView));
		columns.add((TextView) findViewById(R.id.priceColTextView));
		columns.add((TextView) findViewById(R.id.ChColTextView));
		columns.add((TextView) findViewById(R.id.RmColTextView));
		columns.add((TextView) findViewById(R.id.percentColTextView));
		class myObj{
			String str;
			int idx;
			public myObj(String str, int idx){
				this.str = str;
				this.idx = idx;
			}
		};
		final HashMap<String, myObj> mmap = new HashMap<String, myObj>();
		mmap.put("Item", new myObj("Item Name", 0));
		mmap.put("Qty", new myObj("Quantity", 1));
		mmap.put("Price", new myObj("Price", 2));
		mmap.put("Ch", new myObj("Channel", 3));
		mmap.put("Rm", new myObj("Room", 4));
		mmap.put("%", new myObj("Percent", 5));
		String[] name = new String[]{"Item Name","Quantity","Price", "Channel", "Room", "Percent"};
		tableRow = (TableRow) findViewById(R.id.sortableColumn);
//		tableRow.setBackgroundColor(Color.parseColor("#ffffff"));
		for (TextView col : columns) 
        {
			col.setTextColor(Color.parseColor("#278bd3"));
			col.setOnClickListener(
                new OnClickListener() 
                {
                	@Override
                    public void onClick(View arg0) 
                    {
                		String colName = ((TextView) arg0).getText().toString();
                		Toast.makeText(myApp, mmap.get(colName).str, Toast.LENGTH_SHORT).show();
                		myApp.getItemAdapter().sortByAttribute(mmap.get(colName).idx, descs[mmap.get(colName).idx]);
                		descs[mmap.get(colName).idx] = !descs[mmap.get(colName).idx];
                    }
                });
        }
		
	}
	
	private void setSpinnerContent( )
	{	
		spinner = (Spinner) findViewById(R.id.serverSpinner);
		spinner.setAdapter(new MyAdapter(HomeActivity.this, R.layout.row,
				serverNames, serverImages));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				myApp.setServer(position);
				findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
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

	
}

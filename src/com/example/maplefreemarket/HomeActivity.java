package com.example.maplefreemarket;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import com.code.freeMarket.R;
import com.example.acountManagement.AccessAcountSettings;
import com.example.acountManagement.SignupDialog;
import com.example.acountManagement.UserProfilePanelActivity;
import com.example.articlesManagement.ArticlesActivity;
import com.example.asyncTasks.HandleItemListJSON;
import com.example.asyncTasks.HandleNotificationJSON;
import com.example.asyncTasks.RetriveJSONAPITask;
import com.example.asyncTasks.RetriveJSONTask;
import com.example.infoClasses.FMItem;
import com.example.interfaces.MyDialogFragmentListener;
import com.example.interfaces.SwipeInterface;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;



public class HomeActivity extends ActionBarActivity implements MyDialogFragmentListener, SwipeInterface{
///	private InterstitialAd interstitial;

	private Spinner spinner;
	private Button refreshButton;
	private ListView listView;
	private MapleFreeMarketApplication myApp;
	private View loadingView;
	private String selection;
	private SortAttrViewHolder sortAttrViewHolder;
	private Integer[] serverImages;
	private String[] serverNames;
	private HandleItemListJSON obj;
//	private OkHttpClient client;
	private ItemArrayAdapter adapter;
	private AnimationAdapter animator;
	private EditText searchEditText;
	private CheckBox cashItemCheckBox;
	private CheckBox soldItemCheckBox;
	private Button finishButton;
	private ActivitySwipeDetector swipe;

	class SortAttrViewHolder {
		int size = 6;
		int selected;
		public List<TextView> attrs;
		public boolean[] descs;
		public HashMap<String, myObj> mmap;
		class myObj{
			String str;
			int idx;
			public myObj(String str, int idx){
				this.str = str;
				this.idx = idx;
			}
		};
		public SortAttrViewHolder(int selected) {
			this.selected = selected;
			this.attrs = new ArrayList<TextView>(6);
			this.descs = new boolean[6];
			this.mmap = new HashMap<String, myObj>();
		}
		public void setList(List<TextView> list) {
			for (int i = 0; i < list.size(); i++){
				attrs.add(list.get(i));
			}
			mmap.put("Item", new myObj("Item Name", 0));
			mmap.put("Qty", new myObj("Quantity", 1));
			mmap.put("Price", new myObj("Price", 2));
	/*		mmap.put("Ch", new myObj("Channel", 3));
			mmap.put("Rm", new myObj("Room", 4));*/
			mmap.put("Pot", new myObj("Potential", 3));
			mmap.put("¡î", new myObj("Enhancement", 4));
			mmap.put("%", new myObj("Percent", 5));
		}
		public void performSort(int idx) {
			sortAttrViewHolder.attrs.get(sortAttrViewHolder.selected).setTextColor(Color.parseColor("#278bd3"));
			TextView newAttr = attrs.get(idx);
			newAttr.setTextColor(Color.parseColor("#e56193"));
    		String colName = newAttr.getText().toString();
    		sortAttrViewHolder.selected = idx;
    		Toast.makeText(myApp, sortAttrViewHolder.mmap.get(colName).str, Toast.LENGTH_SHORT).show();
    		animator.reset();
    		adapter.sortByAttribute(idx, sortAttrViewHolder.descs[idx]);
    		sortAttrViewHolder.descs[idx] = !sortAttrViewHolder.descs[idx];
    		myApp.saveSortConfiguration(idx);
		}
		//itemTextView;
		//quantityTextView;
		//priceTextView;
		//potentialTextView;
		//enhancementTextView;
		//percentageView;
    }
	
	//	private TableRow tableRow;
	public EditText getSearchEditText() {
		return searchEditText;
	}
	
	public ListView getListView() {
		return listView;
	}
	
	public ItemArrayAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ItemArrayAdapter adapter) {
		this.adapter = adapter;
	}

	public HandleItemListJSON getObj() {
		return obj;
	}

	public void setObj(HandleItemListJSON obj) {
		this.obj = obj;
	}
	
	
	@Override
	protected void onResume(){
		super.onResume();
/*		if (myApp.init != 0)
		retriveNotification(1);*/
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApp = (MapleFreeMarketApplication) this.getApplication();
		sortAttrViewHolder = new SortAttrViewHolder(myApp.getSortConfiguration());
		setContentView(R.layout.activity_main);
		spinner = (Spinner) findViewById(R.id.serverSpinner);
		listView = (ListView) findViewById(R.id.itemListView);
		findViewById(R.id.loadingPanel).setVisibility(View.GONE);
		adapter = new ItemArrayAdapter(HomeActivity.this, new ArrayList<FMItem>());
		
		ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
//		AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter (adapter);
//		SwingLeftInAnimationAdapter animationAdapter = new SwingLeftInAnimationAdapter(adapter);
//		ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
//		ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
		animator = animationAdapter;
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);
//		ItemArrayAdapter oriAdapter = new ItemArrayAdapter(HomeActivity.this, new ArrayList<FMItem>());
		myApp.setItemAdapter(adapter);
//		listView.setAdapter(adapter);
		cashItemCheckBox = (CheckBox) findViewById(R.id.cashItemCheckBox);
		soldItemCheckBox = (CheckBox) findViewById(R.id.soldItemCheckBox);
		refreshButton = (Button) findViewById(R.id.refreshButton);
		finishButton = (Button) findViewById(R.id.finishButton);
		loadingView = findViewById(R.id.loadingPanel);
		finishButton.setVisibility(View.GONE);
		setSpinnerContent();
		sortableColumnSetup();
		String result = "[{\"fm_items\":[{\"U\":\"1102484\",\"a\":\"1\",\"b\":\"1\",\"c\":\"3750000000\",\"d\":\"5\",\"e\":\"5\",\"f\":\"Click Me!\",\"g\":\"eurekaG1\",\"O\":\"Tyrant Lycaon Cloak\",\"T\":\"1102481\",\"X\":3471928570,\"Q\":\"Equip\",\"R\":\"Armor\",\"S\":\"Cape\",\"Y\":\"0\",\"h\":\"2\",\"j\":\"999\",\"k\":\"999\",\"l\":\"999\",\"m\":\"999\",\"p\":\"999\",\"q\":\"999\",\"r\":\"999\",\"s\":\"999\",\"F\":\"0\",\"G\":\"4\",\"H\":\"15\",\"W\":\"999\"},{\"U\":\"2049300\",\"a\":\"11\",\"b\":\"1\",\"c\":\"84999999\",\"d\":\"1\",\"e\":\"11\",\"f\":\"Click me!\",\"g\":\"Example\",\"O\":\"Advanced Equip Enhancement Scroll\",\"T\":\"5530246\",\"X\":38872403,\"P\":\"Enhances #cupgraded equipment#.\\nMore successes increase the chance of a good enhancement.\\nThe item is destroyed upon failure. Cannot be used on 15-star+ items.\\n\\n#c[Enhancement Success Rate]#\\n1 success: 100%\\n2 successes: 90%\\n3 successes: 80%\\n4 successes: 70%\\n5 successes: 60%\\n6 successes: 50%\\n7 successes: 40%\\n8 successes: 30%\\n9 successes: 20%\\n10 successes: 10%\\n11+ successes: 5% or lower\",\"Q\":\"Use\",\"R\":\"Armor Scroll\",\"S\":\"Accessory\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\"},{\"U\":\"1012306\",\"a\":\"1\",\"b\":\"1\",\"c\":\"700000000\",\"d\":\"3\",\"e\":\"3\",\"f\":\"Click Me!\",\"g\":\"Example\",\"O\":\"Lucky Tree Branch Nose\",\"T\":\"1012058\",\"X\":136666666,\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Face Accessory\",\"i\":\"10\",\"j\":\"10\",\"k\":\"10\",\"l\":\"10\",\"m\":\"14\",\"p\":\"10\",\"r\":\"6\",\"s\":\"6\",\"t\":\"3\",\"u\":\"3\",\"F\":\"0\",\"G\":\"3\",\"H\":\"6\",\"W\":\"10\"},{\"U\":\"1432187\",\"a\":\"1\",\"b\":\"1\",\"c\":\"1500000000\",\"d\":\"3\",\"e\":\"2\",\"f\":\"Click Me!\",\"g\":\"Example\",\"O\":\"Sweetwater Spear\",\"T\":\"1432187\",\"X\":91365064,\"Q\":\"Equip\",\"R\":\"Two-Handed Weapon\",\"S\":\"Spear\",\"Y\":\"0\",\"i\":\"6\",\"j\":\"97\",\"k\":\"85\",\"n\":\"255\",\"o\":\"255\",\"p\":\"294\",\"t\":\"173\",\"C\":\"30\",\"D\":\"10\",\"F\":\"0\",\"G\":\"2\",\"H\":\"0\",\"W\":\"160\"},{\"U\":\"1122057\",\"a\":\"1\",\"b\":\"1\",\"c\":\"5000000000\",\"d\":\"1\",\"e\":\"2\",\"f\":\"Click Me!\",\"g\":\"Example\",\"O\":\"Awakening Mind of Maple Necklace\",\"T\":\"1122052\",\"P\":\"A Mind of Maple Necklace that is beginning to be restored. One more gem, and its mystical powers will be amplified and awakened into a power on another level.\",\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Pendant\",\"Y\":\"0\",\"p\":\"15\",\"q\":\"15\",\"r\":\"5\",\"F\":\"0\",\"G\":\"1\",\"H\":\"0\",\"W\":\"70\"}]},{\"seconds_ago\":\"999999\"}]";
	//	[{"fm_items":[{"U":"1102484","a":"1","b":"1","c":"3750000000","d":"5","e":"5","f":"Click Me!","g":"eurekaG1","O":"Tyrant Lycaon Cloak","T":"1102481","X":3471928570,"Q":"Equip","R":"Armor","S":"Cape","Y":"0","h":"2","j":"999","k":"999","l":"999","m":"999","p":"999","q":"999","r":"999","s":"999","F":"0","G":"4","H":"15","W":"999"},{"U":"2049300","a":"11","b":"1","c":"84999999","d":"1","e":"11","f":"Click me!","g":"Example","O":"Advanced Equip Enhancement Scroll","T":"5530246","X":38872403,"P":"Enhances #cupgraded equipment#.\\nMore successes increase the chance of a good enhancement.\\nThe item is destroyed upon failure. Cannot be used on 15-star+ items.\\n\\n#c[Enhancement Success Rate]#\\n1 success: 100%\\n2 successes: 90%\\n3 successes: 80%\\n4 successes: 70%\\n5 successes: 60%\\n6 successes: 50%\\n7 successes: 40%\\n8 successes: 30%\\n9 successes: 20%\\n10 successes: 10%\\n11+ successes: 5% or lower","Q":"Use","R":"Armor Scroll","S":"Accessory","F":"0","G":"0","H":"0"},{"U":"1012306","a":"1","b":"1","c":"700000000","d":"3","e":"3","f":"Click Me!","g":"Example","O":"Lucky Tree Branch Nose","T":"1012058","X":136666666,"Q":"Equip","R":"Accessory","S":"Face Accessory","i":"10","j":"10","k":"10","l":"10","m":"14","p":"10","r":"6","s":"6","t":"3","u":"3","F":"0","G":"3","H":"6","W":"10"},{"U":"1432187","a":"1","b":"1","c":"1500000000","d":"3","e":"2","f":"Click Me!","g":"Example","O":"Sweetwater Spear","T":"1432187","X":91365064,"Q":"Equip","R":"Two-Handed Weapon","S":"Spear","Y":"0","i":"6","j":"97","k":"85","n":"255","o":"255","p":"294","t":"173","C":"30","D":"10","F":"0","G":"2","H":"0","W":"160"},{"U":"1122057","a":"1","b":"1","c":"5000000000","d":"1","e":"2","f":"Click Me!","g":"Example","O":"Awakening Mind of Maple Necklace","T":"1122052","P":"A Mind of Maple Necklace that is beginning to be restored. One more gem, and its mystical powers will be amplified and awakened into a power on another level.","Q":"Equip","R":"Accessory","S":"Pendant","Y":"0","p":"15","q":"15","r":"5","F":"0","G":"1","H":"0","W":"70"}]},{"seconds_ago":"999999"}]
		obj = new HandleItemListJSON(HomeActivity.this, 0);
		obj.execute(result);
    	searchEditText = (EditText) findViewById(R.id.searchEditText);
    	swipe = new ActivitySwipeDetector(HomeActivity.this);
    	
/*		searchEditText.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				searchEditText.setText("");
		        return false;
			}
		});
		*/
		cashItemCheckBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				CheckBox checkBox = (CheckBox)view;
//				checkBox.setChecked(!checkBox.isChecked());
				String str = searchEditText.getText().toString();
				adapter.setFilterCashItem(checkBox.isChecked());
				adapter.getFilter().filter(str + "1");
			}
		});
		
		soldItemCheckBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				CheckBox checkBox = (CheckBox)view;
//				checkBox.setChecked(!checkBox.isChecked());
				String str = searchEditText.getText().toString();
				adapter.setFilterSoldItem(checkBox.isChecked());
				adapter.getFilter().filter(str + "1");
			}
		});
		
		
		searchEditText.addTextChangedListener(new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		        System.out.println("Text ["+s+"]");
	//	        adapter.resetItemsRefresh(filteredData.subList(0, Math.min(10, filteredData.size())));
		        animator.reset();
		        adapter.getFilter().filter(s.toString()+"2");    
	        	finishButton.setVisibility(View.VISIBLE);
		        refreshButton.setVisibility(View.GONE);
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
			
			@Override
			public void onClick(View v) {

	//			String result = "{\"result\":[{\"fm_items\":[{\"U\":\"1102484\",\"a\":\"1\",\"b\":\"1\",\"c\":\"3750000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Tyrant Lycaon Cloak\",\"T\":\"1102481\",\"X\":3471928570,\"Q\":\"Equip\",\"R\":\"Armor\",\"S\":\"Cape\",\"Y\":\"0\",\"h\":\"2\",\"j\":\"50\",\"k\":\"50\",\"l\":\"50\",\"m\":\"50\",\"p\":\"30\",\"q\":\"30\",\"r\":\"150\",\"s\":\"150\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"150\"},{\"U\":\"1012306\",\"a\":\"1\",\"b\":\"1\",\"c\":\"700000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Lucky Tree Branch Nose\",\"T\":\"2870295\",\"X\":136666666,\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Face Accessory\",\"i\":\"10\",\"j\":\"10\",\"k\":\"10\",\"l\":\"10\",\"m\":\"14\",\"p\":\"10\",\"r\":\"6\",\"s\":\"6\",\"t\":\"3\",\"u\":\"3\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"10\"},{\"U\":\"1432187\",\"a\":\"1\",\"b\":\"1\",\"c\":\"1500000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Sweetwater Spear\",\"T\":\"1432187\",\"X\":91365064,\"Q\":\"Equip\",\"R\":\"Two-Handed Weapon\",\"S\":\"Spear\",\"Y\":\"0\",\"i\":\"6\",\"j\":\"97\",\"k\":\"85\",\"n\":\"255\",\"o\":\"255\",\"p\":\"294\",\"t\":\"173\",\"C\":\"30\",\"D\":\"10\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"160\"},{\"U\":\"1122057\",\"a\":\"1\",\"b\":\"1\",\"c\":\"5000000000\",\"d\":\"4\",\"e\":\"4\",\"f\":\"NUT SHOPz\",\"g\":\"DotaMagina\",\"O\":\"Awakening Mind of Maple Necklace\",\"T\":\"1122052\",\"P\":\"A Mind of Maple Necklace that is beginning to be restored. One more gem, and its mystical powers will be amplified and awakened into a power on another level.\",\"Q\":\"Equip\",\"R\":\"Accessory\",\"S\":\"Pendant\",\"Y\":\"0\",\"p\":\"15\",\"q\":\"15\",\"r\":\"5\",\"F\":\"0\",\"G\":\"0\",\"H\":\"0\",\"W\":\"70\"}]},{\"seconds_ago\":\"797\"}]}";
				retriveServerData();	
			}
		});
		finishButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finishButton.setVisibility(View.GONE);
				if (loadingView.getVisibility() == View.GONE)
					refreshButton.setVisibility(View.VISIBLE);
				InputMethodManager imm = (InputMethodManager)getSystemService(
			    	     Context.INPUT_METHOD_SERVICE);
			    imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
			    imm = null;
			}
		});
		
		refreshButton.setVisibility(View.GONE);
		
		listView.setOnScrollListener(newOnScrollListener());
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				if(swipe.action != 0) {
					
		        } else {
		        	ImageView imageView = (ImageView) view.findViewById(R.id.icon1);
					BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
					FMItem item = (FMItem) adapter.getAdapter().getItem(position);
					myApp.setSelectedItem(item);
					myApp.setDrawable(bitmapDrawable);
					bitmapDrawable = null;
					ItemDetailDialog dialog = ItemDetailDialog.newInstance("");
					if (dialog == null)	return;
					FragmentManager fm = getSupportFragmentManager();
					dialog.show(fm, "language");
		        }
				
			}

		});
		listView.setOnTouchListener(swipe);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() { 
			 
            public boolean onItemLongClick(AdapterView<?> adapter, View view,
                    int position, long id) {
            	ImageView imageView = (ImageView) view.findViewById(R.id.icon1);
				BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
				FMItem item = (FMItem) adapter.getAdapter().getItem(position);
				myApp.setSelectedItem(item);
				myApp.setDrawable(bitmapDrawable);
				bitmapDrawable = null;
				ItemSummaryDialog dialog = ItemSummaryDialog.newInstance("");
				if (dialog == null)	return true;
				FragmentManager fm = getSupportFragmentManager();
				dialog.show(fm, "language");
 
                return true; 
            } 
        });
		listView.setLongClickable(true);
	}
	
	private void retriveNotification(int mode) {
		AsyncTask<String, Void, String> asyncTask;
		asyncTask = new HandleNotificationJSON(this, mode);
		new RetriveJSONAPITask(HomeActivity.this, asyncTask, 1).execute("");
	}
	
	private void retriveServerData() {
		
//		PicassoTools.clearCache(Picasso.with(getApplicationContext()));
		AsyncTask<String, Void, String> preTask = myApp.getPreTask();
		Toast.makeText(myApp, "Fetching data...Please wait...", Toast.LENGTH_LONG).show();
		if (preTask != null){
			preTask.cancel(true);
//			Toast.makeText(myApp, "Previous task canceled.", Toast.LENGTH_SHORT).show();
		}
		loadingView.setVisibility(View.VISIBLE);
		refreshButton.setVisibility(View.GONE);
		AsyncTask<String, Void, String> asyncTask;
		asyncTask = new HandleItemListJSON(HomeActivity.this, 1);
		myApp.setPreTask(asyncTask);
		new RetriveJSONTask(HomeActivity.this, asyncTask).execute(getSearchRequestURL());	
		
	}
	
	public OnScrollListener newOnScrollListener(){
		OnScrollListener onScrollListener = new InfiniteScrollListener(adapter.setLoading) {
	        @Override
	        public void loadMore(int page, int totalItemsCount) {
	        	List<FMItem> curDataList = adapter.getFilteredDisplayItems();
	//        	System.out.print(totalItemsCount + "load more");
	        	int size = curDataList.size();
	        	List<FMItem> items = adapter.getFilteredItems();
	        	if (size >= items.size())	return;
	        	adapter.addItemsRefresh(items.subList(size, Math.min(page * 50, items.size())));
	        }
	    };
	    return onScrollListener;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
        case R.id.action_about:
        	AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        	builder.setTitle("About");
        	builder.setMessage(getWhatIsNew());
        	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

               }
           });
        	AlertDialog dialog = builder.show();
        	TextView textView = (TextView) dialog.findViewById(android.R.id.message);
            textView.setTextSize(16);
            return true;
        case R.id.action_profile:
        	AccessAcountSettings settings = AccessAcountSettings.getInstance();
			if (settings.isSignIn()) {
				Intent myIntent = new Intent(this, UserProfilePanelActivity.class);
				startActivity(myIntent);
			} else {
				SignupDialog signupDialog = new SignupDialog();
	    		signupDialog.show(getFragmentManager(), "signup");
			}	
            return true;
        case R.id.action_notify:
        	retriveNotification(1);
        	return true;
        case R.id.action_article:
        	Intent myIntent = new Intent(this, ArticlesActivity.class);
			startActivity(myIntent);
        default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	
	private String getWhatIsNew() {
		StringBuilder sb = new StringBuilder();
    	sb.append("V 2.008\n\n");
    	sb.append("What's new:\n\n");
    	sb.append("-add user profile \n");
    	sb.append("-add simple animation \n");
    	sb.append("-add small forum \n");
    	sb.append("-fix bugs \n\n");
    	sb.append("Tips:\n\n");
    	sb.append("-Sorting\n");
    	sb.append("-You can tap the 'sort by' row to choose sort category\n");
    	sb.append("-or swipe screen to change sort category\n");
    	sb.append("-Item\n");
    	sb.append("-You can short press item to open item detail dialog\n");
    	sb.append("-or long press item to open item summary dialog\n\n");
    	sb.append("-Posts\n");
    	sb.append("-You can post anything related to MapleStory.\n");
    	sb.append("-You can reply to any posts.\n\n");
    	sb.append("-If you find any bugs or have any suggestions, please feel free to contact me.\n");
    	sb.append("-If you like this app, please give it a rating :)\n");
    	sb.append("\n\n");
    	sb.append("Happy Mapling!");
    	return sb.toString();
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
		sortAttrViewHolder.setList(columns);
		for (TextView col : columns) 
        {
			col.setTextColor(Color.parseColor("#278bd3"));
			col.setOnClickListener(
                new OnClickListener() 
                {
                	@Override
                    public void onClick(View arg0) 
                    {
                		sortAttrViewHolder.attrs.get(sortAttrViewHolder.selected).setTextColor(Color.parseColor("#278bd3"));
                		((TextView) arg0).setTextColor(Color.parseColor("#e56193"));
                		String colName = ((TextView) arg0).getText().toString();
                		int idx = sortAttrViewHolder.mmap.get(colName).idx;
                		sortAttrViewHolder.selected = idx;
                		Toast.makeText(myApp, sortAttrViewHolder.mmap.get(colName).str, Toast.LENGTH_SHORT).show();
                		adapter.sortByAttribute(idx, sortAttrViewHolder.descs[idx]);
                		sortAttrViewHolder.descs[idx] = !sortAttrViewHolder.descs[idx];
                		myApp.saveSortConfiguration(idx);
                    }
                });
        }
		int idx = myApp.getSortConfiguration();
		sortAttrViewHolder.attrs.get(idx).setTextColor(Color.parseColor("#e56193"));
	}
	
	private void setSpinnerContent( )
	{	
		serverImages = new Integer[] { R.drawable.scania, R.drawable.windia,
				R.drawable.bera, R.drawable.broa, R.drawable.khaini, R.drawable.mardia, R.drawable.arcania,
				R.drawable.bellocan, R.drawable.renegades};
		serverNames = getResources().getStringArray(R.array.servers); 
		spinner = (Spinner) findViewById(R.id.serverSpinner);
		spinner.setAdapter(new MyAdapter(HomeActivity.this, R.layout.row,
				serverNames, serverImages));
		spinner.setSelection(myApp.getServerConfiguration());
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
				myApp.saveServerConfiguration(position);
//				Toast.makeText(myApp, selection +" is selected", Toast.LENGTH_SHORT).show();
				retriveServerData();
				if (myApp.init == 1 || myApp.init == -1) {
					retriveNotification(1);		//0-show as needed, 1-show 
				} 
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});

	}

	@Override
	public void onReturnValue(String result) {
		searchEditText.setText(result);	
	}

	@Override
	public void right2left(View v) {
		int currentIdx = sortAttrViewHolder.selected;
		int nextIdx = (currentIdx + 1) % sortAttrViewHolder.size;
		sortAttrViewHolder.performSort(nextIdx);
	//	Toast.makeText(myApp, "swipe to right", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void left2right(View v) {
	//	Toast.makeText(myApp, "swipe to left", Toast.LENGTH_SHORT).show();
		int currentIdx = sortAttrViewHolder.selected;
		int nextIdx = (currentIdx - 1);
		if (nextIdx < 0)	nextIdx = sortAttrViewHolder.size - 1;
		sortAttrViewHolder.performSort(nextIdx);
	}


	
}

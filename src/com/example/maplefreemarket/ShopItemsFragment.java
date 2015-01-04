package com.example.maplefreemarket;


import java.util.ArrayList;
import java.util.List;

import com.code.freeMarket.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ShopItemsFragment extends Fragment{
	private View view;
	private ListView listView;
	private MapleFreeMarketApplication myApp;
	private String characterName;
	private ItemArrayAdapter adapter;
	private Fragment rootFragment;
	private List<Item> items;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.seller_info_fragment, container, false);
		this.view = view;
		this.rootFragment = this;
		myApp = (MapleFreeMarketApplication) this.getActivity().getApplication();
		Bundle bundle = getArguments();
		characterName = bundle.getString("characterName").toLowerCase();
		setListView();
		LoadShopItems loading = new LoadShopItems();
		loading.execute("1");
		return view;
	} 
	
	
	private void setListView(){
		listView = (ListView) view.findViewById(R.id.itemInShopListView);	
		items = new ArrayList<Item>();
		adapter = new ItemArrayAdapter(getActivity(), items);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Item item = (Item) adapter.getAdapter().getItem(position);
				myApp.setDrawable(item.getDrawableImage());
				ItemDetailDialog dialog = ItemDetailDialog.newInstance(item.getJSONString());
				if (dialog == null)	return;
				FragmentManager fm = rootFragment.getFragmentManager();
				dialog.show(fm, "language");
			}

		});
	}
	
	private List<Item> getSellerItems(){
		List<Item> allItems = myApp.getItemAdapter().getItems();
		List<Item> filteredItems = new ArrayList<Item>();
		String filterableString;
		for (int i = 0; i < allItems.size(); i++) {
			filterableString = characterName;
			if (allItems.get(i).getCharacterName().toLowerCase().contains(filterableString) ) {
				filteredItems.add(allItems.get(i));
			}
		}
		return filteredItems;
	}
	
	private class LoadShopItems extends AsyncTask<String, Integer, List<Item>> {
	     protected List<Item> doInBackground(String... urls) {
	         return items = getSellerItems();
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(List<Item> result) {
	         int totalSize = result.size();
	    	 adapter.setItems(items);
	 		 adapter.notifyDataSetChanged();
	         Toast.makeText(getActivity(), result + "items. ", Toast.LENGTH_SHORT);
	     }
	 }
	

}

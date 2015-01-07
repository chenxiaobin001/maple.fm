package com.example.maplefreemarket;


import java.util.ArrayList;
import java.util.List;

import com.code.freeMarket.R;
import com.example.maplefreemarket.SellerInfoFragment.OnImageLoadedListener;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ShopItemsFragment extends Fragment{
	private View view;
	private ListView listView;
	private MapleFreeMarketApplication myApp;
	private String characterName;
	private String shopName;
	private ItemArrayAdapter adapter;
	private Fragment rootFragment;
	private List<FMItem> items;
	
	// setup the fragment view
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.shop_info_fragment, container, false);
		this.view = view;
		this.rootFragment = this;
		myApp = (MapleFreeMarketApplication) this.getActivity().getApplication();
		Bundle bundle = getArguments();
		characterName = bundle.getString("characterName");
		shopName = bundle.getString("shopName");
		TextView sellerTextView = (TextView) view.findViewById(R.id.sellerAndShopTitleTextView);
		sellerTextView.setText(characterName + "'s shop");
		TextView shopTextView = (TextView) view.findViewById(R.id.ShopDescTitleTextView);
		shopTextView.setText(shopName);
		setListView();	
		return view;
	} 
	
	@Override
	public void onStart (){
		super.onStart();
		view.findViewById(R.id.shopLoadingPanel).setVisibility(View.VISIBLE);
		LoadShopItems loading = new LoadShopItems();
		loading.execute("1");
	}
	
	private void setListView(){
		listView = (ListView) view.findViewById(R.id.itemInShopListView);	
		items = new ArrayList<FMItem>();
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
	
	private List<FMItem> getSellerItems(){
		//TODO
		List<FMItem> allItems = myApp.getItemAdapter().getItems();
		List<FMItem> filteredItems = new ArrayList<FMItem>();
		String filterableString;
		filterableString = characterName.toLowerCase();
		for (int i = 0; i < allItems.size(); i++) {		
			if (allItems.get(i).getCharacterName().toLowerCase().contains(filterableString) ) {
				filteredItems.add(allItems.get(i));
			}
		}
		return filteredItems;
	}
	
	private class LoadShopItems extends AsyncTask<String, Integer, List<FMItem>> {
	     protected List<FMItem> doInBackground(String... urls) {
	         items = getSellerItems();
	         adapter.setItems(items);
	 		 return items;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(List<FMItem> result) {
	    	 if (getActivity() == null)	 return;
	         int totalSize = result.size();
	         Toast.makeText(getActivity(), totalSize + " items. ", Toast.LENGTH_SHORT).show();
	         view.findViewById(R.id.shopLoadingPanel).setVisibility(View.GONE);
	         adapter.notifyDataSetChanged();
	     }
	 }
	
	 public void setSellerImage(Bitmap bitmap){
		ImageView iv; 
		while ((iv = (ImageView) view.findViewById(R.id.sellerImageButton)) == null ){
			;
		}
		int width = iv.getMeasuredHeight();
     	int height = (int) (width*1.0/bitmap.getWidth() *bitmap.getHeight());
     	bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
     	iv.setImageBitmap(bitmap);
	 }

}

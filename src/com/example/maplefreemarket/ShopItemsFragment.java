package com.example.maplefreemarket;


import java.util.ArrayList;
import java.util.List;

import com.code.freeMarket.R;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ShopItemsFragment extends Fragment{
	private View view;
	private ListView listView;
	private MapleFreeMarketApplication myApp;
	private String characterName;
	private String shopName;
	private ItemArrayAdapter adapter;
	private Fragment rootFragment;
	
	// setup the fragment view
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.shop_info_fragment, container, false);
		this.view = view;
		this.rootFragment = this;
		myApp = (MapleFreeMarketApplication) this.getActivity().getApplication();
		Bundle bundle = getArguments();
		adapter = new ItemArrayAdapter(getActivity(), new ArrayList<FMItem>());//do not share adapter with HomeActivity, sync problem!
//		adapter = myApp.getItemAdapter();		
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
	public void onResume(){
		super.onResume();
		LoadShopItems loading = new LoadShopItems();
		view.findViewById(R.id.shopLoadingPanel).setVisibility(View.VISIBLE);
		loading.execute("1");
	}
	
	private void setListView(){
		listView = (ListView) view.findViewById(R.id.itemInShopListView);	
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				FMItem item = (FMItem) adapter.getAdapter().getItem(position);
				final ImageView imageView = (ImageView) view.findViewById(R.id.icon1);
				final BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
				myApp.setSelectedItem(item);
				myApp.setDrawable(drawable);
				ItemDetailDialog dialog = ItemDetailDialog.newInstance("");
				if (dialog == null)	return;
				FragmentManager fm = rootFragment.getFragmentManager();
				dialog.show(fm, "language");
			}

		});
	}
	
	private List<FMItem> getSellerItems(){

		List<FMItem> allItems = myApp.getItemAdapter().getItems();
		List<FMItem> filteredItems = new ArrayList<FMItem>();
		String filterableString;
		filterableString = characterName;
		for (int i = 0; i < allItems.size(); i++) {		
			if (allItems.get(i).getCharacterName().equals(filterableString) ) {
				filteredItems.add(allItems.get(i));
			}
		}
		return filteredItems;
	}
	
	private class LoadShopItems extends AsyncTask<String, Integer, List<FMItem>> {
	     protected List<FMItem> doInBackground(String... urls) {
	    	 //keep complaince with adapter filter operation
	    	 //should not change listView content in a background thread
	    	 return getSellerItems();
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(List<FMItem> result) {
	    	 if (getActivity() == null)	 return;
	         int totalSize = result.size();
	         adapter.setItems(result);
	         Toast.makeText(getActivity(), totalSize + " items. ", Toast.LENGTH_SHORT).show();
	         view.findViewById(R.id.shopLoadingPanel).setVisibility(View.GONE);
	         adapter.resetItemsRefresh(result);
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

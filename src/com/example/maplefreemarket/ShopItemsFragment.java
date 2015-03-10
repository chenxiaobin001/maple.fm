package com.example.maplefreemarket;


import java.util.ArrayList;
import java.util.List;

import com.code.freeMarket.R;
import com.example.infoClasses.FMItem;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
	private String characterName;
	private String shopName;
	private ItemArrayAdapter adapter;
	
	// setup the fragment view
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.shop_info_fragment, container, false);
		this.view = view;
		Bundle bundle = getArguments();
		adapter = new ItemArrayAdapter(getActivity(), new ArrayList<FMItem>());//do not share adapter with HomeActivity, sync problem!
//		adapter = myApp.getItemAdapter();		
		characterName = bundle.getString("characterName");
		shopName = bundle.getString("shopName");
		TextView sellerTextView = (TextView) view.findViewById(R.id.sellerAndShopTitleTextView);
		sellerTextView.setText(characterName + "'s shop");
		TextView shopTextView = (TextView) view.findViewById(R.id.ShopDescTitleTextView);
		shopTextView.setText(shopName);
		
/*		
		List<FMItem> result = getSellerItems();
		int totalSize = result.size();
		adapter.setItems(result, totalSize);
		setListView();*/
		
		return view;
	}
	

	
	@Override
	public void onResume(){
		super.onResume();
		getSellItems();

	}
	
	public void getSellItems(){
		
/*		LoadShopItems loading = new LoadShopItems();
		loading.execute("1");*/

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		  @Override
		  public void run() {
			  LoadShopItems loading = new LoadShopItems();
			  loading.execute("1");
		  }
		}, 500);
	
	}
	
	private void setListView(){
		final MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) MapleFreeMarketApplication.getContext();
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
				FragmentManager fm = getFragmentManager();
				dialog.show(fm, "language");
			}

		});
	}
	
	private List<FMItem> getSellerItems(){

		final MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) MapleFreeMarketApplication.getContext();
		List<FMItem> filteredItems = new ArrayList<FMItem>();
		String filterableString = characterName;
		List<FMItem> shopItems = myApp.getShops().get(filterableString);
		if (shopItems != null) {
			for (int i = 0; i < shopItems.size(); i++) {		
				filteredItems.add(shopItems.get(i));
			}
		}
		return filteredItems;
	}
	
	
	/*private List<FMItem> getSellerItems(){

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
	}*/
	
	public interface OnItemsLoadedListener {
		public void onItemsLoaded();
	};
	
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
	         adapter.setItems(result, totalSize);
	         Toast.makeText(getActivity(), totalSize + " items. ", Toast.LENGTH_SHORT).show();
	         view.findViewById(R.id.shopLoadingPanel).setVisibility(View.GONE);
	         setListView();	
             OnItemsLoadedListener onItemsLoadedListener = (OnItemsLoadedListener) getActivity();
             onItemsLoadedListener.onItemsLoaded();
//	         adapter.resetItemsRefresh(result);
	         // after delete onImageLoaded, on loading lag problem fixed...
	     }
	 }
	
	 public void setSellerImage(Bitmap bitmap){
		ImageView iv; 
		if ((iv = (ImageView) view.findViewById(R.id.sellerImageButton)) == null ) return;
/*		while ((iv = (ImageView) view.findViewById(R.id.sellerImageButton)) == null ){
			;
		}*/
		int width = iv.getMeasuredHeight();
     	int height = (int) (width*1.0/bitmap.getWidth() *bitmap.getHeight());
     	iv.setImageBitmap(Bitmap.createScaledBitmap(bitmap, width, height, false));
	 }

}

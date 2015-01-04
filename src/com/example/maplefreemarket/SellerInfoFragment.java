package com.example.maplefreemarket;

import com.code.freeMarket.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class SellerInfoFragment extends Fragment{
	private View view;
	private ListView listView;
	private MapleFreeMarketApplication myApp;
	private String characterName;
	private Fragment rootFragment;
	
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

	}
	
	
	private class LoadShopItems extends AsyncTask<String, Integer, Integer> {
	     protected Integer doInBackground(String... urls) {
	         int totalSize = 0;
	         return totalSize;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(Integer result) {
	         Toast.makeText(myApp, result + "items. ", Toast.LENGTH_SHORT);
	     }
	 }
	

}

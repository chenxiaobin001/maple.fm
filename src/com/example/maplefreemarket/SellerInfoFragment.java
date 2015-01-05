package com.example.maplefreemarket;

import com.code.freeMarket.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class SellerInfoFragment extends Fragment{
	private View view;
	private MapleFreeMarketApplication myApp;
	private String characterName;
	private String shopName;
	private Fragment rootFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.seller_info_fragment, container, false);
		this.view = view;
		this.rootFragment = this;
		myApp = (MapleFreeMarketApplication) this.getActivity().getApplication();
		Bundle bundle = getArguments();
		characterName = bundle.getString("characterName").toLowerCase();
		getSellerInfo();
		return view;
	} 
	
	private void getSellerInfo(){
		AsyncTask<String, Void, String> parseJSON = new HandleSellerAndShopJSON(myApp, view);
		RetrieveJSonTask task = new RetrieveJSonTask(getActivity(), parseJSON);
		String url = getActivity().getResources().getString(R.string.api_rankings);
		url += ("name=" + characterName);
		task.execute(url);
	}
	

}

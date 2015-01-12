package com.example.maplefreemarket;

import com.code.freeMarket.R;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SellerInfoFragment extends Fragment{
	private View view;
//	private MapleFreeMarketApplication myApp;
	private String characterName;
//	private Fragment rootFragment;
	
	OnImageLoadedListener mCallback;

    // Container Activity must implement this interface
    public interface OnImageLoadedListener {
        public void onImageLoaded(Bitmap bitmap, boolean isSeller);
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.seller_info_fragment, container, false);
		this.view = view;
//		this.rootFragment = this;
//		myApp = (MapleFreeMarketApplication) this.getActivity().getApplication();
		Bundle bundle = getArguments();
		characterName = bundle.getString("characterName").toLowerCase();
//		getSellerInfo();
		return view;
	} 
	
	public void getSellerInfo(){
		AsyncTask<String, Void, String> parseJSON = new HandleSellerAndShopJSON(getActivity(), view);
		RetrieveJSonTask task = new RetrieveJSonTask(getActivity(), parseJSON);
		String url = getActivity().getResources().getString(R.string.api_rankings);
		url += ("name=" + characterName);
		task.execute(url);
	}
	

}

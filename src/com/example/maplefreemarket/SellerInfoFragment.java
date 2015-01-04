package com.example.maplefreemarket;


import com.code.freeMarket.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SellerInfoFragment extends Fragment{
	private View view;
	private ListView listView;
	private MapleFreeMarketApplication myApp;
	private String characterName;
	private ItemArrayAdapter adapter;
	private Fragment rootFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.seller_info_fragment, container, false);
		this.view = view;
		this.rootFragment = this;
		myApp = (MapleFreeMarketApplication) this.getActivity().getApplication();
		Bundle bundle = getArguments();
		characterName = bundle.getString("characterName");
		setListView();
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
		return view;
	} 
	
	
	
	private void setListView(){
		listView = (ListView) view.findViewById(R.id.itemInShopListView);	
		adapter = myApp.getItemAdapter();
		listView.setAdapter(adapter);
		adapter.getFilter().filter(characterName+"2");
	}
	

}

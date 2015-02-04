package com.example.maplefreemarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;

public class ComputeShopItem extends AsyncTask<String, Void, String>{


	public ComputeShopItem(){
	}
	
	private void computeShops(){
		
		MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) MapleFreeMarketApplication.getContext();
		List<FMItem> items = myApp.getItemAdapter().getItems();
		HashMap<String, List<FMItem>> shops = new HashMap<String, List<FMItem>>();
		if (items == null)
				return;
		int sz = items.size();
		for (int i = 0; i < sz; i++){
			String seller = items.get(i).getCharacterName();
			if (shops.get(seller) == null){
				List<FMItem> tmp = new ArrayList<FMItem>();
				shops.put(seller, tmp);
			}
			shops.get(seller).add(items.get(i));
		}
		
		HashMap<String, List<FMItem>> preShops = myApp.getShops();
		if (preShops != null)
			preShops.clear();
		myApp.setShops(shops);
	}
	@Override
	protected String doInBackground(String... params) {
		computeShops();
		return null;
	}

}

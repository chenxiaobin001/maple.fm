package com.example.maplefreemarket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;

public class ComputeMarketSummary extends AsyncTask<String, Void, HashMap<String, FMItemSummary>>{
	private List<FMItem> items;
	private HashMap<String, FMItemSummary> itemSummarys;
	
	public ComputeMarketSummary (List<FMItem> items) {
		if (items == null)	return;
		this.items = items;
		this.itemSummarys = new HashMap<String, FMItemSummary>();
	}
	@Override
	protected HashMap<String, FMItemSummary> doInBackground(String... arg0) {
		Collections.sort(items, FMItem.getComparator(0));
		int sz = items.size();
		if (sz == 0)	return itemSummarys;
		for (int i = 0; i < sz; i++) {
			int qty = items.get(i).getQuantity();
			if (qty == 0)	continue;
			String cur = items.get(i).getItemName();
			if (!itemSummarys.containsKey(cur)) {
				itemSummarys.put(cur, new FMItemSummary(items.get(i)));
			}else {
				FMItemSummary tmp = itemSummarys.get(cur);
				tmp.updateWith(items.get(i));
				itemSummarys.put(cur, tmp);
			}
		}
		HashMap<String, FMItemSummary> tmp = itemSummarys;
		this.items = null;
		this.itemSummarys = null;
		return tmp;
	}
	
	@Override
	protected void onPostExecute(HashMap<String, FMItemSummary> itemSummary) {
		MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) MapleFreeMarketApplication.getContext();
		myApp.setItemSummary(itemSummary);
	}
	
	

}

package com.example.maplefreemarket;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.code.freeMarket.R;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

public class HandleItemListJSON extends AsyncTask<String, Void, String> {
	private String secondsAgo;
	private List<Item> items;
	private Context mContext;
	
	public String getSecondsAgo() {
		return secondsAgo;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public Item[] getItemsArray() {
		Item[] itemArr = items.toArray(new Item[items.size()]);
		return itemArr;
	}

	
	public HandleItemListJSON(Context context){
		items = new ArrayList<Item>();
		this.mContext = context;
	}
	
	private void handleJson(String[] strs) throws JSONException{
		String result = "{\"result\":" + strs[0] + "}";
		JSONObject jObject = new JSONObject(result);
		JSONArray resultArray = jObject.getJSONArray("result");
		secondsAgo = resultArray.getJSONObject(1).getString("seconds_ago");
		JSONArray jArray = resultArray.getJSONObject(0).getJSONArray("fm_items");
		for (int i=0; i < jArray.length(); i++)
		{
		    try {
		        JSONObject oneObject = jArray.getJSONObject(i);
		        // Pulling items from the array
		        Item item = new Item();
		        item.setJSONString(oneObject.toString());
		        item.setBundle(oneObject.optInt("b"));
		        item.setAvgPrice(oneObject.optLong("X"));
		        item.setCategory(oneObject.optString("Q"));
		        item.setChannel(oneObject.optInt("d"));
		        item.setCharacterName(oneObject.optString("g"));
		        item.setDescription(oneObject.optString("P"));
		        item.setDetailcategory(oneObject.optString("S"));
		        item.setIconID(oneObject.optString("T"));
		        item.setId(oneObject.optInt("U"));
		        item.setItemName(oneObject.optString("O"));
		        item.setPrice(oneObject.optLong("c"));
		        item.setQuantity(oneObject.optInt("a"));
		        item.setReqLevel(oneObject.optInt("W"));
		        item.setRoom(oneObject.optInt("e"));
		        item.setShopName(oneObject.optString("f"));
		        item.setSubcategory(oneObject.optString("R"));
		        items.add(item);
		    } catch (JSONException e) {
		        // Oops
		    }
		}
	}

	@Override
	protected String doInBackground(String... JSonString) {
		try {

			handleJson(JSonString);
			return secondsAgo;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (result == null){
			Toast.makeText(mContext, "Failed to get data.", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(((HomeActivity)mContext).getMyApp(), "updated " + getSecondsAgo() + "s ago", Toast.LENGTH_SHORT).show();
		ItemArrayAdapter adapter = ((HomeActivity)mContext).getAdapter();
		adapter.clear();
		adapter.setItems(getItems());
		adapter.notifyDataSetChanged();
		((Activity) mContext).findViewById(R.id.loadingPanel).setVisibility(View.GONE);
		((Activity) mContext).findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
    }
}

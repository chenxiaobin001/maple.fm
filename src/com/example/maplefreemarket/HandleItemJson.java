package com.example.maplefreemarket;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HandleItemJson {
	private String secondsAgo;
	public String getSecondsAgo() {
		return secondsAgo;
	}

	public List<Item> getItems() {
		return items;
	}

	private List<Item> items;
	public HandleItemJson(String itemJson) throws JSONException{
		items = new ArrayList<Item>();
		handleJson(itemJson);
	}
	
	private void handleJson(String result) throws JSONException{
		
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
		        item.setBundle(oneObject.optInt("b"));
		        item.setCategory(oneObject.optString("Q"));
		        item.setChannel(oneObject.optInt("d"));
		        item.setCharacterName(oneObject.optString("g"));
		        item.setDescription(oneObject.optString("P"));
		        item.setDetailcategory(oneObject.optString("S"));
		        item.setIconID(oneObject.optInt("T"));
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
}

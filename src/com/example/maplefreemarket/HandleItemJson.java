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
		        item.setBundle(oneObject.getInt("b"));
		        item.setCategory(oneObject.getString("Q"));
		        item.setChannel(oneObject.getInt("d"));
		        item.setCharacterName(oneObject.getString("g"));
		        item.setDescription(oneObject.getString("P"));
		        item.setDetailcategory(oneObject.getString("S"));
		        item.setIconID(oneObject.getInt("T"));
		        item.setId(oneObject.getInt("U"));
		        item.setItemName(oneObject.getString("O"));
		        item.setPrice(oneObject.getInt("c"));
		        item.setQuantity(oneObject.getInt("a"));
		        item.setReqLevel(oneObject.getInt("W"));
		        item.setRoom(oneObject.getInt("e"));
		        item.setShopName(oneObject.getString("f"));
		        item.setSubcategory(oneObject.getString("R"));
		        items.add(item);
		    } catch (JSONException e) {
		        // Oops
		    }
		}
	}
}

package com.example.maplefreemarket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.code.freeMarket.R;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

public class HandleItemListJSON extends AsyncTask<String, Void, String> {
	private String secondsAgo;
	private List<FMItem> fmItems;
	private HomeActivity mContext;
	
	public String getSecondsAgo() {
		return secondsAgo;
	}

	public List<FMItem> getItems() {
		return fmItems;
	}
	
	public List<FMItem> getItems(int size){
		List<FMItem> newList = new ArrayList<FMItem>(fmItems.subList(0, size));
		return newList;
	}
	public HandleItemListJSON(Context context){
		fmItems = new ArrayList<FMItem>();
		this.mContext = (HomeActivity) context;
	}
	
	private void handleJsonJacksonStreaming(String[] strs) throws JsonParseException, JsonMappingException, IOException{
		String result = strs[0];
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);	
        JsonParser parser = factory.createParser(result);
        while (!parser.isClosed()) {
        	JsonToken token = parser.nextToken();
        	// if its the last token then we are done
            if (token == null)
                break;
            if (JsonToken.FIELD_NAME.equals(token) && "fm_items".equals(parser.getCurrentName())){
            	token = parser.nextToken();
            	TypeReference<List<FMItem>> typeRef = new TypeReference<List<FMItem>>(){};
            	fmItems = parser.readValueAs(typeRef);
            }
            if (JsonToken.FIELD_NAME.equals(token) && "seconds_ago".equals(parser.getCurrentName())){
            	token = parser.nextToken();
            	secondsAgo = parser.getText();
            }
        }
		fmItems.size();
		
	}

/*	private void handleJsonJackson(String[] strs) throws JsonParseException, JsonMappingException, IOException{
		String result = strs[0];
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);	
		JsonNode node = mapper.readTree(result);
		secondsAgo = node.get(1).path("seconds_ago").textValue();
		TypeReference<List<FMItem>> typeRef = new TypeReference<List<FMItem>>(){};
		fmItems = mapper.readValue(node.get(0).get("fm_items").traverse(), typeRef);
		fmItems.size();
		
	}
	*/
	//deprecated
	/*private void handleJson(String[] strs) throws JSONException{
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
	}*/

	@Override
	protected String doInBackground(String... JSonString) {
		try {
			handleJsonJacksonStreaming(JSonString);
	//		handleJsonJackson(JSonString);
	//		handleJson(JSonString);
			return secondsAgo;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) mContext.getApplication();
		if (result == null){
			Toast.makeText(mContext, "Failed to get data.", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(((HomeActivity)mContext).getMyApp(), fmItems.size() + " items, " + "updated " + getSecondsAgo() + "s ago", Toast.LENGTH_SHORT).show();
		ItemArrayAdapter adapter = ((HomeActivity)mContext).getAdapter();
		adapter.clear();
		adapter.setItems(fmItems);
		myApp.setItemAdapter(adapter);
		adapter.resetItemsRefresh(getItems(Math.min(10, fmItems.size())));
//		adapter.notifyDataSetChanged();
		((Activity) mContext).findViewById(R.id.loadingPanel).setVisibility(View.GONE);
		((Activity) mContext).findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
    }
}

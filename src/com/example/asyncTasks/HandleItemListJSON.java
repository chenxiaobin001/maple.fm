package com.example.asyncTasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.code.freeMarket.R;
import com.example.infoClasses.FMItem;
import com.example.infoClasses.FMItemSummary;
import com.example.maplefreemarket.HomeActivity;
import com.example.maplefreemarket.ItemArrayAdapter;
import com.example.maplefreemarket.MapleFreeMarketApplication;
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
	private MapleFreeMarketApplication myApp;
	private int mode;
	
	public String getSecondsAgo() {
		return secondsAgo;
	}
	
	public int getMinutesAgo() {
		return Integer.valueOf(secondsAgo) / 60;
	}

	public List<FMItem> getItems() {
		return fmItems;
	}
	
	public List<FMItem> getItems(int size){
		List<FMItem> newList = new ArrayList<FMItem>(fmItems.subList(0, size));
		return newList;
	}
	public HandleItemListJSON(Context context, int mode){
		fmItems = new ArrayList<FMItem>();
		this.mContext = (HomeActivity) context;
		this.mode = mode;
		this.myApp = (MapleFreeMarketApplication) mContext.getApplication();
	}
	
	public ArrayList<FMItem> handleJsonJacksonStreaming0(String... JSonString) {
		String result = JSonString[0];
		ArrayList<FMItem> ret = new ArrayList<FMItem>();
		if (result == null) return ret;
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);	
		try {
			JsonParser parser = factory.createParser(result);
	        while (!parser.isClosed()) {
	        	JsonToken token = parser.nextToken();
	        	// if its the last token then we are done
	            if (token == null)
	                break;
	            if (JsonToken.FIELD_NAME.equals(token) && "fm_items".equals(parser.getCurrentName())){
	            	token = parser.nextToken();
	            	TypeReference<List<FMItem>> typeRef = new TypeReference<List<FMItem>>(){};
	            	ret = parser.readValueAs(typeRef);
	            }
	            if (JsonToken.FIELD_NAME.equals(token) && "seconds_ago".equals(parser.getCurrentName())){
	            	token = parser.nextToken();
	            	secondsAgo = parser.getText();
	            }
	        }
		}catch (Exception e) {
			
		}
		return ret;
	}
	private void handleJsonJacksonStreaming(String[] strs) throws JsonParseException, JsonMappingException, IOException{
		String result = strs[0];
		if (result == null) return;
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
			if(isCancelled()){
                return null;
            }
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
			((Activity) mContext).findViewById(R.id.loadingPanel).setVisibility(View.GONE);
			((Activity) mContext).findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
			return;
		}
		ItemArrayAdapter adapter = ((HomeActivity)mContext).getAdapter();
		adapter.clear();
		/*************** sort ************/
		List<FMItem> tmp = new ArrayList<FMItem>(fmItems);
		Collections.sort(fmItems, FMItem.getComparator(myApp.getSortConfiguration()));
		Collections.reverse(fmItems);
		adapter.setItems(fmItems, 10);
		myApp.setItemAdapter(adapter);
		
//		adapter.resetItemsRefresh(getItems(Math.min(10, fmItems.size())));
//		adapter.notifyDataSetChanged();
		this.myApp.setPreTask(null);
		if (mode == 1){	//network mode
			Toast.makeText(((HomeActivity)mContext).getMyApp(), fmItems.size() + " items, " + "updated " + getMinutesAgo() + " minutes ago", Toast.LENGTH_SHORT).show();
			((Activity) mContext).findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
			((Activity) mContext).findViewById(R.id.loadingPanel).setVisibility(View.GONE);
		}
		
		
		AsyncTask<String, Void, String> computeShopsTask = new ComputeShopItem();
		computeShopsTask.execute("");
		AsyncTask<String, Void, HashMap<String, FMItemSummary>> computeMarketSummay = new ComputeMarketSummary(tmp);
		computeMarketSummay.execute("");
		this.mContext = null;
		this.myApp = null;
    }
}

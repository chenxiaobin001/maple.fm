package com.example.maplefreemarket;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

public class HandleSellerAndShopJSON extends AsyncTask<String, Void, String> {

	private Context mContext;
	private CharacterInfo characterInfo;
	private TextView view;

	public HandleSellerAndShopJSON(Context context, View view) {
		this.mContext = context;
		this.view = (TextView)view;
		this.characterInfo = new CharacterInfo();
	}
	
	private void handleJson(String[] strs) throws JSONException{
		JSONObject jObject = new JSONObject(strs[0]);
		characterInfo.name = jObject.optString("name");
		characterInfo.level = jObject.optInt("level");
		characterInfo.exp = jObject.optString("exp");
		characterInfo.expRequired = jObject.optString("expRequired");
		characterInfo.job = jObject.optString("job");
		characterInfo.world = jObject.optString("world");
		JSONObject images = jObject.getJSONObject("images"); 	
		characterInfo.image.petImage = images.optString("Pet");
		characterInfo.image.characterImage = images.optString("Character");
		JSONObject rank = jObject.getJSONObject("ranking"); 	
		JSONObject overall = rank.getJSONObject("overall"); 
		characterInfo.ranking.overall.setRankDetail(overall.optInt("move_rank"), overall.optString("move_change"), overall.optLong("rank"));
		JSONObject world = rank.getJSONObject("world"); 
		characterInfo.ranking.world.setRankDetail(world.optInt("move_rank"), world.optString("move_change"), world.optLong("rank"));
		JSONObject job = rank.getJSONObject("job"); 
		characterInfo.ranking.world.setRankDetail(job.optInt("move_rank"), job.optString("move_change"), job.optLong("rank"));
		JSONObject fame = rank.getJSONObject("fame"); 
		characterInfo.fameRank = fame.optLong("rank");
		characterInfo.fame = jObject.optInt("fame");
	}
	
	@Override
	protected String doInBackground(String... JSonString) {
		try {

			handleJson(JSonString);
			return "OK";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (result == null)	return;

    }
}

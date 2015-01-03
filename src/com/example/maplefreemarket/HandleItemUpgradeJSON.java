package com.example.maplefreemarket;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

public class HandleItemUpgradeJSON extends AsyncTask<String, Void, String> {
	private Context mContext;
	private ItemMore itemMore;
	private ItemMore itemMoreUpgrade;
	private TextView view;

	public HandleItemUpgradeJSON(Context context, View view, ItemMore itemMore) {
		this.itemMore = itemMore;
		this.mContext = context;
		this.view = (TextView)view;
		this.itemMoreUpgrade = new ItemMore();
	}
	
	private void handleJson(String[] strs) throws JSONException{
		JSONObject jObject = new JSONObject(strs[0]);
		JSONObject itemJSON = jObject.getJSONObject("item");
		itemMoreUpgrade.str = itemJSON.optInt("incSTR");
		itemMoreUpgrade.dex = itemJSON.optInt("incDEX");
		itemMoreUpgrade.intellegence = itemJSON.optInt("incINT");
		itemMoreUpgrade.luk = itemJSON.optInt("incLUK");
		itemMoreUpgrade.maxHP = itemJSON.optInt("incMHP");
		itemMoreUpgrade.maxMP = itemJSON.optInt("incMMP");
		itemMoreUpgrade.weaponAttack = itemJSON.optInt("incPAD");
		itemMoreUpgrade.magicAttack = itemJSON.optInt("incMAD");
		itemMoreUpgrade.weaponDefence = itemJSON.optInt("incPDD");
		itemMoreUpgrade.magicDefence = itemJSON.optInt("incMDD");
		itemMoreUpgrade.accuracy = itemJSON.optInt("incACC");
		itemMoreUpgrade.avoidability = itemJSON.optInt("incEVA");
		itemMoreUpgrade.jump = itemJSON.optInt("incJump");
		itemMoreUpgrade.speed = itemJSON.optInt("incSpeed");
		
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
		view.setText(itemMore.toString(itemMoreUpgrade));
    }
}
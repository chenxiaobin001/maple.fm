package com.example.maplefreemarket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.code.freeMarket.R;

public class ItemDetailDialog extends DialogFragment {
	
	String item;
	String itemID;
	private MapleFreeMarketApplication myApp;
	private HomeActivity activity;
	private ItemMore itemMore;
	private View view;
	private TextView itemDetailTextView;
	private TextView categoryTextView;
	private TextView itemDescTextView;
	private TextView sellerTextView;
	private TextView shopTextView;
	private ImageView sellerImage;
	
	static ItemDetailDialog newInstance(String jsonString) {
		ItemDetailDialog f = new ItemDetailDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("item", jsonString);
        f.setArguments(args);
        return f;
    }
	
	@Override 
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        updateItemDetails();
    }
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
    	
    	item = getArguments().getString("item");
    	myApp = (MapleFreeMarketApplication) getActivity().getApplication();
    	activity = (HomeActivity) getActivity();
    	JSONObject jObject = null;
		try {
			jObject = new JSONObject(item);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		itemMore = new ItemMore(jObject);
		return setupDialogView(jObject);

    }
    
    private Spanned formatTheString(String original){
    	String newStr = original;
    	String regex = "#c[^#]+#";
    	String regex0 = "<.+>";
    	Pattern p0 = Pattern.compile(regex0);
    	Matcher m0 = p0.matcher(newStr);
    	if(m0.find()) {
    		int start = m0.start();
    		int end = m0.end();
    		String tmp = m0.group().substring(1, m0.group().length() - 1);
    		tmp = "["+tmp+"]";
    		StringBuilder sb = new StringBuilder();
    		sb.append(newStr.subSequence(0, start));
    		sb.append(tmp);
    		sb.append(newStr.substring(end, newStr.length()));
    		newStr = sb.toString();
        }
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(newStr);
    	if(m.find()) {
    		String tmp = m.group().substring(2, m.group().length() - 1);
    		tmp = "<font color=#ffa21f>"+tmp+"</font> ";
    		newStr = newStr.replaceAll(regex, tmp);
        }
    	String regex1 = "#\\*[^#]+#";
    	Pattern p1 = Pattern.compile(regex1);
    	Matcher m1 = p1.matcher(newStr);
    	if(m1.find()) {
    		int start = m1.start();
    		int end = m1.end();
    		String tmp = m1.group().substring(2, m1.group().length() - 1);
    		tmp = "<br /><font color=#612759>* "+tmp+"</font> ";
    		StringBuilder sb = new StringBuilder();
    		sb.append(newStr.subSequence(0, start));
    		sb.append(tmp);
    		sb.append(newStr.substring(end, newStr.length()));
    		newStr = sb.toString();
        }
    	newStr = newStr.replace("\\n", "<br />").replace("\\r", "    ");
    	return Html.fromHtml(newStr);
    }
    
    private String getCategory(JSONObject jObject){
    	
    	String result = "";
    	final String NOFOUND = "N/A";
    	if (jObject == null)	return result;
    	StringBuilder sb = new StringBuilder();
    	sb.append("Category: ");
    	String category = jObject.optString("Q");
    	if (category != null){
    		sb.append(category);
    	}else{
    		sb.append(NOFOUND);
    	}
    	sb.append("/");
    	String subCategory = jObject.optString("R");
    	if (subCategory != null){
    		sb.append(subCategory);
    	}else{
    		sb.append(NOFOUND);
    	}
    	sb.append("/");
    	String detailCategory = jObject.optString("S");
    	if (detailCategory != null){
    		sb.append(detailCategory);
    	}else{
    		sb.append(NOFOUND);
    	}
    	sb.append("\n");
    	return sb.toString();
    }
    
    private Dialog setupDialogView(JSONObject jObject){
    	
    	this.itemID = jObject.optString("U");
    	String itemName = jObject.optString("O"); 	
    	final String itemName1 = jObject.optString("O");
    	String desc = jObject.optString("P");
    	String scroll = jObject.optString("i");
    	String seller = "Seller: " + jObject.optString("g");
    	String shop = "Shop: " + jObject.optString("f");
    	if (scroll != ""){
    		itemName += "(+" + scroll + ")";
    	}
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.item_detail, null);
        categoryTextView = (TextView) view.findViewById(R.id.itemCategoryTextView);
        categoryTextView.setText(getCategory(jObject));
        itemDescTextView = (TextView) view.findViewById(R.id.itemDescTextView);
        itemDescTextView.setText(formatTheString(desc));
        itemDetailTextView = (TextView) view.findViewById(R.id.itemDetailTextView);
        sellerImage = (ImageView) view.findViewById(R.id.sellerImageView);
        String details = itemMore.toString(itemMore);
        if (details == "")	
        	itemDetailTextView.setVisibility(View.INVISIBLE);
        else	
        	itemDetailTextView.setText(details);
        sellerTextView = (TextView) view.findViewById(R.id.sellerTextView);
        sellerTextView.setText(formatTheString(seller));
        shopTextView = (TextView) view.findViewById(R.id.shopTextView);
        shopTextView.setText(formatTheString(shop));
        sellerImage.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showCharacterDetail();
			}
		} );
        
        sellerTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showCharacterDetail();				
			}
		});
        shopTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showCharacterDetail();
			}
		});
        builder.setView(view);
        
        builder.setTitle(itemName).setIcon(myApp.getDrawable())
               .setPositiveButton(R.string.item_button_search, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   myApp.getItemAdapter().getFilter().filter(itemName1);  
                	   EditText searchEditText = (EditText) activity.findViewById(R.id.searchEditText);
                	   searchEditText.setText(itemName1);
                   }
               })
               .setNegativeButton(R.string.item_button_no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    private void showCharacterDetail(){
    	Intent myIntent = new Intent(getActivity(), SellerAndShopActivity.class);
		myIntent.putExtra("charName", itemMore.characterName);
		startActivity(myIntent);	
		dismiss();
    }
    
    private void updateItemDetails(){
    	AsyncTask<String, Void, String> upgradeAsyncTask = new HandleItemUpgradeJSON(getActivity(), itemDetailTextView, itemMore);
    	new RetrieveJSonTask(getActivity(), upgradeAsyncTask).execute(getSearchRequestURL());		
    }
    
    private String getSearchRequestURL(){
		String URL =  getResources().getString(R.string.api_item);
		URL = URL + "id=" + itemID;
		return URL;
    }
/*    private String getPropertyValue(JSONObject jObject, String key, String desc){
    	StringBuilder sb = new StringBuilder();
    	if (jObject.optString(key) != ""){
    		sb.append(desc + ": ");
    		if (key != "h" && key != "A")
    			sb.append("+");
    		sb.append(jObject.optString(key));
    		if (key == "C" || key == "D")
    			sb.append("%");
    		sb.append("\n");		
    	}
    	return sb.toString();
    	
    }*/
   /* private String getItemDetails(JSONObject jObject){

    	StringBuilder sb = new StringBuilder();
    	sb.append(getPropertyValue(jObject, "k", "STR"));
    	sb.append(getPropertyValue(jObject, "j", "DEX"));
    	sb.append(getPropertyValue(jObject, "l", "INT"));
    	sb.append(getPropertyValue(jObject, "m", "LUK"));
    	sb.append(getPropertyValue(jObject, "n", "Max HP"));
    	sb.append(getPropertyValue(jObject, "o", "Max MP"));
    	sb.append(getPropertyValue(jObject, "p", "WEAPON ATTACK"));
    	sb.append(getPropertyValue(jObject, "q", "MAGIC ATTACK"));
    	sb.append(getPropertyValue(jObject, "r", "WEAPON DEFENSE"));
    	sb.append(getPropertyValue(jObject, "s", "MAGIC DEFENSE"));
    	sb.append(getPropertyValue(jObject, "t", "ACCURACY"));
    	sb.append(getPropertyValue(jObject, "u", "AVOIDABILITY"));
    	sb.append(getPropertyValue(jObject, "v", "DILIGENCE"));
    	sb.append(getPropertyValue(jObject, "w", "SPEED"));
    	sb.append(getPropertyValue(jObject, "x", "JUMP"));
  //  	sb.append(getPropertyValue(jObject, "y", "STR"));
    	sb.append(getPropertyValue(jObject, "B", "BATTLE MODE ATTACK"));
    	sb.append(getPropertyValue(jObject, "C", "When attacking bosses, damage"));
    	sb.append(getPropertyValue(jObject, "D", "Ignore Monster DEF"));
    	sb.append(getPropertyValue(jObject, "E", "CRAFTER"));
   // 	sb.append(getPropertyValue(jObject, "F", "Potential"));
   // 	sb.append(getPropertyValue(jObject, "G", "Rank"));
   // 	sb.append(getPropertyValue(jObject, "H", "Enhancements applied"));
    	sb.append(getPropertyValue(jObject, "I", "1st POTENTIAL"));
    	sb.append(getPropertyValue(jObject, "J", "2st POTENTIAL"));
    	sb.append(getPropertyValue(jObject, "K", "3st POTENTIAL"));
    	sb.append(getPropertyValue(jObject, "L", "1st BONUS POTENTIAL"));
    	sb.append(getPropertyValue(jObject, "M", "2st BONUS POTENTIAL"));
    	sb.append(getPropertyValue(jObject, "N", "3st BONUS POTENTIAL"));
    	sb.append(getPropertyValue(jObject, "h", "NUMBER OF UPGRADES AVAILABLE"));
    	sb.append(getPropertyValue(jObject, "A", "NUMBER OF HAMMER APPLIED"));
 //   	sb.append(getPropertyValue(jObject, "X", "STR"));	
    	return sb.toString();
    }*/
}
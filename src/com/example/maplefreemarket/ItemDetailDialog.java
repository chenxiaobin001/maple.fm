package com.example.maplefreemarket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ItemDetailDialog extends DialogFragment {
	
	String item;
	private MapleFreeMarketApplication myApp;
	
	static ItemDetailDialog newInstance(String jsonString) {
		ItemDetailDialog f = new ItemDetailDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("item", jsonString);
        f.setArguments(args);
        return f;
    }
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
    	
    	item = getArguments().getString("item");
    	myApp = (MapleFreeMarketApplication) getActivity().getApplication();
    	JSONObject jObject = null;
		try {
			jObject = new JSONObject(item);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	String itemName = jObject.optString("O");
    	String desc = jObject.optString("P");
	    
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_detail, null);
        TextView categoryTextView = (TextView) view.findViewById(R.id.itemCategoryTextView);
        categoryTextView.setText(getCategory(jObject));
        TextView itemDescTextView = (TextView) view.findViewById(R.id.itemDescTextView);
        itemDescTextView.setText(formatTheString(desc));
        TextView itemDetailTextView = (TextView) view.findViewById(R.id.itemDetailTextView);
        itemDetailTextView.setText(getItemDetails(jObject));
        builder.setView(view);
        
        builder.setTitle(itemName).setIcon(myApp.getDrawable())
               .setPositiveButton(R.string.item_button_search, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
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
    
    private Spanned formatTheString(String original){
    	String newStr = original.replace("\\n", "<br />").replace("\\r", "    ");
    	String regex = "#c[^#]+#";
    	Spanned result = null;
    	String regex0 = "<.+>";
    	Pattern p0 = Pattern.compile(regex0);
    	Matcher m0 = p0.matcher(newStr);
    	if(m0.find()) {
    		int start = m0.start();
    		int end = m0.end();
    		String t = m0.group();
    		String tmp = m0.group().substring(1, m0.group().length() - 1);
    		tmp = "<font color=#46ea46>"+tmp+"</font> ";
    		StringBuilder sb = new StringBuilder();
    		sb.append(newStr.subSequence(0, start));
    		sb.append(tmp);
    		sb.append(newStr.substring(end, newStr.length()));
    		newStr = sb.toString();
    		result = Html.fromHtml(newStr);
        }
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(newStr);
    	if(m.find()) {
    		String tmp = m.group().substring(2, m.group().length() - 1);
    		tmp = "<font color=#ffa21f>"+tmp+"</font> ";
    		newStr = newStr.replaceAll(regex, tmp);
    		result = Html.fromHtml(newStr);
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
    		result = Html.fromHtml(newStr);
        }
    	
    	return Html.fromHtml(newStr);
    }
    
    private String getCategory(JSONObject jObject){
    	
    	String result = "";
    	final String NOFOUND = "N/A";
    	if (jObject == null)	return result;
    	StringBuilder sb = new StringBuilder();
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
    
    private String getPropertyValue(JSONObject jObject, String key, String desc){
    	StringBuilder sb = new StringBuilder();
    	if (jObject.optString(key) != ""){
    		sb.append(desc + ": +");
    		sb.append(jObject.optString(key));
    		sb.append("\n");		
    	}
    	return sb.toString();
    	
    }
    
    private String getItemDetails(JSONObject jObject){

    	StringBuilder sb = new StringBuilder();
    	sb.append(getPropertyValue(jObject, "k", "STR"));
    	sb.append(getPropertyValue(jObject, "j", "DEX"));
    	sb.append(getPropertyValue(jObject, "l", "INT"));
    	sb.append(getPropertyValue(jObject, "m", "LUK"));
    	sb.append(getPropertyValue(jObject, "n", "Max HP"));
    	sb.append(getPropertyValue(jObject, "o", "Max MP"));
    	sb.append(getPropertyValue(jObject, "p", "Weapon attack"));
    	sb.append(getPropertyValue(jObject, "q", "Magic attack"));
    	sb.append(getPropertyValue(jObject, "r", "Weapon defense"));
    	sb.append(getPropertyValue(jObject, "s", "Magic defense"));
    	sb.append(getPropertyValue(jObject, "t", "Accuracy"));
    	sb.append(getPropertyValue(jObject, "u", "Avoidability"));
    	sb.append(getPropertyValue(jObject, "v", "Diligence"));
    	sb.append(getPropertyValue(jObject, "w", "Speed"));
    	sb.append(getPropertyValue(jObject, "x", "Jump"));
  //  	sb.append(getPropertyValue(jObject, "y", "STR"));
    	sb.append(getPropertyValue(jObject, "A", "Hammers applied"));
    	sb.append(getPropertyValue(jObject, "B", "Battle Mode attack"));
    	sb.append(getPropertyValue(jObject, "C", "Boss damage"));
    	sb.append(getPropertyValue(jObject, "D", "Enemy's defense ignored"));
    	sb.append(getPropertyValue(jObject, "E", "Crafter"));
   // 	sb.append(getPropertyValue(jObject, "F", "Potential"));
   // 	sb.append(getPropertyValue(jObject, "G", "Rank"));
   // 	sb.append(getPropertyValue(jObject, "H", "Enhancements applied"));
    	sb.append(getPropertyValue(jObject, "I", "1st potential"));
    	sb.append(getPropertyValue(jObject, "J", "2st potential"));
    	sb.append(getPropertyValue(jObject, "K", "3st potential"));
    	sb.append(getPropertyValue(jObject, "L", "1st bonus potential"));
    	sb.append(getPropertyValue(jObject, "M", "2st bonus potential"));
    	sb.append(getPropertyValue(jObject, "N", "3st bonus potential"));
 //   	sb.append(getPropertyValue(jObject, "X", "STR"));	
    	return sb.toString();
    }
}
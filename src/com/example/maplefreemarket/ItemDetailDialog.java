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
    	return sb.toString();
    }
    
    private String getItemDetails(JSONObject jObject){
    	String result = "N/A";
    	return result;
    }
}
package com.example.maplefreemarket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import android.widget.ImageView;
import android.widget.TextView;
import com.code.freeMarket.R;

public class ItemDetailDialog extends DialogFragment {
	
	private FMItem selectedItem;
	private MapleFreeMarketApplication myApp;
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
        super.onStart();
        updateItemDetails();
    }
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
    	
       	myApp = (MapleFreeMarketApplication) getActivity().getApplication();
       	selectedItem = myApp.getSelectedItem();
		return setupDialogView();

    }
    
    private Spanned formatTheString(String original){
    	if (original == null)	return null;
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
    	newStr = newStr.replace("\n", "<br />").replace("\r", "    ");
    	return Html.fromHtml(newStr);
    }
    
    private String getCategory(){
    	
    	final String NOFOUND = "N/A";
    	StringBuilder sb = new StringBuilder();
    	sb.append("Category: ");
    	String category = selectedItem.getCategory();
    	if (category != null){
    		sb.append(category);
    	}else{
    		sb.append(NOFOUND);
    	}
    	sb.append("/");
    	String subCategory = selectedItem.getSubcategory();
    	if (subCategory != null){
    		sb.append(subCategory);
    	}else{
    		sb.append(NOFOUND);
    	}
    	sb.append("/");
    	String detailCategory = selectedItem.getDetailcategory();
    	if (detailCategory != null){
    		sb.append(detailCategory);
    	}else{
    		sb.append(NOFOUND);
    	}
    	sb.append("\n");
    	return sb.toString();
    }
    
    private Dialog setupDialogView(){
    	
    	if (selectedItem == null)	return null;
    	String itemTitle = selectedItem.getItemName();
    	final String itemName = selectedItem.getItemName();
    	String desc = selectedItem.getDescription();
    	String scroll = String.valueOf(selectedItem.getScrollApplied());
    	String seller = "Seller: " + selectedItem.getCharacterName();
    	String shop = "Shop: " + selectedItem.getShopName();
    	if (!("0".equals(scroll))){
    		itemTitle += "(+" + scroll + ")";
    	}
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.item_detail, null);
        categoryTextView = (TextView) view.findViewById(R.id.itemCategoryTextView);
        categoryTextView.setText(getCategory());
        itemDescTextView = (TextView) view.findViewById(R.id.itemDescTextView);
        itemDescTextView.setText(formatTheString(desc));
        itemDetailTextView = (TextView) view.findViewById(R.id.itemDetailTextView);
        sellerImage = (ImageView) view.findViewById(R.id.sellerImageView);
        String details = selectedItem.getItemMore().toString(selectedItem.getItemMore());
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
        
        builder.setTitle(itemTitle).setIcon(myApp.getDrawable())
               .setPositiveButton(R.string.item_button_search, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
               // 	   myApp.getItemAdapter().getFilter().filter(itemName + "2");  
                	   ((MyDialogFragmentListener) getActivity()).onReturnValue(itemName);
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
		myIntent.putExtra("charName", selectedItem.getCharacterName());
		myIntent.putExtra("shopName", selectedItem.getShopName());
		startActivity(myIntent);	
		dismiss();
    }
    private void updateItemDetails(){
    	AsyncTask<String, Void, String> upgradeAsyncTask = new HandleItemUpgradeJSON(getActivity(), itemDetailTextView, selectedItem.getItemMore());
    	new RetrieveJSonTask(getActivity(), upgradeAsyncTask).execute(getSearchRequestURL());		
    }
    
    private String getSearchRequestURL(){
		String URL =  getResources().getString(R.string.api_item);
		URL = URL + "id=" + selectedItem.getId();
		return URL;
    }

}
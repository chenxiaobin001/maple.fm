package com.example.maplefreemarket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.interfaces.MyDialogFragmentListener;

public class ItemDetailDialog extends DialogFragment {
	
	private FMItem selectedItem;
	private MapleFreeMarketApplication myApp;
	private View view;
	private View titleView;
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
    
    
    private void setPotentialView(View potentialView){
    	String p1 = selectedItem.getPotential1();
    	String p2 = selectedItem.getPotential2(); 
    	String p3 = selectedItem.getPotential3(); 
    	String bp1 = selectedItem.getBonusPotential1(); 
    	String bp2 = selectedItem.getBonusPotential2(); 
    	String bp3 = selectedItem.getBonusPotential3(); 
    	View separator1 = potentialView.findViewById(R.id.potentialSeparator);
    	View separator2 = potentialView.findViewById(R.id.bonusPotentialSeparator);
    	TextView potentialTextView1 = (TextView) potentialView.findViewById(R.id.potentialTextView1);
    	TextView potentialTextView2 = (TextView) potentialView.findViewById(R.id.potentialTextView2);
    	TextView potentialTextView3 = (TextView) potentialView.findViewById(R.id.potentialTextView3);
    	TextView bonusPotentialTextView1 = (TextView) potentialView.findViewById(R.id.bonusPotentialTextView1);
    	TextView bonusPotentialTextView2 = (TextView) potentialView.findViewById(R.id.bonusPotentialTextView2);
    	TextView bonusPotentialTextView3 = (TextView) potentialView.findViewById(R.id.bonusPotentialTextView3);
    	boolean hasPotential = false;
    	boolean hasBonusPotential = false;
    	if (p1 != null){
    		potentialTextView1.setText(p1);
    		hasPotential = true;
    	}else{
    		potentialTextView1.setVisibility(View.GONE);
    	}
    	if (p2 != null){
    		potentialTextView2.setText(p2);
    		hasPotential = true;
    	}else{
    		potentialTextView2.setVisibility(View.GONE);
    	}
    	if (p3 != null){
    		potentialTextView3.setText(p3);
    		hasPotential = true;
    	}else{
    		potentialTextView3.setVisibility(View.GONE);
    	}
    	if (!hasPotential){
    		separator1.setVisibility(View.GONE);
    	}
    	if (bp1 != null){
    		bonusPotentialTextView1.setText(bp1);
    		hasBonusPotential = true;
    	}else{
    		bonusPotentialTextView1.setVisibility(View.GONE);
    	}
    	if (bp2 != null){
    		bonusPotentialTextView2.setText(bp2);
    		hasBonusPotential = true;
    	}else{
    		bonusPotentialTextView2.setVisibility(View.GONE);
    	}
    	if (bp3 != null){
    		bonusPotentialTextView3.setText(bp3);
    		hasBonusPotential = true;
    	}else{
    		bonusPotentialTextView3.setVisibility(View.GONE);
    	}
    	if (!hasBonusPotential){
    		separator2.setVisibility(View.GONE);
    	}
    }
    
    private void setNebuliteView(View view){
    	String nebulite = selectedItem.getNebuliteId();
    	View sepatator = view.findViewById(R.id.nebuliteSeparator);
    	TextView nebuliteTextView = (TextView) view.findViewById(R.id.nebuliteTextView);
    	if (nebulite == null){
    		sepatator.setVisibility(View.GONE);
    		nebuliteTextView.setVisibility(View.GONE);
    	}else{
    	/*	String regex = "\\[(A|B|C|D|S)\\]";
        	Pattern p0 = Pattern.compile(regex);
        	Matcher m0 = p0.matcher(nebulite);
        	if(m0.find()) {
        		String tmp = m0.group().substring(1, m0.group().length() - 1);
        		if ("A".equals(tmp)){
        			nebuliteTextView.setTextColor(Color.parseColor("#1575f4"));
        		}else if ("B".equals(tmp)){
        			nebuliteTextView.setTextColor(Color.parseColor("#1575f4"));
        		}else if ("C".equals(tmp)){
        			nebuliteTextView.setTextColor(Color.parseColor("#1575f4"));
        		}else if ("D".equals(tmp)){
        			nebuliteTextView.setTextColor(Color.parseColor("#1575f4"));
        		}else if ("S".equals(tmp)){
        			nebuliteTextView.setTextColor(Color.parseColor("#1575f4"));
        		}
        		nebuliteTextView.setText(tmp);
            }*/
    		nebuliteTextView.setTextColor(Color.parseColor("#0eb800"));
    		nebuliteTextView.setText(nebulite);
    	}
    }
    
    private void setTitleView(View titleView){
    	TextView itemNameTextView = (TextView) titleView.findViewById(R.id.itemNameTextView);
    	TextView starTextView = (TextView) titleView.findViewById(R.id.starTextView);
    	TextView rankTextView = (TextView) titleView.findViewById(R.id.rankTextView);
    	ImageView itemDialogIcon = (ImageView) titleView.findViewById(R.id.itemDialogIcon);
    	itemDialogIcon.setImageDrawable(myApp.getDrawable());
    	String itemTitle = selectedItem.getItemName();
    	String scroll = String.valueOf(selectedItem.getScrollApplied());
    	if (!("0".equals(scroll))){
    		itemTitle += "(+" + scroll + ")";
    	}
    	if (itemTitle.length() < 20){
    		itemNameTextView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
    	}
    	itemNameTextView.setTextColor(Color.parseColor("#33b5e5"));
    	if (selectedItem.getEnhancements() > 0){
    		StringBuilder sb = new StringBuilder();
    		for (int i = 1; i <= selectedItem.getEnhancements(); i++){
    			sb.append("✰");
    			if (i % 5 == 0){
    				sb.append(" ");
    			}
    		}
    		String tmp = "<font color=#ffa21f>"+sb.toString()+"</font> ";
    		starTextView.setText(Html.fromHtml(tmp));
    	//	starTextView.setTextColor(Color.parseColor("#FFD700"));
    	}else{
    		starTextView.setText(" ");
    		starTextView.setHeight(20);
        	//	starTextView.setVisibility(View.GONE);
    	}
    	if (Integer.parseInt(selectedItem.getPotentialRank()) > 0){
    		int i = Integer.parseInt(selectedItem.getPotentialRank());
    		String rank = "";
    		String color  = "#33b5e5";
    		switch (i){
    		case 1:	rank = "(Rare item)"; color  = "#1575f4"; break;
    		case 2: rank = "(Epic item)"; color  = "#9124ff"; break;
    		case 3: rank = "(Unique item)"; color  = "#f5b00a"; break;
    		case 4: rank = "(Legendary item)"; color  = "#0eb800"; break;
    		case 5: rank = "(Hidden Potential)"; color  = "#4378ad"; break;
    		}
    		rankTextView.setTextColor(Color.parseColor(color));
    		rankTextView.setText(rank);
    	}else{
    		rankTextView.setText(" ");
    		rankTextView.setHeight(15);
    	//	rankTextView.setVisibility(View.GONE);
    	}
    	itemNameTextView.setText(itemTitle);
    }
    
    private Dialog setupDialogView(){
    	
    	if (selectedItem == null)	return null;   	
    	final String itemName = selectedItem.getItemName();
    	String desc = selectedItem.getDescription(); 	
    	String seller = "Seller: " + selectedItem.getCharacterName();
    	String shop = "Shop: " + selectedItem.getShopName();    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.item_detail, null);
        titleView = inflater.inflate(R.layout.item_detail_title, null);
        categoryTextView = (TextView) view.findViewById(R.id.itemCategoryTextView);
        categoryTextView.setText(getCategory());
        itemDescTextView = (TextView) view.findViewById(R.id.itemDescTextView);
        if ("".equals(desc) || desc == null){
        	itemDescTextView.setVisibility(View.GONE);
        }else{
        	itemDescTextView.setText(formatTheString(desc));
        }
        itemDetailTextView = (TextView) view.findViewById(R.id.itemDetailTextView);
        sellerImage = (ImageView) view.findViewById(R.id.sellerImageView);
        String details = selectedItem.getItemMore().toString(selectedItem.getItemMore());
        if ("".equals(details) || details == null)	
        	itemDetailTextView.setVisibility(View.GONE);
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
        setPotentialView(view);      
        setTitleView(titleView); 
        setNebuliteView(view);
        builder.setCustomTitle(titleView);
        builder.setPositiveButton(R.string.item_button_search, new DialogInterface.OnClickListener() {
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
package com.example.maplefreemarket;

import java.util.HashMap;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.code.freeMarket.R;
import com.example.infoClasses.FMItem;
import com.example.infoClasses.FMItemSummary;
import com.example.interfaces.MyDialogFragmentListener;

public class ItemSummaryDialog extends DialogFragment {
	
	private FMItem selectedItem;
	private View view;
	private TextView itemSummaryTextView;
	private TextView categoryTextView;
	
	static ItemSummaryDialog newInstance(String jsonString) {
		ItemSummaryDialog f = new ItemSummaryDialog();
        return f;
    }
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
    	
    	MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) getActivity().getApplication();
       	selectedItem = myApp.getSelectedItem();
		return setupDialogView();

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
    	MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) MapleFreeMarketApplication.getContext();
    	final String itemName = selectedItem.getItemName();   	
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.item_summary, null);
        categoryTextView = (TextView) view.findViewById(R.id.itemCategoryTextView);
        categoryTextView.setText(getCategory());
        itemSummaryTextView = (TextView) view.findViewById(R.id.itemSummaryTextView);

        setSummaryTextView();
        builder.setView(view);
        builder.setTitle(itemName);
        builder.setIcon(myApp.getDrawable());
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
    
    private void setSummaryTextView() {
    	MapleFreeMarketApplication myApp = (MapleFreeMarketApplication) MapleFreeMarketApplication.getContext();
    	HashMap<String, FMItemSummary> itemSummaries = myApp.getItemSummary();
    	FMItemSummary itemSum = itemSummaries.get(selectedItem.getItemName());
    	if (itemSum == null)	return;
    	StringBuilder sb = new StringBuilder();
    	//tmp = "<br /><font color=#612759>* "+tmp+"</font> ";
    	sb.append("Summary of this item in current free market.<br /><br />");
    	sb.append("Quantity: 			<font color=" + getPriceColor(itemSum.getQuantity()) + ">" + itemSum.getQuantity() + "</font><br />");
    	sb.append("MinPrice: 			<font color=" + getPriceColor(itemSum.getMinPrice()) + ">" + itemSum.getMinPrice() + "</font><br />");
    	sb.append("MaxPrice: 			<font color=" + getPriceColor(itemSum.getMaxPrice()) + ">" + itemSum.getMaxPrice() + "</font><br />");
    	sb.append("AvgPrice:			<font color=" + getPriceColor(itemSum.getAvgPrice()) + ">" + itemSum.getAvgPrice() + "</font><br />");
    	itemSummaryTextView.setText(Html.fromHtml(sb.toString()));
    }
    
    private String getPriceColor(long price){
		  String result = "#000000";
		  if (price < 1e6){
			  result = "#0000ff";
		  }else if (price < 1e7){
			  result = "#0aa90a";
		  }else if (price < 1e8){
			  result = "#f27d0d";
		  }else if (price < 1e9){
			  result = "#e56193";
		  }else{
			  result = "#a5124a";
		  }
		  return result;
	  }
}
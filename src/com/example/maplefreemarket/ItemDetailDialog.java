package com.example.maplefreemarket;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
    	int id = jObject.optInt("T");
	    String url = getActivity().getResources().getString(R.string.item_icon_url) + String.valueOf(id) + ".png";
    	
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_detail, null);
        builder.setView(view);
        
        builder.setTitle(itemName).setMessage(desc).setIcon(myApp.getDrawable())
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
        ImageView image = (ImageView) view.findViewById(R.id.itemImage);
        return builder.create();
    }
}
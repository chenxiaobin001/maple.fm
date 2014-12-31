package com.example.maplefreemarket;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

class ItemArrayAdapter extends ArrayAdapter<Item> {
	  private final Context context;
	  private final List<Item> items;

	  public ItemArrayAdapter(Context context, List<Item> items) {
	    super(context, R.layout.item_row, items);
	    this.context = context;
	    this.items = items;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		
		final Item cur = items.get(position);
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.item_row, parent, false);
	    TextView itemNameTextView = (TextView) rowView.findViewById(R.id.itemNameTextView);
	    TextView itemPriceTextView = (TextView) rowView.findViewById(R.id.itemPriceTextView);
	    TextView channelRoomTextView = (TextView) rowView.findViewById(R.id.roomTextView);
	    TextView quantityTextView = (TextView) rowView.findViewById(R.id.quantityTextView);
	    TextView percentageTextView = (TextView) rowView.findViewById(R.id.percentageTextView);
	    final ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    itemNameTextView.setText(items.get(position).getItemName());
	    itemPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(items.get(position).getPrice()));
	    itemPriceTextView.setTextColor(Color.parseColor(getPriceColor(items.get(position).getPrice())));
	    channelRoomTextView.setText(getRoomChannel(position));
	    quantityTextView.setText(getBundleQuantity(position));
	    String tmp = getPercentage(position);
	    percentageTextView.setText(tmp + "%");
	    percentageTextView.setTextColor(Color.parseColor(getPercentColor(tmp)));
	    int id = items.get(position).getIconID();
	    String url = context.getResources().getString(R.string.item_icon_url) + String.valueOf(id) + ".png";
	    Picasso.with(context).load(url).into(new Target() {

            @Override
            public void onPrepareLoad(Drawable arg0) {


            }

            @Override
            public void onBitmapLoaded(Bitmap bitmap, LoadedFrom arg1) {
            	Drawable d = new BitmapDrawable(context.getResources(),bitmap);
            	imageView.setImageBitmap(bitmap);
            	cur.setDrawableImage(d);
            }

            @Override
            public void onBitmapFailed(Drawable arg0) {


            }

			
        });
	    
	    return rowView;
	  }
	  
	  private String getPercentColor(String percent){
		  String result = "#000000";
		  int percentage = 0;
		  if ("N/A".equals(percent)){
			  result = "#0aa90a";
		  }else if(">999".equals(percent)){
			  result = "#ff0000";
		  }else{ 
			  percentage = Integer.valueOf(percent);
			  if (percentage < 100){
				  result = "#0aa90a";
			  }else if (percentage < 150){
				  result = "#f27d0d";
			  }else{
				  result = "#ff0000";
			  }
		  }
		  return result;
	  }
	  
	  
	  private String getPercentage(int position){
		  long avg = items.get(position).getAvgPrice();
		  long price = items.get(position).getPrice();
		  if (avg == 0){
			  return "N/A";
		  }else{
			  if (price*1.0/avg * 100 > 1000){
				  return ">999";
			  }else
				  return String.format( "%.0f", price*1.0/avg * 100);
		  }
	  }
	  private String getBundleQuantity(int position){
		  StringBuilder sb = new StringBuilder();
		  sb.append("Qty:");
		  sb.append(items.get(position).getQuantity());
/*		  sb.append("(");
		  sb.append(items.get(position).getBundle());
		  sb.append(")");*/
		  return sb.toString();
	  }
	  private String getRoomChannel(int position){
		  StringBuilder sb = new StringBuilder();
		  sb.append("Ch:");
		  sb.append(items.get(position).getChannel());
		  sb.append(" Rm:");
		  sb.append(items.get(position).getRoom());
		  return sb.toString();
		  
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

package com.example.maplefreemarket;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
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

import com.code.freeMarket.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;
import android.widget.Filter;
class ItemArrayAdapter extends ArrayAdapter<Item> {
	  private final Context context;
	  private List<Item> items;
	  private List<Item> filteredData = null;
	  private ItemFilter mFilter = new ItemFilter();

	  public ItemArrayAdapter(Context context, List<Item> items) {
	    super(context, R.layout.item_row, items);
	    this.context = context;
	    this.items = items;
	    this.filteredData = items;
	  }
	  public int getCount() {
		  return filteredData.size();
	  }
	  
	  public void setItems(List<Item> items){
		  this.items = items;
		  this.filteredData = items;
	  }
	  public Item getItem(int position) {
		  return filteredData.get(position);
	  }
	 
	  public long getItemId(int position) {
		  return position;
	  }
	  
	  public void sortByAttribute(int idx, boolean desc){
		  switch (idx){
		  case 0:	Collections.sort(filteredData, Item.getItemNameComparator()); break;
		  case 1:	Collections.sort(filteredData, Item.getQtyComparator()); break;
		  case 2:	Collections.sort(filteredData, Item.getPriceComparator()); break;
		  case 3:	Collections.sort(filteredData, Item.getChannelComparator()); break;
		  case 4: 	Collections.sort(filteredData, Item.getRoomComparator()); break;
		  case 5:	Collections.sort(filteredData, Item.getPercentComparator()); break;
		  }
		  if (desc){
			  Collections.reverse(filteredData);
		  }
		  notifyDataSetChanged();
	  }
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		
			final Item cur = filteredData.get(position);
		    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.item_row, parent, false);
		    TextView itemNameTextView = (TextView) rowView.findViewById(R.id.itemNameTextView);
		    TextView itemPriceTextView = (TextView) rowView.findViewById(R.id.itemPriceTextView);
		    TextView channelRoomTextView = (TextView) rowView.findViewById(R.id.roomTextView);
		    TextView quantityTextView = (TextView) rowView.findViewById(R.id.quantityTextView);
		    TextView percentageTextView = (TextView) rowView.findViewById(R.id.percentageTextView);
		    final ImageView imageView = (ImageView) rowView.findViewById(R.id.icon1);
		    itemNameTextView.setText(filteredData.get(position).getItemName());
		    itemPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(filteredData.get(position).getPrice()));
		    itemPriceTextView.setTextColor(Color.parseColor(getPriceColor(filteredData.get(position).getPrice())));
		    channelRoomTextView.setText(getRoomChannel(position));
		    quantityTextView.setText(getBundleQuantity(position));
		    String tmp = getPercentage(position);
		    percentageTextView.setText(tmp + "%");
		    percentageTextView.setTextColor(Color.parseColor(getPercentColor(tmp)));
		    String id = filteredData.get(position).getIconID();
		    String url = context.getResources().getString(R.string.item_icon_url) + id + ".png";
		    Picasso.with(context).load(url).into(new Target() {

            @Override
            public void onPrepareLoad(Drawable arg0) {


            }

            @Override
            public void onBitmapLoaded(Bitmap bitmap, LoadedFrom arg1) {
            	int height = 130;
            	int width = (int) (bitmap.getWidth()*1.0/bitmap.getHeight()*height);
            	bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
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
		  long avg = filteredData.get(position).getAvgPrice();
		  long price = filteredData.get(position).getPrice();
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
		  sb.append(filteredData.get(position).getQuantity());
/*		  sb.append("(");
		  sb.append(items.get(position).getBundle());
		  sb.append(")");*/
		  return sb.toString();
	  }
	  private String getRoomChannel(int position){
		  StringBuilder sb = new StringBuilder();
		  sb.append("Ch:");
		  sb.append(filteredData.get(position).getChannel());
		  sb.append(" Rm:");
		  sb.append(filteredData.get(position).getRoom());
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
	  
	  public Filter getFilter() {
		  return mFilter;
	  }
	  
	  private class ItemFilter extends Filter {
		  @Override
		  protected FilterResults performFiltering(CharSequence constraint) {
			  String str = constraint.toString().toLowerCase();
			  String filterString = str.substring(0, str.length() - 1);
			  String tag = str.substring(str.length() - 1, str.length());
			  FilterResults results = new FilterResults();
				
			  final List<Item> list = items;
	 
			  int count = list.size();
			  final List<Item> nlist = new ArrayList<Item>(count);
 
			  String filterableString, filterableString1;
			  
			  if ("1".equals(tag)){
				  for (int i = 0; i < count; i++) {
						filterableString = list.get(i).getItemName();
						filterableString1 = list.get(i).getCharacterName();
						if (filterableString.toLowerCase().contains(filterString) ||
								filterableString1.toLowerCase().contains(filterString) ) {
							nlist.add(list.get(i));
						}
				  }
			  }else if ("2".equals(tag)){
				  for (int i = 0; i < count; i++) {
						filterableString = list.get(i).getCharacterName();
						if (filterableString.toLowerCase().contains(filterString) ) {
							nlist.add(list.get(i));
						}
				  }
			  }
				
			  results.values = nlist;
			  results.count = nlist.size();
 
			  return results;
		  }
 
		  @SuppressWarnings("unchecked")
		  @Override
		  protected void publishResults(CharSequence constraint, FilterResults results) {
			  filteredData = (ArrayList<Item>) results.values;
			  notifyDataSetChanged();
		  }
 
	  }
	  
}

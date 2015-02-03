package com.example.maplefreemarket;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.freeMarket.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.widget.Filter;
class ItemArrayAdapter extends ArrayAdapter<FMItem> {
	  private final Context context;
	  private List<FMItem> items;				//original data
	  private List<FMItem> filteredData;		//filtered data
	  private List<FMItem> filteredDataDisplay;	//displayed data
	  private int preResetSize = -1;					//avoid infinite scroll out of function
	  private ItemFilter mFilter = new ItemFilter();
	  private boolean filterCashItem;
	  private boolean filterSoldItem;
	  private final ReentrantLock lock = new ReentrantLock();
	  private boolean DEBUG = false;
	  public InfiniteScrollListener.SetLoading setLoading = new InfiniteScrollListener.SetLoading();
	  static class ViewHolder {
	    public TextView itemNameTextView;
	    public TextView itemPriceTextView;
	    public TextView channelRoomTextView;
	    public TextView quantityTextView;
	    public TextView percentageTextView;
	    public ImageView imageView;
	    public View rowView;
	  }

	  public class CropSquareTransformation implements Transformation {
		  @Override 
		  public Bitmap transform(Bitmap bitmap) {
			  int height = 130;
			  int width = (int) (bitmap.getWidth()*1.0/bitmap.getHeight()*height);
			  Bitmap result = Bitmap.createScaledBitmap(bitmap, width, height, false);
		    
			  if (result != bitmap) {
				  bitmap.recycle();
			  }
			  return result;
		  }

		  @Override public String key() { return "square()"; }
	  }
	  
	public boolean isFilterCashItem() {
		return filterCashItem;
	}

	public void setFilterCashItem(boolean filterCashItem) {
		this.filterCashItem = filterCashItem;
	}

	public boolean isFilterSoldItem() {
		return filterSoldItem;
	}

	public void setFilterSoldItem(boolean filterSoldItem) {
		this.filterSoldItem = filterSoldItem;
	}

	public ItemArrayAdapter(Context context, List<FMItem> items) {
	    super(context, R.layout.item_row, items);
	    this.context = context;
	    this.items = items;
	    this.filteredData = items;
	    this.filteredDataDisplay = new ArrayList<FMItem>();
	    this.filterCashItem = false;
	    this.filterSoldItem = false;
	  }
	  
	  @Override
	  public int getCount() {
		  if (filteredDataDisplay == null)	return 0;
		  return filteredDataDisplay.size();
	  }
	  
	  public void resetItemsRefresh(List<FMItem> items){
		  if (items == null)	return;
		  int curResetSize = items.size();
		  if (curResetSize == preResetSize){
			  setLoading.loading = false;
		  }
		  preResetSize = curResetSize;
		  List<FMItem> newList = new ArrayList<FMItem>();
		  for (int i = 0; i < curResetSize; i++){
			  newList.add(items.get(i));
		  }
		  if (this.filteredDataDisplay != null)
			  this.filteredDataDisplay.clear();
		  this.filteredDataDisplay = newList;
		  notifyDataSetChanged();
	  }
	  
	  public void addItemsRefresh(List<FMItem> items){
		  List<FMItem> newList = new ArrayList<FMItem>();
		  for (FMItem item : items){
			  newList.add(item);
		  }
		  this.filteredDataDisplay.addAll(newList);
		  notifyDataSetChanged();
	  }
	  
	  public List<FMItem> getItems(){
		  return this.items;
	  }
	  
	  public List<FMItem> getFilteredItems(){
		  return this.filteredData;
	  }
	  
	  public List<FMItem> getFilteredDisplayItems(){
		  return this.filteredDataDisplay;
	  }
	  public void setItems(List<FMItem> items, int size){
		  if (this.items != null)	this.items.clear();
		  if (this.filteredData != null)	this.filteredData.clear();
		  this.items = items;	  
		  this.filteredData = items;
		  if (filteredData != null)
			  resetItemsRefresh(filteredData.subList(0, Math.min(size, filteredData.size())));
//		  this.filteredDataDisplay = items;
	  }
	  
	  @Override
	  public FMItem getItem(int position) {
		  return filteredDataDisplay.get(position);
	  }
	 
	  @Override
	  public long getItemId(int position) {
		  return position;
	  }
	  
	  public void sortByAttribute(int idx, boolean desc){
		  switch (idx){
		  case 0:	Collections.sort(filteredData, FMItem.getItemNameComparator()); break;
		  case 1:	Collections.sort(filteredData, FMItem.getQtyComparator()); break;
		  case 2:	Collections.sort(filteredData, FMItem.getPriceComparator()); break;
		  case 3:	Collections.sort(filteredData, FMItem.getRankComparator()); break;
		  case 4: 	Collections.sort(filteredData, FMItem.getEnhancementComparator()); break;
		  case 5:	Collections.sort(filteredData, FMItem.getPercentComparator()); break;
		  }
		  if (desc){
			  Collections.reverse(filteredData);
		  }
		  resetItemsRefresh(filteredData.subList(0, Math.min(10, filteredData.size())));
	  }
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  
		  	
		  	if (filteredDataDisplay == null || filteredDataDisplay.size() == 0 || filteredDataDisplay.size() <= position){
		  		//early terminaion
		  		return null;
		  	}
		//	final FMItem cur = filteredDataDisplay.get(position);
			
		  	View rowView = convertView;
		  	ViewHolder viewHolder = new ViewHolder();
		  //Avoiding layout inflation and object creation
		  	if (rowView == null){
	  		
			    LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    rowView = inflater.inflate(R.layout.item_row, parent, false);
			    viewHolder.itemNameTextView = (TextView) rowView.findViewById(R.id.itemNameTextView);
			    viewHolder.itemPriceTextView = (TextView) rowView.findViewById(R.id.itemPriceTextView);
			    viewHolder.channelRoomTextView = (TextView) rowView.findViewById(R.id.roomTextView);
			    viewHolder.quantityTextView = (TextView) rowView.findViewById(R.id.quantityTextView);
			    viewHolder.percentageTextView = (TextView) rowView.findViewById(R.id.percentageTextView);
			    viewHolder.imageView = (ImageView) rowView.findViewById(R.id.icon1);
			    rowView.setTag(viewHolder);
		  	}else{
		  		viewHolder = (ViewHolder) rowView.getTag();
		//  		Picasso.with(this.context).cancelRequest(viewHolder.imageView);
		  	}
	  		
		  	String itemName = filteredDataDisplay.get(position).getItemName();
		  	String scroll = String.valueOf(filteredDataDisplay.get(position).getScrollApplied());
		  	if (!("0".equals(scroll))){
		  		itemName += "(+" + scroll + ")";
	    	}
		  	viewHolder.itemNameTextView.setText(itemName);
		  	viewHolder.itemPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(filteredDataDisplay.get(position).getPrice()));
		  	viewHolder.itemPriceTextView.setTextColor(Color.parseColor(getPriceColor(filteredDataDisplay.get(position).getPrice())));
		  	viewHolder.channelRoomTextView.setText(getRoomChannel(position));
		  	viewHolder.quantityTextView.setText(getBundleQuantity(position));
		  	if (filteredDataDisplay.get(position).getQuantity() == 0){
	//	  		holder.quantityTextView.setTextColor(Color.parseColor("#fe170b"));
		  		rowView.setBackgroundColor(Color.parseColor("#fee1f4"));
		  	}else{
		  		rowView.setBackgroundColor(Color.parseColor("#00000000"));
		  	}
		    String tmp = getPercentage(position);
		    viewHolder.percentageTextView.setText(tmp + "%");
		    viewHolder.percentageTextView.setTextColor(Color.parseColor(getPercentColor(tmp)));
		    String id = filteredDataDisplay.get(position).getIconID();
		    String url = context.getResources().getString(R.string.item_icon_url) + id + ".png";
		    if (DEBUG == true){
		    	Picasso.with(context.getApplicationContext()).setIndicatorsEnabled(true);
		        Picasso.with(context.getApplicationContext()).setLoggingEnabled(true);
		    }
		    Picasso.with(context.getApplicationContext()).load(url).placeholder(R.drawable.mesos).transform(new CropSquareTransformation()).into(viewHolder.imageView); 

		    return rowView;
	  }
	  
	  private List<FMItem> filterNonCashItems(List<FMItem> oldList){
		  if (oldList == null || !filterCashItem)	return oldList;
		  List<FMItem> newList = new ArrayList<FMItem>();
		  for (int i = 0; i < oldList.size(); i++){
			  if (oldList.get(i).getReqLevel() == 0 && "Equip".equals(oldList.get(i).getCategory())){
				  if (oldList.get(i).getItemName().contains("Absolute") || oldList.get(i).getItemName().contains("2013 Evolving Ring")){
					  continue;
				  }else{
					  newList.add(oldList.get(i));
				  }
			  }
		  }
		  return newList;
	  }
	  private List<FMItem> filterSoldItems(List<FMItem> oldList){
		  if (oldList == null || !filterSoldItem)	return oldList;
		  List<FMItem> newList = new ArrayList<FMItem>();
		  for (int i = 0; i < oldList.size(); i++){
			  if (oldList.get(i).getQuantity() > 0){
				  newList.add(oldList.get(i));
			  }
		  }
		  return newList;
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
		  long avg = filteredDataDisplay.get(position).getAvgPrice();
		  long price = filteredDataDisplay.get(position).getPrice();
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
		  sb.append(filteredDataDisplay.get(position).getQuantity());
/*		  sb.append("(");
		  sb.append(items.get(position).getBundle());
		  sb.append(")");*/
		  return sb.toString();
	  }
	  private String getRoomChannel(int position){
		  StringBuilder sb = new StringBuilder();
		  sb.append("Ch:");
		  sb.append(filteredDataDisplay.get(position).getChannel());
		  sb.append(" Rm:");
		  sb.append(filteredDataDisplay.get(position).getRoom());
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
	//	  private String touch = "1";
		  @Override
		  protected FilterResults performFiltering(CharSequence constraint) {
	//		  filteredDataDisplay.clear();		//clear display data, make sure filteredDataDisplay is empty???
			  String str = constraint.toString().toLowerCase();
			  String filterString = str.substring(0, str.length() - 1);
			  String tag = str.substring(str.length() - 1, str.length());
			  FilterResults results = new FilterResults();
/*			  if (touch.equals(filterString)){		//prevent same filter that may affect infiniteScroll
				  results.count = 0;
				  results.values = null;
				  return results;
			  }
			  System.out.println("touch[" + touch + "]");
			  touch = filterString;*/
	//		  Toast.makeText(context, filterString, Toast.LENGTH_SHORT).show();
			  if (filteredData != null && filteredData != items){
				  lock.lock();
				  try{				  
					  filteredData.clear();
				  }finally {
					  lock.unlock();
			      }		  
			  }
			  final List<FMItem> list = items;
			  
			  int count = list.size();
			  List<FMItem> nlist;
			 
			  String filterableString, filterableString1;
			  if ("".equals(filterString)){		//early termination, save time and memory
				  nlist = items;
			  }else{
				  nlist = new ArrayList<FMItem>(20);
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
							filterableString = list.get(i).getItemName();
							if (filterableString.toLowerCase().contains(filterString) ) {
								nlist.add(list.get(i));
							}
					  }
				  }
				  else if ("3".equals(tag)){
					  for (int i = 0; i < count; i++) {
							filterableString = list.get(i).getCharacterName();
							if (filterableString.toLowerCase().equals(filterString) ) {
								nlist.add(list.get(i));
							}
					  }
				  }
			  }
			  nlist = filterNonCashItems(nlist);
			  nlist = filterSoldItems(nlist);
			  results.values = nlist;
			  results.count = 1;
			  return results;
		  }
 
		  @SuppressWarnings("unchecked")
		  @Override
		  protected void publishResults(CharSequence constraint, FilterResults results) {
			  if (results.count == 0)	return;		//same filter, do nothing.
			  filteredData = (ArrayList<FMItem>) results.values;	  
/*			  int totalSize = filteredData.size();
		      Toast.makeText(context, totalSize + " items. ", Toast.LENGTH_SHORT).show();*/
		      //do the filter , but not do UI display ???
			  List<FMItem> tmp = new ArrayList<FMItem>(); 
			  int sz = filteredData.size();
			  for (int i = 0; i < Math.min(10, sz); i++){
				  if (filteredData == null )	return;			//early termination
				  lock.lock();
				  try{
					  if (filteredData.size() == 0)				//avoid race condition
						  tmp.clear();
					  else
						  tmp.add(filteredData.get(i));
				  }finally {
					  lock.unlock();
			      }		  
			  }
		      resetItemsRefresh(tmp);
		  }
 
	  }
	  
}

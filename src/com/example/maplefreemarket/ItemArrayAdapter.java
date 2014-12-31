package com.example.maplefreemarket;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
		
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.item_row, parent, false);
	    TextView itemNameTextView = (TextView) rowView.findViewById(R.id.itemNameTextView);
	    TextView itemPriceTextView = (TextView) rowView.findViewById(R.id.itemPriceTextView);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    itemNameTextView.setText(items.get(position).getItemName());
	    itemPriceTextView.setText(NumberFormat.getNumberInstance(Locale.US).format(items.get(position).getPrice()));
	    int id = items.get(position).getIconID();
	    String url = context.getResources().getString(R.string.item_icon_url) + String.valueOf(id) + ".png";
	    Picasso.with(context).load(url).into(imageView);
	    
	    return rowView;
	  }
}

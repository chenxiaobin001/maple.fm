package com.example.maplefreemarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.code.fm.R;

//Creating an Adapter Class
	public class MyAdapter extends ArrayAdapter {
		
		private Context context;
		private String[] serverNames;
		private Integer[] serverImages;
		
		public MyAdapter(Context context, int textViewResourceId, String[] serverNames, Integer[] serverImages) {
			super(context, textViewResourceId, serverNames);
			this.context = context;
			this.serverNames = serverNames;
			this.serverImages = serverImages;
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

			// Inflating the layout for the custom Spinner
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.row, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView serverName = (TextView) layout.findViewById(R.id.serverName);
		
			// Setting the text using the array
			serverName.setText(serverNames[position]);
		
			// Declaring and Typecasting the imageView in the inflated layout
			ImageView img = (ImageView) layout.findViewById(R.id.serverImage);
		
			// Setting an image using the id's in the array
			img.setImageResource(serverImages[position]);

			return layout;
		}

		// It gets a View that displays in the drop down popup the data at the specified position
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
	
		// It gets a View that displays the data at the specified position
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
	}
	
	

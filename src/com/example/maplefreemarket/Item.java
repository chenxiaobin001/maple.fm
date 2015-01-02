package com.example.maplefreemarket;

import java.util.Comparator;

import android.graphics.drawable.Drawable;

public class Item {
	private int id;
	private int quantity;
	private int bundle;
	private long price;
	private int channel;
	private int room;
	private long avgPrice;
	private String shopName;
	private String characterName;
	private String itemName;
	private String description;
	private String category;
	private String subcategory;
	private String detailcategory;
	private String JSONString;
	private String iconID;
	private int reqLevel;
	private Drawable drawableImage;
	
	
	public Drawable getDrawableImage() {
		return drawableImage;
	}
	public void setDrawableImage(Drawable drawableImage) {
		this.drawableImage = drawableImage;
	}
	public String getJSONString() {
		return JSONString;
	}
	public void setJSONString(String jSONString) {
		JSONString = jSONString;
	}
	public long getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(long avgPrice) {
		this.avgPrice = avgPrice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getBundle() {
		return bundle;
	}
	public void setBundle(int bundle) {
		this.bundle = bundle;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
	//	description = description.replace("\\n", "\n").replace("\\r", "\r");
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getDetailcategory() {
		return detailcategory;
	}
	public void setDetailcategory(String detailcategory) {
		this.detailcategory = detailcategory;
	}
	public String getIconID() {
		return iconID;
	}
	public void setIconID(String iconID) {
		this.iconID = iconID;
	}
	public int getReqLevel() {
		return reqLevel;
	}
	public void setReqLevel(int reqLevel) {
		this.reqLevel = reqLevel;
	}
	
	static Comparator<Item> getItemNameComparator() {
        return new Comparator<Item>() {

			@Override
			public int compare(Item first, Item second) {
				return first.getItemName().compareTo(second.getItemName());
			}

        };
    }

	static Comparator<Item> getPriceComparator() {
        return new Comparator<Item>() {

			@Override
			public int compare(Item first, Item second) {
				
				if (first.getPrice() < second.getPrice()){
					return -1;
				}else if (first.getPrice() > second.getPrice()){
					return 1;
				}else{
					return 0;
				}
			}

        };
    }
	static Comparator<Item> getChannelComparator() {
        return new Comparator<Item>() {

			@Override
			public int compare(Item first, Item second) {
				return first.getChannel() - second.getChannel();
			}

        };
    }
	static Comparator<Item> getRoomComparator() {
        return new Comparator<Item>() {

			@Override
			public int compare(Item first, Item second) {		
				return first.getRoom() - second.getRoom();
			}

        };
    }
	static Comparator<Item> getPercentComparator() {
        return new Comparator<Item>() {

			@Override
			public int compare(Item first, Item second) {	
				int p1 = getPercent(first.getPrice(), first.getAvgPrice());
				int p2 = getPercent(second.getPrice(), second.getAvgPrice());
				return p1 - p2;
			}

        };
    }
	static Comparator<Item> getQtyComparator() {
        return new Comparator<Item>() {

			@Override
			public int compare(Item first, Item second) {
				return first.getQuantity() - second.getQuantity();
			}

        };
    }
	
	private static int getPercent(long price, long avgPrice){
		if (avgPrice == 0){
			  return -1;
		  }else{
			  return (int)(price*1.0/avgPrice * 100);
		  }
	}
}

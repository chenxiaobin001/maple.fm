package com.example.infoClasses;


public class FMItemSummary {
	private String itemName;	//itemName;
	private String iconID;	//iconID;
	private String category;	//category;
	private String subcategory;	//subcategory;
	private String detailcategory;	//detailcategory;
	private long avgPrice;		//The mean price for this item as a percent
	private int id;		//The item ID of the item
	private long maxPrice;
	private int quantity;
	private long minPrice;
	private long curAvgPrice;
	private long sumPrice;
	
	public FMItemSummary(FMItem item) {
		this.itemName = item.getItemName();
		this.category = item.getCategory();
		this.subcategory = item.getSubcategory();
		this.detailcategory = item.getDetailcategory();
		this.iconID = item.getIconID();
		this.avgPrice = item.getAvgPrice();
		this.id = item.getId();
		this.quantity = item.getQuantity();
		this.maxPrice = item.getPrice();
		this.minPrice = item.getPrice();
		this.setCurAvgPrice(item.getPrice());
		this.setSumPrice(item.getPrice());
	}
	
	public void updateWith(FMItem item) {
		this.quantity += item.getQuantity();
		this.maxPrice = Math.max(this.maxPrice, item.getPrice());
		this.minPrice = Math.min(this.minPrice, item.getPrice());
		this.sumPrice += item.getPrice() ;
		this.setCurAvgPrice(this.sumPrice / this.quantity);			//careful!
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getIconID() {
		return iconID;
	}
	public void setIconID(String iconID) {
		this.iconID = iconID;
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
	public long getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(long maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(long minPrice) {
		this.minPrice = minPrice;
	}

	public long getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(long sumPrice) {
		this.sumPrice = sumPrice;
	}

	public long getCurAvgPrice() {
		return curAvgPrice;
	}

	public void setCurAvgPrice(long curAvgPrice) {
		this.curAvgPrice = curAvgPrice;
	}
}

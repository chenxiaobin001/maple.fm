package com.example.infoClasses;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonProperty;


public class FMItem {
	@JsonProperty("U")
	private int id;		//The item ID of the item
	@JsonProperty("a")
	private int quantity;		//A quantity of the item
	@JsonProperty("b")
	private int bundle;		//The number of items 
	@JsonProperty("c")
	private long price;		//The item's price.
	@JsonProperty("d")
	private int channel;		//The channel the item
	@JsonProperty("e")
	private int room;		//The room the item
	@JsonProperty("f")
	private String shopName;	//shop_name
	@JsonProperty("g")
	private String characterName;	//character_name 
	@JsonProperty("h")
	private int upgradeAvailable;		//upgrades_available 
	@JsonProperty("i")
	private int scrollApplied;		//The amount of scrolls applied
	@JsonProperty("j")
	private int str;		//str
	@JsonProperty("k")
	private int dex;		//dex
	@JsonProperty("l")
	private int intellegence;		//intellegence;
	@JsonProperty("m")
	private int luk;		//luk;
	@JsonProperty("n")
	private int maxHP;		//maxHP;
	@JsonProperty("o")
	private int maxMP;		//maxMP;
	@JsonProperty("p")
	private int weaponAttack;		//weaponAttack;
	@JsonProperty("q")
	private int magicAttack;		//magicAttack;
	@JsonProperty("r")
	private int weaponDefence;		//weaponDefence;
	@JsonProperty("s")
	private int magicDefence;		//magicDefence;
	@JsonProperty("t")
	private int accuracy;		//accuracy;
	@JsonProperty("u")
	private int avoidability;		//avoidability;
	@JsonProperty("v")
	private int diligence;		//diligence;
	@JsonProperty("w")
	private int speed;		//speed;
	@JsonProperty("x")
	private int jump;		//jump;
	@JsonProperty("y")
	private int growth;		//The amount of item growth;
	@JsonProperty("A")
	private int hammerApplied;		//hammerApplied;
	@JsonProperty("B")
	private int battleModeAttack;		//battle_mode_att 
	@JsonProperty("C")
	private int bossDamage;		//bossDamage;
	@JsonProperty("D")
	private int igDEF;		//igDEF;
	@JsonProperty("E")
	private String crafter;	//The name of the character that crafted the item
	@JsonProperty("F")
	private String potentialIdtf;	//0-no 1 - yes
	@JsonProperty("G")
	private String potentialRank;	//The potential's current rank 0-4; 5-unidentified
	@JsonProperty("H")
	private int enhancements;		//The number of enhancements applied to the item
	@JsonProperty("I")
	private String potential1;		//first line of potential
	@JsonProperty("J")
	private String potential2;		//second line of potential 
	@JsonProperty("K")
	private String	potential3;		//third line of potential
	@JsonProperty("L")
	private String bonusPotential1;		//bonusPotential1;
	@JsonProperty("M")
	private String bonusPotential2;		//bonusPotential2
	@JsonProperty("N")
	private String bonusPotential3;		//bonusPotential3
	@JsonProperty("V")
	private String nebuliteId;		//nebulite_id 
	@JsonProperty("X")
	private long avgPrice;		//The mean price for this item as a percent	
	@JsonProperty("O")
	private String itemName;	//itemName;
	@JsonProperty("P")
	private String description;	//description;
	@JsonProperty("Q")
	private String category;	//category;
	@JsonProperty("R")
	private String subcategory;	//subcategory;
	@JsonProperty("S")
	private String detailcategory;	//detailcategory;
	@JsonProperty("T")
	private String iconID;	//iconID;
	@JsonProperty("W")
	private int reqLevel;		//reqLevel;
//	private Drawable drawableImage;
	
	public ItemMore getItemMore(){
		ItemMore im = new ItemMore();
		im.str = str;
		im.dex = dex;
		im.intellegence = intellegence;
		im.luk = luk;
		im.maxHP = maxHP;
		im.maxMP = maxMP;
		im.weaponAttack = weaponAttack;
		im.magicAttack = magicAttack;
		im.weaponDefence = weaponDefence;
		im.magicDefence = magicDefence;
		im.accuracy = accuracy;
		im.avoidability = avoidability;
		im.jump = jump;
		im.speed = speed;	
		im.battleModeAttack = battleModeAttack;
		im.bossDamage = bossDamage;
		im.igDEF = igDEF;
		im.crafter = crafter;
		im.hammerApplied = hammerApplied;
		im.upgradeAvailable = upgradeAvailable;
		return im;
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
	public int getUpgradeAvailable() {
		return upgradeAvailable;
	}
	public void setUpgradeAvailable(int upgradeAvailable) {
		this.upgradeAvailable = upgradeAvailable;
	}
	public int getScrollApplied() {
		return scrollApplied;
	}
	public void setScrollApplied(int scrollApplied) {
		this.scrollApplied = scrollApplied;
	}
	public int getStr() {
		return str;
	}
	public void setStr(int str) {
		this.str = str;
	}
	public int getDex() {
		return dex;
	}
	public void setDex(int dex) {
		this.dex = dex;
	}
	public int getIntellegence() {
		return intellegence;
	}
	public void setIntellegence(int intellegence) {
		this.intellegence = intellegence;
	}
	public int getLuk() {
		return luk;
	}
	public void setLuk(int luk) {
		this.luk = luk;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public int getMaxMP() {
		return maxMP;
	}
	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}
	public int getWeaponAttack() {
		return weaponAttack;
	}
	public void setWeaponAttack(int weaponAttack) {
		this.weaponAttack = weaponAttack;
	}
	public int getMagicAttack() {
		return magicAttack;
	}
	public void setMagicAttack(int magicAttack) {
		this.magicAttack = magicAttack;
	}
	public int getWeaponDefence() {
		return weaponDefence;
	}
	public void setWeaponDefence(int weaponDefence) {
		this.weaponDefence = weaponDefence;
	}
	public int getMagicDefence() {
		return magicDefence;
	}
	public void setMagicDefence(int magicDefence) {
		this.magicDefence = magicDefence;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public int getAvoidability() {
		return avoidability;
	}
	public void setAvoidability(int avoidability) {
		this.avoidability = avoidability;
	}
	public int getDiligence() {
		return diligence;
	}
	public void setDiligence(int diligence) {
		this.diligence = diligence;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getJump() {
		return jump;
	}
	public void setJump(int jump) {
		this.jump = jump;
	}
	public int getGrowth() {
		return growth;
	}
	public void setGrowth(int growth) {
		this.growth = growth;
	}
	public int getHammerApplied() {
		return hammerApplied;
	}
	public void setHammerApplied(int hammerApplied) {
		this.hammerApplied = hammerApplied;
	}
	public int getBattle_mode_att() {
		return battleModeAttack;
	}
	public void setBattle_mode_att(int battle_mode_att) {
		this.battleModeAttack = battle_mode_att;
	}
	public int getBossDamage() {
		return bossDamage;
	}
	public void setBossDamage(int bossDamage) {
		this.bossDamage = bossDamage;
	}
	public int getIgDEF() {
		return igDEF;
	}
	public void setIgDEF(int igDEF) {
		this.igDEF = igDEF;
	}
	public String getCrafter() {
		return crafter;
	}
	public void setCrafter(String crafter) {
		this.crafter = crafter;
	}
	public String getPotentialIdtf() {
		return potentialIdtf;
	}
	public void setPotentialIdtf(String potentialIdtf) {
		this.potentialIdtf = potentialIdtf;
	}
	public String getPotentialRank() {
		return potentialRank;
	}
	public void setPotentialRank(String potentialRank) {
		this.potentialRank = potentialRank;
	}
	public int getEnhancements() {
		return enhancements;
	}
	public void setEnhancements(int enhancements) {
		this.enhancements = enhancements;
	}
	public String getPotential1() {
		return potential1;
	}
	public void setPotential1(String potential1) {
		this.potential1 = potential1;
	}
	public String getPotential2() {
		return potential2;
	}
	public void setPotential2(String potential2) {
		this.potential2 = potential2;
	}
	public String getPotential3() {
		return potential3;
	}
	public void setPotential3(String potential3) {
		this.potential3 = potential3;
	}
	public String getBonusPotential1() {
		return bonusPotential1;
	}
	public void setBonusPotential1(String bonusPotential1) {
		this.bonusPotential1 = bonusPotential1;
	}
	public String getBonusPotential2() {
		return bonusPotential2;
	}
	public void setBonusPotential2(String bonusPotential2) {
		this.bonusPotential2 = bonusPotential2;
	}
	public String getBonusPotential3() {
		return bonusPotential3;
	}
	public void setBonusPotential3(String bonusPotential3) {
		this.bonusPotential3 = bonusPotential3;
	}
	public String getNebuliteId() {
		return nebuliteId;
	}
	public void setNebuliteId(String nebuliteId) {
		this.nebuliteId = nebuliteId;
	}
	public long getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(long avgPrice) {
		this.avgPrice = avgPrice;
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
/*	@JsonIgnore
	public Drawable getDrawableImage() {
		return drawableImage;
	}
	@JsonIgnore
	public void setDrawableImage(Drawable drawableImage) {
		this.drawableImage = drawableImage;
	}*/
	public static Comparator<FMItem> getComparator(int idx) {
		switch (idx){
		case 0:	return getItemNameComparator();
		case 1:	return getQtyComparator();
		case 2:	return getPriceComparator();
		case 3:	return getRankComparator();
		case 4:	return getEnhancementComparator();
		case 5:	return getPercentComparator();
		}
		return getItemNameComparator();
	}
	
	public static Comparator<FMItem> getItemNameComparator() {
        return new Comparator<FMItem>() {

			@Override
			public int compare(FMItem first, FMItem second) {
				if (first.getItemName() == null || second.getItemName() ==  null)	return	0;
				return first.getItemName().compareTo(second.getItemName());
			}

        };
    }

	public static Comparator<FMItem> getPriceComparator() {
        return new Comparator<FMItem>() {

			@Override
			public int compare(FMItem first, FMItem second) {
				
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
	public static Comparator<FMItem> getChannelComparator() {
        return new Comparator<FMItem>() {

			@Override
			public int compare(FMItem first, FMItem second) {
				return first.getChannel() - second.getChannel();
			}

        };
    }
	public static Comparator<FMItem> getRoomComparator() {
        return new Comparator<FMItem>() {

			@Override
			public int compare(FMItem first, FMItem second) {		
				return first.getRoom() - second.getRoom();
			}

        };
    }
	public static Comparator<FMItem> getPercentComparator() {
        return new Comparator<FMItem>() {

			@Override
			public int compare(FMItem first, FMItem second) {	
				Double p1 = getPercent(first.getPrice(), first.getAvgPrice());
				Double p2 = getPercent(second.getPrice(), second.getAvgPrice());
				return p1.compareTo(p2);
			}

        };
    }
	public static Comparator<FMItem> getQtyComparator() {
        return new Comparator<FMItem>() {

			@Override
			public int compare(FMItem first, FMItem second) {
				return first.getQuantity() - second.getQuantity();
			}

        };
    }
	
	public static Comparator<FMItem> getEnhancementComparator() {
        return new Comparator<FMItem>() {

			@Override
			public int compare(FMItem first, FMItem second) {
				return first.getEnhancements() - second.getEnhancements();
			}

        };
    }
	
	public static Comparator<FMItem> getRankComparator() {
        return new Comparator<FMItem>() {
        	
        	private int[] mapping = new int[]{0, 2, 3, 4, 5, 1};
			@Override
			public int compare(FMItem first, FMItem second) {
				String r1 = first.getPotentialRank();
				String r2 = second.getPotentialRank();
				if (r1 == null && r2 == null)	return 0;
				else if (r1 == null)	return -1;
				else if (r2 == null)	return 1;
				else{
					return mapping[r1.charAt(0) - '0'] - mapping[r2.charAt(0) - '0'];
				}
			}

        };
    }
	
	private static double getPercent(long price, long avgPrice){
		if (avgPrice == 0){
			  return -1;
		  }else{
			  return price*1.0/avgPrice * 100;
		  }
	}
	
	
	
}

package com.example.maplefreemarket;

import org.json.JSONObject;


public class ItemMore {
	private int id;
    private String shopName;
	private String characterName;
	private String itemName;
	private String description;
	private String category;
	private String subcategory;
	private String detailcategory;
	private String iconID;
	private int reqLevel;
	private int str;
	private int dex;
	private int intellegence;
	private int luk;
	private int maxHP;
	private int maxMP;
	private int weaponAttack;
	private int magicAttack;
	private int weaponDefence;
	private int magicDefence;
	private int accuracy;
	private int avoidability;
	private int diligence;
	private int speed;
	private int jump;
	private int battleModeAttack;
	private int bossDamage;
	private int igDEF;
	private int upgradeAvailable;
	private int hammerApplied;
	private String crafter;
	private String potential1;
	private String potential2;
	private String potential3;
	private String bonusPotential1;
	private String bonusPotential2;
	private String bonusPotential3;
	
	public ItemMore(JSONObject jObject){
		str = jObject.optInt("k");
		dex = jObject.optInt("j");
		intellegence = jObject.optInt("l");
		luk = jObject.optInt("m");
		maxHP = jObject.optInt("n");
		maxMP = jObject.optInt("o");
		weaponAttack = jObject.optInt("p");
		magicAttack = jObject.optInt("q");
	 	weaponDefence = jObject.optInt("r");
	 	magicDefence = jObject.optInt("s");
	 	accuracy = jObject.optInt("t");
	 	avoidability = jObject.optInt("u");
	 	diligence = jObject.optInt("v");
	 	speed = jObject.optInt("w");
	 	jump = jObject.optInt("x");
	 	battleModeAttack = jObject.optInt("B");
	 	bossDamage = jObject.optInt("C");
	 	igDEF = jObject.optInt("D");
		crafter = jObject.optString("E");
		potential1 = jObject.optString("I");
		potential2 = jObject.optString("J");
		potential3 = jObject.optString("K");
		bonusPotential1 = jObject.optString("L");
		bonusPotential2 = jObject.optString("M");
		bonusPotential3 = jObject.optString("N");
		upgradeAvailable = jObject.optInt("h");
	 	hammerApplied = jObject.optInt("A");
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getPropertyValue(str, 0, "STR"));
    	sb.append(getPropertyValue(dex, 0, "DEX"));
    	sb.append(getPropertyValue(intellegence, 0, "INT"));
    	sb.append(getPropertyValue(luk, 0, "LUK"));
    	sb.append(getPropertyValue(maxHP, 0, "Max HP"));
    	sb.append(getPropertyValue(maxMP, 0, "Max MP"));
    	sb.append(getPropertyValue(weaponAttack, 0, "WEAPON ATTACK"));
    	sb.append(getPropertyValue(magicAttack, 0, "MAGIC ATTACK"));
    	sb.append(getPropertyValue(weaponDefence, 0, "WEAPON DEFENSE"));
    	sb.append(getPropertyValue(magicDefence, 0, "MAGIC DEFENSE"));
    	sb.append(getPropertyValue(accuracy, 0, "ACCURACY"));
    	sb.append(getPropertyValue(avoidability, 0, "AVOIDABILITY"));
    	sb.append(getPropertyValue(diligence, 0, "DILIGENCE"));
    	sb.append(getPropertyValue(speed, 0, "SPEED"));
    	sb.append(getPropertyValue(jump, 0, "JUMP"));
    	sb.append(getPropertyValue(battleModeAttack, 0, "BATTLE MODE ATTACK"));
    	sb.append(getPropertyValue(bossDamage, 0,"When attacking bosses, damage"));
    	sb.append(getPropertyValue(igDEF, 0, "Ignore Monster DEF"));
    	if (crafter != "")	sb.append("CRAFTER: " + crafter);
   // 	
   // 	sb.append(getPropertyValue("F", "Potential"));
   // 	sb.append(getPropertyValue("G", "Rank"));
   // 	sb.append(getPropertyValue("H", "Enhancements applied"));
   /* 	sb.append("1st POTENTIAL");
    	sb.append("2st POTENTIAL");
    	sb.append("3st POTENTIAL");
    	sb.append("1st BONUS POTENTIAL");
    	sb.append("2st BONUS POTENTIAL");
    	sb.append("3st BONUS POTENTIAL");*/
    	sb.append(getPropertyValue(upgradeAvailable, 0, "NUMBER OF UPGRADES AVAILABLE"));
    	sb.append(getPropertyValue(hammerApplied, 0, "NUMBER OF HAMMER APPLIED"));
		return sb.toString();
	}
	
	private String getPropertyValue(int val1, int val2, String desc){
    	StringBuilder sb = new StringBuilder();
    	if (val1 != 0){
    		sb.append(desc + ": ");
    		if (val2 == 0){
    			if (!("NUMBER OF UPGRADES AVAILABLE".equals(desc) || "NUMBER OF HAMMER APPLIED".equals(desc)))
        			sb.append("+");
    			sb.append(String.valueOf(val1));
    			if ("When attacking bosses, damage".equals(desc) || "Ignore Monster DEF".equals(desc))
        			sb.append("%");
    			sb.append("\n");
    		}
    	}
    	return sb.toString();
    	
    }
	public int getId() {
		return id;
	}

	public String getShopName() {
		return shopName;
	}

	public String getCharacterName() {
		return characterName;
	}

	public String getItemName() {
		return itemName;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public String getDetailcategory() {
		return detailcategory;
	}

	public String getIconID() {
		return iconID;
	}

	public int getReqLevel() {
		return reqLevel;
	}

	public int getStr() {
		return str;
	}

	public int getDex() {
		return dex;
	}

	public int getIntellegence() {
		return intellegence;
	}

	public int getLuc() {
		return luk;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public int getWeaponAttack() {
		return weaponAttack;
	}

	public int getMagicAttack() {
		return magicAttack;
	}

	public int getWeaponDefence() {
		return weaponDefence;
	}

	public int getMagicDefence() {
		return magicDefence;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public int getAvoidability() {
		return avoidability;
	}

	public int getDiligence() {
		return diligence;
	}

	public int getSpeed() {
		return speed;
	}

	public int getJUMP() {
		return jump;
	}

	public int getBattleModeAttack() {
		return battleModeAttack;
	}

	public int getBossDamage() {
		return bossDamage;
	}

	public int getIgDEF() {
		return igDEF;
	}

	public int getUpgradeAvailable() {
		return upgradeAvailable;
	}

	public int getHammerApplied() {
		return hammerApplied;
	}

	public String getCrafter() {
		return crafter;
	}

	public String getPotential1() {
		return potential1;
	}

	public String getPotential2() {
		return potential2;
	}

	public String getPotential3() {
		return potential3;
	}

	public String getBonusPotential1() {
		return bonusPotential1;
	}

	public String getBonusPotential2() {
		return bonusPotential2;
	}

	public String getBonusPotential3() {
		return bonusPotential3;
	}
}

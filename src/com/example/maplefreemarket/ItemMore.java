package com.example.maplefreemarket;

import org.json.JSONObject;


public class ItemMore {
	int id;
    String shopName;
	String characterName;
	String itemName;
	String description;
	String category;
	String subcategory;
	String detailcategory;
	String iconID;
	int reqLevel;
	int str;
	int dex;
	int intellegence;
	int luk;
	int maxHP;
	int maxMP;
	int weaponAttack;
	int magicAttack;
	int weaponDefence;
	int magicDefence;
	int accuracy;
	int avoidability;
	int diligence;
	int speed;
	int jump;
	int battleModeAttack;
	int bossDamage;
	int igDEF;
	int upgradeAvailable;
	int hammerApplied;
	String crafter;
	String potential1;
	String potential2;
	String potential3;
	String bonusPotential1;
	String bonusPotential2;
	String bonusPotential3;
	
	public ItemMore(){
		str = 0;
		dex = 0;
		intellegence = 0;
		luk = 0;
		maxHP = 0;
		maxMP = 0;
		weaponAttack = 0;
		magicAttack = 0;
	 	weaponDefence = 0;
	 	magicDefence = 0;
	 	accuracy = 0;
	 	avoidability = 0;
	 	diligence = 0;
	 	speed = 0;
	 	jump = 0;
	 	battleModeAttack = 0;
	 	bossDamage = 0;
	 	igDEF = 0;
		crafter = "";
		potential1 = "";
		potential2 = "";
		potential3 = "";
		bonusPotential1 = "";
		bonusPotential2 = "";
		bonusPotential3 = "";
		upgradeAvailable = 0;
	 	hammerApplied = 0;
	}
	public ItemMore(JSONObject jObject){
		shopName = jObject.optString("f");
		characterName = jObject.optString("g");		
		str = jObject.optInt("j");
		dex = jObject.optInt("k");
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
	
	public String toString(ItemMore upgrade){
		if (upgrade == null)
			upgrade = new ItemMore();
		StringBuilder sb = new StringBuilder();
		sb.append(getPropertyValue(str, upgrade.str, "STR"));
    	sb.append(getPropertyValue(dex, upgrade.dex, "DEX"));
    	sb.append(getPropertyValue(intellegence, upgrade.intellegence, "INT"));
    	sb.append(getPropertyValue(luk, upgrade.luk, "LUK"));
    	sb.append(getPropertyValue(maxHP, upgrade.maxHP, "Max HP"));
    	sb.append(getPropertyValue(maxMP, upgrade.maxMP, "Max MP"));
    	sb.append(getPropertyValue(weaponAttack, upgrade.weaponAttack, "WEAPON ATTACK"));
    	sb.append(getPropertyValue(magicAttack, upgrade.magicAttack, "MAGIC ATTACK"));
    	sb.append(getPropertyValue(weaponDefence, upgrade.weaponDefence, "WEAPON DEFENSE"));
    	sb.append(getPropertyValue(magicDefence, upgrade.magicDefence, "MAGIC DEFENSE"));
    	sb.append(getPropertyValue(accuracy, upgrade.accuracy, "ACCURACY"));
    	sb.append(getPropertyValue(avoidability, upgrade.avoidability, "AVOIDABILITY"));
    	sb.append(getPropertyValue(diligence, upgrade.diligence, "DILIGENCE"));
    	sb.append(getPropertyValue(speed, upgrade.speed, "SPEED"));
    	sb.append(getPropertyValue(jump, upgrade.jump, "JUMP"));
    	sb.append(getPropertyValue(battleModeAttack, upgrade.battleModeAttack, "BATTLE MODE ATTACK"));
    	sb.append(getPropertyValue(bossDamage, upgrade.bossDamage,"When attacking bosses, damage"));
    	sb.append(getPropertyValue(igDEF, upgrade.igDEF, "Ignore Monster DEF"));
    	if (crafter != null)	sb.append("CRAFTER: " + crafter);

    	sb.append(getPropertyValue(upgradeAvailable, 0, "NUMBER OF UPGRADES AVAILABLE"));
    	sb.append(getPropertyValue(hammerApplied, 0, "NUMBER OF HAMMER APPLIED"));
		return sb.toString();
	}
	
	private String getPropertyValue(int val1, int val2, String desc){
    	StringBuilder sb = new StringBuilder();
    	if (val1 != 0){
    		sb.append(desc + ": ");
			if (!("NUMBER OF UPGRADES AVAILABLE".equals(desc) || "NUMBER OF HAMMER APPLIED".equals(desc)))
    			sb.append("+");
			sb.append(String.valueOf(val1));
			if (!("NUMBER OF UPGRADES AVAILABLE".equals(desc) || "NUMBER OF HAMMER APPLIED".equals(desc) || 
					"When attacking bosses, damage".equals(desc) || "Ignore Monster DEF".equals(desc))){
				int inc = val1 - val2;
				if (inc > 0){
					String str = " (" + val2 + " + " + inc + ")";
					sb.append(str);
				}
			}
			if ("When attacking bosses, damage".equals(desc) || "Ignore Monster DEF".equals(desc))
    			sb.append("%");
			sb.append("\n");
    	}
    	return sb.toString();
    	
    }

}

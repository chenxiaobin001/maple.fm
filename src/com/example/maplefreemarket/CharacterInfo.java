package com.example.maplefreemarket;

public class CharacterInfo {
	String name;
	int level;
	String exp;
	String expRequired;
	String job;
	String world;
	Images image;
	Ranking ranking;
	long fameRank;
	int fame;
	
	class Images{
		String petImage;
		String characterImage;
	}
	class Ranking{
		RankDetail overall;
		RankDetail world;
		RankDetail job;
	}
	class RankDetail{
		int moveRank;
		String direction;
		long rank;
		public void setRankDetail(int moveRank, String direction, long rank){
			this.moveRank = moveRank;
			this.direction = direction;
			this.rank = rank;
		}
	}
	
	public CharacterInfo(){
		final String nofound = "N/A";
		name = nofound;
		level = 0;
		exp = nofound;
		expRequired = nofound;
		job = nofound;
		world = nofound;
		image = new Images();
		ranking = new Ranking();
		fameRank = 0;
		fame = 0;
	}
}

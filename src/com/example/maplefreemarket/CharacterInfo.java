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
	
	public String getOverallRand(){
		return String.valueOf(ranking.overall.rank);
	}
	public String getWorldRand(){
		return String.valueOf(ranking.world.rank);
	}
	public String getJobRand(){
		return String.valueOf(ranking.job.rank);
	}
	class Images{
		String petImage;
		String characterImage;
	}
	class Ranking{
		RankDetail overall;
		RankDetail world;
		RankDetail job;
		Ranking(){
			overall = new RankDetail();
			world = new RankDetail();
			job = new RankDetail();
		}
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

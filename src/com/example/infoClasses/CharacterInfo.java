package com.example.infoClasses;

public class CharacterInfo {
	public String name;
	public int level;
	public String exp;
	public String expRequired;
	public String job;
	public String world;
	public Images image;
	public Ranking ranking;
	public long fameRank;
	public int fame;
	
	public String getOverallRand(){
		return String.valueOf(ranking.overall.rank);
	}
	public String getWorldRand(){
		return String.valueOf(ranking.world.rank);
	}
	public String getJobRand(){
		return String.valueOf(ranking.job.rank);
	}
	public class Images{
		public String petImage;
		public String characterImage;
	}
	public class Ranking{
		public RankDetail overall;
		public RankDetail world;
		public RankDetail job;
		Ranking(){
			overall = new RankDetail();
			world = new RankDetail();
			job = new RankDetail();
		}
	}
	public class RankDetail{
		public int moveRank;
		public String direction;
		public long rank;
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

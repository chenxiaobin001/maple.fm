package com.example.asyncTasks;

import org.json.JSONException;
import org.json.JSONObject;

import com.code.freeMarket.R;
import com.example.infoClasses.CharacterInfo;
import com.example.maplefreemarket.SellerInfoFragment.OnImageLoadedListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HandleSellerAndShopJSON extends AsyncTask<String, Void, String> {

	private Context mContext;
	private CharacterInfo characterInfo;
	private View view;

	public HandleSellerAndShopJSON(Context context, View view) {
		this.mContext = context;
		this.view = view;
		this.characterInfo = new CharacterInfo();
	}
	
	private void handleJson(String[] strs) throws JSONException{
		JSONObject jObject = new JSONObject(strs[0]);		//early termination.
		if (jObject.optString("name") == null){
			return;
		}
		characterInfo.name = jObject.optString("name");		
		characterInfo.level = jObject.optInt("level");
		characterInfo.exp = jObject.optString("exp");
		characterInfo.expRequired = jObject.optString("expRequired");
		characterInfo.job = jObject.optString("job");
		characterInfo.world = jObject.optString("world");
		JSONObject images = jObject.getJSONObject("images"); 	
		characterInfo.image.petImage = images.optString("Pet");
		characterInfo.image.characterImage = images.optString("Character");
		JSONObject rank = jObject.getJSONObject("ranking"); 	
		JSONObject overall = rank.getJSONObject("overall"); 
		characterInfo.ranking.overall.setRankDetail(overall.optInt("move_rank"), overall.optString("move_change"), overall.optLong("rank"));
		JSONObject world = rank.getJSONObject("world"); 
		characterInfo.ranking.world.setRankDetail(world.optInt("move_rank"), world.optString("move_change"), world.optLong("rank"));
		JSONObject job = rank.getJSONObject("job"); 
		characterInfo.ranking.job.setRankDetail(job.optInt("move_rank"), job.optString("move_change"), job.optLong("rank"));
		JSONObject fame = rank.getJSONObject("fame"); 
		characterInfo.fameRank = fame.optLong("rank");
		characterInfo.fame = jObject.optInt("fame");
	}
	
	private void updateTextView(){
		TextView sellerCharNameTextView = (TextView) view.findViewById(R.id.sellerCharNameTextView);
		TextView sellerJobTextView = (TextView) view.findViewById(R.id.sellerJobTextView);
		TextView sellerWorldTextView = (TextView) view.findViewById(R.id.sellerWorldTextView);
		TextView levelTextView = (TextView) view.findViewById(R.id.levelTextView);
		TextView EXPTextView = (TextView) view.findViewById(R.id.EXPTextView);
		TextView EXPRequiredTextView = (TextView) view.findViewById(R.id.EXPRequiredTextView);
		TextView globalRankingTextView = (TextView) view.findViewById(R.id.globalRankingTextView);
		TextView worldRankingTextView = (TextView) view.findViewById(R.id.worldRankingTextView);
		TextView jobRankingTextView = (TextView) view.findViewById(R.id.jobRankingTextView);
		TextView fameRankingTextView = (TextView) view.findViewById(R.id.fameRankingTextView);
		TextView fameTextView = (TextView) view.findViewById(R.id.fameTextView);
		ImageView charImage = (ImageView) view.findViewById(R.id.characterImageView);
		ImageView petImage = (ImageView) view.findViewById(R.id.petImageimageView);
		TextView globalRankingMoveTextView = (TextView) view.findViewById(R.id.globalRankingMoveTextView);
		TextView worldRankingMoveTextView = (TextView) view.findViewById(R.id.worldRankingMoveTextView);
		TextView jobRankingMoveTextView = (TextView) view.findViewById(R.id.jobRankingMoveTextView);
		sellerCharNameTextView.setText(characterInfo.name);
		sellerJobTextView.setText(characterInfo.job);
		sellerWorldTextView.setText(characterInfo.world);
		levelTextView.setText(String.valueOf(characterInfo.level));
		EXPTextView.setText(characterInfo.exp);
		EXPRequiredTextView.setText(characterInfo.expRequired);
		globalRankingTextView.setText(characterInfo.getOverallRand());
		worldRankingTextView.setText(characterInfo.getWorldRand());
		jobRankingTextView.setText(characterInfo.getJobRand());
		fameRankingTextView.setText(String.valueOf(characterInfo.fameRank));
		fameTextView.setText(String.valueOf(characterInfo.fame));
		globalRankingMoveTextView.setText(getMoveRank(characterInfo.ranking.overall));
		worldRankingMoveTextView.setText(getMoveRank(characterInfo.ranking.world));
		jobRankingMoveTextView.setText(getMoveRank(characterInfo.ranking.job));
		getSellerImage(characterInfo.image.characterImage, charImage, true);
		getSellerImage(characterInfo.image.petImage, petImage, false);
		
	}
	
	private Spanned getMoveRank(CharacterInfo.RankDetail detail){
		int move = detail.moveRank;
		String direction = detail.direction;
		StringBuilder sb = new StringBuilder();
		Spanned result = null;
		if ("up".equals(direction)){
			String tmp = sb.append("¡ø").append(String.valueOf(move)).toString();
			tmp = "<font color=#02c200>"+tmp+"</font> ";
			result = Html.fromHtml(tmp);
		}else{
			String tmp = sb.append("¨‹").append(String.valueOf(move)).toString();
			tmp = "<font color=#fe170b>"+tmp+"</font> ";
			result = Html.fromHtml(tmp);
		}
		return result;
	}
	
	@Override
	protected String doInBackground(String... JSonString) {
		try {

			handleJson(JSonString);
			return "OK";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (result == null){
            Toast.makeText(mContext, "Failed to get character's info", Toast.LENGTH_SHORT).show();
            return;
		}
		updateTextView();
    }
	private void getSellerImage(String URL, ImageView view, boolean seller){
		
		final ImageView mView = view;
		final boolean isSeller = seller;
		Picasso.with(mContext).load(URL).into(new Target() {

            @Override
            public void onPrepareLoad(Drawable arg0) {


            }

            @Override
            public void onBitmapLoaded(Bitmap bitmap, LoadedFrom arg1) {
            	int width = mView.getMeasuredHeight();
            	int height = (int) (width*1.0/bitmap.getWidth() *bitmap.getHeight());
            	bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            	Bitmap oldBitmap = bitmap;
            	mView.setImageBitmap(bitmap);
            	OnImageLoadedListener activity = (OnImageLoadedListener)mContext;
            	activity.onImageLoaded(oldBitmap, isSeller);
            }

            @Override
            public void onBitmapFailed(Drawable arg0) {


            }

		
	    });
	}
}

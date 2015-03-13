package com.example.articlesManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.code.freeMarket.R;
import com.example.infoClasses.Article;
import com.github.curioustechizen.ago.RelativeTimeTextView;



public class ArticleArrayAdapter extends ArrayAdapter<Article> {
	
	private final int previewLength = 500;
	
    static class ViewHolder {
	    public TextView articleTitleTextView;
	    public RelativeTimeTextView articleTimeTextView;
	    public TextView articleContentTextView;
	    public TextView articleAuthorTextView;
	    public TextView articleLikeTextView;
	    public TextView articleDislikeTextView;
	    public TextView articleCommentTextView;
	}
	
	private Context context;
	private List<Article> articles;
	public ArticleArrayAdapter(Context context, List<Article> articles) {
	    super(context, R.layout.article_row, articles);
	    this.context = context;
	    this.articles = articles;

	}
	@Override
	public void add(Article article) {
		super.add(article);
		articles.add(1, article);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (articles == null)	return 0;
		return articles.size();
	}
	
	public void addArticles(List<Article> articles){
		List<Article> newList = new ArrayList<Article>();
		if (articles == null)	return;
		for (Article article : articles){
			newList.add(article);
		}
		this.articles.addAll(newList);
		notifyDataSetChanged();
	}
	
	public List<Article> getArticles(){
		return this.articles;
	}
	
	public void setArticles(List<Article> articles){
		if (this.articles != null)	this.articles.clear();
		this.articles = articles;	  
	}
	
	@Override
	public Article getItem(int position) {
		return articles.get(position);
	}
	 
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  
		  	
		  	if (articles == null || articles.size() == 0 || articles.size() <= position){
		  		//early terminaion
		  		return null;
		  	}
	
		  	View rowView = convertView;
		  	ViewHolder viewHolder = new ViewHolder();
		  //Avoiding layout inflation and object creation
		  	if (rowView == null){
	  		
			    LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    rowView = inflater.inflate(R.layout.article_row, parent, false);
			    viewHolder.articleAuthorTextView = (TextView) rowView.findViewById(R.id.articleAuthorTextView);
			    viewHolder.articleTitleTextView = (TextView) rowView.findViewById(R.id.articleTitleTextView);
			    viewHolder.articleTimeTextView = (RelativeTimeTextView) rowView.findViewById(R.id.articleTimeTextView);
			    viewHolder.articleContentTextView = (TextView) rowView.findViewById(R.id.articleContentTextView);
			    viewHolder.articleLikeTextView = (TextView) rowView.findViewById(R.id.articleLikeTextView);
			    viewHolder.articleDislikeTextView = (TextView) rowView.findViewById(R.id.articleDislikeTextView);
			    viewHolder.articleCommentTextView = (TextView) rowView.findViewById(R.id.articleCommentTextView);
	//		    viewHolder.profileImageView = (ImageView) rowView.findViewById(R.id.profileImageView);
			    rowView.setTag(viewHolder);
		  	}else{
		  		viewHolder = (ViewHolder) rowView.getTag();
		//  		Picasso.with(this.context).cancelRequest(viewHolder.imageView);
		  	}
		  	setArticleInfo(viewHolder, position);
		  	return rowView;
	}
	 
	private void setArticleInfo(ViewHolder viewHolder, int position) {
		 Article article = articles.get(position);	 
		 String dateString = article.getUpdateTime();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 Date date;
		 try {
			 date = sdf.parse(dateString);
			 long startDate = date.getTime() - 10800000 - 3600000;
			 viewHolder.articleTimeTextView.setReferenceTime(startDate);
		 } catch (ParseException e) {
			 viewHolder.articleTimeTextView.setText("unexpected");
			 e.printStackTrace();
			 Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		 } 
		 viewHolder.articleTitleTextView.setText(article.getTitle());
		 viewHolder.articleAuthorTextView.setText(article.getAuthor());
		 viewHolder.articleContentTextView.setText(getPreview(article.getContent()));
		 viewHolder.articleLikeTextView.setText("O " + String.valueOf(article.getLike()));
		 viewHolder.articleDislikeTextView.setText("X " + String.valueOf(article.getDislike()));
		 viewHolder.articleCommentTextView.setText("Reply " + String.valueOf(article.getComment()));
	}
	
	private String getPreview(String content) {
		if (content.length() > previewLength) {
			return content.substring(0, previewLength - 3) + "...";
		}
		return content;
	}
	
}

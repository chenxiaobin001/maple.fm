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
import com.example.infoClasses.Comment;
import com.github.curioustechizen.ago.RelativeTimeTextView;



public class CommentArrayAdapter extends ArrayAdapter<Comment> {
	
    static class ViewHolder {
	    public RelativeTimeTextView CommentTimeTextView;
	    public TextView CommentContentTextView;
	    public TextView CommentAuthorTextView;
	    public TextView replycontentTextView;
	    public TextView replyCommentAuthorTextView;
	    public View replyLayout;
	}
	
	private Context context;
	private List<Comment> comments;
	public CommentArrayAdapter(Context context, List<Comment> Comments) {
	    super(context, R.layout.comment_row, Comments);
	    this.context = context;
	    this.comments = Comments;

	}
	
	@Override
	public int getCount() {
		if (comments == null)	return 0;
		return comments.size();
	}
	
	public void addComments(List<Comment> Comments){
		List<Comment> newList = new ArrayList<Comment>();
		if (Comments == null)	return;
		for (Comment Comment : Comments){
			newList.add(Comment);
		}
		this.comments.addAll(newList);
		notifyDataSetChanged();
	}
	
	public List<Comment> getComments(){
		return this.comments;
	}
	
	public void setComments(List<Comment> Comments){
		if (this.comments != null)	this.comments.clear();
		this.comments = Comments;	  
	}
	
	@Override
	public Comment getItem(int position) {
		return comments.get(position);
	}
	 
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  
		  	
		  	if (comments == null || comments.size() == 0 || comments.size() <= position){
		  		//early terminaion
		  		return null;
		  	}
	
		  	View rowView = convertView;
		  	ViewHolder viewHolder = new ViewHolder();
		  //Avoiding layout inflation and object creation
		  	if (rowView == null){
	  		
			    LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    rowView = inflater.inflate(R.layout.comment_row, parent, false);
			    viewHolder.CommentAuthorTextView = (TextView) rowView.findViewById(R.id.commenter1TextView);
			    viewHolder.CommentContentTextView = (TextView) rowView.findViewById(R.id.commentContentTextView);
			    viewHolder.CommentTimeTextView = (RelativeTimeTextView) rowView.findViewById(R.id.commentTimeTextView);
			    viewHolder.replycontentTextView = (TextView) rowView.findViewById(R.id.replyContentTextView);
			    viewHolder.replyCommentAuthorTextView = (TextView) rowView.findViewById(R.id.replyCommentAuthorTextView);
			    viewHolder.replyLayout = rowView.findViewById(R.id.replyLayout);
			    rowView.setTag(viewHolder);
		  	}else{
		  		viewHolder = (ViewHolder) rowView.getTag();
		//  		Picasso.with(this.context).cancelRequest(viewHolder.imageView);
		  	}
		  	setCommentInfo(viewHolder, position);
		  	return rowView;
	}
	 
	private void setCommentInfo(ViewHolder viewHolder, int position) {
		 Comment comment = comments.get(position);	 
		 String dateString = comment.getCreatedDate();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 Date date;
		 try {
			 date = sdf.parse(dateString);
			 long startDate = date.getTime() - 10800000 - 3600000;
			 viewHolder.CommentTimeTextView.setReferenceTime(startDate);
		 } catch (ParseException e) {
			 viewHolder.CommentTimeTextView.setText("unexpected");
			 e.printStackTrace();
			 Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		 } 
		 viewHolder.CommentAuthorTextView.setText(comment.getCommenter1());
		 viewHolder.CommentContentTextView.setText(comment.getText());
		 if (comment.getCommenterID2() != 0) {
			 for (int i = 0; i < comments.size(); i++) {
				 if (comments.get(i).getCommenterID1() == comment.getCommenterID2()) {
					 viewHolder.replycontentTextView.setText(comment.getText());
					 viewHolder.replyCommentAuthorTextView.setText("X " + String.valueOf(comment.getCommenter1()));
					 viewHolder.replyLayout.setVisibility(View.VISIBLE);
				 }
			 }
		 } 
	}
}

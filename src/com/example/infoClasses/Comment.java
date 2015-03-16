package com.example.infoClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment implements Parcelable {
	@JsonProperty("article_id")
	private int articleID;
	@JsonProperty("id")
	private int id;
	@JsonProperty("id1")
	private int commenterID1;
	@JsonProperty("id2")
	private int commenterID2;
	@JsonProperty("commenter")
	private String commenter1;
	@JsonProperty("commenter2")
	private String commenter2;
	@JsonProperty("updated_at")
	private String updatedDate;
	@JsonProperty("created_at")
	private String createdDate;
	@JsonProperty("body")
	private String text;
	private long updatedAtL;
	private long createdAtL;
	
	public int getArticleID() {
		return articleID;
	}
	public void setArticleID(int articleID) {
		this.articleID = articleID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCommenterID1() {
		return commenterID1;
	}
	public void setCommenterID1(int commenterID1) {
		this.commenterID1 = commenterID1;
	}
	public int getCommenterID2() {
		return commenterID2;
	}
	public void setCommenterID2(int commenterID2) {
		this.commenterID2 = commenterID2;
	}
	public String getCommenter1() {
		return commenter1;
	}
	public void setCommenter1(String commenter1) {
		this.commenter1 = commenter1;
	}
	public String getCommenter2() {
		return commenter2;
	}
	public void setCommenter2(String commenter2) {
		this.commenter2 = commenter2;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
		this.updatedAtL = dateToLong(updatedDate);
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
		this.createdAtL = dateToLong(createdDate);
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
        	Comment comment = new Comment(); 
        	comment.commenter1 = source.readString(); 
        	comment.commenter2 = source.readString(); 
        	comment.updatedDate = source.readString(); 
        	comment.createdDate = source.readString(); 
        	comment.text = source.readString(); 
        	comment.articleID = source.readInt(); 
        	comment.id = source.readInt(); 
        	comment.commenterID1 = source.readInt(); 
        	comment.commenterID2 = source.readInt(); 
        	return comment;  
        }

		@Override
		public Comment[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

    };
    
    private long dateToLong(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance(); 
		TimeZone tz = cal.getTimeZone();
		Date d = cal.getTime();
		long msFromEpochGmt = d.getTime();
		int offsetFromUTC = tz.getOffset(msFromEpochGmt);
		Date date;	
		try {
			date = sdf.parse(dateString);
			long longDate = date.getTime() + offsetFromUTC;
			return longDate;
		 } catch (ParseException e) {
			return 0;
		 } 
	}
    
    public static Comparator<Comment> getUpdateDateComparator() {
		return new Comparator<Comment>() {
			@Override
			public int compare(Comment first, Comment second) {
				if (first.updatedAtL < second.updatedAtL)	return 1;
				else if (first.updatedAtL > second.updatedAtL)	return -1;
				else return 0;
			}
		};
 	}
    
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeString(commenter1); 
		parcel.writeString(commenter2); 
		parcel.writeString(updatedDate); 
		parcel.writeString(createdDate); 
		parcel.writeString(text); 
		parcel.writeInt(articleID);  
		parcel.writeInt(id);
		parcel.writeInt(commenterID1);
		parcel.writeInt(commenterID2);
	}
	public long getUpdatedAtL() {
		return updatedAtL;
	}
	public void setUpdatedAtL(long updatedAtL) {
		this.updatedAtL = updatedAtL;
	}
	public long getCreatedAtL() {
		return createdAtL;
	}
	public void setCreatedAtL(long createdAtL) {
		this.createdAtL = createdAtL;
	}
}

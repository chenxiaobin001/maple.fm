package com.example.infoClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Article implements Parcelable {
	@JsonProperty("id")
	private int id;
	@JsonProperty("name")
	private String author;
	@JsonProperty("title")
	private String title;
	@JsonProperty("url")
	private String authorImage;
	@JsonProperty("updated_at")
	private String updateTime;
	@JsonProperty("created_at")
	private String createTime;
	@JsonProperty("text")
	private String content;
	@JsonProperty("like")
	private int like;
	@JsonProperty("dislike")
	private int dislike;
	@JsonProperty("comment")
	private int comment;
	public String getAuthor() {
		return author;
	}
	private long updateTimeL;
	public long getUpdateTimeL() {
		return updateTimeL;
	}
	public void setUpdateTimeL(long updateTimeL) {
		this.updateTimeL = updateTimeL;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthorImage() {
		return authorImage;
	}
	public void setAuthorImage(String authorImage) {
		this.authorImage = authorImage;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
		this.updateTimeL = dateToLong(updateTime);
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public int getDislike() {
		return dislike;
	}
	public void setDislike(int dislike) {
		this.dislike = dislike;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static Comparator<Article> getUpdateDateComparator() {
		return new Comparator<Article>() {
			@Override
			public int compare(Article first, Article second) {
				if (first.updateTimeL < second.updateTimeL)	return -1;
				else if (first.updateTimeL > second.updateTimeL)	return 1;
				else return 0;
			}
		};
 	}
	
	public static Comparator<Article> getCreateDateComparator() {
        return new Comparator<Article>() {

			@Override
			public int compare(Article first, Article second) {
				if (first.getCreateTime() == null || second.getCreateTime() ==  null)	return	0;
				return first.getId() - second.getId();
			}

        };
    }
	
	public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel source) {
        	Article article = new Article(); 
        	article.author = source.readString(); 
        	article.title = source.readString(); 
        	article.authorImage = source.readString(); 
        	article.updateTime = source.readString(); 
        	article.createTime = source.readString(); 
        	article.content = source.readString(); 
        	article.id = source.readInt(); 
        	article.like = source.readInt(); 
        	article.dislike = source.readInt(); 
        	article.comment = source.readInt(); 
        	return article;  
        }

		@Override
		public Article[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

    }; 
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeString(author); 
		parcel.writeString(title); 
		parcel.writeString(authorImage); 
		parcel.writeString(updateTime); 
		parcel.writeString(createTime); 
		parcel.writeString(content); 
		parcel.writeInt(id);  
		parcel.writeInt(like);
		parcel.writeInt(dislike);
		parcel.writeInt(comment);
	}
	
	private long dateToLong(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(dateString);
			long longDate = date.getTime() - 10800000 - 3600000;
			return longDate;
		 } catch (ParseException e) {
			return 0;
		 } 
	}
}

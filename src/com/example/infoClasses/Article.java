package com.example.infoClasses;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {
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
	
	public static Comparator<Article> getCreateDateComparator() {
        return new Comparator<Article>() {

			@Override
			public int compare(Article first, Article second) {
				if (first.getCreateTime() == null || second.getCreateTime() ==  null)	return	0;
				return first.getId() - second.getId();
			}

        };
    }
}

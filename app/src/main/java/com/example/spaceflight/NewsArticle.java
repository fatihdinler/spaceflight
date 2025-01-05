package com.example.spaceflight;

import com.google.gson.annotations.SerializedName;

public class NewsArticle {
	private int id;
	private String title;
	private String summary;
	
	@SerializedName("published_at")
	private String publishedAt;
	
	@SerializedName("image_url")
	private String imageUrl;
	
	private String url;
	
	// Getters
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public String getPublishedAt() {
		return publishedAt;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getUrl() {
		return url;
	}
	
	@Override
	public String toString() {
		return "NewsArticle{" +
			"id=" + id +
			", title='" + title + '\'' +
			", summary='" + summary + '\'' +
			", publishedAt='" + publishedAt + '\'' +
			", imageUrl='" + imageUrl + '\'' +
			", url='" + url + '\'' +
			'}';
	}
}

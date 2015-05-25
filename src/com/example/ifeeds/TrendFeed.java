package com.example.ifeeds;

import java.io.Serializable;

public class TrendFeed implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private String title;
	private String link;
	private String description;
	private String category;
	private String pubDate;
	private String guid;
	private String feedburnerOrigLink;
	public TrendFeed()
	{
		
	}
	 public TrendFeed(String title, String link, String description, String category, String pubDate,
	            String guid, String feedburnerOrigLink) {
	        this.title = title;
	        this.link = link;
	        this.description = description;
	        this.category = category;
	        this.pubDate = pubDate;
	        this.guid = guid;
	        this.feedburnerOrigLink = feedburnerOrigLink;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public String getLink() {
	        return link;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public String getCategory() {
	        return category;
	    }

	    public String getPubDate() {
	        return pubDate;
	    }

	    public String getGuid() {
	        return guid;
	    }

	    public String getFeedburnerOrigLink() {
	        return feedburnerOrigLink;
	    }


	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public void setLink(String link) {
	        this.link = link;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public void setCategory(String category) {
	        this.category = category;
	    }

	    public void setPubDate(String pubDate) {
	        this.pubDate = pubDate;
	    }

	    public void setGuid(String guid) {
	        this.guid = guid;
	    }

	    public void setFeedburnerOrigLink(String feedburnerOrigLink) {
	        this.feedburnerOrigLink = feedburnerOrigLink;
	    }
	
}


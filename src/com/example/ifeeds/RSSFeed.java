package com.example.ifeeds;

import java.util.List ;

public class RSSFeed {
	String title;
    String description;
    String link;
    String rsslink;
    String language;
    List<RSSItem> items;
    public RSSFeed()
	{
		
	}
    
    public RSSFeed(String title, String description, String link, String rsslink, String language) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.rsslink = rsslink;
        this.language = language;
    }

    public void setItems(List<RSSItem> items) {
        this.items = items;
    }

    public List<RSSItem> getItems() {
        return this.items;
    }
 
    public String getTitle() {
        return this.title;
    }
 
    public String getDescription() {
        return this.description;
    }
 
    public String getLink() {
        return this.link;
    }
 
    public String getRSSLink() {
        return this.rsslink;
    }
 
    public String getLanguage() {
        return this.language;
    }
}

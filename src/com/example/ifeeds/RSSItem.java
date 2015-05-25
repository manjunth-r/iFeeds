package com.example.ifeeds;

public class RSSItem {
	String title;
    String link;
    String description;
    String pubdate;
    String guid;
    
    public RSSItem(){
         
    }     

    public RSSItem(String title, String link, String description, String pubdate, String guid){
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubdate = pubdate;
        this.guid = guid;
    }

    public void setTitle(String title){
        this.title = title;
    }
     
    public void setLink(String link){
        this.link = link;
    }
     
    public void setDescription(String description){
        this.description = description;
    }
     
    public void setPubdate(String pubDate){
        this.pubdate = pubDate;
    }
     
     
    public void setGuid(String guid){
        this.guid = guid;
    }

    public String getTitle(){
        return this.title;
    }
     
    public String getLink(){
        return this.link;
    }
     
    public String getDescription(){
        return this.description;
    }
     
    public String getPubdate(){
        return this.pubdate;
    }
     
    public String getGuid(){
        return this.guid;
    }
}

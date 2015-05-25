package com.example.ifeeds;

public class WebSite {
	Integer id;
    String title;
    String link;
    String rsslink;
    String description;
     
    public WebSite(){
         
    }
 
    public WebSite(String title, String link, String rsslink, String description){
        this.title = title;
        this.link = link;
        this.rsslink = rsslink;
        this.description = description;
    }

    public void setId(Integer id){
        this.id = id;
    }
     
    public void setTitle(String title){
        this.title = title;
    }
     
    public void setLink(String link){
        this.link = link;
    }
     
    public void setRSSLink(String rsslink){
        this.rsslink = rsslink;
    }
     
    public void setDescription(String description){
        this.description = description;
    }

    public Integer getId(){
        return this.id;
    }
     
    public String getTitle(){
        return this.title;
    }
     
    public String getLink(){
        return this.link;
    }
     
    public String getRSSLink(){
        return this.rsslink;
    }
     
    public String getDescription(){
        return this.description;
    }
}

package com.example.ifeeds;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RSSDatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "iFeeds";
    private static final String TABLE_WEBSITE = "website";
    private static final String TABLE_LOGIN = "login" ;
 
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LINK = "link";
    private static final String KEY_RSS_LINK = "rss_link";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PASSWORD = "password" ;
    
    String CREATE_IFEEDS_WEBSITE = "CREATE TABLE " + TABLE_WEBSITE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_LINK
            + " TEXT," + KEY_RSS_LINK + " TEXT," + KEY_DESCRIPTION
            + " TEXT" + ")";
    
    String CREATE_IFEEDS_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_PASSWORD + " TEXT" + ")";
 
    public RSSDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) 
    {        
        db.execSQL(CREATE_IFEEDS_WEBSITE);
        db.execSQL(CREATE_IFEEDS_LOGIN);
        
    }
 

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEBSITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }
    

    public void insertPassword(Login login){
		
		SQLiteDatabase db = this.getWritableDatabase();		 
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD,login.getPassword());
        db.insert(TABLE_LOGIN, null, values);
        db.close();
	}
 
    public void addSite(WebSite site) 
    {	
        SQLiteDatabase db = this.getWritableDatabase(); 
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, site.getTitle());
        values.put(KEY_LINK, site.getLink()); 
        values.put(KEY_RSS_LINK, site.getRSSLink());
        values.put(KEY_DESCRIPTION, site.getDescription());

        if (!isSiteExists(db, site.getRSSLink())) {
            db.insert(TABLE_WEBSITE, null, values);
            db.close();
        } else {
            updateSite(site);
            db.close();
        }
    }
 
    public List<WebSite> getAllSites() {
        List<WebSite> siteList = new ArrayList<WebSite>();
        String selectQuery = "SELECT  * FROM " + TABLE_WEBSITE
                + " ORDER BY id DESC";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                WebSite site = new WebSite();
                site.setId(Integer.parseInt(cursor.getString(0)));
                site.setTitle(cursor.getString(1));
                site.setLink(cursor.getString(2));
                site.setRSSLink(cursor.getString(3));
                site.setDescription(cursor.getString(4));
                siteList.add(site);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return siteList;
    }
    
    public int updateLogin(Login login) {
		
        SQLiteDatabase db = this.getWritableDatabase(); 
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, login.getPassword());

        int update = db.update(TABLE_LOGIN, values, KEY_ID + " = ?",
                new String[] { String.valueOf(login.getId())});
        db.close();
        return update;
    }
	
	public void deleteLogin() {
		
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN, KEY_ID + " = ?",
                new String[] { String.valueOf(1)});
        db.close();
    }

    public int updateSite(WebSite site) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, site.getTitle());
        values.put(KEY_LINK, site.getLink());
        values.put(KEY_RSS_LINK, site.getRSSLink());
        values.put(KEY_DESCRIPTION, site.getDescription());

        int update = db.update(TABLE_WEBSITE, values, KEY_RSS_LINK + " = ?",
                new String[] { String.valueOf(site.getRSSLink()) });
        db.close();
        return update;
 
    }
    
    public Login getLogin() {
		
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Login login = null ;
        if (cursor.moveToFirst()) {
        	login = new Login();
        	login.setPassword(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return login ;
	}

    public WebSite getSite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_WEBSITE, new String[] { KEY_ID, KEY_TITLE,
                KEY_LINK, KEY_RSS_LINK, KEY_DESCRIPTION }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        WebSite site = new WebSite(cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));
 
        site.setId(Integer.parseInt(cursor.getString(0)));
        site.setTitle(cursor.getString(1));
        site.setLink(cursor.getString(2));
        site.setRSSLink(cursor.getString(3));
        site.setDescription(cursor.getString(4));
        cursor.close();
        db.close();
        return site;
    }
 
    public void deleteSite(WebSite site) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEBSITE, KEY_ID + " = ?",
                new String[] { String.valueOf(site.getId())});
        db.close();
    }
 
    public boolean isSiteExists(SQLiteDatabase db, String rss_link) {
 
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_WEBSITE
                + " WHERE rss_link = '" + rss_link + "'", new String[] {});
        boolean exists = (cursor.getCount() > 0);
        return exists;
    }
}

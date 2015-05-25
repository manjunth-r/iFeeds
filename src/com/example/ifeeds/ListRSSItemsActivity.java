package com.example.ifeeds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListRSSItemsActivity extends ListActivity {

	private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> rssItemList = new ArrayList<HashMap<String,String>>();
    RSSParser rssParser = new RSSParser();     
    List<RSSItem> rssItems = new ArrayList<RSSItem>();
    List<RSSItem> rssItems1 = new ArrayList<RSSItem>();
    
    private NewsFeedParser mNewsFeeder;
    private List<TrendFeed> mRssFeedList;
    RSSFeed rssFeed;
 
    private static String array[];    
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_PUB_DATE = "pubDate";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_item_list);

        Intent i = getIntent();
        Integer site_id = Integer.parseInt(i.getStringExtra("id"));
        RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getApplicationContext());     
        WebSite site = rssDB.getSite(site_id);
        String rss_link = site.getRSSLink();

        new loadRSSFeedItems().execute(rss_link);
         
        ListView lv = getListView();
  
        lv.setOnItemClickListener(new OnItemClickListener() {
  
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Intent in = new Intent(getApplicationContext(), DisplayWebPageActivity.class);

                String page_url = ((TextView) view.findViewById(R.id.page_url)).getText().toString();
                in.putExtra("page_url", page_url);
                startActivity(in);
            }
        });
    }
     
    class loadRSSFeedItems extends AsyncTask<String, String, String> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(
                    ListRSSItemsActivity.this);
            pDialog.setMessage("Loading recent articles...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... args) {
            String rss_url = args[0];
            rssItems = rssParser.getRSSFeedItems(rss_url);
            mNewsFeeder = new NewsFeedParser("https://www.google.co.in/trends/hottrends/atom/feed?pn=p3");
            mRssFeedList = mNewsFeeder.parse();
            array=new String[mRssFeedList.size()];
        	for(int i=0;i<mRssFeedList.size();i++)
            {
            	array[i]=mRssFeedList.get(i).getTitle();
            	Log.d("Trend", array[i]);
            }

        	for(int i=0;i<array.length;i++)
            {
            	for(int j=0;j<rssItems.size();j++)
            	{
            		String s = rssItems.get(j).getDescription();
            		if(s.contains(array[i]))
            		{
            			rssItems1.add(rssItems.get(j));
            			rssItems.remove(j);
            		}
            	}
            }
            rssItems1.addAll(rssItems);

            for(RSSItem item : rssItems1){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TAG_TITLE, item.getTitle());
                map.put(TAG_LINK, item.getLink());
                map.put(TAG_PUB_DATE, item.getPubdate());
                String description = item.getDescription();
                if(description.length() > 100){
                    description = description.substring(0, 97) + "..";
                }
                map.put(TAG_DESRIPTION, description);
                rssItemList.add(map);
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            ListRSSItemsActivity.this,
                            rssItemList, R.layout.rss_item_list_row,
                            new String[] { TAG_LINK, TAG_TITLE, TAG_PUB_DATE, TAG_DESRIPTION },
                            new int[] { R.id.page_url, R.id.title, R.id.pub_date, R.id.link });
                    setListAdapter(adapter);
                }
            });
            return null;
        }
 
        protected void onPostExecute(String args) {
            pDialog.dismiss();
        }
    }
}
package com.example.ifeeds;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Trend extends Activity  implements OnItemClickListener
{
	private static final String TOPSTORIES = "https://www.google.co.in/trends/hottrends/atom/feed?pn=p3";
    private ListView mRssListView;
    private NewsFeedParser mNewsFeeder;
    private List<TrendFeed> mRssFeedList;
    private String[] temp;
    private RssAdapter mRssAdap;
    private String country="India";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trend);
		mRssListView = (ListView) findViewById(R.id.rss_list_view);
        mRssFeedList = new ArrayList<TrendFeed>();
        new DoRssFeedTask().execute(TOPSTORIES);
        Toast.makeText(getApplicationContext(), "Country :"+country , Toast.LENGTH_SHORT).show();			
        mRssListView.setOnItemClickListener(this);
	}
    private class RssAdapter extends ArrayAdapter<TrendFeed> {
        private List<TrendFeed> rssFeedLst;

        public RssAdapter(Context context, int textViewResourceId, List<TrendFeed> rssFeedLst) {
            super(context, textViewResourceId, rssFeedLst);
            this.rssFeedLst = rssFeedLst;
        }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        RssHolder rssHolder = null;
        if (convertView == null) {
            view = View.inflate(Trend.this, R.layout.rss_list_item, null);
            rssHolder = new RssHolder();
            rssHolder.rssTitleView = (TextView) view.findViewById(R.id.rss_title_view);
            view.setTag(rssHolder);
        } else {
            rssHolder = (RssHolder) view.getTag();
        }
        TrendFeed rssFeed = rssFeedLst.get(position);
        rssHolder.rssTitleView.setText(rssFeed.getTitle());
        return view;
    }
}
    static class RssHolder {
        public TextView rssTitleView;
    }
    public class DoRssFeedTask extends AsyncTask<String, Void, List<TrendFeed>> {
        ProgressDialog prog;
        String jsonStr = null;
        Handler innerHandler;

       
        protected void onPreExecute() {
            prog = new ProgressDialog(Trend.this);
            prog.setMessage("Loading....");
            prog.show();
        }

        protected List<TrendFeed> doInBackground(String... params) {
            for (String urlVal : params) {
                mNewsFeeder = new NewsFeedParser(urlVal);
            }
            mRssFeedList = mNewsFeeder.parse();
            return mRssFeedList;
        }

        protected void onPostExecute(List<TrendFeed> result) {
            prog.dismiss();
            runOnUiThread(new Runnable() {
        
                public void run() {
                    mRssAdap = new RssAdapter(Trend.this, R.layout.rss_list_item,
                            mRssFeedList);
                    int count = mRssAdap.getCount();
                    if (count != 0 && mRssAdap != null) {
                        mRssListView.setAdapter(mRssAdap);
                    }
                }
            });
        }

        protected void onProgressUpdate(Void... values) {
        }
    }

    public class DoRssFeedTask1 extends AsyncTask<String, Void, List<TrendFeed>> {
        String jsonStr = null;
        Handler innerHandler;
       
        protected void onPreExecute() 
        {

        }
    
        protected List<TrendFeed> doInBackground(String... params) {
            for (String urlVal : params) {
                mNewsFeeder = new NewsFeedParser(urlVal);
            }
            mRssFeedList = mNewsFeeder.parse();
            return mRssFeedList;
        }

        protected void onPostExecute(List<TrendFeed> result) {
            runOnUiThread(new Runnable() {
           
                public void run() {
                	temp=new String[mRssFeedList.size()];
                	for(int i=0;i<mRssFeedList.size();i++)
                    {
                    	temp[i]=mRssFeedList.get(i).getTitle();
                    }
                }
            });
        }

        protected void onProgressUpdate(Void... values) {
        }
    }
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view,
            int position, long id) 
	{
		        Toast.makeText(getApplicationContext(), mRssFeedList.get(position).getTitle(), Toast.LENGTH_SHORT).show();			
	}
		
}

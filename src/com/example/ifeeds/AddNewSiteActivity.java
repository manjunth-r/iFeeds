package com.example.ifeeds;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewSiteActivity extends Activity {

	Button btnSubmit,btn1Submit;
    Button btnCancel,btn1Cancel;
    EditText txtUrl,rssURL;
    TextView lblMessage;
 
    RSSParser rssParser = new RSSParser();
 
    RSSFeed rssFeed;
    private ProgressDialog pDialog;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_site);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btn1Submit = (Button) findViewById(R.id.btn1Submit);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btn1Cancel = (Button) findViewById(R.id.btn1Cancel);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        rssURL = (EditText) findViewById(R.id.rssURL);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                String url = txtUrl.getText().toString();
                Log.d("URL Length", "" + url.length());

                if (url.length() > 0) {
                    String urlPattern = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
                    if (url.matches(urlPattern)) {
                        new loadRSSFeed().execute(url);
                    } else {
                    	Toast.makeText(getApplicationContext(), "Enter a Valid URL...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                	Toast.makeText(getApplicationContext(), "Enter a Website URL...", Toast.LENGTH_SHORT).show();
                }
 
            }
        });
        btn1Submit.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View v) {
                String url = rssURL.getText().toString();
                Log.d("URL Length", "" + url.length());
                if (url.length() > 0) 
                {
                    new loadManualRSSFeed().execute(url);                    
                } else {
                	Toast.makeText(getApplicationContext(), "Enter a URL...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        
        btn1Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    class loadRSSFeed extends AsyncTask<String, String, String> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNewSiteActivity.this);
            pDialog.setMessage("Fetching RSS Feeds ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) 
        {
            String url = args[0];
            rssFeed = rssParser.getRSSFeed(url);
            Log.d("rssFeed", " "+ url);
            if (rssFeed != null) {
                Log.e("RSS URL",
                        rssFeed.getTitle() + "" + rssFeed.getLink() + ""
                                + rssFeed.getDescription() + ""
                                + rssFeed.getLanguage());
                RSSDatabaseHandler rssDb = new RSSDatabaseHandler(
                        getApplicationContext());
                WebSite site = new WebSite(rssFeed.getTitle(), rssFeed.getLink(), rssFeed.getRSSLink(),
                        rssFeed.getDescription());
                rssDb.addSite(site);
                Intent i = getIntent();
                setResult(100, i);
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                    	Toast.makeText(getApplicationContext(), "RSS URL Not Found...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
        
        protected void onPostExecute(String args) {

            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (rssFeed != null) {
                    } 
                }
            }); 
        } 
    }


    class loadManualRSSFeed extends AsyncTask<String, String, String> {
	 
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(AddNewSiteActivity.this);
        pDialog.setMessage("Fetching RSS Feeds ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... args) 
    {
        String url = args[0];
        rssFeed = rssParser.getManualRSSFeed(url);
        if (rssFeed != null) {
            Log.e("RSS URL",
                    rssFeed.getTitle() + "" + rssFeed.getLink() + ""
                            + rssFeed.getDescription() + ""
                            + rssFeed.getLanguage());
            RSSDatabaseHandler rssDb = new RSSDatabaseHandler(
                    getApplicationContext());
            WebSite site = new WebSite(rssFeed.getTitle(), rssFeed.getLink(), rssFeed.getRSSLink(),
                    rssFeed.getDescription());
            rssDb.addSite(site);
            Intent i = getIntent();
            setResult(100, i);
            finish();
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                	Toast.makeText(getApplicationContext(), "Not a Valid RSS URL...", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return null;
    }

    protected void onPostExecute(String args) {
        pDialog.dismiss();
        runOnUiThread(new Runnable() {
            public void run() {
                	if (rssFeed != null) {
                	}
            	}
        	});
    	}
    }
}
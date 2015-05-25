package com.example.ifeeds;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class DisplayWebPageActivity extends Activity {

	WebView webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		Intent in = getIntent();
		final String page_url = in.getStringExtra("page_url");
		
		webview = (WebView) findViewById(R.id.webpage);
		webview.loadUrl(page_url);
		Button save=(Button) findViewById(R.id.save);
		webview.setWebViewClient(new DisPlayWebPageActivityClient());
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			new Download().execute(page_url);
				
			}
		});

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
	    	webview.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	private class DisPlayWebPageActivityClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.webview, menu);
		return true;
	}

    class Download extends AsyncTask<String, String, String> 
    {
        @Override
        protected void onPreExecute() 
        {
            Toast.makeText(getApplicationContext(), "Download Started", Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }
        public String url_trim(String url)
        {
        	String[] parts = url.split("/");
        	String lastWord = parts[parts.length- 1];
			return lastWord;
		}
        @Override
        protected String doInBackground(String... f_url) 
        {
            int count;
            Log.d("Error :", f_url[0].toString());
            try 
            {
                URL url = new URL(f_url[0]);
                String last = url_trim(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                InputStream input = new BufferedInputStream(url.openStream(),10*1024);
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/RSS/"+last);
                byte data[] = new byte[1024];
                @SuppressWarnings("unused")
				long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            }
            catch (Exception e) 
            {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) 
        {
        }
        @Override
        protected void onPostExecute(String file_url) {
            Toast.makeText(getApplicationContext(), "Download complete", Toast.LENGTH_LONG).show();
        }
    }
}

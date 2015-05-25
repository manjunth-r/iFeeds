package com.example.ifeeds;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

public class OpenURL extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_url);
		Intent in = getIntent();
		final String page_url = in.getStringExtra("page_url");
		
	    WebView mWebView = null;
	    mWebView = (WebView) findViewById(R.id.webview);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    Toast.makeText(getApplicationContext(), "Opening File : "+page_url, Toast.LENGTH_SHORT).show();
	    mWebView.loadUrl("file:///"+page_url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_open_url, menu);
		return true;
	}

}

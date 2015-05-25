package com.example.ifeeds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btnLogin ;
	EditText etPassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnLogin = (Button) findViewById(R.id.btnSubmit1);
        etPassword = (EditText) findViewById(R.id.rssURL);
        
        btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pass = etPassword.getText().toString();
				
				if(pass.length()>0){
										
					RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getApplicationContext());
					Login login = new Login(pass);
					Login log = rssDB.getLogin();
					
					if(log == null){
						rssDB.insertPassword(login);
						Toast.makeText(getApplicationContext(), "Your Password is Set...", Toast.LENGTH_SHORT).show();
						Intent i=new Intent(MainActivity.this,ListWebsitesActivity.class);
						startActivity(i);
						finish();
					} else if(login.getPassword().equals(log.getPassword())){
						Intent i=new Intent(MainActivity.this,ListWebsitesActivity.class);
						startActivity(i);
						finish();
					} else{
						Toast.makeText(getApplicationContext(), "Invalid Password...", Toast.LENGTH_SHORT).show();
					}
					
				} else{
					Toast.makeText(getApplicationContext(), "Enter Password...", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}

package com.example.ifeeds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordResetActivity extends Activity {

	EditText etOldPassword ;
	EditText etNewPassword ;
	Button btnReset ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_reset);
		
		etOldPassword = (EditText) findViewById(R.id.rssURL);
		etNewPassword = (EditText) findViewById(R.id.editText2);
		btnReset = (Button) findViewById(R.id.btnSubmit1);
		
		btnReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String oldPassword = etOldPassword.getText().toString() ;
				String newPassword = etNewPassword.getText().toString() ;
				
				if(oldPassword.length() > 0){
					if(newPassword.length() > 0){
						RSSDatabaseHandler rssDB = new RSSDatabaseHandler(getApplicationContext());
						Login login = new Login(oldPassword);				
						Login log = rssDB.getLogin();
						if(log.getPassword().equals(login.getPassword())){
							login.setPassword(newPassword);
							rssDB.updateLogin(login);
							Toast.makeText(getApplicationContext(), "Password Reset Successfully...", Toast.LENGTH_SHORT).show();
							Intent i=new Intent(getApplicationContext(),MainActivity.class);
							startActivity(i);
							finish();
						} else{
							Toast.makeText(getApplicationContext(), "Invalid Password...", Toast.LENGTH_SHORT).show();
						}
					} else{
						Toast.makeText(getApplicationContext(), "Enter New Password...", Toast.LENGTH_SHORT).show();
					}
				} else{
					Toast.makeText(getApplicationContext(), "Enter Old Password...", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_password_reset, menu);
		return true;
	}

}

package com.example.ifeeds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OpenFile extends ListActivity {

	private List<String> item = null;
	 private List<String> path = null;
	 private String root = "/";
	 private TextView myPath;

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_open_file);
	  myPath = (TextView) findViewById(R.id.path);
	  getDir(Environment.getExternalStorageDirectory().getPath()+"/RSS/");
	 }
	 private void getDir(String dirPath) {
	  myPath.setText("Location: " + dirPath);
	  item = new ArrayList<String>();
	  path = new ArrayList<String>();
	  File f = new File(dirPath);
	  File[] files = f.listFiles();
	  if (!dirPath.equals(root)) {
	   item.add(root);
	   path.add(root);
	   item.add("../");
	   path.add(f.getParent());
	  }
	  for (int i = 0; i < files.length; i++) {
	   File file = files[i];
	   path.add(file.getPath());
	   if (file.isDirectory())
	    item.add(file.getName() + "/");
	   else
	    item.add(file.getName());
	  }
	  ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,R.layout.explorer_row, item);
	  setListAdapter(fileList);
	         }
	       File file;
	 
	       protected void onListItemClick(ListView l, View v, int position, long id) 
	       {
	    	   file = new File(path.get(position));
	  if (file.isDirectory()) {
	    if (file.canRead())
	    getDir(path.get(position));
	   else {
	    new AlertDialog.Builder(this)
	    .setIcon(R.drawable.ic_launcher)
	    .setTitle("[" + file.getName()+ "] folder can't be read!")
	    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
	       @Override public void onClick(DialogInterface dialog,int which) {
	     }}).show();
	   }
	  } else {
	      new AlertDialog.Builder(this)
	     .setIcon(R.drawable.ic_launcher)
	     .setTitle("Select").
	     setMessage("Select " + file.getName() + "to server ?")
	    .setPositiveButton("Select",new DialogInterface.OnClickListener() 
	    {
	         
	  public void onClick(DialogInterface dialog,int which) 
	  {

		  Intent in = new Intent(getApplicationContext(), OpenURL.class);
		  Toast.makeText(getApplicationContext(),file.getAbsolutePath() , Toast.LENGTH_SHORT).show();
	      in.putExtra("page_url", file.getAbsolutePath().toString());
	      startActivity(in);
	      finish();
	   }
	 })
	 .setNegativeButton("No",new DialogInterface.OnClickListener() {
	  @Override
	          public void onClick(DialogInterface dialog,int which) {
	         dialog.dismiss();
	      }
	   }).show();
	  }
	     }

}

package com.sample;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CheckBook extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		
		ArrayList<String> list=new ArrayList<String>();
		list=getIntent().getStringArrayListExtra("get");
		 ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
	        
	     ListView listview=(ListView)findViewById(R.id.myListView);
	     listview.setAdapter(adapter);
	}
	
}

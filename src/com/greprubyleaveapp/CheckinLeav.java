package com.greprubyleaveapp;



import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class CheckinLeav extends ListActivity
{
	
	ImageView back;
	private static final String TAG_ID = "id";
	private static final String TAG_Date = "date";
	private static final String TAG_DAYS = "days";
	private static final String TAG_STATUS = "status";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin_leav);
		
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
		
		back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Intent i = new Intent(view.getContext(), ApplyOrCheckin.class); 	
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		// adding each child node to HashMap key => value
		map.put(TAG_ID, "1");
		map.put(TAG_Date, "01/08/2013");
		map.put(TAG_DAYS, "5");
		//map.put(TAG_STATUS, "view detail");
		// adding HashList to ArrayList
		
		contactList.add(map);
		contactList.add(map);
		contactList.add(map);
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		
		ListAdapter adapter = new SimpleAdapter(this, contactList,R.layout.list_item,new String[] { TAG_Date, TAG_DAYS }, new int[] {
						R.id.name, R.id.email});

		setListAdapter(adapter);

		
		// selecting single ListView item
				ListView lv = getListView();

				// Launching new screen on Selecting Single ListItem
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// getting values from selected ListItem
						String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
						String cost = ((TextView) view.findViewById(R.id.email)).getText().toString();
						
						
						// Starting new intent
						Intent in = new Intent(getApplicationContext(), LeaveDetail.class);
						in.putExtra(TAG_Date, name);
						in.putExtra(TAG_DAYS, cost);
						
						startActivity(in);
					}
				});
				
	}
}
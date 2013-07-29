package com.greprubyleaveapp;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LeaveDetail  extends Activity {
	
	// JSON node keys
	private static final String TAG_Date = "date";
	private static final String TAG_DAYS = "days";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_detail);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_Date);
        String cost = in.getStringExtra(TAG_DAYS);
       
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblCost = (TextView) findViewById(R.id.email_label);
        
        
        lblName.setText(name);
        lblCost.setText(cost);
        
    }
}

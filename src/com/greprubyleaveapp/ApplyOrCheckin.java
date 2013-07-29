package com.greprubyleaveapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ApplyOrCheckin extends Activity
{
	Button apply,checkin,signOut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_or_checkin);
		
		apply=(Button)findViewById(R.id.apply);
		apply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Intent i = new Intent(view.getContext(), ApplyLeave.class); 	
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		checkin=(Button)findViewById(R.id.checkin);
		checkin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Intent i = new Intent(view.getContext(), CheckinLeav.class); 	
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		signOut=(Button)findViewById(R.id.signout);
		signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Intent i = new Intent(view.getContext(), MainActivity.class); 	
	        	startActivity(i);
	        	finish();
	        	
            }
           });
	}
}
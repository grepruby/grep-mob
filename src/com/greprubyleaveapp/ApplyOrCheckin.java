package com.greprubyleaveapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ApplyOrCheckin extends Activity
{
	Button apply,checkin,signOut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_or_checkin);
		
		Bundle gb  = this.getIntent().getExtras();
		final String apiToken = gb.getString("apiToken");
		final String uName  = gb.getString("uName");
		
		//String answer = String.valueOf(apiToken);
    	Toast.makeText(getApplicationContext(), uName, Toast.LENGTH_LONG).show();
		
		apply=(Button)findViewById(R.id.apply);
		apply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Bundle pb = new Bundle();
            	pb.putString("uName", uName);
            	pb.putString("apiToken", apiToken);
            	
            	Intent i = new Intent(view.getContext(), ApplyLeave.class); 	
            	i.putExtras(pb);
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		checkin=(Button)findViewById(R.id.checkin);
		checkin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Bundle pb = new Bundle();
            	pb.putString("uName", uName);
            	pb.putString("apiToken", apiToken);
            	
            	Intent i = new Intent(view.getContext(), CheckinLeav.class); 	
            	i.putExtras(pb);
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
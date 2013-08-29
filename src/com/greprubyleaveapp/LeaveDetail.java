package com.greprubyleaveapp;



import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LeaveDetail  extends Activity {
	
	
	
	ImageView back,signOut;
	private String apiToken;
	private String uName;
	
	// JSON node keys
	private static final String LEAVE_FROM = "leave_from";
	private static final String LEAVE_TO = "to";
	private static final String LEAVE_TYPE = "leave_type";
	private static final String REASON = "reason";
	private static final String STATUS = "status";
	private String isComingFrom="";
	private float x1, x2;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_detail);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String leave_frm = in.getStringExtra(LEAVE_FROM);
        String leave_to = in.getStringExtra(LEAVE_TO);
        String leave_type = in.getStringExtra(LEAVE_TYPE);
        String reason = in.getStringExtra(REASON);
        String status = in.getStringExtra(STATUS);
        uName  = in.getStringExtra("uName");
        apiToken  = in.getStringExtra("apiToken");
        isComingFrom  = in.getStringExtra("isComingFrom");
        
        // Displaying all values on the screen
        TextView txtName = (TextView) findViewById(R.id.name_id);
        //TextView txtDateFrom = (TextView) findViewById(R.id.date_from_id);
        //TextView txtDateTo = (TextView) findViewById(R.id.date_to_id);
        TextView txtLeaveType = (TextView) findViewById(R.id.leave_type_id);
        TextView txtReason = (TextView) findViewById(R.id.reason_id);
        //TextView txtStatus = (TextView) findViewById(R.id.status_id);
        
        
        
        txtName.setText(uName);
        //txtDateFrom.setText(leave_frm);
       // txtDateTo.setText(leave_to);
        txtLeaveType.setText(leave_type);
        txtReason.setText(reason);
      /*  txtStatus.setText(status);
        if(status.equals("pending")){
        	txtStatus.setTextColor(Color.BLUE);
        }else{
	        if(status.equals("approved")){
	        	txtStatus.setTextColor(Color.parseColor("#21610B"));
	        }else{
	        	txtStatus.setTextColor(Color.RED);
	        	}
	     }
        
        */
        
        
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	
            	
            	if(isComingFrom.equals("Checkin")){
            	Intent i = new Intent(view.getContext(), CheckinLeav.class); 	
	        	startActivity(i);
	        	overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
            	}else{
            		Intent i = new Intent(view.getContext(), ApplyOrCheckin.class); 	
    	        	startActivity(i);
    	        	overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
            	}
            	
	        	finish();
	        	
            }
           });
        
        signOut=(ImageView)findViewById(R.id.signout);
		signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent i = new Intent(view.getContext(), Login.class); 
	        	startActivity(i);
	        	finish();
	        	
            }
           });
        
       
    }
	
	
	public boolean onTouchEvent(MotionEvent touchevent)
    {
                 switch (touchevent.getAction())
                 {
                        // when user first touches the screen we get x and y coordinate
                         case MotionEvent.ACTION_DOWN:
                         {
                             x1 = touchevent.getX();
                             break;
                        }
                         case MotionEvent.ACTION_UP:
                         {
                             x2 = touchevent.getX();

                             //if left to right sweep event on screen
                             if (x1 < x2)
                             {
                             	
                             	if(isComingFrom.equals("Checkin")){
                             	Intent i = new Intent(this, CheckinLeav.class); 
                 	        	startActivity(i);
                 	        	overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
                             	}else{
                             		Intent i = new Intent(this, ApplyOrCheckin.class); 	
                     	        	startActivity(i);
                     	        	overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
                             	}
                             	
                 	        	finish();
                            	 
                            	 
                              }
                     
                             break;
                         }
                 }
                 return false;
    }
	
	
	
	
	
	
}

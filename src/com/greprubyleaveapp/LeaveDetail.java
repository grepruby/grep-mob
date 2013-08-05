package com.greprubyleaveapp;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LeaveDetail  extends Activity {
	
	
	
	ImageView back;
	private String apiToken;
	private String uName;
	
	// JSON node keys
	private static final String LEAVE_FROM = "leave_from";
	private static final String LEAVE_TO = "to";
	private static final String LEAVE_TYPE = "leave_type";
	private static final String REASON = "reason";
	private static final String STATUS = "status";
	
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
        
        // Displaying all values on the screen
        TextView txtName = (TextView) findViewById(R.id.name_id);
        //TextView txtDateFrom = (TextView) findViewById(R.id.date_from_id);
        //TextView txtDateTo = (TextView) findViewById(R.id.date_to_id);
        TextView txtLeaveType = (TextView) findViewById(R.id.leave_type_id);
        TextView txtReason = (TextView) findViewById(R.id.reason_id);
        TextView txtStatus = (TextView) findViewById(R.id.status_id);
        
        
        
        txtName.setText(uName);
        //txtDateFrom.setText(leave_frm);
       // txtDateTo.setText(leave_to);
        txtLeaveType.setText(leave_type);
        txtReason.setText(reason);
        txtStatus.setText(status);
        
        
        
        
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
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
        
       
    }
}

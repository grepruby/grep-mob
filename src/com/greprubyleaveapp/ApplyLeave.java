package com.greprubyleaveapp;


import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


public class ApplyLeave extends Activity
{
	
	Button cancel,leaveFromButton,leaveUntilButton;
	TextView leaveFromTxt,leaveUntillTxt,currentDate;
	public static final int Date_dialog_id = 1;
	// date and time
	private int mYear;
	private int mMonth;
	private int mDay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_leave);
		
		leaveFromButton=(Button)findViewById(R.id.leave_from_btn);
		leaveFromTxt=(TextView)findViewById(R.id.leave_from_txt);
		leaveUntilButton=(Button)findViewById(R.id.until_btn);
		leaveUntillTxt=(TextView)findViewById(R.id.until_txt);
		currentDate=(TextView)findViewById(R.id.current_date);
		cancel=(Button)findViewById(R.id.cancel);
		
		cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Intent i = new Intent(view.getContext(), ApplyOrCheckin.class); 	
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		   leaveFromButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	DatePickerDialog DPD = new DatePickerDialog(ApplyLeave.this, mDateSetListenerFrom, mYear, mMonth,mDay);
                DPD.show();
            }
           });
		   leaveUntilButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	DatePickerDialog DPD = new DatePickerDialog(ApplyLeave.this, mDateSetListenerUntil, mYear, mMonth,mDay);
	                DPD.show();
	            }
	           });
		
		    final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
			currentDate();
			changeDateFrom();
			//changeDateUntil();
	}
	@Override
	@Deprecated
	protected void onPrepareDialog(int id, Dialog dialog) {
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog);

		((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);

	}

	private DatePickerDialog.OnDateSetListener mDateSetListenerFrom = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			changeDateFrom();
		}
	};
	private DatePickerDialog.OnDateSetListener mDateSetListenerUntil = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			changeDateUntil();
		}
	};

	private void currentDate() {
		// TODO Auto-generated method stub
		// Month is 0 based so add 1
		currentDate.setText(new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear));
	}
	private void changeDateFrom() {
		// TODO Auto-generated method stub
		// Month is 0 based so add 1
		leaveFromTxt.setText(new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear));
	}
	private void changeDateUntil() {
		// TODO Auto-generated method stub
		// Month is 0 based so add 1
		leaveUntillTxt.setText(new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear));
	}

}

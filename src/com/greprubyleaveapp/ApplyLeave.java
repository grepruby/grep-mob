package com.greprubyleaveapp;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;


public class ApplyLeave extends Activity
{
	
	private Button cancel,leaveFromButton,leaveUntilButton,submit;
	private TextView leaveFromTxt,leaveUntillTxt,currentDate,name,toId,fromId;
	private EditText reason;
	private RadioGroup radioGroup;
	private RadioButton radioButton,fullDay,fhalfDay,shalfDay;
	
    JSONParser jsonParser = new JSONParser();
	
	
	
	private static final String TAG_SUCCESS = "success";
	
	
	public static final int Date_dialog_id = 1;
	// date and time
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private String apiToken;
	private String uName;
	
	private int selectedId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_leave);
		
		leaveFromButton=(Button)findViewById(R.id.leave_from_btn);
		leaveFromTxt=(TextView)findViewById(R.id.leave_from_txt);
		leaveUntilButton=(Button)findViewById(R.id.until_btn);
		leaveUntillTxt=(TextView)findViewById(R.id.until_txt);
		currentDate=(TextView)findViewById(R.id.current_date);
		toId=(TextView)findViewById(R.id.to_id);
		fromId=(TextView)findViewById(R.id.from_id);
		name=(TextView)findViewById(R.id.name);
		reason=(EditText)findViewById(R.id.reason);
		cancel=(Button)findViewById(R.id.cancel);
		submit=(Button)findViewById(R.id.submit);
		
		
		radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
		
		
		fullDay = (RadioButton)findViewById(R.id.full_day);
		fhalfDay = (RadioButton)findViewById(R.id.f_half_day);
		shalfDay = (RadioButton)findViewById(R.id.s_half_day);
		
		Bundle gb  = this.getIntent().getExtras();
		apiToken = gb.getString("apiToken");
		uName  = gb.getString("uName");
		name.setText(uName);
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) { 
            	
            	if (checkedId == fullDay.getId()) { //do something
            		//Toast.makeText(ApplyLeave.this,"full day", 2000).show();
            		toId.setVisibility(View.VISIBLE);
            		leaveUntilButton.setVisibility(View.VISIBLE);
            		leaveUntillTxt.setVisibility(View.VISIBLE);
            		fromId.setText("Leave From : ");
                } 
            	else{ // do other thing
            		//Toast.makeText(ApplyLeave.this,"half day", 2000).show();
            		toId.setVisibility(View.GONE);
            		leaveUntilButton.setVisibility(View.GONE);
            		leaveUntillTxt.setVisibility(View.GONE);
            		fromId.setText("Leave On : ");
                }
                 
            }
		});
		
		submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	
            	new SendLeave().execute(); 
            	
	        	
            }
           });
		cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Bundle pb = new Bundle();
            	pb.putString("uName", uName);
            	pb.putString("apiToken", apiToken);
            	
            	Intent i = new Intent(view.getContext(), ApplyOrCheckin.class);
            	i.putExtras(pb);
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
			changeDateUntil();
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
	
	
	
	
	
	
	/**
	 * Background Async Task to Create new leave
	 * */
	class SendLeave extends AsyncTask<String, String, String> {

		

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			
			String curDate = currentDate.getText().toString();
			String leaveFrom = leaveFromTxt.getText().toString();
			String leaveUntill = leaveUntillTxt.getText().toString();
			String leaveReason = reason.getText().toString();
			
			selectedId = radioGroup.getCheckedRadioButtonId();
			radioButton = (RadioButton) findViewById(selectedId);
			
			String leaveType = radioButton.getText().toString();
			
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("api_token", apiToken));
			//params.add(new BasicNameValuePair("leave[name]", uName));
			params.add(new BasicNameValuePair("leave[applied_date]", curDate));
			params.add(new BasicNameValuePair("leave[leave_from]", leaveFrom));
			params.add(new BasicNameValuePair("leave[to]", leaveUntill));
			params.add(new BasicNameValuePair("leave[reason]", leaveReason));
			params.add(new BasicNameValuePair("leave[leave_type]", leaveType));
			
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(BeanClass.url_apply_leave,"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				String success = json.getString(TAG_SUCCESS);
				
				
				if (success.equals("true")) {
					// successfully sign up
					
					

	            	Bundle pb = new Bundle();
	            	pb.putString("uName", uName);
	            	pb.putString("apiToken", apiToken);
	            	
	            	Intent i = new Intent(getApplicationContext(), ApplyOrCheckin.class);
	            	i.putExtras(pb);
		        	startActivity(i);
		        	finish();
					
				} else {
					// failed to signup
					
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

	}
	}
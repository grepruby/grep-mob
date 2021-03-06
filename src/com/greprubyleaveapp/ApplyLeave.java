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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;


public class ApplyLeave extends Activity
{
	
	private Button submit;
	private TextView leaveFromTxt,leaveUntillTxt,currentDate,name,toId,fromId;
	private EditText reason;
	private RadioGroup radioGroup;
	private RadioButton radioButton,fullDay,fhalfDay,shalfDay;
	private ImageView leaveFromButton,leaveUntilButton,back,signOut;
	
    JSONParser jsonParser = new JSONParser();
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ERRORS = "errors";
	private String server_error; 
	
	public static final int Date_dialog_id = 1;
	// date and time
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private ProgressDialog pDialog;
	
	private String apiToken;
	private String uName;
	
	private int selectedId;
	
	private int responseToken = 0;
	
	private float x1,x2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_leave);
		
		leaveFromButton=(ImageView)findViewById(R.id.leave_from_btn);
		leaveFromTxt=(TextView)findViewById(R.id.leave_from_txt);
		leaveUntilButton=(ImageView)findViewById(R.id.until_btn);
		leaveUntillTxt=(TextView)findViewById(R.id.until_txt);
		currentDate=(TextView)findViewById(R.id.current_date);
		toId=(TextView)findViewById(R.id.to_id);
		fromId=(TextView)findViewById(R.id.from_id);
		name=(TextView)findViewById(R.id.name);
		reason=(EditText)findViewById(R.id.reason);
		back=(ImageView)findViewById(R.id.back);
		submit=(Button)findViewById(R.id.submit);
		
		
		radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
		
		
		fullDay = (RadioButton)findViewById(R.id.full_day);
		fhalfDay = (RadioButton)findViewById(R.id.f_half_day);
		shalfDay = (RadioButton)findViewById(R.id.s_half_day);
		
		uName = BeanClass.getUserName();
		apiToken = BeanClass.getApiToken();
		
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
		back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	
            	Intent i = new Intent(view.getContext(), ApplyOrCheckin.class);
	        	startActivity(i);
	        	finish();
	        	overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
	        	
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
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ApplyLeave.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		
		

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
			JSONObject json = jsonParser.makeHttpRequest(GlobalVariables.url_apply_leave,"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			
			try {
				String success = json.getString(TAG_SUCCESS);
				pDialog.dismiss();
				
				if (success.equals("true")) {
					// successfully sign up
					
					responseToken=1;

	            	
	            	
	            	Intent i = new Intent(getApplicationContext(), ApplyOrCheckin.class);
		        	startActivity(i);
		        	finish();
					
				} else {
					// failed to signup
					server_error = json.getString(TAG_ERRORS);
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			if(responseToken==0){
				
				server_error = server_error.replace("[", "");
				server_error = server_error.replace("]", "");
				server_error = server_error.replace("{", "");
				server_error = server_error.replace("}", "");
				server_error = server_error.replace(":", " ");
				server_error = server_error.replace("\"", "");
				alertDilog(server_error);
			
			}
			
			
		}

	}
	
	
void alertDilog(String msg){
		
		// custom dialog
		final Context context = this;
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dilog);
		dialog.setTitle("Log In");
		
		
		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(msg);
		text.setTextColor(Color.parseColor("#000000"));
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.ic_launcher);
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {				
            	
            	dialog.dismiss();
	        	
            }
           });

		dialog.show();


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
		                        	
		                         	
		                         	Intent i = new Intent(this, ApplyOrCheckin.class);
		                         	startActivity(i);
		             	        	finish();
		             	        	overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
		             	        	
		                          }
		                 
		                         break;
		                     }
		             }
		             return false;
		}
			
	
	}
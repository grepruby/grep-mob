package com.greprubyleaveapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends Activity
{
	EditText uname,email,password,c_password,mobile;
	Button submit,reset;
	
	JSONParser jsonParser = new JSONParser();
	
	private static String url_signup = "http://grep-ruby-leave-app.herokuapp.com/api/v1/registration";
	
	private static final String TAG_SUCCESS = "success";
	
	int token=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		uname=(EditText)findViewById(R.id.uname);
		email=(EditText)findViewById(R.id.email);
		password=(EditText)findViewById(R.id.password);
		c_password=(EditText)findViewById(R.id.c_password);
		mobile=(EditText)findViewById(R.id.mobile);
		submit=(Button)findViewById(R.id.submit);
		reset=(Button)findViewById(R.id.reset);
		
		
		// button click event
		submit.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						// signup in background thread
						new SignupData().execute();
					}
		});
		
		// button click event
		reset.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View view) {
								
								
								uname.setText("");uname.setHint("New Username(min 6 characters)");
								email.setText("");email.setHint("Email");
								password.setText("");password.setHint("Password(min 8 alphanumerics)");
								c_password.setText("");c_password.setHint("Confirm Password");
								mobile.setText("");mobile.setHint("Mobile Phone Number");
								
								
							}
		});
		
	}
	
	
	
	
	
	/**
	 * Background Async Task to Create new product
	 * */
	class SignupData extends AsyncTask<String, String, String> {

		

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			
			String userName = uname.getText().toString();
			String userEmail = email.getText().toString();
			String userPassword = password.getText().toString();
			String confirmPassword = c_password.getText().toString();
			String mobileNumber = mobile.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user[username]", userName));
			params.add(new BasicNameValuePair("user[email]", userEmail));
			params.add(new BasicNameValuePair("user[password]", userPassword));
			params.add(new BasicNameValuePair("user[password_confirmation]", confirmPassword));
			params.add(new BasicNameValuePair("user[phone]", mobileNumber));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_signup,"POST", params);
			
			// check log cat fro response
			//Log.d("Create Response", json.toString());/
			// check for success tag
			try {
				String success = json.getString(TAG_SUCCESS);
				
				
				if (success.equals("true")) {
					// successfully sign up
					token=1;
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(i);
					finish();
				} else {
					// failed to signup
					//test();
					//token=0;
					//Toast.makeText(Signup.this, "faild to Sign up", Toast.LENGTH_SHORT).show();
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			if(token==0){
				alertDilog();
			}
		}
		
	}
	@SuppressWarnings("deprecation")
	void alertDilog(){
		
		AlertDialog alertDialog = new AlertDialog.Builder(Signup.this).create();
		alertDialog.setTitle("Sign up");
		alertDialog.setMessage("Please fill the correct information.");
		alertDialog.setIcon(R.drawable.logo);
		
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // Write your code here to execute after dialog closed
            //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
    });

    // Showing Alert Message
    alertDialog.show();

}

}

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
	private EditText uname,email,password,c_password,mobile;
	private Button submit,reset;
	
	JSONParser jsonParser = new JSONParser();
	
	
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ERRORS = "errors";
	private String server_error;
	private TextView wrongUname,wrongEmail,wrongPassword,wrongCpassword,wrongMobile;
	
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
		wrongUname=(TextView)findViewById(R.id.wrong_uname);
		wrongEmail=(TextView)findViewById(R.id.wrong_email);
		wrongPassword=(TextView)findViewById(R.id.wrong_password);
		wrongCpassword=(TextView)findViewById(R.id.wrong_cpassword);
		wrongMobile=(TextView)findViewById(R.id.wrong_mobile);
		
		// button click event
		submit.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						// signup in background thread
						 int token=0;
						if(uname.getText().toString().equals("") || uname.getText().toString().length()<6){
							wrongUname.setText("User name must be at least 6 characters.");
							token=1;
						}else{
							wrongUname.setText("");
						}
						if(email.getText().toString().equals("")){
							token=1;
							wrongEmail.setText("You can't leave this empty.");
						}else{
							wrongEmail.setText("");
						}
						if(password.getText().toString().equals("")){
							token=1;
							wrongPassword.setText("You can't leave this empty.");
						}else{
							wrongPassword.setText("");
						}
						if(c_password.getText().toString().equals("") || !c_password.getText().toString().equals(password.getText().toString())){
							token=1;
							wrongCpassword.setText("Confirm your password here.");
						}else{
							wrongCpassword.setText("");
						}
						if(mobile.getText().toString().equals("") || mobile.getText().toString().length()<10){
							token=1;
							wrongMobile.setText("Mobile no. must be 10 digit.");
						}else{
							wrongMobile.setText("");
						}
						if(token==0){
							new SignupData().execute();
						}
						
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
			JSONObject json = jsonParser.makeHttpRequest(BeanClass.url_signup,"POST", params);
			
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
					
					server_error = json.getString(TAG_ERRORS);
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
				server_error = server_error.replace("[", "");
				server_error = server_error.replace("]", "");
				server_error = server_error.replace("{", "");
				server_error = server_error.replace("}", "");
				server_error = server_error.replace(":", " ");
				server_error = server_error.replace("\"", "");
				alertDilog(server_error);
				System.out.println(server_error);
			}
			
			
		}
		
	}
	@SuppressWarnings("deprecation")
	void alertDilog(String msg){
		
		AlertDialog alertDialog = new AlertDialog.Builder(Signup.this).create();
		alertDialog.setTitle("Sign up");
		alertDialog.setMessage(msg);
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

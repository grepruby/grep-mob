package com.greprubyleaveapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

public class Login extends Activity
{
	
	Button loginButton;
	TextView forgot,wrongEmail,wrongPassword;
	EditText email,password;
	
	String userEmail ;
	String userPassword;
	String success;
	int token=0;
	
	// Progress Dialog
	private ProgressDialog pDialog;
	
	private static final String TAG = "Login";
    private static final int DLG_EXAMPLE1 = 0;
    private static final int TEXT_ID = 0;
   
    
    
    JSONParser jsonParser = new JSONParser();
    
    private static String url_signin = "http://grep-ruby-leave-app.herokuapp.com/api/v1/session";
    
    private static final String TAG_SUCCESS = "success";
    private static final String TOKEN="api_token";
    private static final String NAME="name";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		loginButton=(Button)findViewById(R.id.login);
		email=(EditText)findViewById(R.id.email);
		password=(EditText)findViewById(R.id.password);
		forgot=(TextView)findViewById(R.id.forgot);
		wrongEmail=(TextView)findViewById(R.id.wrong_email);
		wrongPassword=(TextView)findViewById(R.id.wrong_password);
				
		
		loginButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
            	
        		userEmail = email.getText().toString();
    			userPassword = password.getText().toString();
    			
    			if(userEmail.equals("")){
    				
    				wrongEmail.setText("Enter your email address.");
    				
    				}else if(userPassword.equals("")){
    				
    					wrongPassword.setText("Enter your password.");
    					wrongEmail.setText("");
    					
    					  }else{
    						  
    						  new SigninData().execute();
    						  
    						/*  final Handler handler = new Handler(); 
    						    Timer t = new Timer(); 
    						    t.schedule(new TimerTask() { 
    						            public void run() { 
    						                    handler.post(new Runnable() { 
    						                            public void run() { 
    						                                
    						                            	//alertDilog();
    						                            	wrongEmail.setText("");
    						                            	wrongPassword.setText("");
    						                            	wrongPassword.setText("The username or password you entered is incorrect.");
    						                            	
    						                            } 
    						                    }); 
    						            } 
    						    }, 6000); 
    						  */
    					  }
        	  
				
            }
           });
		
		forgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	showDialog(DLG_EXAMPLE1);
	        	
            }
           });
		
		
	}
	
	
	
	

    /**
     * Called to create a dialog to be shown.
     */
    @Override
    protected Dialog onCreateDialog(int id) {
 
        switch (id) {
            case DLG_EXAMPLE1:
                return createExampleDialog();
            default:
                return null;
        }
    }
    
    
    
 
    /**
     * If a dialog has already been created,
     * this is called to reset the dialog
     * before showing it a 2nd time. Optional.
     */
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
 
        switch (id) {
            case DLG_EXAMPLE1:
                // Clear the input box.
                EditText text = (EditText) dialog.findViewById(TEXT_ID);
                text.setText("");
                break;
        }
    }
 
    
    
    /**
     * Create and return an example alert dialog with an edit text box.
     */
    private Dialog createExampleDialog() {
 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GrepRuby");
        builder.setMessage("Enter your email:");
 
         // Use an EditText view to get user input.
         final EditText input = new EditText(this);
         input.setId(TEXT_ID);
         builder.setView(input);
 
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
 
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Log.d(TAG, "User name: " + value);
                return;
            }
        });
 
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
 
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
 
        return builder.create();
    }
    
    
    
    
    /**
	 * Background Async Task to Create new product
	 * */
	class SigninData extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
			
		/**
		 * Creating product
		 * */
		@SuppressWarnings("deprecation")
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("user[email]", userEmail));
			params.add(new BasicNameValuePair("user[password]", userPassword));
			

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_signin,"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				success = json.getString(TAG_SUCCESS);
				pDialog.dismiss();
				if (success.equals("true")) {
					token=1;
					String apiToken = json.getString(TOKEN);
					String uName = json.getString(NAME);
					Bundle bundle = new Bundle();
					bundle.putString("apiToken", apiToken);
					bundle.putString("uName", uName);
					// successfully created product
					Intent i = new Intent(getApplicationContext(), ApplyOrCheckin.class);
					i.putExtras(bundle);
					startActivity(i);
					// closing this screen
					finish();
				} else {
					// failed to signup
					//alertDilog();
					//token=0;
					//System.out.println("------"+success+"-------");
					
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
				
				AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
				alertDialog.setTitle("Log in");
				alertDialog.setMessage("The username or password you entered is incorrect.");
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
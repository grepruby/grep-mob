package com.greprubyleaveapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends Activity
{
	
	private Button loginButton;
	private TextView forgot,signUp,wrongEmail,wrongPassword;
	private EditText email,password;
	private String userEmail ;
	private String userPassword;
	private String success;
	private String forgotPassword;
	private String apiToken;
	private int responseToken = 0;
	private static final String TAG = "Login";
    private static final int DLG = 0;
    private static final int TEXT_ID = 0;
    private static final String TAG_SUCCESS = "success";
    private static final String TOKEN="api_token";
    private static final String NAME="name";
    private String storedEmail;
    private String storedPassword;
	private ProgressDialog pDialog;
    private LoginDataBaseAdapter loginDataBaseAdapter;
    private JSONParser jsonParser = new JSONParser();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		loginButton = (Button)findViewById(R.id.login);
		email = (EditText)findViewById(R.id.email);
		password = (EditText)findViewById(R.id.password);
		signUp = (TextView)findViewById(R.id.new_user);
		forgot = (TextView)findViewById(R.id.forgot);
		wrongEmail = (TextView)findViewById(R.id.wrong_email);
		wrongPassword = (TextView)findViewById(R.id.wrong_password);
		
	// get Instance  of Database Adapter
		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();
				
		
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
    					
    					  }
        	  
				
            }
           });
		
		signUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {				
            	
            	Intent i = new Intent(getApplicationContext(), Signup.class);
				startActivity(i);
				finish();
	        	
            }
           });
		
		
		forgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {				
            	
            	showDialog(DLG);
	        	
            }
           });
		
		
		
		storedEmail = loginDataBaseAdapter.getSinlgeEntryEmail();
		storedPassword = loginDataBaseAdapter.getSinlgeEntryPassword();
		
		email.setText(storedEmail);
		password.setText(storedPassword);
		
		
		//Toast.makeText(getApplicationContext(), storedEmail, Toast.LENGTH_SHORT).show();
		//Toast.makeText(getApplicationContext(), storedPassword, Toast.LENGTH_SHORT).show();
		
		
	}
	
	
	
	

    /**
     * Called to create a dialog to be shown.
     */
    @Override
    protected Dialog onCreateDialog(int id) {
 
        switch (id) {
            case DLG:
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
            case DLG:
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
        builder.setTitle("Enter your email:");
        builder.setIcon(R.drawable.logo);
 
         // Use an EditText view to get user input.
         final EditText input = new EditText(this);
         input.setId(TEXT_ID);
         builder.setView(input);
         
         
         builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
 
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
            	forgotPassword = input.getText().toString();
                Log.d(TAG, "User name: " + forgot);
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
			
		
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("user[email]", userEmail));
			params.add(new BasicNameValuePair("user[password]", userPassword));
			

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(GlobalVariables.url_signin,"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				success = json.getString(TAG_SUCCESS);
				pDialog.dismiss();
				if (success.equals("true")) {
					responseToken=1;
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
					
					//System.out.println("------"+fail+"-------");
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(responseToken==0){
				alertDilog();
			}else{
				
				setupDataBase();
			}
		}
		
		
	}
	
	
	
	
	/**
	 * Background Async Task to Create new product
	 * */
	
	class SendEmail extends AsyncTask<String, String, String> {
		
		
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("send[email]", forgotPassword));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(GlobalVariables.url_forgot,"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				success = json.getString(TAG_SUCCESS);
				
				if (success.equals("true")) {
					apiToken = json.getString(TOKEN);
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
					
					
					//System.out.println("------"+failed+"-------");
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(responseToken==0){
				alertDilog();
			}else{
				//setupDataBase1();
				
			}
		}
		
		
	}
	
			
			void alertDilog(){
				
				// custom dialog
				final Context context = this;
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.dilog);
				dialog.setTitle("Log In");
				
	 
				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText("The username or password you entered is incorrect.");
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
			
			
			
	  private void setupDataBase(){
		    	
		  loginDataBaseAdapter.insertEntry(userEmail, userPassword);
		        
		       // Toast.makeText(getApplicationContext(), userEmail, Toast.LENGTH_SHORT).show();
		       
		    }
	  
	  
	  
	  @Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			
			loginDataBaseAdapter.close();
		}
	 
	
}
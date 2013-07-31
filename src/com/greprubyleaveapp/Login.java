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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity
{
	
	Button loginButton;
	TextView forgot,wrongInfo;
	EditText email,password;
	
	String userEmail ;
	String userPassword;
	String success;
	
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
		wrongInfo=(TextView)findViewById(R.id.wrong_info);
				
		
		loginButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
            	
        		userEmail = email.getText().toString();
    			userPassword = password.getText().toString();
    			
    			if(userEmail.equals("") || userPassword.equals("")){
    				//alertDilog();
    				wrongInfo.setText("The username or password you entered is incorrect.");
    			}else{
    				
    				// signup in background thread
    				new SigninData().execute();
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
				
				if (success.equals("true")) {
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
					
					System.out.println("------"+success+"-------");
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

	
		

	}
	
			@SuppressWarnings("deprecation")
			void alertDilog(){
				
				AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
				alertDialog.setTitle("Log in");
				alertDialog.setMessage("Blank user name or password");
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
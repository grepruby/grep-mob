package com.greprubyleaveapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity
{
	
	Button loginButton;
	TextView forgot;
	private static final String TAG = "Login";
    private static final int DLG_EXAMPLE1 = 0;
    private static final int TEXT_ID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		loginButton=(Button)findViewById(R.id.login);
		loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Intent i = new Intent(view.getContext(), ApplyOrCheckin.class); 	
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		forgot=(TextView)findViewById(R.id.forgot);
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
}
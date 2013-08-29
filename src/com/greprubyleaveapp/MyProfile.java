package com.greprubyleaveapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyProfile extends Activity {
    
	
	private static int RESULT_LOAD_IMAGE = 1;
	
	private String uName,uEmail;
	private ImageView back,signOut,editCancel,editSave;
	private TextView userName,userEmail,userPhone,userDob,localAddress,parmanentAddress,editDob;
	private LinearLayout editLayout1;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        
        
        userName = (TextView)findViewById(R.id.name);
        userEmail = (TextView)findViewById(R.id.email);
        userPhone = (TextView)findViewById(R.id.phone);
        userDob = (TextView)findViewById(R.id.dob);
        localAddress = (TextView)findViewById(R.id.l_address);
        parmanentAddress = (TextView)findViewById(R.id.p_address);
        editDob = (TextView)findViewById(R.id.edit_dob);
        editSave = (ImageView)findViewById(R.id.edit_save);
        editCancel = (ImageView)findViewById(R.id.edit_cancel);
        editLayout1 = (LinearLayout)findViewById(R.id.linear1);
        
        uName = BeanClass.getUserName();
		BeanClass.getApiToken();
		uEmail = BeanClass.getEmail();
		
		userName.setText(uName);
		userEmail.setText(uEmail);
		
        
        TextView buttonLoadImage = (TextView) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(i, RESULT_LOAD_IMAGE);
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
		
		 back=(ImageView)findViewById(R.id.back);
	        back.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	
	            		Intent i = new Intent(view.getContext(), ApplyOrCheckin.class); 
	    	        	startActivity(i);
	    	        	overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
	            	
	            	
		        	finish();
		        	
	            }
	           });
	        editDob.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	editDob.setVisibility(View.GONE);
	            	editLayout1.setVisibility(View.VISIBLE);
	        	
            }
           });
	        
	       editCancel.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	
	            	editLayout1.setVisibility(View.GONE);
	            	editDob.setVisibility(View.VISIBLE);
	        	
            }
           });
	       editSave.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	
	            	editLayout1.setVisibility(View.GONE);
	            	editDob.setVisibility(View.VISIBLE);
	        	
           }
          });
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			
			ImageView imageView = (ImageView) findViewById(R.id.imgView);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		
		}
    
    
    }
}
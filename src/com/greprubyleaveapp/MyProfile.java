package com.greprubyleaveapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProfile extends Activity {
    
	
	private static int RESULT_LOAD_IMAGE = 1;
	
	private String apiToken;
	private String uName;
	private ImageView back,signOut;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        
        Intent in = getIntent();
        uName  = in.getStringExtra("uName");
        apiToken  = in.getStringExtra("apiToken");
        
        
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
	            	
	            	Bundle pb = new Bundle();
	            	pb.putString("uName", uName);
	            	pb.putString("apiToken", apiToken);
	            	
	            	
	            		Intent i = new Intent(view.getContext(), ApplyOrCheckin.class); 	
	                	i.putExtras(pb);
	    	        	startActivity(i);
	    	        	overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
	            	
	            	
		        	finish();
		        	
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
package com.greprubyleaveapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyProfile extends Activity {
    
	
	private static int RESULT_LOAD_IMAGE = 1;
	
	private String uName,uEmail;
	private ImageView back,signOut,editCancel,editSave,editDob,editLocalAdd,editLaddressSave,editLaddressCancel,changeDobBtn,imgView;
	private TextView userName,userEmail,userPhone,userDob,localAddress;
	private EditText editAddress;
	private LinearLayout editLayout1,editLayout2,editLayout3;
	
	private String addressText;
	private int mYear;
	private int mMonth;
	private int mDay;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        
        
        userName = (TextView)findViewById(R.id.name);
        userEmail = (TextView)findViewById(R.id.email);
        userPhone = (TextView)findViewById(R.id.phone);
        userDob = (TextView)findViewById(R.id.dob);
        localAddress = (TextView)findViewById(R.id.l_address);
        editAddress = (EditText)findViewById(R.id.edit_address);
        editDob = (ImageView)findViewById(R.id.edit_dob);
        editSave = (ImageView)findViewById(R.id.edit_save);
        editCancel = (ImageView)findViewById(R.id.edit_cancel);
        editLocalAdd = (ImageView)findViewById(R.id.edit_local_add);
        editLaddressSave = (ImageView)findViewById(R.id.edit_laddress_save);
        editLaddressCancel = (ImageView)findViewById(R.id.edit_laddress_cancel);
        changeDobBtn = (ImageView)findViewById(R.id.change_dob_btn);
        editLayout1 = (LinearLayout)findViewById(R.id.linear1);
        editLayout2 = (LinearLayout)findViewById(R.id.linear2);
        editLayout3 = (LinearLayout)findViewById(R.id.linear3);
        
        
        uName = BeanClass.getUserName();
		BeanClass.getApiToken();
		uEmail = BeanClass.getEmail();
		
		userName.setText(uName);
		userEmail.setText(uEmail);
		
        
        TextView buttonLoadImage = (TextView) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
	       editLocalAdd.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	
	            	editLayout2.setVisibility(View.GONE);
	            	editLayout3.setVisibility(View.VISIBLE);
	        	
	            }
	       });
	       editLaddressSave.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	addressText=editAddress.getText().toString();
	            	localAddress.setText(addressText);
	            	editLayout2.setVisibility(View.VISIBLE);
	            	editLayout3.setVisibility(View.GONE);
	        	
	            }
	       });
	       editLaddressCancel.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	
	            	editLayout2.setVisibility(View.VISIBLE);
	            	editLayout3.setVisibility(View.GONE);
	        	
	            }
	       });
	       changeDobBtn.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	            	
	            	DatePickerDialog DPD = new DatePickerDialog(MyProfile.this, mDateSetListener, mYear, mMonth,mDay);
	                DPD.show();
	            }
	       });
	      
    }
    
    @Override
	@Deprecated
	protected void onPrepareDialog(int id, Dialog dialog) {
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog);

		((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);

	}
    
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			changeDate();
		}
	};
	private void changeDate() {
		// TODO Auto-generated method stub
		// Month is 0 based so add 1
		userDob.setText(new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear));
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